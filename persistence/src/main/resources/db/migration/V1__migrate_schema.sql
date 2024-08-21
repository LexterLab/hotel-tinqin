CREATE TABLE beds
(
    id           UUID         NOT NULL,
    bed_size     VARCHAR(255) NOT NULL,
    bed_capacity INTEGER      NOT NULL,
    CONSTRAINT pk_beds PRIMARY KEY (id)
);


CREATE TABLE rooms
(
    id            UUID           NOT NULL,
    room_no       VARCHAR(255)   NOT NULL,
    bathroom_type VARCHAR(255)   NOT NULL,
    floor         INTEGER        NOT NULL,
    price         DECIMAL(10, 2) NOT NULL,
    CONSTRAINT pk_rooms PRIMARY KEY (id)
);


CREATE TABLE room_beds
(
    bed_id  UUID NOT NULL,
    room_id UUID NOT NULL
);

ALTER TABLE room_beds
    ADD CONSTRAINT fk_room_beds_on_bed FOREIGN KEY (bed_id) REFERENCES beds (id);

ALTER TABLE room_beds
    ADD CONSTRAINT fk_room_beds_on_room FOREIGN KEY (room_id) REFERENCES rooms (id);

CREATE TABLE guests
(
    id                      UUID         NOT NULL,
    first_name              VARCHAR(255) NOT NULL,
    last_name               VARCHAR(255) NOT NULL,
    birthday                date         NOT NULL,
    id_card_no              VARCHAR(255) NOT NULL,
    id_card_validity        date         NOT NULL,
    id_card_issue_authority VARCHAR(255) NOT NULL,
    id_card_issue_date      date         NOT NULL,
    CONSTRAINT pk_guests PRIMARY KEY (id)
);

ALTER TABLE guests
    ADD CONSTRAINT uc_guests_idcardno UNIQUE (id_card_no);

CREATE TABLE bookings
(
    id         UUID                        NOT NULL,
    start_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    end_date   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    user_id    UUID                        NOT NULL,
    room_id    UUID,
    CONSTRAINT pk_bookings PRIMARY KEY (id)
);

CREATE TABLE guest_bookings
(
    booking_id UUID NOT NULL,
    guest_id   UUID NOT NULL
);

ALTER TABLE bookings
    ADD CONSTRAINT FK_BOOKINGS_ON_ROOM FOREIGN KEY (room_id) REFERENCES rooms (id);

ALTER TABLE guest_bookings
    ADD CONSTRAINT fk_gueboo_on_booking FOREIGN KEY (booking_id) REFERENCES bookings (id);

ALTER TABLE guest_bookings
    ADD CONSTRAINT fk_gueboo_on_guest FOREIGN KEY (guest_id) REFERENCES guests (id);

INSERT INTO public.beds (id, bed_capacity, bed_size) VALUES ('e2d6e8b0-1258-4213-9d1c-79e198831e3b', 2, 'SINGLE');
INSERT INTO public.beds (id, bed_capacity, bed_size) VALUES ('e18788fc-831a-4dbc-b708-ef781cf93e96', 4, 'DOUBLE');
INSERT INTO public.beds (id, bed_capacity, bed_size) VALUES ('1b1ae79d-129b-4c8b-8e2c-5b2d2d2b2838', 1, 'SMALL_SINGLE');
INSERT INTO public.beds (id, bed_capacity, bed_size) VALUES ('425953af-baa2-4251-a11f-3dee642f47dc', 2, 'SMALL_DOUBLE');
INSERT INTO public.beds (id, bed_capacity, bed_size) VALUES ('4f53298e-340f-4a9e-85bb-8a399ae53ebc', 15, 'KING_SIZE');

INSERT INTO public.rooms (id, bathroom_type, floor, price, room_no) VALUES ('923364b0-4ed0-4a7e-8c23-ceb5c238ceee', 'PRIVATE', 4, 20000.00, '201A');

INSERT INTO public.room_beds (room_id, bed_id) VALUES ('923364b0-4ed0-4a7e-8c23-ceb5c238ceee', 'e2d6e8b0-1258-4213-9d1c-79e198831e3b');

INSERT INTO public.bookings (id, end_date, start_date, user_id, room_id) VALUES ('4e754a8c-1cca-4abb-b49a-4a07fc98a751', '2025-08-13 16:07:24.284000', '2025-08-13 16:07:24.284000', '8eabb4ff-df5b-4e39-8642-0dcce375798c', '923364b0-4ed0-4a7e-8c23-ceb5c238ceee');

INSERT INTO public.guests (id, birthday, first_name, id_card_issue_authority, id_card_issue_date, id_card_no, id_card_validity, last_name) VALUES ('dd51c63d-1814-4cc5-9e42-eede0a5d18e1', '2021-08-13', 'Michael', 'Authority', '2021-01-01', '3232 3232 3232 3232', '2025-01-01', 'Jordan');

INSERT INTO public.guest_bookings (booking_id, guest_id) VALUES ('4e754a8c-1cca-4abb-b49a-4a07fc98a751', 'dd51c63d-1814-4cc5-9e42-eede0a5d18e1');