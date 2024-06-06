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

CREATE OR REPLACE FUNCTION find_path(arretDepart INT, arretArrive INT)
RETURNS TABLE(
    stop_ids INT[],
    stop_labels VARCHAR[],
    line_ids INT[],
    line_labels VARCHAR[],
    total_duration SMALLINT,
    num_escales INT
) AS $$
BEGIN
    RETURN QUERY
    WITH RECURSIVE path_finding AS (
        SELECT 
            lp.line_id,
            s_from.id AS from_stop_id,
            s_from.label AS from_stop_label,
            lp.to_stop_id,
            s_to.label AS to_stop_label,
            ARRAY[s_from.id] AS stop_ids,
            ARRAY[s_from.label]::VARCHAR[] AS stop_labels,
            ARRAY[lp.line_id] AS line_ids,
            ARRAY[l.label]::VARCHAR[] AS line_labels,
            lp.estimated_duration AS total_duration,
            0 AS num_escales
        FROM line_path lp
        JOIN stop s_from ON lp.from_stop_id = s_from.id
        JOIN stop s_to ON lp.to_stop_id = s_to.id
        JOIN line l ON lp.line_id = l.id
        WHERE lp.from_stop_id = arretDepart

        UNION ALL

        SELECT 
            lp.line_id,
            s_from.id AS from_stop_id,
            s_from.label AS from_stop_label,
            lp.to_stop_id,
            s_to.label AS to_stop_label,
            pf.stop_ids || s_to.id,
            pf.stop_labels || s_to.label,
            pf.line_ids || lp.line_id,
            pf.line_labels || l.label,
            pf.total_duration + lp.estimated_duration,
            CASE WHEN lp.line_id <> pf.line_id THEN pf.num_escales + 1 ELSE pf.num_escales END AS num_escales
        FROM line_path lp
        JOIN stop s_from ON lp.from_stop_id = s_from.id
        JOIN stop s_to ON lp.to_stop_id = s_to.id
        JOIN line l ON lp.line_id = l.id
        INNER JOIN path_finding pf ON lp.from_stop_id = pf.to_stop_id
        WHERE s_to.label <> ALL (pf.stop_labels)
          AND NOT (lp.line_id = ANY(pf.line_ids) AND lp.from_stop_id = pf.from_stop_id)
    )
    SELECT 
        pf.stop_ids,
        pf.stop_labels,
        pf.line_ids,
        pf.line_labels,
        pf.total_duration,
        pf.num_escales
    FROM path_finding pf
    WHERE pf.to_stop_id = arretArrive
    ORDER BY pf.total_duration;
END;
$$ LANGUAGE plpgsql;
