-- Affichage des bus avec leurs lignes respectives
CREATE OR REPLACE VIEW v_bus AS
SELECT b.id,
       b.license_plate,
       b.number_of_seats,
       b.line_id,
       l.label AS line_label
FROM bus b
         JOIN
     line l ON l.id = b.line_id;

-- L'association entre les lignes et les arrêts
CREATE OR REPLACE VIEW v_line_stop AS
SELECT l.id    AS line_id,
       l.label AS line_label,
       s.id    AS stop_id,
       s.label AS stop_label,
       s.latitude,
       s.longitude,
       ls.is_terminus
FROM line_stop ls
         JOIN
     line l ON ls.line_id = l.id
         JOIN
     stop s ON ls.stop_id = s.id
ORDER BY l.id;

-- Meilleur affichage du trajet de chaque ligne mais pas en ordre
CREATE OR REPLACE VIEW v_line_path AS
SELECT lp.line_id,
       vls_1.line_label,
       lp.from_stop_id,
       vls_1.stop_label  AS from_stop_label,
       vls_1.latitude    AS from_stop_latitude,
       vls_1.longitude   AS from_stop_longitude,
       vls_1.is_terminus AS from_stop_is_terminus,
       lp.to_stop_id,
       vls_2.stop_label  AS to_stop_label,
       vls_2.latitude    AS to_stop_latitude,
       vls_2.longitude   AS to_stop_longitude,
       vls_2.is_terminus AS to_stop_is_terminus,
       lp.estimated_duration
FROM line_path lp
         JOIN
     v_line_stop vls_1 ON lp.line_id = vls_1.line_id AND lp.from_stop_id = vls_1.stop_id
         JOIN
     v_line_stop vls_2 ON lp.line_id = vls_2.line_id AND lp.to_stop_id = vls_2.stop_id;

/*
 * Permet de trouver en ordre le trajet d'une ligne de bus
 */
CREATE OR REPLACE FUNCTION get_ordered_line_path(p_line_id INT)
RETURNS TABLE (
    path           VARCHAR[],
    total_duration INT
) AS $$
DECLARE
    start_stop_id INT;
BEGIN
    SELECT stop_id
    INTO start_stop_id
    FROM v_line_stop
    WHERE line_id = p_line_id AND is_terminus = TRUE
    LIMIT 1;

    RETURN QUERY
    WITH RECURSIVE ordered_path AS (
        SELECT vlp.line_id,
               vlp.from_stop_id,
               vlp.to_stop_id,
               vlp.estimated_duration::INT                              AS total_duration,
               ARRAY[vlp.from_stop_label, vlp.to_stop_label]::VARCHAR[] AS path
        FROM v_line_path vlp
        WHERE vlp.from_stop_id = start_stop_id

        UNION ALL

        SELECT vlp.line_id,
               vlp.from_stop_id,
               vlp.to_stop_id,
               op.total_duration + vlp.estimated_duration,
               op.path || vlp.to_stop_label
        FROM ordered_path op
                JOIN
             v_line_path vlp ON
                op.line_id = vlp.line_id         AND
                op.to_stop_id = vlp.from_stop_id AND (
                    (op.from_stop_id = vlp.to_stop_id AND vlp.from_stop_is_terminus) OR
                    (op.from_stop_id != vlp.to_stop_id)
                )
        WHERE op.to_stop_id != start_stop_id
    )
    SELECT op.path, op.total_duration::INT
    FROM ordered_path op
    ORDER BY array_length(op.path, 1) DESC
    LIMIT 1;
END;
$$ LANGUAGE plpgsql;

/*
 * Permet de retrouver tous les itinéraires possibles en donnant
 * l'arrêt de départ et l'arrêt d'arrivée
 */
