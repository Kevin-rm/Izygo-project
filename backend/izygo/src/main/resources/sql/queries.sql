-- Affichage des bus avec leurs lignes respectives
SELECT
    b.id,
    b.license_plate,
    b.number_of_seats,
    b.line_id,
    l.label AS line_label
FROM
    bus b
        JOIN
    line l ON l.id = b.line_id;