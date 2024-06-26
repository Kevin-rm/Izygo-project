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
       ('Zoky','SSPR','696969699','masosomZoky lelike', 2),
       ('Tahiry Kevin', 'RAMAROZATOVO', '348510135', '123456', 3),
       ('Tiana', 'Rakotondrabe', '309124561', 'Zab456', 2),
       ('Lova', 'Razafindrakoto', '310123456', 'Cde567$', 2),
       ('Tiana', 'Rakotoarisoa', '311123456', 'Fgh678@', 2),
       ('Faniry', 'Rakotomalala', '312123456', 'Hij789#', 2),
       ('Tovo', 'Rakotoarivelo', '313123456', 'Klm890!', 2),
       ('Tahina', 'Razafimahatratra', '314123456', 'Nop901^', 2),
       ('Tantely', 'Rakotomanjato', '315123456', 'Qrs012$', 2),
       ('Fandresena', 'Rakotoarisoa', '316123456', 'Tuv123@', 2),
       ('Lova', 'Ranaivosoa', '317123456', 'Wxy234!', 2),
       ('Mandresy', 'Razafindrakoto', '318123486', 'Zab345^', 2),
       ('Tahina', 'Rakotoarisoa', '319144560', 'Cde456$', 2),
       ('Tahiana', 'Ratsimandresy', '340000456', 'Fgh567@', 2),
       ('Henintsoa', 'Razafindramanitra', '321123456', 'Hij678#', 2),
       ('Hasina', 'Rakotomalala', '322123456', 'Klm789!', 2),
       ('Tahiry', 'Razafindrakoto', '323123456', 'Nop890^', 2),
       ('Andrianaivo', 'Rakotoarisoa', '324823456', 'Qrs901$', 2),
       ('Tiana', 'Rakotoarimanana', '325123456', 'Tuv012@', 2),
       ('Fehizoro', 'Rakotomamonjy', '326187456', 'Wxy123#', 2),
       ('Faneva', 'Razakamanana', '327234562', 'Zab234!', 2),
       ('Faly', 'Raharison', '328122456', 'Cde345^', 2),
       ('Mamisoa', 'Rakotondrafara', '329122456', 'Fgh456$', 2),
       ('Mampionona', 'Rakotoarimanana', '330123465', 'Hij567@', 2),
       ('Tahiana', 'Rakotondrabe', '331123450', 'Klm678#', 2),
       ('Toky', 'Rakotoarisoa', '332123156', 'Nop789!', 2),
       ('Tovo', 'Razakamanana', '333133156', 'Qrs890@', 2),
       ('Fetra', 'Rakotomanga', '334123489', 'Tuv901#', 2),
       ('Tahina', 'Rakotoarisoa', '335123416', 'Wxy012$', 2),
       ('Mamisoa', 'Rakotoarisoa', '330003456', 'Zab123@', 2),
       ('Faharoa', 'Rasamison', '337121406', 'Cde234#', 2),
       ('Lanto', 'Rakotomanga', '338123056', 'Fgh345!', 2),
       ('Mamy', 'Rajaonarivelo', '339123756', 'Hij456^', 2),
       ('Liva', 'Rakotonirina', '340126756', 'Klm567$', 2),
       ('Tantely', 'Rakotomalala', '341153656', 'Nop678@', 2),
       ('Mampionona', 'Ravelomanantsoa', '332103406', 'Qrs789#', 2),
       ('Fehizoro', 'Raharison', '333121456', 'Tuv890!', 2),
       ('Fetra', 'Rakotondrafara', '344133356', 'Wxy901^', 2),
       ('Tsiky', 'Rajaobelina', '342120496', 'Zab012@', 2),
       ('Tahina', 'Razafimanjato', '346126456', 'Cde123#', 2),
       ('Fandresena', 'Rakotoarisoa', '347123451', 'Fgh234!', 2),
       ('Tahiry', 'Rasolonirina', '348183756', 'Hij345^', 2),
       ('Tiana', 'Rakotondrabe', '341124561', 'Klm456$', 2),
       ('Lova', 'Razafindrakoto', '340123406', 'Nop567@', 2),
       ('Tiana', 'Rakotoarisoa', '341123416', 'Qrs678#', 2),
       ('Faniry', 'Rakotomalala', '334213456', 'Tuv789!', 2),
       ('Tovo', 'Rakotoarivelo', '345120452', 'Wxy890^', 2),
       ('Tahina', 'Razafimahatratra', '346123456', 'Zab901@', 2),
       ('Tantely', 'Rakotomanjato', '347123126', 'Cde012!', 2),
       ('Fandresena', 'Rakotoarisoa', '348123416', 'Fgh123^', 2),
       ('Ryan', 'Tsanobe', '349929996', 'mot_de_passe', 2),
       ('Lolia', 'Finday', '339129496', 'hehehe', 2),
       ('Soprano', 'Jean', '329129496', 'hohoho', 2),
       ('Nathan', 'Nirina', '349829486', 'hahaha', 2),
       ('Andreas', 'Nirina', '349129433', 'huhuhu', 2),
       ('Rija', 'Ralahy', '345125495', 'hwhwhw', 2);

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
VALUES ('2024-06-26 07:45:00', 3, 12, 13, 4), -- Mahazo -> Ankadindramamy
       ('2024-06-26 07:45:00', 3, 14, 13, 3);

INSERT INTO reservation(date_time, user_id, bus_id, departure_stop_id, arrival_stop_id)
VALUES
    (CURRENT_TIMESTAMP, 1, 1, 1, 2),
    (CURRENT_TIMESTAMP, 2, 2, 1, 2),
    (CURRENT_TIMESTAMP, 3, 1, 1, 2);

INSERT INTO reservation_seat(reservation_id, seat_id)
VALUES
     (1, 2),
     (2, 4),
     (3, 4);

INSERT INTO reservation_seat (reservation_id,seat_id,is_active,on_bus,seat_price)
       VALUES
       (1,2,DEFAULT,DEFAULT,2500),
       (1,3,DEFAULT,DEFAULT,2500);

INSERT INTO reservation (date_time,user_id,bus_id,departure_stop_id,arrival_stop_id)
       VALUES('2024-06-26 08:00:00', 2, 1, 1, 9);

INSERT INTO reservation_seat (reservation_id,seat_id,is_active,on_bus,seat_price)
       VALUES
       (2,5,DEFAULT,DEFAULT,2500),
       (2,6,DEFAULT,DEFAULT,2500),
       (2,7,DEFAULT,DEFAULT,2500);
