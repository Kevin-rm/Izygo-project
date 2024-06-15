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

-- Recherche de bus
WITH reserved_seats AS (
    SELECT COUNT(seat_id) AS count
    FROM v_active_reservation
    WHERE departure_stop_id <= 2 AND
          arrival_stop_id > 2    AND
          bus_id = 1
)
SELECT b.*
FROM bus_position bs
        JOIN
     bus b ON bs.bus_id = b.id
WHERE bs.line_id = ?         AND
      bs.current_stop_id = ? AND
      bs.to_stop_id = ?      AND
      bs.date_time_passage::TIME BETWEEN ? AND ? AND
      b.number_of_seats - reserved_seats.count > 0;

-- Liste des réservations actives
CREATE OR REPLACE VIEW v_active_reservation AS
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
       r.departure_stop_id,
       st_1.label        AS departure_stop_label,
       r.arrival_stop_id,
       st_2.label        AS arrival_stop_label,
       rs.on_bus
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
     stop st_1 ON r.departure_stop_id = st_1.id
         JOIN
     stop st_2 ON r.arrival_stop_id = st_2.id
         LEFT JOIN
     cancellation c ON rs.id = c.reservation_seat_id
WHERE rs.is_active = TRUE AND c.id IS NULL;

-- RESERVATION ACTIF PAR BUS // EN FONCTION DES ARRETS ET RESERVATION
-- requete pour avec reserver des place à un arret
SELECT
    count(rs.seat_id) as reserved_seat
FROM
    v_active_reservation AS rs
WHERE
        rs.departure_stop_id <= 2 AND
        rs.arrival_stop_id > 2 AND
        bus_id = 1;

--mettre la reservation est utilisé
update reservation_seat set is_active = TRUE where id = 1;

--annuler la reservation
INSERT INTO cancellation (reservation_seat_id)
VALUES
    (3);
