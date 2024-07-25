CREATE TYPE BED AS ENUM ('', 'single', 'double', 'smallSingle', 'smallDouble', 'kingSize');
CREATE TYPE BATHROOM_TYPE AS ENUM ('', 'private', 'shared');

CREATE TABLE beds (
    id UUID PRIMARY KEY,
    bed_size BED NOT NULL,
    bed_capacity INT NOT NULL
);

CREATE TABLE rooms (
    id UUID PRIMARY KEY,
    room_no VARCHAR(4),
    room_bathroom_type BATHROOM_TYPE NOT NULL,
    floor INT NOT NULL,
    price NUMERIC(12,2) NOT NULL
);

CREATE TABLE room_beds (
    bed_id UUID,
    room_id UUID,
    FOREIGN KEY (room_id) REFERENCES rooms(id),
    FOREIGN KEY (bed_id) REFERENCES beds(id)
);

CREATE TABLE users (
      id UUID PRIMARY KEY,
      email VARCHAR(100) NOT NULL,
      password VARCHAR(255) NOT NULL,
      first_name VARCHAR(20) NOT NULL,
      last_name VARCHAR(20) NOT NULL,
      phone_no CHAR(14) NOT NULL
);

CREATE TABLE guests (
    id UUID PRIMARY KEY,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    birthday DATE NOT NULL,
    id_card_no TEXT UNIQUE NOT NULL,
    id_card_validity DATE NOT NULL,
    id_card_issue_authority VARCHAR(50) NOT NULL,
    id_card_issue_date DATE NOT NULL
);

CREATE TABLE bookings (
  id UUID PRIMARY KEY,
  start_date TIMESTAMP NOT NULL,
  end_date TIMESTAMP NOT NULL,
  room_id UUID NOT NULL,
  user_id UUID NOT NULL,
  FOREIGN KEY (room_id) REFERENCES rooms(id),
  FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE guest_bookings (
      booking_id UUID NOT NULL,
      guest_id UUID,
      FOREIGN KEY (booking_id) REFERENCES bookings(id),
      FOREIGN KEY (guest_id) REFERENCES guests(id)
);