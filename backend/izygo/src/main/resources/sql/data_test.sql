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
       ('Tiana', 'Rakotondrabe', '309124561', 'Zab456', 1),
       ('Lova', 'Razafindrakoto', '310123456', 'Cde567$', 1),
       ('Tiana', 'Rakotoarisoa', '311123456', 'Fgh678@', 1),
       ('Faniry', 'Rakotomalala', '312123456', 'Hij789#', 1),
       ('Tovo', 'Rakotoarivelo', '313123456', 'Klm890!', 1),
       ('Tahina', 'Razafimahatratra', '314123456', 'Nop901^', 1),
       ('Tantely', 'Rakotomanjato', '315123456', 'Qrs012$', 1),
       ('Fandresena', 'Rakotoarisoa', '316123456', 'Tuv123@', 1),
       ('Lova', 'Ranaivosoa', '317123456', 'Wxy234!', 1),
       ('Mandresy', 'Razafindrakoto', '318123486', 'Zab345^', 1),
       ('Tahina', 'Rakotoarisoa', '319144560', 'Cde456$', 1),
       ('Tahiana', 'Ratsimandresy', '340000456', 'Fgh567@', 1),
       ('Henintsoa', 'Razafindramanitra', '321123456', 'Hij678#', 1),
       ('Hasina', 'Rakotomalala', '322123456', 'Klm789!', 1),
       ('Tahiry', 'Razafindrakoto', '323123456', 'Nop890^', 1),
       ('Andrianaivo', 'Rakotoarisoa', '324823456', 'Qrs901$', 1),
       ('Tiana', 'Rakotoarimanana', '325123456', 'Tuv012@', 1),
       ('Fehizoro', 'Rakotomamonjy', '326187456', 'Wxy123#', 1),
       ('Faneva', 'Razakamanana', '327234562', 'Zab234!', 1),
       ('Faly', 'Raharison', '328122456', 'Cde345^', 1),
       ('Mamisoa', 'Rakotondrafara', '329122456', 'Fgh456$', 1),
       ('Mampionona', 'Rakotoarimanana', '330123465', 'Hij567@', 1),
       ('Tahiana', 'Rakotondrabe', '331123450', 'Klm678#', 1),
       ('Toky', 'Rakotoarisoa', '332123156', 'Nop789!', 1),
       ('Tovo', 'Razakamanana', '333133156', 'Qrs890@', 1),
       ('Fetra', 'Rakotomanga', '334123489', 'Tuv901#', 1),
       ('Tahina', 'Rakotoarisoa', '335123416', 'Wxy012$', 1),
       ('Mamisoa', 'Rakotoarisoa', '330003456', 'Zab123@', 1),
       ('Faharoa', 'Rasamison', '337121406', 'Cde234#', 1),
       ('Lanto', 'Rakotomanga', '338123056', 'Fgh345!', 1),
       ('Mamy', 'Rajaonarivelo', '339123756', 'Hij456^', 1),
       ('Liva', 'Rakotonirina', '340126756', 'Klm567$', 1),
       ('Tantely', 'Rakotomalala', '341153656', 'Nop678@', 1),
       ('Mampionona', 'Ravelomanantsoa', '332103406', 'Qrs789#', 1),
       ('Fehizoro', 'Raharison', '333121456', 'Tuv890!', 1),
       ('Fetra', 'Rakotondrafara', '344133356', 'Wxy901^', 1),
       ('Tsiky', 'Rajaobelina', '342120496', 'Zab012@', 1),
       ('Tahina', 'Razafimanjato', '346126456', 'Cde123#', 1),
       ('Fandresena', 'Rakotoarisoa', '347123451', 'Fgh234!', 1),
       ('Tahiry', 'Rasolonirina', '348183756', 'Hij345^', 1),
       ('Tiana', 'Rakotondrabe', '341124561', 'Klm456$', 1),
       ('Lova', 'Razafindrakoto', '340123406', 'Nop567@', 1),
       ('Tiana', 'Rakotoarisoa', '341123416', 'Qrs678#', 1),
       ('Faniry', 'Rakotomalala', '334213456', 'Tuv789!', 1),
       ('Tovo', 'Rakotoarivelo', '345120452', 'Wxy890^', 1),
       ('Tahina', 'Razafimahatratra', '346123456', 'Zab901@', 1),
       ('Tantely', 'Rakotomanjato', '347123126', 'Cde012!', 1),
       ('Fandresena', 'Rakotoarisoa', '348123416', 'Fgh123^', 1),
       ('Ryan', 'Tsanobe', '349929996', 'mot_de_passe', 1),
       ('Lolia', 'Finday', '339129496', 'hehehe', 1),
       ('Soprano', 'Jean', '329129496', 'hohoho', 1),
       ('Nathan', 'Nirina', '349829486', 'hahaha', 1),
       ('Andreas', 'Nirina', '349129433', 'huhuhu', 1),
       ('Rija', 'Ralahy', '345125495', 'hwhwhw', 1),
       ('Jean', 'Razafindramanitra', '331234567', 'Abc@123!', 2),
       ('Marie', 'Rakotomalala', '332345678', 'Def$456@', 2),
       ('Pierre', 'Razafindrakoto', '333456789', 'Ghi#789^', 2),
       ('Anne', 'Rakotoarisoa', '334567890', 'Jkl*890(', 2),
       ('Claude', 'Ranaivosoa', '335678901', 'Mno)901_', 2),
       ('Luc', 'Rakotonirina', '336789012', 'Pqr-012=', 2),
       ('Elise', 'Ranaivo', '337890123', 'Stu+123?', 2),
       ('Marc', 'Rasoanaivo', '338901234', 'Vwx=234|', 2),
       ('Sophie', 'Ramaroson', '339012345', 'Yza234#', 2),
       ('Paul', 'Razanakoto', '340123456', 'Bcd345@', 2),
       ('Julie', 'Razafindrabe', '341123456', 'Efg456!', 2),
       ('Henri', 'Rakotondrainibe', '342120456', 'Hij567@', 2),
       ('Claire', 'Rasolondraibe', '343123446', 'Klm678#', 2),
       ('Louis', 'Rakotoarimanana', '344153456', 'Nop789!', 2),
       ('Camille', 'Razakamanana', '345126456', 'Qrs901@', 2),
       ('Antoine', 'Ratsimandresy', '346923456', 'Tuv012^', 2),
       ('Nathalie', 'Ramaromisa', '327123456', 'Wxy123&', 2),
       ('Thierry', 'Rakotoarivelo', '349123456', 'Zab234*', 2),
       ('Benoît', 'Rakotomamonjy', '349923456', 'Cde345@', 2),
       ('Laurent', 'Ranaivomanana', '380123456', 'Fgh456!', 2),
       ('Hélène', 'Rakotondravelo', '381123456', 'Ijk567#', 2),
       ('Jacques', 'Rakotonirina', '382123454', 'Lmn678@', 2),
       ('Isabelle', 'Rasoanandrasana', '383123456', 'Opq789^', 2),
       ('François', 'Ravelonarivo', '384123456', 'Rst890!', 2),
       ('Sylvie', 'Rakotondrabe', '385123456', 'Uvw901@', 2),
       ('Tahiry', 'Razafindrabe', '346023456', 'Xyz012!', 2),
       ('Ando', 'Rakotoarimanana', '387123456', 'Abc123^', 2),
       ('Fara', 'Rasamison', '388123456', 'Def234$', 2);