CREATE OR REPLACE FUNCTION find_route(departure_stop INT, arrival_stop INT)
RETURNS TABLE(
    stop_ids              INT[],
    stop_labels           VARCHAR[],
    line_ids              INT[],
    line_labels           VARCHAR[],
    stop_latitudes        DECIMAL[],
    stop_longitudes       DECIMAL[],
    total_duration        SMALLINT,
    line_transition_count INT
) AS $$
BEGIN
    RETURN QUERY
    WITH RECURSIVE route_search AS (
        SELECT vlp.line_id,
               vlp.from_stop_id,
               vlp.to_stop_id,
               ARRAY[vlp.from_stop_id, vlp.to_stop_id]                             AS stop_ids,
               ARRAY[vlp.from_stop_label, vlp.to_stop_label]::VARCHAR[]            AS stop_labels,
               ARRAY[vlp.line_id, vlp.line_id]                                     AS line_ids,
               ARRAY[vlp.line_label, vlp.line_label]::VARCHAR[]                    AS line_labels,
               ARRAY[vlp.from_stop_latitude, vlp.to_stop_latitude]::DECIMAL[]      AS stop_latitudes,
               ARRAY[vlp.from_stop_longitude, vlp.to_stop_longitude]::DECIMAL[]    AS stop_longitudes,
               vlp.estimated_duration                                              AS total_duration,
               0                                                                   AS line_transition_count
        FROM v_line_path vlp
        WHERE vlp.from_stop_id = departure_stop

        UNION ALL

        SELECT vlp.line_id,
               vlp.from_stop_id,
               vlp.to_stop_id,
               rs.stop_ids    || vlp.to_stop_id,
               rs.stop_labels || vlp.to_stop_label,
               rs.line_ids    || vlp.line_id,
               rs.line_labels || vlp.line_label,
               rs.stop_latitudes  || vlp.to_stop_latitude,
               rs.stop_longitudes || vlp.to_stop_longitude,
               rs.total_duration + vlp.estimated_duration,
               CASE
                    WHEN vlp.line_id <> rs.line_id THEN rs.line_transition_count + 1
                    ELSE rs.line_transition_count
               END AS line_transition_count
        FROM v_line_path vlp
                INNER JOIN
            route_search rs ON vlp.from_stop_id = rs.to_stop_id
        WHERE vlp.to_stop_id <> ALL (rs.stop_ids)
    )
    SELECT rs.stop_ids,
           rs.stop_labels,
           rs.line_ids,
           rs.line_labels,
           rs.stop_latitudes,
           rs.stop_longitudes,
           rs.total_duration,
           rs.line_transition_count
    FROM route_search rs
    WHERE rs.to_stop_id = arrival_stop
    ORDER BY rs.line_transition_count, rs.total_duration
    LIMIT 5;
END;
$$ LANGUAGE plpgsql;

/*
 * Recherche du ou des bus qui vont(va) arriver à l'arrêt de départ choisi
 * dans un intervalle de temps donné
 */
CREATE OR REPLACE FUNCTION find_future_arriving_buses(p_stop_id INT, p_date_time_1 TIMESTAMP, p_date_time_2 TIMESTAMP, p_margin INTERVAL)
RETURNS TABLE (
    bus_id            BIGINT,
    line_id           INT,
    stop_id           INT,
    date_time_passage TIMESTAMP
) AS $$
BEGIN
    p_date_time_1 := p_date_time_1 - p_margin;
    p_date_time_2 := p_date_time_2 + p_margin;

    RETURN QUERY
        WITH RECURSIVE bus_position_prediction AS (
            SELECT bp.bus_id,
                   bp.line_id,
                   bp.current_stop_id,
                   bp.to_stop_id,
                   bp.date_time_passage,
                   vlp.estimated_duration
            FROM bus_position bp
                     JOIN
                 v_line_path vlp
                 ON bp.line_id = vlp.line_id              AND
                    bp.current_stop_id = vlp.from_stop_id AND
                    bp.to_stop_id = vlp.to_stop_id

            UNION ALL

            SELECT bpp.bus_id,
                   bpp.line_id,
                   bpp.to_stop_id AS current_stop_id,
                   vlp.to_stop_id,
                   bpp.date_time_passage + INTERVAL '1 minute' * vlp.estimated_duration AS date_time_passage,
                   vlp.estimated_duration
            FROM bus_position_prediction bpp
                     JOIN
                 v_line_path vlp ON
                    bpp.line_id = vlp.line_id         AND
                    bpp.to_stop_id = vlp.from_stop_id AND (
                        (bpp.current_stop_id = vlp.to_stop_id AND vlp.from_stop_is_terminus) OR
                        (bpp.current_stop_id != vlp.to_stop_id)
                    )
            WHERE bpp.date_time_passage + INTERVAL '1 minute' * vlp.estimated_duration < p_date_time_2
        )
        SELECT bpp.bus_id,
               bpp.line_id,
               p_stop_id,
               bpp.date_time_passage
        FROM bus_position_prediction bpp
        WHERE bpp.current_stop_id = p_stop_id AND
              bpp.date_time_passage BETWEEN p_date_time_1 AND p_date_time_2;
