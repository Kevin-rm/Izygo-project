CREATE OR REPLACE VIEW v_list_Admin AS 
SELECT phone_Number 
FROM "user" 
WHERE "role_id" = 2;

----------------par id reservation (liste siege an reservation ray)
--------------par id user ,liste reservation (user_id,resv_id,)
SELECT 
    r.user_id AS user_id,
    r.id AS reservation_id,
    r.date_time AS reservation_date,
    COUNT(rs.seat_id) AS number_of_seats,
    l.label AS bus_line
FROM 
    reservation r
JOIN 
    bus b ON r.bus_id = b.id
JOIN 
    "line" l ON b.line_id = l.id
JOIN 
    reservation_seat rs ON r.id = rs.reservation_id
GROUP BY 
    r.id, r.date_time, l.label;





