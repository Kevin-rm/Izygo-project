INSERT INTO "roles" ("type") VALUES ('user');
INSERT INTO "roles" ("type") VALUES ('backoffice');




INSERT INTO line (label)
VALUES ('Ligne A'),
       ('Ligne B'),
       ('Ligne C');

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
       ('Soarano');
INSERT INTO bus(license_plate,number_of_seats,line_id)
VALUES('3652TCA',20,1),
      ('3620TCA',20,2),
      ('9842TCA',20,3);

INSERT INTO seat(label)
VALUES('a1'),
      ('a2'),
      ('a3'),
      ('a4');

INSERT INTO bus_seat(bus_id,seat_id)
VALUES(1,1),
      (1,2),
      (1,3),
      (1,4),
      (2,1),
      (2,2),
      (2,3),
      (2,4),
      (3,1),
      (3,2),
      (3,3),
      (3,4);


INSERT INTO reservation(date_time,user_id,bus_id)
VALUES('2024-05-06 10:20:00',1,1),
      ('2024-05-06 11:20:00',1,1),
      ('2024-05-06 8:20:00',3,2),
      ('2024-05-10 7:20:00',3,3);


INSERT INTO reservation_seat(seat_id,reservation_id)
VALUES(1,1),
      (2,2),
      (3,2),
      (4,2);