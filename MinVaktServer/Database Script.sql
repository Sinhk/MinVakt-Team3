DROP TABLE IF EXISTS shift_assignment;
DROP TABLE IF EXISTS shift_department;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS shift;
DROP TABLE IF EXISTS employee_category;
DROP TABLE IF EXISTS department;

CREATE TABLE IF NOT EXISTS employee_category
(
  category_id          INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  category_name        VARCHAR(30)     NOT NULL,
  admin                BOOL                     DEFAULT FALSE,
  required_per_shift   INT                      DEFAULT 0,
  available_for_shifts BOOL                     DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS employee
(
  employee_id         INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  category_id         INT             NOT NULL,
  first_name          VARCHAR(30)     NOT NULL,
  last_name           VARCHAR(30)     NOT NULL,
  phone               INT             NOT NULL,
  email               VARCHAR(50)     NOT NULL,
  position_percentage INT             NOT NULL,
  passwd              VARCHAR(60),
  CONSTRAINT employee_category_fk FOREIGN KEY (category_id) REFERENCES employee_category (category_id)
);

CREATE TABLE IF NOT EXISTS shift (
  shift_id  INT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
  from_time DATETIME NOT NULL,
  to_time   DATETIME NOT NULL,
  comments  VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS shift_assignment
(
  shift_id       INT                 NOT NULL,
  employee_id    INT                 NOT NULL,
  responsible    BOOL DEFAULT FALSE  NOT NULL,
  change_request BOOL DEFAULT FALSE  NOT NULL,
  absent         BOOL DEFAULT FALSE  NOT NULL,
  CONSTRAINT shift_assignment_pk PRIMARY KEY (employee_id, shift_id),
  CONSTRAINT shift_assignment_employee_id_fk FOREIGN KEY (employee_id) REFERENCES employee (employee_id),
  CONSTRAINT shift_assignment_shift_id_fk FOREIGN KEY (shift_id) REFERENCES shift (shift_id)
    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS department (
  department_id   INT         NOT NULL PRIMARY KEY AUTO_INCREMENT,
  department_name VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS shift_department (
  shift_id      INT NOT NULL,
  department_id INT NOT NULL,
  CONSTRAINT shift_department_pk PRIMARY KEY (shift_id, department_id),
  CONSTRAINT shift_department_shift_fk FOREIGN KEY (shift_id) REFERENCES shift (shift_id),
  CONSTRAINT shift_department_department_fk FOREIGN KEY (department_id) REFERENCES department (department_id)
);

-- Test Data

INSERT INTO employee_category (category_name, admin) VALUES ('Administrasjon', 1);
INSERT INTO employee_category (category_name, required_per_shift) VALUES ('Sykepleier', 20);
INSERT INTO employee_category (category_name, required_per_shift) VALUES ('Helsefagarbeider', 30);
INSERT INTO employee_category (category_name) VALUES ('Assistent');

INSERT INTO employee (employee_id, category_id, first_name, last_name, phone, email, position_percentage)
VALUES (1, 1, 'Jennifer', 'Payne', '12345678', 'jpayne0@comcast.net', 100);
INSERT INTO employee (employee_id, category_id, first_name, last_name, phone, email, position_percentage)
VALUES (2, 2, 'Andrea', 'Ferguson', '12345678', 'aferguson1@blogger.com', 100);
INSERT INTO employee (employee_id, category_id, first_name, last_name, phone, email, position_percentage)
VALUES (3, 2, 'Patricia', 'Pierce', '12345678', 'ppierce2@flickr.com', 100);
INSERT INTO employee (employee_id, category_id, first_name, last_name, phone, email, position_percentage)
VALUES (4, 2, 'Nancy', 'Johnston', '12345678', 'njohnston3@angelfire.com', 100);
INSERT INTO employee (employee_id, category_id, first_name, last_name, phone, email, position_percentage)
VALUES (5, 3, 'Sarah', 'Morales', '12345678', 'smorales4@netscape.com', 100);
INSERT INTO employee (employee_id, category_id, first_name, last_name, phone, email, position_percentage)
VALUES (6, 3, 'Andrew', 'Smith', '12345678', 'asmith5@theglobeandmail.com', 100);
INSERT INTO employee (employee_id, category_id, first_name, last_name, phone, email, position_percentage)
VALUES (7, 4, 'Harry', 'Garcia', '12345678', 'hgarcia6@hc360.com', 100);
INSERT INTO employee (employee_id, category_id, first_name, last_name, phone, email, position_percentage)
VALUES (8, 4, 'Daniel', 'Ruiz', '12345678', 'druiz7@google.com', 100);
INSERT INTO employee (employee_id, category_id, first_name, last_name, phone, email, position_percentage)
VALUES (9, 1, 'Diane', 'Watkins', '12345678', 'dwatkins8@archive.org', 100);
INSERT INTO employee (employee_id, category_id, first_name, last_name, phone, email, position_percentage)
VALUES (10, 3, 'Aaron', 'Watkins', '12345678', 'awatkins9@sina.com.cn', 100);

INSERT INTO shift (from_time, to_time) VALUES ('2017-01-17 06:00', '2017-01-17 14:00');
INSERT INTO shift (from_time, to_time) VALUES ('2017-01-17 14:00', '2017-01-17 22:00');
INSERT INTO shift (from_time, to_time) VALUES ('2017-01-17 22:00', '2017-01-18 06:00');
INSERT INTO shift (from_time, to_time) VALUES ('2017-01-18 06:00', '2017-01-18 14:00');
INSERT INTO shift (from_time, to_time) VALUES ('2017-01-18 14:00', '2017-01-18 22:00');
INSERT INTO shift (from_time, to_time) VALUES ('2017-01-18 22:00', '2017-01-19 06:00');

INSERT INTO shift_assignment (shift_id, employee_id, responsible) VALUES (1, 2, FALSE);
INSERT INTO shift_assignment (shift_id, employee_id, responsible) VALUES (1, 3, FALSE);
INSERT INTO shift_assignment (shift_id, employee_id, responsible) VALUES (2, 4, FALSE);
INSERT INTO shift_assignment (shift_id, employee_id, responsible) VALUES (2, 5, FALSE);

INSERT INTO department (department_name) VALUES ('etg1');
INSERT INTO department (department_name) VALUES ('etg2');
INSERT INTO department (department_name) VALUES ('etg3');

INSERT INTO shift_department (shift_id, department_id) VALUES (1, 1);
INSERT INTO shift_department (shift_id, department_id) VALUES (2, 1);
INSERT INTO shift_department (shift_id, department_id) VALUES (3, 2);
INSERT INTO shift_department (shift_id, department_id) VALUES (4, 2);
INSERT INTO shift_department (shift_id, department_id) VALUES (5, 3);
INSERT INTO shift_department (shift_id, department_id) VALUES (6, 3);
