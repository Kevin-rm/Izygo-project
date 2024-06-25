-- Ligne 
INSERT INTO line (label)
VALUES ('Ligne A'),
       ('Ligne B'),
       ('Ligne C');

-- Arrets
INSERT INTO stop (label)
VALUES ('Andoharanofotsy'), 
       ('Malaza'),
       ('Magasin M-'),
       ('Tanjombato'),
       ('Fasika'),
       ('Ankandimbahoaka'),
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
       ('Soarano'),

-- Personne

INSERT INTO roles ("type") 
VALUES
       ('client'),
       ('kiosk');

INSERT INTO "user" ("firstname","lastname","phone_number","password","role_id")
VALUES
       ('Ny Ony','RAMAVO','341610025','12345678910',1),
       ('Fanantenana','HARINAIVO','342500116','10987654321',1),
       ('Sariaka','RAKOTODRANIVO','341245567','azertyuiop',1),
       ('Zoky','SSPR','696969699','masosomZoky lelike',2);
-- Route
INSERT INTO line_stop (line_id, stop_id, is_terminus)
VALUES
       -- Ligne A
       (1, 1, TRUE),
       (1, 2, DEFAULT),
       (1, 3, DEFAULT),
       (1, 4, DEFAULT),
       (1, 5, DEFAULT),
       (1, 6, DEFAULT),
       (1, 7, DEFAULT),
       (1, 8, DEFAULT),
       (1, 9, DEFAULT),
       (1, 10, TRUE),

       -- Ligne B
       (2, 11, TRUE),
       (2, 12, DEFAULT),
       (2, 13, DEFAULT),
       (2, 14, DEFAULT),
       (2, 15, DEFAULT),
       (2, 16, DEFAULT),
       (2, 17, DEFAULT),
       (2, 18, DEFAULT),
       (2, 19, DEFAULT),
       (2, 10, DEFAULT),
       (2, 20, DEFAULT),
       (2, 21, DEFAULT),
       (2, 22, DEFAULT),
       (2, 23, DEFAULT),
       (2, 24, DEFAULT),
       (2, 25, DEFAULT),

       -- Ligne C
       (3, 12, TRUE),
       (3, 13, DEFAULT),
       (3, 14, DEFAULT),
       (3, 15, DEFAULT),
       (3, 25, DEFAULT),
       (3, 26, DEFAULT),
       (3, 27, DEFAULT),
       (3, 28, TRUE),

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
       (3, 13, 12, 15),

-- Bus
INSERT INTO bus (license_plate,number_of_seats,line_id)
VALUES
    ('1234 TAA',19,1),
    ('9876 TAB',19,2),
    ('9812 TAN',19,3),
    ('9834 TAE',19,3);

-- Chaise
INSERT INTO seat (label)
VALUES
    ('1A'),
    ('2A'),('2B'),('2C'),
    ('3A'),('3B'),
    ('4A'),('4B'),('4C'),
    ('5A'),('5B'),('5C'),
    ('7A'),('7B'),('7C'),
    ('7A'),('7B'),('7C'),('7D');


-- - -  - - - -  - - - - - - - -  - - - - - - - - - - - - - - - - -
-- Donnée réel

-- Ligne 
INSERT INTO "line"("label")
VALUES 
       ('Ligne 116'), -- 116 TREMA
       ('Ligne 144'), -- 144 Mitsinjo
       ('Ligne 117'), -- 117 EZAKA
       ('Ligne 151'), -- 151 EZAKA
       ('Ligne 113'), -- 113 SOCOTRA
       ('Ligne 106'); -- 106 RAP BUS

