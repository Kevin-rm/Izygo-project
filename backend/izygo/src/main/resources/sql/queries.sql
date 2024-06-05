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
WITH RECURSIVE path_finding AS (
    SELECT lp.from_stop_id,
           lp.to_stop_id,
           ARRAY [lp.from_stop_id, lp.to_stop_id] AS path,
           lp.estimated_duration                  AS total_duration
    FROM line_path lp
    WHERE lp.from_stop_id = 1

    UNION ALL

    SELECT pf.from_stop_id,
           lp.to_stop_id,
           pf.path || lp.to_stop_id,
           pf.total_duration + lp.estimated_duration
    FROM line_path lp
             INNER JOIN
         path_finding pf ON lp.from_stop_id = pf.to_stop_id
    WHERE lp.to_stop_id <> ALL (pf.path)
)
SELECT path,
       total_duration
FROM path_finding
WHERE to_stop_id = 15
ORDER BY total_duration;