-- Ligne et arrêts
INSERT INTO line_stop (line_id, stop_id, employee_id, is_terminus)
VALUES
       -- Ligne A
       (1, 1, 60, TRUE),
       (1, 2, 61, DEFAULT),
       (1, 3, 62, DEFAULT),
       (1, 4, 63, DEFAULT),
       (1, 5, 64, DEFAULT),
       (1, 6, 65, DEFAULT),
       (1, 7, 66, DEFAULT),
       (1, 8, 67, DEFAULT),
       (1, 9, 68, DEFAULT),
       (1, 10, 69, TRUE),

       -- Ligne B
       (2, 11, 70, TRUE),
       (2, 12, 71, DEFAULT),
       (2, 13, 72, DEFAULT),
       (2, 14, 73, DEFAULT),
       (2, 15, 74, DEFAULT),
       (2, 16, 75, DEFAULT),
       (2, 17, 76, DEFAULT),
       (2, 18, 77, DEFAULT),
       (2, 19, 78, DEFAULT),
       (2, 10, 69, DEFAULT),
       (2, 20, 79, DEFAULT),
       (2, 21, 80, DEFAULT),
       (2, 22, 81, DEFAULT),
       (2, 23, 82, DEFAULT),
       (2, 24, 83, DEFAULT),
       (2, 25, 84, DEFAULT),

       -- Ligne C
       (3, 12, 71, TRUE),
       (3, 13, 72, DEFAULT),
       (3, 14, 73, DEFAULT),
       (3, 15, 74, DEFAULT),
       (3, 25, 84, DEFAULT),
       (3, 26, 85, DEFAULT),
       (3, 27, 86, DEFAULT),
       (3, 28, 87, TRUE);

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