-- Arrets
INSERT INTO "stop"("label", "latitude", "longitude")
VALUES 
    ('Terminus 116 - Mandroseza', -18.936925, 47.552257),
    ('Jirama', -18.930857, 47.549326),
    ('Ceg - Ambohimiandra', -18.928950, 47.544482),
    ('Fiangonana Ambatoroka', -18.924866, 47.542049),
    ('Poste - Ambanidia', -18.918799, 47.539418),
    ('Tfm - Ambanidia', -18.918359, 47.537248),
    ('Pharmacie Hanitra - Antsakaviro', -18.916457, 47.537145),
    ('Scav - Antsakaviro', -18.914070, 47.536909),
    ('Mascotte (Vers Anosy) - Antsahabe', -18.911474, 47.529048),
    ('Terminus 116 - Ambohijatovo', -18.910658, 47.528576),
    ('Ambohijatovo (De Anosy)', -18.913441, 47.529310),
    ('Antsahabe', -18.914307, 47.536973),
    ('Antsakaviro', -18.915056, 47.537527),
    ('Pompe - Ambanidia', -18.920081, 47.539750),
    ('Sampanana Ambohimiandra', -18.927401, 47.543553),
    ('Shell - Ambohimiandra', -18.934394, 47.550157),

    ('Ambodifilao', -18.905807, 47.526044),
    ('Firaisana Soarano', -18.903531, 47.523926),
    ('Ambany Tetezana - Behoririka', -18.902466, 47.523497),
    ('Tsena - Andravoahangy', -18.898543, 47.530859),
    ('Ambodimanga - Avaradoha', -18.901636, 47.536391),
    ('Avaradoha', -18.901742, 47.542599),
    ('Vingt-Trois - Ampasampito', -18.897252, 47.545691),
    ('Assurance - Ampasapito', -18.895880, 47.546276),
    ('Tamboho - Ampasapito', -18.894267, 47.548769),
    ('Vatosoa - Ankadindramamy', -18.892472, 47.554897),
    ('Total - Ankadindramamy', -18.892044, 47.559032),
    ('Adventiste Mahazo', -18.890068, 47.557569),
    ('Primus Ambohimahitsy', -18.876411, 47.546727),
    ('Kiraro - Ankadindramamy', -18.886942, 47.550317),
    ('Pain De Tana, Ampasapito', -18.889746, 47.548814),
    ('Météo Ampasapito', -18.896263, 47.544954),
    ('Centre - Avaradoha', -18.900146, 47.544804),
    ('Tsena - Besarety', -18.901604, 47.536410),
    ('Autogi - Andravoahangy', -18.898692, 47.531732),
    ('Sodiat - Behoririka', -18.898150, 47.520843),
    ('Shalimar', -18.906082, 47.519844),
    ('Sicam', -18.907546, 47.523810),

    ('Terminus 117 - Ambohijatovo', -18.912855, 47.528934),
    ('Alliance Françaice - Antsahabe', -18.915166, 47.533624),
    ('Total Ankazotokana', -18.917025, 47.535713),
    ('Rond - Point Ambanidia', -18.920017, 47.536860),
    ('Garage Ambatoroka', -18.922151, 47.540660),
    ('Sampanana Mandroseza', -18.929000, 47.544397),
    ('Sampanana Mahazoarivo', -18.932380, 47.544817),
    ('16 Arrêt Bus', -18.935014, 47.544781),
    ('Terminus Ambohitsoa', -18.937637, 47.546002),

       ('NY Havana 67ha', -18.909603, 47.506869),
       ('Poste - 67ha', -18.908985, 47.508767),
       ('Jirama - Andavamamba', -18.915564, 47.508142),
       ('Andavamamba (Vers Anosy)', -18.916495, 47.510092),
       ('Complexe (Vers Anosy) - Ampefiloha', -18.916495, 47.513270),
       ('Fiaro - Ampefiloha', -18.912780, 47.517770),
       ('Radio (Vers Anosy) - Anosy', -18.913984, 47.518678),
       ('Voninkazo - Anosikely', -18.917265, 47.520330),
       ('Motel Anosibe', -18.920915, 47.518591),
       ('Barrière Anosibe', -18.924525, 47.514711),
       ('Akoho Anosibe', -18.927941, 47.511107),
       ('Mpivaro-Kena Anosibe', -18.930460, 47.509651), 
       ('Rond Point - Namontana', -18.935626, 47.509918),
       ('Tsena Namontana', -18.935962, 47.513915),
       ('Colis - Namontana', -18.936096, 47.516211),
       ('Tetezana - Namontana', -18.938305, 47.521113),
       ('Sampanan''Ny Fasan''Ny Karana', -18.942923, 47.523058),
       ('Ankadimbahoaka', -18.944132, 47.524180),
       ('Sampanana Ankadimbahoaka', -18.947261, 47.525236),
       ('Arrêt Bus 5', -18.947538, 47.526577),
       ('Arrêt Bus 6', -18.949786, 47.531550),
       ('Arrêt Bus 7', -18.939534, 47.540042),
       ('Gare Soanierana', -18.941761, 47.537509),
       ('Tetezana - Soanierana', -18.944789, 47.524614),
       ('Pharmacie Anosibe', -18.923295, 47.516328),
       ('Tsena Anosibe', -18.920303, 47.519571),
       ('Anosy', -18.918281, 47.521996),
       ('Fitsarana - Anosy', -18.912337, 47.526300),
       ('Fiaro (Vers 67ha) - Ampefiloha', -18.903395, 47.522803),
       ('Lalamby - Ampefiloha', -18.895186, 47.517346),
       ('Garage Ampefiloha', -18.896324, 47.511410),
       ('Fokontany - Andavamamba', -18.907102, 47.505866),
       ('Trente - 67ha', -18.910287, 47.505956),

       ('Terminus 113 - Sicam', -18.907542, 47.523798),
       ('Anosy (Vers 67ha)', -18.909244, 47.526496),
       ('Andrefan''Ambohijanahary', -18.920914, 47.518589),
       ('Mon Gouter', -18.920055, 47.519819),
       ('Jirama - Soanierana', -18.928167, 47.518975),
       ('Paraky', -18.929833, 47.519315),
       ('Descours', -18.932479, 47.520781),
       ('Soanierana', -18.935324, 47.522102),
       ('Toby', -18.923513, 47.520638),
       ('Rm1 - Andohan''Analakely', -18.908717, 47.527292),
       ('Tohatoha-Baton''Ambondrona', -18.906845, 47.526614),

       ('Jesosy Mamonjy - Ankorondrano', -18.893115, 47.522320),
       ('Jumbo - Ankorondrano', -18.890959, 47.522320),
       ('Voninkazo - Ivandry', -18.887119, 47.522890),
       ('Travaux - Alarobia', -18.877609, 47.521057),
       ('Tsarasaotra', -18.869914, 47.518157),
       ('Antsampanimahazo', -18.868716, 47.516354),
       ('Fiangonana Soavimasoandro', -18.863332, 47.5171802405951),
       ('Fary Soavimasoandro', -18.860398, 47.514961),
       ('Terminus 120 - Soavimasoandro', -18.859545, 47.511975),
       ('Gare - Alarobia', -18.869943, 47.518058),
       ('Ivandry', -18.876440, 47.520415),
       ('Tana 2000 - Ankorondrano', -18.881797, 47.522280),
       ('Score - Akorondrano', -18.884458, 47.522717),
       ('Midi, Ankorondrano', -18.887953, 47.522564),
       ('Codal, Ankorondrano', -18.890287, 47.522247),
       ('Fraise - Ankorondrano', -18.893643, 47.521818),
       ('Ceg Antanimena', -18.898126, 47.519003);

