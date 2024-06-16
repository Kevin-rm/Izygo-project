-- L'association entre les lignes et les arrêts
CREATE OR REPLACE VIEW v_line_stop AS
SELECT l.id    AS line_id,
       l.label AS line_label,
       s.id    AS stop_id,
       s.label AS stop_label,
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
       vls_1.is_terminus AS from_stop_is_terminus,
       lp.to_stop_id,
       vls_2.stop_label  AS to_stop_label,
       vls_2.is_terminus AS to_stop_is_terminus,
       lp.estimated_duration
FROM line_path lp
         JOIN
     v_line_stop vls_1 ON lp.line_id = vls_1.line_id AND lp.from_stop_id = vls_1.stop_id
         JOIN
     v_line_stop vls_2 ON lp.line_id = vls_2.line_id AND lp.to_stop_id = vls_2.stop_id;

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
    total_duration        SMALLINT,
    line_transition_count INT
) AS $$
BEGIN
    RETURN QUERY
    WITH RECURSIVE route_search AS (
        SELECT vlp.line_id,
               vlp.from_stop_id,
               vlp.to_stop_id,
               ARRAY[vlp.from_stop_id, vlp.to_stop_id]                  AS stop_ids,
               ARRAY[vlp.from_stop_label, vlp.to_stop_label]::VARCHAR[] AS stop_labels,
               ARRAY[vlp.line_id, vlp.line_id]                          AS line_ids,
               ARRAY[vlp.line_label, vlp.line_label]::VARCHAR[]         AS line_labels,

               vlp.estimated_duration                                   AS total_duration,
               0                                                        AS line_transition_count
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
           rs.total_duration,
           rs.line_transition_count
    FROM route_search rs
    WHERE rs.to_stop_id = arrival_stop
    ORDER BY rs.total_duration;
END;
$$ LANGUAGE plpgsql;

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

-- Réservation active
CREATE OR REPLACE VIEW v_reservation AS
SELECT r.id              AS id,
       rs.id             AS reservation_seat_id,
       r.user_id,
       u.firstname,
       u.lastname,
       r.bus_id,
       rs.seat_id,
       s.label           AS seat_label,
       vb.license_plate,
       vb.line_label,
       r.departure_stop,
       st_1.label        AS start_stop,
       r.arrival_stop,
       st_2.label        AS end_stop,
       rs.is_active
FROM reservation r
        JOIN
    reservation_seat rs ON r.id = rs.reservation_id
        JOIN
    "user" u ON r.user_id = u.id
        JOIN
    v_bus AS vb ON r.bus_id = vb.id
        JOIN
    seat AS s ON rs.seat_id = s.id
        JOIN
    stop st_1 ON r.departure_stop = st_1.id
        JOIN
    stop st_2 ON r.arrival_stop = st_2.id
        LEFT JOIN
    cancellation c ON rs.id = c.reservation_seat_id
WHERE rs.is_active = FALSE AND c.id IS NULL;

SELECT id,
       reservation_seat_id,
       firstname,
       lastname,
       license_plate,
       line_label,
       seat_label,
       start_stop,
       end_stop
FROM v_reservation;

-- RESERVATION ACTIF PAR BUS // EN FONCTION DES ARRETS ET RESERVATION
-- requete pour avec reserver des place à un arret
SELECT
    rs.seat_id,
    rs.user_id
FROM
    v_reservation AS rs
WHERE
    rs.start_stop_id <= 16 AND
    rs.end_stop_id >= 16 AND
    bus_id = 2
ORDER BY
    rs.user_id;

-- Soumetre que la reservation est bien pris 
update reservation_seat set is_active = TRUE where id = 1;

--mettre la reservation est utilisé
update reservation_seat set is_active = TRUE where id = 1;

--annuler la reservation
INSERT INTO cancellation (reservation_seat_id)
VALUES
    (3);

-- rechercher le bus suivant
CREATE OR REPLACE FUNCTION get_following_bus_id(p_bus_id BIGINT)
RETURNS BIGINT AS $$
DECLARE
    result_bus_id BIGINT;
BEGIN
    SELECT bp1.bus_id
    INTO result_bus_id
    FROM bus_position bp1
    JOIN bus_position bp2
        ON bp1.to_stop_id = bp2.current_stop_id
        AND bp1.current_stop_id != bp2.to_stop_id
    WHERE bp2.bus_id = p_bus_id;

    RETURN result_bus_id;
END;
$$ LANGUAGE plpgsql;

-- fonction pour rechercher le prochain utilisateur à qui envoyer une notification
CREATE OR REPLACE FUNCTION select_next_user_id(
    current_user_id BIGINT, 
    reference_departure_stop_id INT, 
    reference_arrival_stop_id INT,
    bus_to_follow_id BIGINT
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
        -- si oui, on retourne simplement le premier utilisateur de la pile
        SELECT r.user_id
        INTO next_user_id
        FROM reservation r
        JOIN reservation_seat rs ON r.id = rs.reservation_id
        WHERE   r.bus_id = get_following_bus_id(bus_to_follow_id)
        AND     r.departure_stop_id = reference_departure_stop_id
        AND     r.arrival_stop_id = reference_arrival_stop_id
        GROUP BY r.id, r.user_id
        HAVING COUNT(rs.id) = 1
        ORDER BY r.date_time ASC
        LIMIT 1;
    ELSE
        -- sinon, on procède à la recherche en décalant les lignes vers le haut
        WITH ordered_users AS (
            SELECT r.user_id,
                   LEAD(r.user_id) OVER (ORDER BY r.date_time ASC) AS next_user_id
            FROM reservation r
            JOIN reservation_seat rs ON r.id = rs.reservation_id
            WHERE   r.bus_id = get_following_bus_id(bus_to_follow_id)
            AND     r.departure_stop_id = reference_departure_stop_id
            AND     r.arrival_stop_id = reference_arrival_stop_id
            GROUP BY r.id, r.user_id, r.date_time
            HAVING COUNT(rs.id) = 1
            ORDER BY r.date_time ASC
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

-- insertion de notification
CREATE OR REPLACE FUNCTION insert_notification(
    p_user_id INT,
    p_message TEXT,
    bus_to_follow_id INT,
    p_seat_id INT,
    departure_stop INT, 
    arrival_stop INT
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

-- fonction d'envoi de la prochaine notification si pas de réaction
CREATE OR REPLACE FUNCTION check_and_trigger_next_notification(
    p_notification_id BIGINT,
    p_delay_interval INTERVAL,
    departure_stop INT,
    arrival_stop INT
) RETURNS VOID AS $$
DECLARE
    v_is_accepted BOOLEAN;
    v_next_user_id INT;
    v_message TEXT;
    v_bus_id INT;
    v_seat_id INT;
    v_user_id INT;
    v_notification_id INT;
BEGIN
    -- Sleep for the specified delay interval
    PERFORM pg_sleep(EXTRACT(EPOCH FROM p_delay_interval));

    RAISE INFO 'Finished sleep';

    -- Fetch the current status of the notification
    SELECT is_accepted, next_user_id, message, bus_id, seat_id, user_id 
    INTO v_is_accepted, v_next_user_id, v_message, v_bus_id, v_seat_id, v_user_id
    FROM notification
    WHERE id = p_notification_id;

    -- Check if the notification is still not accepted
    IF (v_is_accepted IS NULL OR v_is_accepted = FALSE) AND v_next_user_id IS NOT NULL THEN
        -- Get the next user ID using the select_next_user function
        v_user_id := v_next_user_id;

        -- Insert the next notification
        SELECT insert_notification(v_user_id, v_message, v_bus_id, v_seat_id, departure_stop, arrival_stop)
        INTO v_notification_id;

        PERFORM check_and_trigger_next_notification(v_notification_id, p_delay_interval, departure_stop, arrival_stop);
    END IF;
END;
$$ LANGUAGE plpgsql;

-- SELECT insert_notification(4, 'Hehe', 2, 4, 1, 2);

-- DO $$
-- BEGIN
--     PERFORM check_and_trigger_next_notification(
--         9, 
--         INTERVAL '10 seconds', 
--         1, 
--         2
--     );
-- END $$;