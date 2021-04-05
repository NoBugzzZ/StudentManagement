DROP TABLE student IF EXISTS;

CREATE TABLE student (
  id INTEGER IDENTITY PRIMARY KEY,
  student_id varchar(15) UNIQUE NOT NULL,
  student_name varchar(32) NOT NULL,
  sex varchar(6) NOT NULL,
  birthday varchar(10) NOT NULL,
  native_place varchar(32) NOT NULL,
  department varchar(64) NOT NULL,
);