-- Personne

INSERT INTO roles ("type") 
VALUES
       ('client'),
       ('kiosk');

INSERT INTO "user"("firstname","lastname","phone_number","password","role_id")
VALUES
    ('Ny Ony','RAMAVO','341610025','12345678910',1),
    ('Fanantenana','HARINAIVO','342500116','10987654321',1),
    ('Sariaka','RAKOTODRANIVO','341245567','azertyuiop',1),
    ('Zoky','SSPR','696969699','masosomZoky lelike',2),
    ('Haja', 'Razafindramanitra', '331234567', 'Abc@123!', 2),
    ('Fanja', 'Rakotomalala', '332345678', 'Def$456@', 2),
    ('Tahina', 'Razafindrakoto', '333456789', 'Ghi#789^', 2),
    ('Andry', 'Rakotoarisoa', '334567890', 'Jkl*890(', 2),
    ('Fitiavana', 'Ranaivosoa', '335678901', 'Mno)901_', 2),
    ('Mamy', 'Rakotonirina', '336789012', 'Pqr-012=', 2),
    ('Lanto', 'Ranaivo', '337890123', 'Stu+123?', 2),
    ('Hery', 'Rasoanaivo', '338901234', 'Vwx=234|', 2),
    ('Nantenaina', 'Ramaroson', '339012345', 'Yza234#', 2),
    ('Herizo', 'Razanakoto', '340123456', 'Bcd345@', 2),
    ('Mialy', 'Razafindrabe', '341123456', 'Efg456!', 2),
    ('Faharoa', 'Rakotondrainibe', '342120456', 'Hij567@', 2),
    ('Tsiry', 'Rasolondraibe', '343123446', 'Klm678#', 2),
    ('Tiana', 'Rakotoarimanana', '344153456', 'Nop789!', 2),
    ('Fiderana', 'Razakamanana', '345126456', 'Qrs901@', 2),
    ('Tina', 'Ratsimandresy', '346923456', 'Tuv012^', 2),
    ('Lova', 'Ramaromisa', '327123456', 'Wxy123&', 2),
    ('Tahiry', 'Rakotoarivelo', '349123456', 'Zab234*', 2),
    ('Toky', 'Rakotomamonjy', '349923456', 'Cde345@', 2),
    ('Mandresy', 'Ranaivomanana', '380123456', 'Fgh456!', 2),
    ('Mamisoa', 'Rakotondravelo', '381123456', 'Ijk567#', 2),
    ('Faharoa', 'Rakotonirina', '382123454', 'Lmn678@', 2),
    ('Henintsoa', 'Rasoanandrasana', '383123456', 'Opq789^', 2),
    ('Hasina', 'Ravelonarivo', '384123456', 'Rst890!', 2),
    ('Mialy', 'Rakotondrabe', '385123456', 'Uvw901@', 2),
    ('Tahiry', 'Razafindrabe', '346023456', 'Xyz012!', 2),
    ('Ando', 'Rakotoarimanana', '387123456', 'Abc123^', 2),
    ('Fara', 'Rasamison', '388123456', 'Def234$', 2),
    ('Lanto', 'Rakotomanga', '389123456', 'Ghi345@', 2),
    ('Mamy', 'Rajaonarivelo', '320123456', 'Jkl456!', 2),
    ('Liva', 'Rakotonirina', '321123151', 'Mno567@', 2),
    ('Tantely', 'Rakotomalala', '321121416', 'Pqr678#', 2),
    ('Mampionona', 'Ravelomanantsoa', '335525556', 'Stu789^', 2),
    ('Fehizoro', 'Raharison', '324123456', 'Vwx890!', 2),
    ('Fetra', 'Rakotondrafara', '325133356', 'Yza901@', 2),
    ('Tsiky', 'Rajaobelina', '346623456', 'Bcd012!', 2),
    ('Tahina', 'Razafimanjato', '327123556', 'Efg123^', 2),
    ('Fandresena', 'Rakotoarisoa', '328123456', 'Hij234$', 2),
    ('Tahiry', 'Rasolonirina', '329123456', 'Klm345@', 2),
    ('Tiana', 'Rakotondrabe', '330123456', 'Nop456!', 2),
    ('Faneva', 'Ratsimandresy', '331124561', 'Qrs567@', 2),
    ('Lova', 'Razafindrakoto', '332123456', 'Tuv678#', 2),
    ('Tiana', 'Rakotoarisoa', '333123456', 'Wxy789^', 2),
    ('Faniry', 'Rakotomalala', '334123456', 'Zab890!', 2),
    ('Tovo', 'Rakotoarivelo', '335123456', 'Cde901@', 2),
    ('Tahina', 'Razafimahatratra', '336123456', 'Fgh012^', 2),
    ('Tantely', 'Rakotomanjato', '337123456', 'Ijk123!', 2),
    ('Fandresena', 'Rakotoarisoa', '338123456', 'Lmn234$', 2),
    ('Lova', 'Ranaivosoa', '339123456', 'Opq345@', 2),
    ('Mandresy', 'Razafindrakoto', '340123486', 'Rst456!', 2),
    ('Tahina', 'Rakotoarisoa', '349144560', 'Uvw567@', 2),
    ('Tahiana', 'Ratsimandresy', '342123456', 'Xyz678#', 2),
    ('Henintsoa', 'Razafindramanitra', '343123456', 'Abc789^', 2),
    ('Hasina', 'Rakotomalala', '344123456', 'Def890!', 2),
    ('Tahiry', 'Razafindrakoto', '345123456', 'Ghi901@', 2),
    ('Andrianaivo', 'Rakotoarisoa', '329129459', 'Jkl012^', 2),
    ('Tiana', 'Rakotoarimanana', '347123456', 'Mno123!', 2),
    ('Fehizoro', 'Rakotomamonjy', '348123456', 'Pqr234$', 2),
    ('Faneva', 'Razakamanana', '341234562', 'Stu345@', 2),
    ('Faly', 'Raharison', '380122456', 'Vwx456!', 2),
    ('Mamisoa', 'Rakotondrafara', '381122456', 'Yza567@', 2),
    ('Mampionona', 'Rakotoarimanana', '382123456', 'Bcd678#', 2),
    ('Tahiana', 'Rakotondrabe', '383123450', 'Efg789^', 2),
    ('Toky', 'Rakotoarisoa', '384123156', 'Hij890!', 2),
    ('Tovo', 'Razakamanana', '325133156', 'Klm901@', 2),
    ('Fetra', 'Rakotomanga', '326123456', 'Nop012^', 2),
    ('Tahina', 'Rakotoarisoa', '387123416', 'Qrs123!', 2),
    ('Mamisoa', 'Rakotoarisoa', '396123456', 'Mno123@', 2),
       ('Faharoa', 'Rasamison', '397123456', 'Pqr234#', 2),
       ('Lanto', 'Rakotomanga', '398123456', 'Stu345!', 2),
       ('Mamy', 'Rajaonarivelo', '399123456', 'Vwx456^', 2),
       ('Liva', 'Rakotonirina', '300123456', 'Yza567$', 2),
       ('Tantely', 'Rakotomalala', '301123456', 'Bcd678@', 2),
       ('Mampionona', 'Ravelomanantsoa', '302123456', 'Efg789#', 2),
       ('Fehizoro', 'Raharison', '303123456', 'Hij890!', 2),
       ('Fetra', 'Rakotondrafara', '304133356', 'Klm901^', 2),
       ('Tsiky', 'Rajaobelina', '305123456', 'Nop012$', 2),
       ('Tahina', 'Razafimanjato', '306123456', 'Qrs123@', 2),
       ('Fandresena', 'Rakotoarisoa', '307123456', 'Tuv234#', 2),
       ('Tahiry', 'Rasolonirina', '308123456', 'Wxy345!', 2),
       ('Tiana', 'Rakotondrabe', '309124561', 'Zab456^', 2),
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

