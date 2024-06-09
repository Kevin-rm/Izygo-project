-- view list_admin

CREATE OR REPLACE VIEW v_list_Admin AS 
SELECT phone_Number 
FROM "user" 
WHERE "role_id" = 2;

--view list reservation par user_id
CREATE OR REPLACE VIEW v_list_reservation AS
SELECT 
    r.user_id AS user_id,
    r.id AS reservation_id,
    r.date_time AS reservation_date,
    COUNT(rs.seat_id) AS number_of_seats,
    l.label AS bus_line,
    r.bus_id AS bus_id
FROM 
    reservation r
JOIN 
    bus b ON r.bus_id = b.id
JOIN 
    "line" l ON b.line_id = l.id
JOIN 
    reservation_seat rs ON r.id = rs.reservation_id
GROUP BY 
    r.user_id, r.id, r.date_time, l.label;   

--view liste seat pour chaque reservation /user_id (reservation active)  
CREATE OR REPLACE VIEW v_list_reservation_seat AS
SELECT
    vr.user_id AS user_id,
    vr.reservation_id AS reservation_id,
    vr.reservation_date AS reservation_date,
    vr.bus_line AS bus_line,
    s.label AS seat_label
FROM 
    v_list_reservation vr
JOIN 
    bus b ON vr.bus_id = b.id
JOIN
    reservation_seat revs ON revs.reservation_id = vr.reservation_id  
JOIN 
    seat s ON revs.seat_id = s.id          
GROUP BY 
    vr.user_id, vr.reservation_id, vr.reservation_date, vr.bus_line, s.label, revs.seat_id;

     









