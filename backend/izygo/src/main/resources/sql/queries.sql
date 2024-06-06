-- Affichage des bus avec leurs lignes respectives
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
    stop_ids INT[],
    stop_labels VARCHAR[],
    line_ids INT[],
    line_labels VARCHAR[],
    total_duration SMALLINT,
    line_transition_count INT
) AS $$
BEGIN
    RETURN QUERY
    WITH RECURSIVE route_search AS (
        SELECT lp.line_id,
               s_from.id                      AS from_stop_id,
               s_from.label                   AS from_stop_label,
               lp.to_stop_id,
               s_to.label                     AS to_stop_label,
               ARRAY[s_from.id]               AS stop_ids,
               ARRAY[s_from.label]::VARCHAR[] AS stop_labels,
               ARRAY[lp.line_id]              AS line_ids,
               ARRAY[l.label]::VARCHAR[]      AS line_labels,
               lp.estimated_duration          AS total_duration,
               0                              AS line_transition_count
        FROM line_path lp
                JOIN
            stop s_from ON lp.from_stop_id = s_from.id
                JOIN
            stop s_to ON lp.to_stop_id = s_to.id
                JOIN
            line l ON lp.line_id = l.id
        WHERE lp.from_stop_id = departure_stop

        UNION ALL

        SELECT lp.line_id,
               s_from.id    AS from_stop_id,
               s_from.label AS from_stop_label,
               lp.to_stop_id,
               s_to.label   AS to_stop_label,
               rs.stop_ids    || s_to.id,
               rs.stop_labels || s_to.label,
               rs.line_ids    || lp.line_id,
               rs.line_labels || l.label,
               rs.total_duration + lp.estimated_duration,
               CASE
                   WHEN lp.line_id <> rs.line_id THEN rs.line_transition_count + 1
                   ELSE rs.line_transition_count
               END AS num_escales
        FROM line_path lp
                JOIN
            stop s_from ON lp.from_stop_id = s_from.id
                JOIN
            stop s_to ON lp.to_stop_id = s_to.id
                JOIN
            line l ON lp.line_id = l.id
                INNER JOIN
            route_search rs ON lp.from_stop_id = rs.to_stop_id
        WHERE s_to.label <> ALL (rs.stop_labels) AND
              NOT (lp.line_id = ANY(rs.line_ids) AND lp.from_stop_id = rs.from_stop_id)
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