-- Route
INSERT INTO "line_stop"("line_id", "stop_id", "is_terminus", "employee_id")
VALUES
       -- Line 116
       (1, 1, TRUE, 4),
       (1, 2, DEFAULT, 5),
       (1, 3, DEFAULT, 6),
       (1, 4, DEFAULT, 7),
       (1, 5, DEFAULT, 8),
       (1, 6, DEFAULT, 9),
       (1, 7, DEFAULT, 10),
       (1, 8, DEFAULT, 11),
       (1, 9, DEFAULT, 12),
       (1, 10, TRUE, 13),
       (1, 11, DEFAULT, 14),
       (1, 12, DEFAULT, 15),
       (1, 13, DEFAULT, 16),
       (1, 14, DEFAULT, 17),
       (1, 15, DEFAULT, 18),
       (1, 16, DEFAULT, 19),

       -- Line 144
       (2, 17, TRUE, 20),
       (2, 18, DEFAULT, 21),
       (2, 19, DEFAULT, 22),
       (2, 20, DEFAULT, 23),
       (2, 21, DEFAULT, 24),
       (2, 22, DEFAULT, 25),
       (2, 23, DEFAULT, 26),
       (2, 24, DEFAULT, 27),
       (2, 25, DEFAULT, 28),
       (2, 26, DEFAULT, 29),
       (2, 27, DEFAULT, 30),
       (2, 28, DEFAULT, 31),
       (2, 29, TRUE, 32),
       (2, 30, DEFAULT, 33),
       (2, 31, DEFAULT, 34),
       (2, 32, DEFAULT, 35),
       (2, 33, DEFAULT, 109),
       (2, 34, DEFAULT, 110),
       (2, 35, DEFAULT, 111),
       (2, 36, DEFAULT, 112),
       (2, 37, DEFAULT, 113),
       (2, 38, DEFAULT, 114),
       

       -- Ligne 117
       (3, 4, DEFAULT, 36),
       (3, 5, DEFAULT, 37),
       (3, 6, DEFAULT, 38),
       (3, 7, DEFAULT, 39),
       (3, 8, DEFAULT, 40),
       (3, 9, DEFAULT, 41),
       (3, 11, DEFAULT, 42),
       (3, 39, TRUE, 43),
       (3, 40, DEFAULT, 44),
       (3, 41, DEFAULT, 45),
       (3, 42, DEFAULT, 46),
       (3, 43, DEFAULT, 47),
       (3, 44, DEFAULT, 48),
       (3, 45, DEFAULT, 49),
       (3, 46, DEFAULT, 50),
       (3, 47, TRUE, 51),

       -- Ligne 151
       (4, 47, TRUE, 52),
       (4, 48, TRUE, 53),
       (4, 49, DEFAULT, 54),
       (4, 50, DEFAULT, 55),
       (4, 51, DEFAULT, 56),
       (4, 52, DEFAULT, 57),
       (4, 53, DEFAULT, 58),
       (4, 54, DEFAULT, 59),
       (4, 55, DEFAULT, 60),
       (4, 56, DEFAULT, 61),
       (4, 57, DEFAULT, 62),
       (4, 58, DEFAULT, 63),
       (4, 59, DEFAULT, 64),
       (4, 60, DEFAULT, 65),
       (4, 61, DEFAULT, 66),
       (4, 62, DEFAULT, 67),
       (4, 63, DEFAULT, 68),
       (4, 64, DEFAULT, 69),
       (4, 65, DEFAULT, 70),
       (4, 66, DEFAULT, 71),
       (4, 67, DEFAULT, 72),
       (4, 68, DEFAULT, 73),
       (4, 69, DEFAULT, 74),
       (4, 70, DEFAULT, 75),
       (4, 71, DEFAULT, 76),
       (4, 72, DEFAULT, 77),
       (4, 73, DEFAULT, 78),
       (4, 74, DEFAULT, 79),
       (4, 75, DEFAULT, 80),
       (4, 76, DEFAULT, 81),
       (4, 77, DEFAULT, 82),
       (4, 78, DEFAULT, 83),
       (4, 79, DEFAULT, 84),
       (4, 80, DEFAULT, 85),

       -- Ligne 113
       (5, 18, DEFAULT, 86),
       (5, 45, TRUE, 87),
       (5, 46, DEFAULT, 88),
       (5, 47, DEFAULT, 89),
       (5, 64, DEFAULT, 90),
       (5, 65, DEFAULT, 91),
       (5, 66, DEFAULT, 92),
       (5, 67, DEFAULT, 93),
       (5, 68, DEFAULT, 94),
       (5, 69, DEFAULT, 95),
       (5, 70, DEFAULT, 96),
       (5, 74, DEFAULT, 97),
       (5, 81, TRUE, 98),
       (5, 82, DEFAULT, 99),
       (5, 83, DEFAULT, 100),
       (5, 84, DEFAULT, 101),
       (5, 85, DEFAULT, 102),
       (5, 86, DEFAULT, 103),
       (5, 87, DEFAULT, 104),
       (5, 88, DEFAULT, 105),
       (5, 89, DEFAULT, 106),
       (5, 90, DEFAULT, 107),
       (5, 91, DEFAULT, 108),

       -- Ligne 106
       (6, 17, TRUE, 115),
       (6, 18, DEFAULT, 116),
       (6, 36, DEFAULT, 117),
       (6, 37, DEFAULT, 118),
       (6, 92, DEFAULT, 119),
       (6, 93, DEFAULT, 120),
       (6, 94, DEFAULT, 121),
       (6, 95, DEFAULT, 122),
       (6, 96, DEFAULT, 123),
       (6, 97, DEFAULT, 124),
       (6, 98, DEFAULT, 125),
       (6, 99, DEFAULT, 126),
       (6, 100, TRUE, 127),
       (6, 101, DEFAULT, 128),
       (6, 102, DEFAULT, 129),
       (6, 103, DEFAULT, 130),
       (6, 104, DEFAULT, 131),
       (6, 105, DEFAULT, 132),
       (6, 106, DEFAULT, 133),
       (6, 107, DEFAULT, 134),
       (6, 108, DEFAULT, 135);


