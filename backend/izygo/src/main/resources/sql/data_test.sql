-- Places
INSERT INTO seat (label)
VALUES ('1A'),
       ('2A'),('2B'),('2C'),
       ('3A'),('3B'),
       ('4A'),('4B'),('4C'),
       ('5A'),('5B'),('5C'),
       ('6A'),('6B'),('6C'),
       ('7A'),('7B'),('7C'),('7D');

-- Rôles
INSERT INTO roles (type)
VALUES ('user'),
       ('kiosk'),
       ('admin');

-- Lignes
INSERT INTO line (label)
VALUES ('Ligne A'),
       ('Ligne B'),
       ('Ligne C');

-- Arrêts
INSERT INTO stop (label, latitude, longitude)
VALUES ('Andoharanofotsy', -18.98590, 47.53278),
       ('Malaza', -18.97313, 47.53128),
       ('Magasin M-', -18.96337, 47.52950),
       ('Tanjombato', -18.95937, 47.52707);

INSERT INTO stop (label, latitude, longitude)
VALUES
       ('Fasika', -18.94743, 47.52652),
       ('Ankadimbahoaka', -18.94431, 47.524332),
       ('Descours', -18.93523, 47.52195),
       ('Paraky', -18.93153, 47.52017),
       ('Toby', -18.923620, 47.520641),
       ('Anosy', -18.917992, 47.522011),

       ('Ambatomaro', -18.90203, 47.56178),
       ('Mahazo', -18.89545, 47.56028),
       ('Ankadindramamy', -18.89242, 47.56032),
       ('Pain de Tana', -18.89375, 47.54982),
       ('Meteo', -18.89712, 47.54618),
       ('Avaradoha', -18.90058, 47.54503),
       ('Betongolo', -18.90684, 47.54153),
       ('Antsakaviro', -18.91254, 47.5366),
       ('Ambohijatovo', -18.91407, 47.53054),
       ('Ampefiloha', -18.9127, 47.5163),
       ('67ha', -18.9028, 47.5092),
       ('Antanimena', -18.89792, 47.51946),
       ('Ankadifotsy', -18.89787, 47.52557),
       ('Andravoahangy ambony', -18.90218, 47.5303),
       ('Besarety', -18.90213, 47.53528),

       ('Tsena Andravoahangy', -18.8951, 47.5306),
       ('Ambany Tetezana - Behoririka', -18.90196, 47.52864),
       ('Soarano', -18.9042, 47.52169);

-- Utilisateurs
INSERT INTO "user" (firstname, lastname, phone_number, password, role_id)
VALUES ('Ny Ony','RAMAVO','341610025','12345678910', 1),
       ('Fanantenana','HARINAIVO','342500116','10987654321', 1),
       ('Sariaka','RAKOTODRANIVO','341245567','azertyuiop', 2),
       ('Tahiry Kevin', 'RAMAROZATOVO', '348510135', '123456', 3);


-- Ligne et arrêts
INSERT INTO line_stop (line_id, stop_id, employee_id, is_terminus)
VALUES
       -- Ligne A
       (1, 1,3, TRUE),
       (1, 2, 3, DEFAULT),
       (1, 3, 3, DEFAULT),
       (1, 4, 3, DEFAULT),
       (1, 5, 3, DEFAULT),
       (1, 6, 3, DEFAULT),
       (1, 7, 3, DEFAULT),
       (1, 8, 3, DEFAULT),
       (1, 9, 3, DEFAULT),
       (1, 10, 3, TRUE),

       -- Ligne B
       (2, 11, 3, TRUE),
       (2, 12, 3, DEFAULT),
       (2, 13, 3, DEFAULT),
       (2, 14, 3, DEFAULT),
       (2, 15, 3, DEFAULT),
       (2, 16, 3, DEFAULT),
       (2, 17, 3, DEFAULT),
       (2, 18, 3, DEFAULT),
       (2, 19, 3, DEFAULT),
       (2, 10, 3, DEFAULT),
       (2, 20, 3, DEFAULT),
       (2, 21, 3, DEFAULT),
       (2, 22, 3, DEFAULT),
       (2, 23, 3, DEFAULT),
       (2, 24, 3, DEFAULT),
       (2, 25, 3, DEFAULT),

       -- Ligne C
       (3, 12, 3, TRUE),
       (3, 13, 3, DEFAULT),
       (3, 14, 3, DEFAULT),
       (3, 15, 3, DEFAULT),
       (3, 25, 3, DEFAULT),
       (3, 26, 3, DEFAULT),
       (3, 27, 3, DEFAULT),
       (3, 28, 3, TRUE);

