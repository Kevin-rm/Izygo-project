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
    "id"    SERIAL PRIMARY KEY,
    "label" VARCHAR(50) UNIQUE NOT NULL
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
    "employee_id" BIGINT REFERENCES "user" ("id") NOT NULL,
    "is_terminus" BOOLEAN DEFAULT FALSE           NOT NULL,
    PRIMARY KEY ("line_id", "stop_id"),
    UNIQUE ("employee_id")
);

CREATE TABLE "line_path"
(
    "line_id"            INT REFERENCES "line" ("id") NOT NULL,
    "from_stop_id"       INT REFERENCES "stop" ("id") NOT NULL,
    "to_stop_id"         INT REFERENCES "stop" ("id") NOT NULL,
    "estimated_duration" SMALLINT                     NOT NULL,
    PRIMARY KEY ("line_id", "from_stop_id", "to_stop_id"),
    FOREIGN KEY ("line_id", "from_stop_id") REFERENCES "line_stop" ("line_id", "stop_id"),
    FOREIGN KEY ("line_id", "to_stop_id") REFERENCES "line_stop" ("line_id", "stop_id"),
    CHECK ("from_stop_id" != "to_stop_id")
);

CREATE TABLE "bus"
(
    "id"              BIGSERIAL PRIMARY KEY,
    "license_plate"   VARCHAR(15)                  NOT NULL,
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
    "id"             BIGSERIAL PRIMARY KEY,
    "date_time"      TIMESTAMP                       NOT NULL,
    "user_id"        BIGINT REFERENCES "user" ("id") NOT NULL,
    "bus_id"         BIGINT REFERENCES "bus" ("id")  NOT NULL,
    "departure_stop" INT REFERENCES "stop" ("id")    NOT NULL,
    "arrival_stop"   INT REFERENCES "stop" ("id")    NOT NULL
);

CREATE TABLE "reservation_seat"
(
    "id"             BIGSERIAL PRIMARY KEY,
    "seat_id"        SMALLINT REFERENCES "seat" ("id")      NOT NULL,
    "reservation_id" BIGINT REFERENCES "reservation" ("id") NOT NULL,
    "is_active"      BOOLEAN DEFAULT TRUE                   NOT NULL,
    "on_bus"         BOOLEAN DEFAULT FALSE                  NOT NULL
);

CREATE TABLE "cancellation"
(
    "id"                  BIGSERIAL PRIMARY KEY,
    "reservation_seat_id" BIGINT REFERENCES "reservation_seat" ("id") NOT NULL,
    "is_active"           BOOLEAN DEFAULT FALSE                       NOT NULL
);

CREATE TABLE "notification"
(
    "id"           BIGSERIAL PRIMARY KEY,
    "user_id"      BIGINT REFERENCES "user" ("id")   NOT NULL,
    "next_user_id" BIGINT REFERENCES "user" ("id")   NOT NULL,
    "bus_id"       BIGINT REFERENCES "bus" ("id")    NOT NULL,
    "seat_id"      SMALLINT REFERENCES "seat" ("id") NOT NULL,
    "message"      VARCHAR(100)                      NOT NULL,
    "sent_at"      TIMESTAMP                         NOT NULL,
    "is_accepted"  BOOLEAN
);