INSERT INTO "line_path"("line_id", "from_stop_id", "to_stop_id", "estimated_duration")
VALUES
       -- Ligne 116
        (1, 1, 2, 10),
        (1, 2, 3, 5),
        (1, 3, 4, 7),
        (1, 4, 5, 6),
        (1, 5, 6, 7),
        (1, 6, 7, 6),
        (1, 7, 8, 8),
        (1, 8, 9, 3),
        (1, 9, 10, 7),
        (1, 10, 11, 10),
        (1, 11, 12, 5),
        (1, 12, 13, 8),
        (1, 13, 14, 6),
        (1, 14, 4, 7),
        (1, 4, 15, 4),
        (1, 15, 2, 5),
        (1, 2, 16, 5),
        (1, 16, 1, 5),

        -- Ligne 144
        (2, 17, 18, 5),
        (2, 18, 19, 6),
        (2, 19, 20, 8),
        (2, 20, 21, 3),
        (2, 21, 22, 7),
        (2, 22, 23, 10),
        (2, 23, 24, 4),
        (2, 24, 25, 5),
        (2, 25, 26, 10),
        (2, 26, 27, 10),
        (2, 27, 28, 7),
        (2, 28, 29, 5),
        (2, 29, 28, 8),
        (2, 28, 30, 7),
        (2, 30, 26, 5),
        (2, 26, 31, 5),
        (2, 31, 32, 6),
        (2, 32, 33, 10),
        (2, 33, 22, 5),
        (2, 22, 34, 8),
        (2, 34, 35, 7),
        (2, 35, 19, 4),
        (2, 19, 36, 4),
        (2, 36, 37, 4),
        (2, 37, 38, 4),
        (2, 38, 17, 4),

        -- Ligne 117
        (3, 39, 11, 5),
        (3, 11, 41, 7),
        (3, 40, 41, 6),
        (3, 41, 42, 10),
        (3, 42, 43, 3),
        (3, 43, 44, 8),
        (3, 44, 45, 5),
        (3, 45, 46, 7),
        (3, 46, 47, 10),
        (3, 47, 46, 6),
        (3, 46, 45, 5),
        (3, 45, 44, 6),
        (3, 44, 4, 4),
        (3, 4, 43, 8),
        (3, 43, 5, 5),
        (3, 5, 6, 3),
        (3, 6, 7, 3),
        (3, 7, 8, 3),
        (3, 8, 9, 3),
        (3, 9, 39, 3),

        -- Ligne 151
       (4, 47, 69, 5),
       (4, 69, 68, 8),
       (4, 68, 66, 7),
       (4, 66, 65, 6),
       (4, 65, 70, 7),
       (4, 70, 71, 7),
       (4, 71, 61, 7),
       (4, 61, 72, 4),
       (4, 72, 58, 6),
       (4, 58, 73, 5),
       (4, 73, 57, 7),
       (4, 57, 56, 7),
       (4, 56, 74, 5),
       (4, 74, 75, 9),
       (4, 75, 76, 7),
       (4, 76, 77, 6),
       (4, 77, 78, 10),
       (4, 78, 79, 10),
       (4, 79, 80, 9),
       (4, 80, 48, 5),

       -- Ligne 113
       (5, 81, 82, 5),
       (5, 82, 83, 6),
       (5, 83, 84, 6),
       (5, 84, 85, 4),
       (5, 85, 86, 5),
       (5, 86, 87, 7),
       (5, 87, 64, 8),
       (5, 64, 66, 10),
       (5, 66, 67, 6),
       (5, 67, 69, 9),
       (5, 69, 47, 6),
       (5, 47, 46, 5),
       (5, 46, 45, 7),
       (5, 45, 46, 7),
       (5, 46, 47, 6),
       (5, 47, 69, 7),
       (5, 69, 68, 9),
       (5, 68, 66, 8),
       (5, 66, 65, 6),
       (5, 65, 70, 7),
       (5, 70, 87, 8),
       (5, 87, 86, 5),
       (5, 86, 88, 6),
       (5, 88, 89, 6),
       (5, 89, 74, 5),
       (5, 74, 90, 7),
       (5, 90, 91, 7),

       (6, 17, 18, 5),
       (6, 18, 36, 7),
       (6, 36, 92, 6),
       (6, 92, 93, 6),
       (6, 93, 94, 7),
       (6, 94, 95, 4),
       (6, 95, 96, 6),
       (6, 96, 97, 5),
       (6, 97, 98, 6),
       (6, 98, 99, 5),
       (6, 99, 100, 10),
       (6, 100, 101, 7),
       (6, 101, 102, 5),
       (6, 102, 103, 9),
       (6, 103, 104, 7),
       (6, 104, 105, 6),
       (6, 105, 106, 10),
       (6, 106, 107, 10),
       (6, 107, 108, 9),
       (6, 108, 37, 5),
       (6, 37, 17, 5);

