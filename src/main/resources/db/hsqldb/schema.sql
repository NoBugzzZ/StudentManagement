DROP TABLE student IF EXISTS;

CREATE TABLE student (
  id INTEGER IDENTITY PRIMARY KEY,
  student_id varchar(15) UNIQUE,
  student_name varchar(32),
  sex varchar(6),
  birthday varchar(10),
  native_place varchar(32),
  department varchar(64),
  phone varchar(32)
);