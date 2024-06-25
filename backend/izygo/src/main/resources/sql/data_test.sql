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
VALUES ('client'),
       ('kiosk');

-- Lignes
INSERT INTO line (label)
VALUES ('Ligne A'),
       ('Ligne B'),
       ('Ligne C');

-- Arrêts
INSERT INTO stop (label, latitude, longitude)
VALUES ('Andoharanofotsy', -18.98590849348459, 47.53278160679259),
       ('Malaza', -18.97313948326775, 47.5312824255956),
       ('Magasin M-', -18.963374586594735, 47.52950146874784),
       ('Tanjombato', -18.95937526511856, 47.52707502784919);

INSERT INTO stop (label)
VALUES
       ('Fasika'),
       ('Ankadimbahoaka'),
       ('Descours'),
       ('Paraky'),
       ('Toby'),
       ('Anosy'),

       ('Ambatomaro'),
       ('Mahazo'),
       ('Ankadindramamy'),
       ('Pain de Tana'),
       ('Meteo'),
       ('Avaradoha'),
       ('Betongolo'),
       ('Antsakaviro'),
       ('Ambohijatovo'),
       ('Ampefiloha'),
       ('67ha'),
       ('Antanimena'),
       ('Ankadifotsy'),
       ('Andravoahangy ambony'),
       ('Besarety'),

       ('Tsena Andravoahangy'),
       ('Ambany Tetezana - Behoririka'),
       ('Soarano');

-- Utilisateurs
INSERT INTO "user" (firstname, lastname, phone_number, password, role_id)
VALUES ('Ny Ony','RAMAVO','341610025','12345678910', 1),
       ('Fanantenana','HARINAIVO','342500116','10987654321', 1),
       ('Sariaka','RAKOTODRANIVO','341245567','azertyuiop', 1),
       ('Zoky','SSPR','696969699','masosomZoky lelike', 2);

-- Ligne et arrêts
INSERT INTO line_stop (line_id, stop_id, employee_id, is_terminus)
VALUES
       -- Ligne A
       (1, 1, 4, TRUE),
       (1, 2, 4, DEFAULT),
       (1, 3, 4, DEFAULT),
       (1, 4, 4, DEFAULT),
       (1, 5, 4, DEFAULT),
       (1, 6, 4, DEFAULT),
       (1, 7, 4, DEFAULT),
       (1, 8, 4, DEFAULT),
       (1, 9, 4, DEFAULT),
       (1, 10, 4, TRUE),

       -- Ligne B
       (2, 11, 4, TRUE),
       (2, 12, 4, DEFAULT),
       (2, 13, 4, DEFAULT),
       (2, 14, 4, DEFAULT),
       (2, 15, 4, DEFAULT),
       (2, 16, 4, DEFAULT),
       (2, 17, 4, DEFAULT),
       (2, 18, 4, DEFAULT),
       (2, 19, 4, DEFAULT),
       (2, 10, 4, DEFAULT),
       (2, 20, 4, DEFAULT),
       (2, 21, 4, DEFAULT),
       (2, 22, 4, DEFAULT),
       (2, 23, 4, DEFAULT),
       (2, 24, 4, DEFAULT),
       (2, 25, 4, DEFAULT),

       -- Ligne C
       (3, 12, 4, TRUE),
       (3, 13, 4, DEFAULT),
       (3, 14, 4, DEFAULT),
       (3, 15, 4, DEFAULT),
       (3, 25, 4, DEFAULT),
       (3, 26, 4, DEFAULT),
       (3, 27, 4, DEFAULT),
       (3, 28, 4, TRUE);

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
VALUES ('2024-06-16 07:45:00', 3, 12, 13, 4), -- Mahazo -> Ankadindramamy
       ('2024-06-16 07:45:00', 3, 14, 13, 3);