-- Route
INSERT INTO line_path (line_id, from_stop_id, to_stop_id, estimated_duration)
VALUES
       -- Ligne A
       (1, 1, 2, 15),
       (1, 2, 3, 15),
       (1, 3, 4, 15),
       (1, 4, 5, 15),
       (1, 5, 6, 15),
       (1, 6, 7, 15),
       (1, 7, 8, 15),
       (1, 8, 9, 15),
       (1, 9, 10, 10),
       (1, 10, 9, 10),
       (1, 9, 8, 15),
       (1, 8, 7, 15),
       (1, 7, 6, 15),
       (1, 6, 5, 15),
       (1, 5, 4, 15),
       (1, 4, 3, 15),
       (1, 3, 2, 15),
       (1, 2, 1, 15),

       -- Ligne B
       (2, 11, 12, 15),
       (2, 12, 13, 15),
       (2, 13, 14, 15),
       (2, 14, 15, 15),
       (2, 15, 16, 15),
       (2, 16, 17, 15),
       (2, 17, 18, 15),
       (2, 18, 19, 15),
       (2, 19, 10, 15),
       (2, 10, 20, 15),
       (2, 20, 21, 15),
       (2, 21, 22, 15),
       (2, 22, 23, 15),
       (2, 23, 24, 15),
       (2, 24, 25, 15),
       (2, 25, 16, 15),
       (2, 16, 15, 15),
       (2, 15, 14, 15),
       (2, 14, 13, 15),
       (2, 13, 12, 15),
       (2, 12, 11, 15),

       -- Ligne C
       (3, 12, 13, 15),
       (3, 13, 14, 15),
       (3, 14, 15, 15),
       (3, 15, 25, 15),
       (3, 25, 26, 15),
       (3, 26, 27, 15),
       (3, 27, 28, 15),
       (3, 28, 27, 15),
       (3, 27, 26, 15),
       (3, 26, 25, 15),
       (3, 25, 15, 15),
       (3, 15, 14, 15),
       (3, 14, 13, 15),
       (3, 13, 12, 15);

-- Bus
INSERT INTO bus (license_plate, number_of_seats, line_id)
VALUES ('1234 TAA', 19, 1),
       ('9876 TAB', 19, 2),
       ('9812 TAN', 19, 3),
       ('9834 TAE', 19, 3);

-- Données fictives pour bus_position
INSERT INTO bus_position(date_time_passage, line_id, current_stop_id, to_stop_id, bus_id)
VALUES ('2024-06-27 07:45:00', 3, 13, 14, 4), -- Ankadindramamy -> Pain
       ('2024-06-27 07:45:00', 3, 14, 15, 3); -- Pain -> MEteo

INSERT INTO reservation(date_time, user_id, bus_id, departure_stop_id, arrival_stop_id)
VALUES
    (CURRENT_TIMESTAMP, 1, 3, 16, 18),
    (CURRENT_TIMESTAMP, 2, 4, 16, 18),
    (CURRENT_TIMESTAMP, 3, 4, 16, 18);


INSERT INTO reservation_seat (reservation_id,seat_id,is_active,on_bus,seat_price)
       VALUES
       (1,2,DEFAULT,DEFAULT,2500),
       (3,4,DEFAULT,DEFAULT,2500),
       (2,3,DEFAULT,DEFAULT,2500);