END
$$ LANGUAGE plpgsql;

-- Rechercher le bus suivant
CREATE OR REPLACE FUNCTION get_following_bus_id(p_bus_id BIGINT)
    RETURNS BIGINT AS $$
DECLARE
    result_bus_id BIGINT;
BEGIN
    SELECT bp1.bus_id
    INTO result_bus_id
    FROM bus_position bp1
             JOIN
        bus_position bp2 ON
            bp1.to_stop_id = bp2.current_stop_id  AND
            bp1.current_stop_id != bp2.to_stop_id AND
            bp1.line_id = bp2.line_id
    WHERE bp2.bus_id = p_bus_id;

    RETURN result_bus_id;
END;
$$ LANGUAGE plpgsql;

-- Fonction pour rechercher le prochain utilisateur à qui envoyer une notification
CREATE OR REPLACE FUNCTION select_next_user_id(
    current_user_id             BIGINT,
    reference_departure_stop_id INT,
    reference_arrival_stop_id   INT,
    bus_to_follow_id            BIGINT
) RETURNS BIGINT AS $$
DECLARE
    next_user_id BIGINT;
BEGIN
    -- Check s'il s'agit d'un kiosk
    IF current_user_id IN (
        SELECT id
        FROM "user"
        WHERE role_id = (
            SELECT id
            FROM roles
            WHERE type = 'kiosk'
        )
    ) THEN
        -- Si oui, on retourne simplement le premier utilisateur de la pile
        SELECT r.user_id
        INTO next_user_id
        FROM reservation r
                 JOIN
             reservation_seat rs ON r.id = rs.reservation_id
        WHERE r.bus_id = get_following_bus_id(bus_to_follow_id) AND
              r.departure_stop_id = reference_departure_stop_id          AND
              r.arrival_stop_id = reference_arrival_stop_id
        GROUP BY r.id, r.user_id
        HAVING COUNT(rs.id) = 1
        ORDER BY r.date_time
        LIMIT 1;
    ELSE
        -- Sinon, on procède à la recherche en décalant les lignes vers le haut
        WITH ordered_users AS (
            SELECT r.user_id,
                   LEAD(r.user_id) OVER (ORDER BY r.date_time) AS next_user_id
            FROM reservation r
                     JOIN
                reservation_seat rs ON r.id = rs.reservation_id
            WHERE r.bus_id = get_following_bus_id(bus_to_follow_id) AND
                  r.departure_stop_id = reference_departure_stop_id          AND
                  r.arrival_stop_id = reference_arrival_stop_id
            GROUP BY r.id, r.user_id, r.date_time
            HAVING COUNT(rs.id) = 1
            ORDER BY r.date_time
            LIMIT 3
        )
        SELECT ou.next_user_id
        INTO next_user_id
        FROM ordered_users ou
        WHERE ou.user_id = current_user_id;
    END IF;

    RETURN next_user_id;
END;
$$ LANGUAGE plpgsql;

-- Insertion de notification
CREATE OR REPLACE FUNCTION insert_notification(
    p_user_id        BIGINT,
    p_message        TEXT,
    bus_to_follow_id BIGINT,
    p_seat_id        SMALLINT,
    departure_stop   INT,
    arrival_stop     INT
) RETURNS BIGINT AS $$
DECLARE
    new_id BIGINT;
BEGIN
    INSERT INTO notification (user_id, next_user_id, bus_id, seat_id, message, sent_at)
    VALUES (p_user_id, select_next_user_id(p_user_id, departure_stop, arrival_stop, bus_to_follow_id), bus_to_follow_id, p_seat_id, p_message, CURRENT_TIMESTAMP)
    RETURNING id INTO new_id;
    RETURN new_id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION notification_delay(
    departure_stop_id INT,
    bus_to_follow_id BIGINT
) RETURNS BIGINT AS $$
DECLARE
    v_current_position INT;
    v_total_duration SMALLINT;
    individual_duration BIGINT;
BEGIN
    SELECT current_stop_id
    INTO v_current_position
    FROM bus_position
    WHERE bus_id = bus_to_follow_id;

    SELECT total_duration
    INTO v_total_duration
    FROM find_route(v_current_position, departure_stop_id);

    individual_duration := v_total_duration * 60000 / (2 * 4); -- Conversion directe en millisecondes

    return individual_duration::BIGINT;
END;
$$ LANGUAGE plpgsql;
