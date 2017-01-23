DROP TABLE IF EXISTS shift_overtime;
DROP TABLE IF EXISTS shift_assignment;
-- DROP TABLE IF EXISTS shift_department;
DROP TABLE IF EXISTS department_required_employees_per_category;
DROP TABLE IF EXISTS department_required_per_shift;
DROP TABLE IF EXISTS change_request;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS shift;
DROP TABLE IF EXISTS employee_category;
DROP TABLE IF EXISTS department;

CREATE TABLE IF NOT EXISTS employee_category (
  category_id          TINYINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  category_name        VARCHAR(30)     NOT NULL,
  admin                BOOL                     DEFAULT FALSE,
  required_per_shift   TINYINT                  DEFAULT 0,
  available_for_shifts BOOL                     DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS employee (
  employee_id         INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  category_id         TINYINT         NOT NULL,
  first_name          VARCHAR(30)     NOT NULL,
  last_name           VARCHAR(30)     NOT NULL,
  phone               INT             NOT NULL,
  email               VARCHAR(50)     NOT NULL UNIQUE,
  position_percentage TINYINT         NOT NULL,
  passwd              VARCHAR(60),
  enabled             BOOL                     DEFAULT TRUE,
  change_pw_on_logon  BOOL            NOT NULL DEFAULT TRUE,
  CONSTRAINT employee_category_fk FOREIGN KEY (category_id) REFERENCES employee_category (category_id)
);

CREATE TABLE IF NOT EXISTS shift (
  shift_id                    INT                   NOT NULL AUTO_INCREMENT PRIMARY KEY,
  responsible_employee_id     INT                   NOT NULL,
  from_time                   DATETIME              NOT NULL,
  to_time                     DATETIME              NOT NULL,
  CONSTRAINT shift_responsible_id_fk FOREIGN KEY (responsible_employee_id) REFERENCES employee (employee_id)
);

CREATE TABLE IF NOT EXISTS shift_assignment (
  shift_id            INT                 NOT NULL,
  employee_id         INT                 NOT NULL,
  absent              BOOL                NOT NULL,
  assigned            BOOL                NOT NULL DEFAULT FALSE,
  comment_for_absence VARCHAR(100),
  CONSTRAINT shift_assignment_pk PRIMARY KEY (employee_id, shift_id),
  CONSTRAINT shift_assignment_employee_id_fk FOREIGN KEY (employee_id) REFERENCES employee (employee_id),
  CONSTRAINT shift_assignment_shift_id_fk FOREIGN KEY (shift_id) REFERENCES shift (shift_id) ON DELETE CASCADE
#   CONSTRAINT shift_assignment_status_id_fk FOREIGN KEY (status_id) REFERENCES shift_status (status_id)
);

CREATE TABLE IF NOT EXISTS shift_overtime (
  shift_id      INT         NOT NULL,
  employee_id   INT         NOT NULL,
  minutes       MEDIUMINT   NOT NULL,
  CONSTRAINT shift_overtime_pk PRIMARY KEY (shift_id, employee_id),
  CONSTRAINT shift_overtime_fk FOREIGN KEY (shift_id, employee_id) REFERENCES shift_assignment (shift_id, employee_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS change_request (
  request_id        INT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
  shift_id          INT       NOT NULL,
  old_employee_id   INT       NOT NULL,
  new_employee_id   INT       NOT NULL,
  CONSTRAINT change_request_shift_id_fk FOREIGN KEY (shift_id) REFERENCES shift (shift_id),
  CONSTRAINT change_request_old_employee_id_fk FOREIGN KEY (old_employee_id) REFERENCES employee (employee_id),
  CONSTRAINT change_request_new_employee_id_fk FOREIGN KEY (new_employee_id) REFERENCES employee (employee_id)
);

CREATE TABLE IF NOT EXISTS department (
  department_id   INT         NOT NULL PRIMARY KEY AUTO_INCREMENT,
  department_name VARCHAR(30) NOT NULL
);

/*
CREATE TABLE IF NOT EXISTS shift_department (
  shift_id      INT NOT NULL,
  department_id INT NOT NULL,
  CONSTRAINT shift_department_pk PRIMARY KEY (shift_id, department_id),
  CONSTRAINT shift_department_shift_fk FOREIGN KEY (shift_id) REFERENCES shift (shift_id),
  CONSTRAINT shift_department_department_fk FOREIGN KEY (department_id) REFERENCES department (department_id)
);
*/

CREATE TABLE IF NOT EXISTS department_required_employees_per_category (
  department_id   TINYINT     NOT NULL,
  category_id     TINYINT     NOT NULL,
  amount          MEDIUMINT     NOT NULL,
  CONSTRAINT department_required_employees_pk PRIMARY KEY (department_id, category_id),
  CONSTRAINT department_required_employees_department_id_fk FOREIGN KEY (department_id) REFERENCES department (department_id),
  CONSTRAINT department_required_employees_category_id_fk FOREIGN KEY (category_id) REFERENCES category (category_id)
);

CREATE TABLE IF NOT EXISTS department_required_per_shift (
  department_id   TINYINT       NOT NULL,
  shift_id        INT           NOT NULL,
  amount          MEDIUMINT     NOT NULL,
  CONSTRAINT department_required_per_shift PRIMARY KEY (department_id, shift_id),
  CONSTRAINT department_required_per_shift_department_id_fk FOREIGN KEY (department_id) REFERENCES department (department_id),
  CONSTRAINT department_required_per_shift_shift_id_fk FOREIGN KEY (shift_id) REFERENCES shift (shift_id)
);

-- Test Data

INSERT INTO employee_category (category_name, admin) VALUES ('Administrasjon', 1);
INSERT INTO employee_category (category_name, required_per_shift) VALUES ('Sykepleier', 20);
INSERT INTO employee_category (category_name, required_per_shift) VALUES ('Helsefagarbeider', 30);
INSERT INTO employee_category (category_name) VALUES ('Assistent');

INSERT INTO employee (category_id, first_name, last_name, phone, email, position_percentage, passwd)
VALUES
  (1, 'admin', '', '12345678', 'admin@minvakt.no', 100, '$2a$04$c7YTJkh8TVGsmCNNWW7pXu0f/dmy6E6TdsCgX7dnZlJQP7DBfuKjq');
INSERT INTO employee (category_id, first_name, last_name, phone, email, position_percentage, passwd)
VALUES
  (2, 'user', '', '12345678', 'user@minvakt.no', 100, '$2a$06$vMO32hhPzSrnvM8tRYwMZ.mzxkrrtXHtsYmRNxESKiClLPtZGRtF6');
INSERT INTO employee (category_id, first_name, last_name, phone, email, position_percentage)
VALUES (1, 'Jennifer', 'Payne', '12345678', 'jpayne0@comcast.net', 100);
INSERT INTO employee (category_id, first_name, last_name, phone, email, position_percentage)
VALUES (2, 'Andrea', 'Ferguson', '12345678', 'aferguson1@blogger.com', 100);
INSERT INTO employee (category_id, first_name, last_name, phone, email, position_percentage)
VALUES (2, 'Patricia', 'Pierce', '12345678', 'ppierce2@flickr.com', 100);
INSERT INTO employee (category_id, first_name, last_name, phone, email, position_percentage)
VALUES (2, 'Nancy', 'Johnston', '12345678', 'njohnston3@angelfire.com', 100);
INSERT INTO employee (category_id, first_name, last_name, phone, email, position_percentage)
VALUES (3, 'Sarah', 'Morales', '12345678', 'smorales4@netscape.com', 100);
INSERT INTO employee (category_id, first_name, last_name, phone, email, position_percentage)
VALUES (3, 'Andrew', 'Smith', '12345678', 'asmith5@theglobeandmail.com', 100);
INSERT INTO employee (category_id, first_name, last_name, phone, email, position_percentage)
VALUES (4, 'Harry', 'Garcia', '12345678', 'hgarcia6@hc360.com', 100);
INSERT INTO employee (category_id, first_name, last_name, phone, email, position_percentage)
VALUES (4, 'Daniel', 'Ruiz', '12345678', 'druiz7@google.com', 100);
INSERT INTO employee (category_id, first_name, last_name, phone, email, position_percentage)
VALUES (1, 'Diane', 'Watkins', '12345678', 'dwatkins8@archive.org', 100);
INSERT INTO employee (category_id, first_name, last_name, phone, email, position_percentage)
VALUES (3, 'Aaron', 'Watkins', '12345678', 'awatkins9@sina.com.cn', 100);

INSERT INTO shift (responsible_employee_id, from_time, to_time) VALUES (4, '2017-01-17 06:00', '2017-01-17 14:00');
INSERT INTO shift (responsible_employee_id, from_time, to_time) VALUES (5, '2017-01-17 14:00', '2017-01-17 22:00');
INSERT INTO shift (responsible_employee_id, from_time, to_time) VALUES (6, '2017-01-17 22:00', '2017-01-18 06:00');
INSERT INTO shift (responsible_employee_id, from_time, to_time) VALUES (5, '2017-01-18 06:00', '2017-01-18 14:00');
INSERT INTO shift (responsible_employee_id, from_time, to_time) VALUES (4, '2017-01-18 14:00', '2017-01-18 22:00');
INSERT INTO shift (responsible_employee_id, from_time, to_time) VALUES (6, '2017-01-18 22:00', '2017-01-19 06:00');

INSERT INTO shift_assignment (shift_id, employee_id, responsible) VALUES (1, 2, FALSE);
INSERT INTO shift_assignment (shift_id, employee_id, responsible) VALUES (1, 3, FALSE);
INSERT INTO shift_assignment (shift_id, employee_id, responsible) VALUES (2, 4, FALSE);
INSERT INTO shift_assignment (shift_id, employee_id, responsible) VALUES (2, 5, FALSE);

INSERT INTO department (department_name) VALUES ('etg1');
INSERT INTO department (department_name) VALUES ('etg2');
INSERT INTO department (department_name) VALUES ('etg3');

/*
INSERT INTO shift_department (shift_id, department_id) VALUES (1, 1);
INSERT INTO shift_department (shift_id, department_id) VALUES (2, 1);
INSERT INTO shift_department (shift_id, department_id) VALUES (3, 2);
INSERT INTO shift_department (shift_id, department_id) VALUES (4, 2);
INSERT INTO shift_department (shift_id, department_id) VALUES (5, 3);
INSERT INTO shift_department (shift_id, department_id) VALUES (6, 3);
*/

INSERT INTO shift_overtime (shift_id, employee_id, minutes) VALUES (1, 3, 60);
INSERT INTO shift_overtime (shift_id, employee_id, minutes) VALUES (4, 5, 75);

INSERT INTO change_request (shift_id, old_employee_id, new_employee_id) VALUES (2, 3, 4);

INSERT INTO department_required_employees_per_category (department_id, category_id, amount) VALUES (1, 2, 3);
INSERT INTO department_required_employees_per_category (department_id, category_id, amount) VALUES (2, 2, 3);
INSERT INTO department_required_employees_per_category (department_id, category_id, amount) VALUES (2, 2, 3);

INSERT INTO department_required_per_shift (department_id, shift_id, amount) VALUES (1, 1, 10);
INSERT INTO department_required_per_shift (department_id, shift_id, amount) VALUES (2, 1, 10);
INSERT INTO department_required_per_shift (department_id, shift_id, amount) VALUES (3, 1, 10);
