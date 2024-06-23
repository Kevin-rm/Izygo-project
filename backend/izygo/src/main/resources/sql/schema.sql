DROP DATABASE IF EXISTS izygo;
CREATE DATABASE izygo;
\c izygo;

CREATE TABLE "roles"
(
    "id"   SMALLSERIAL PRIMARY KEY,
    "type" VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE "user"
(
    "id"           BIGSERIAL PRIMARY KEY,
    "firstname"    VARCHAR(255)                       NOT NULL,
    "lastname"     VARCHAR(255)                       NOT NULL,
    "phone_number" VARCHAR(10) UNIQUE                 NOT NULL CHECK (length("phone_number") = 9),
    "password"     VARCHAR(255)                       NOT NULL CHECK (length("password") >= 5),
    "role_id"      SMALLINT REFERENCES "roles" ("id") NOT NULL
);

CREATE TABLE "stop"
(
    "id"        SERIAL PRIMARY KEY,
    "label"     VARCHAR(50) UNIQUE NOT NULL,
    "latitude"  NUMERIC(9,6), -- NOT NULL,
    "longitude" NUMERIC(9,6) --NOT NULL
);

CREATE TABLE "line"
(
    "id"    SERIAL PRIMARY KEY,
    "label" VARCHAR(10) UNIQUE NOT NULL
);

CREATE TABLE "line_stop"
(
    "line_id"     INT REFERENCES "line" ("id")    NOT NULL,
    "stop_id"     INT REFERENCES "stop" ("id")    NOT NULL,
    "employee_id" BIGINT REFERENCES "user" ("id") NOT NULL, -- Mpitazona kiosk
    "is_terminus" BOOLEAN DEFAULT FALSE           NOT NULL,
    PRIMARY KEY ("line_id", "stop_id")
   -- UNIQUE ("employee_id")
);

CREATE TABLE "line_path"
(
    "line_id"            INT      NOT NULL,
    "from_stop_id"       INT      NOT NULL,
    "to_stop_id"         INT      NOT NULL,
    "estimated_duration" SMALLINT NOT NULL,
    PRIMARY KEY ("line_id", "from_stop_id", "to_stop_id"),
    FOREIGN KEY ("line_id", "from_stop_id") REFERENCES "line_stop" ("line_id", "stop_id"),
    FOREIGN KEY ("line_id", "to_stop_id") REFERENCES "line_stop" ("line_id", "stop_id"),
    CHECK ("from_stop_id" != "to_stop_id")
);

CREATE TABLE "bus"
(
    "id"              BIGSERIAL PRIMARY KEY,
    "license_plate"   VARCHAR(15)                  NOT NULL UNIQUE,
    "number_of_seats" SMALLINT                     NOT NULL,
    "line_id"         INT REFERENCES "line" ("id") NOT NULL
);

CREATE TABLE "seat"
(
    "id"    SMALLSERIAL PRIMARY KEY,
    "label" VARCHAR(5) NOT NULL
);

CREATE TABLE "bus_seat"
(
    "bus_id"  BIGINT REFERENCES "bus" ("id")    NOT NULL,
    "seat_id" SMALLINT REFERENCES "seat" ("id") NOT NULL
);

/*
 * La table "bus_position" permet de suivre la position des bus en temps réel, en indiquant
 * sur quel arrêt ils se trouvent et quelle est leur prochaine destination.
 */
CREATE TABLE "bus_position"
(
    "date_time_passage" TIMESTAMP                      NOT NULL,
    "line_id"           INT                            NOT NULL,
    "current_stop_id"   INT                            NOT NULL,
    "to_stop_id"        INT                            NOT NULL,
    "bus_id"            BIGINT REFERENCES "bus" ("id") NOT NULL,
    FOREIGN KEY ("line_id", "current_stop_id", "to_stop_id") REFERENCES "line_path" ("line_id", "from_stop_id", "to_stop_id")
);

CREATE TABLE "reservation"
(
    "id"                BIGSERIAL PRIMARY KEY,
    "date_time"         TIMESTAMP                       NOT NULL,
    "user_id"           BIGINT REFERENCES "user" ("id") NOT NULL,
    "bus_id"            BIGINT REFERENCES "bus" ("id")  NOT NULL,
    /*
     * On peut aussi utiliser les "foreign key" de la table line_stop au lieu de stop,
     * mais il faudra ajouter un nouveau champ line_id
     */
    "departure_stop_id" INT REFERENCES "stop" ("id")    NOT NULL,
    "arrival_stop_id"   INT REFERENCES "stop" ("id")    NOT NULL
);

-- Notons qu'on a un ticket par "reservation_seat" et non par "reservation"
CREATE TABLE "reservation_seat"
(
    "id"             BIGSERIAL PRIMARY KEY,
    "reservation_id" BIGINT REFERENCES "reservation" ("id") NOT NULL,
    "seat_id"        SMALLINT REFERENCES "seat" ("id")      NOT NULL,
    /*
     * is_active vérifie la validité de la réservation tandis que on_bus servira d'indication
     * si la siège dans le bus est déjà occupé par le client qui l'a réservé.
     *
     * Par défaut is_active est TRUE, et on_bus est FALSE.
     * - Lorsqu'on entre dans le bus alors on_bus devient TRUE
     * - Sinon lorsqu'on y sort, is_active et on_bus deviennent FALSE
     */
    "is_active"      BOOLEAN DEFAULT TRUE                   NOT NULL,
    "on_bus"         BOOLEAN DEFAULT FALSE                  NOT NULL
);

CREATE TABLE "cancellation"
(
    "id"                  BIGSERIAL PRIMARY KEY,
    "reservation_seat_id" BIGINT REFERENCES "reservation_seat" ("id") NOT NULL
);

CREATE TABLE "notification"
(
    "id"           BIGSERIAL PRIMARY KEY,
    "user_id"      BIGINT REFERENCES "user" ("id")   NOT NULL,
    "next_user_id" BIGINT REFERENCES "user" ("id")   NOT NULL,
    "bus_id"       BIGINT REFERENCES "bus" ("id")    NOT NULL,
    "seat_id"      SMALLINT REFERENCES "seat" ("id") NOT NULL,
    "message"      VARCHAR                           NOT NULL,
    "sent_at"      TIMESTAMP                         NOT NULL,
    "is_accepted"  BOOLEAN
);