-- Bus
INSERT INTO "bus" ("license_plate", "number_of_seats", "line_id")
VALUES
    ('1234 TAA', 19, 1),
    ('9876 TAB', 19, 1),
    ('9812 TAN', 19, 1),
    ('9834 TAE', 19, 1),
    ('6814 TSM', 19, 1),
    ('6963 TRR', 19, 1),
    ('3153 TQX', 19, 1),
    ('6960 TIE', 19, 1),
    ('8746 TBW', 19, 1),
    ('5164 TBK', 19, 1),

    ('2164 TLS', 19, 2),
    ('7724 TSC', 19, 2),
    ('6728 TLY', 19, 2),
    ('9868 TGJ', 19, 2),
    ('5416 TNX', 19, 2),
    ('3158 TPT', 19, 2),
    ('1545 TOZ', 19, 2),
    ('9112 TKJ', 19, 2),
    ('7098 TRX', 19, 2),
    ('9376 TGM', 19, 2),

    ('1659 TVI', 19, 3),
    ('5199 TLS', 19, 3),
    ('9533 TPY', 19, 3),
    ('8440 TUP', 19, 3),
    ('6248 TCD', 19, 3),
    ('4411 TQT', 19, 3),
    ('5529 TSL', 19, 3),
    ('6097 TQD', 19, 3),
    ('8002 TON', 19, 3),
    ('5786 TJT', 19, 3),
    
    ('3299 TNN', 19, 4),
    ('7853 TFF', 19, 4),
    ('6445 TGL', 19, 4),
    ('8486 TPO', 19, 4),
    ('9002 TLI', 19, 4),
    ('0627 TPL', 19, 4),
    ('0327 TKA', 19, 4),
    ('2854 TFS', 19, 4),
    ('1018 TTK', 19, 4),
    ('7070 TOY', 19, 4),

    ('7201 TOK', 19, 5),
    ('7246 TUF', 19, 5),
    ('1303 TES', 19, 5),
    ('8942 TMB', 19, 5),
    ('8623 TIU', 19, 5),
    ('4984 TFT', 19, 5),
    ('6774 TGW', 19, 5),
    ('8851 TIR', 19, 5),
    ('4115 TAF', 19, 5),
    ('7237 THD', 19, 5),

    ('1453 TPE', 19, 6),
    ('2400 THZ', 19, 6),
    ('6222 TAK', 19, 6),
    ('1963 TDI', 19, 6),
    ('5393 TEJ', 19, 6),
    ('0374 TLV', 19, 6),
    ('5930 TVQ', 19, 6),
    ('3884 TOT', 19, 6),
    ('6893 TOJ', 19, 6),
    ('0057 THP', 19, 6),
    ('1051 TRR', 19, 6);


-- Chaise
INSERT INTO seat (label)
VALUES
    ('1A'),
    ('2A'),('2B'),('2C'),
    ('3A'),('3B'),
    ('4A'),('4B'),('4C'),
    ('5A'),('5B'),('5C'),
    ('7A'),('7B'),('7C'),
    ('7A'),('7B'),('7C'),('7D');