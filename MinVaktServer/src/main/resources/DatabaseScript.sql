DROP TABLE IF EXISTS shift_overtime;
DROP TABLE IF EXISTS shift_assignment;
-- DROP TABLE IF EXISTS shift_department;
DROP TABLE IF EXISTS department_required_employees_per_category;
DROP TABLE IF EXISTS department_required_per_shift;
DROP TABLE IF EXISTS change_request;
DROP TABLE IF EXISTS shift;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS employee_category;
DROP TABLE IF EXISTS department;

CREATE TABLE IF NOT EXISTS employee_category (
  category_id          SMALLINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  category_name        VARCHAR(30)     NOT NULL,
  admin                BOOL                     DEFAULT FALSE,
  required_per_shift   SMALLINT                  DEFAULT 0,
  available_for_shifts BOOL                     DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS employee (
  employee_id         INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  category_id         SMALLINT        NOT NULL,
  first_name          VARCHAR(30)     NOT NULL,
  last_name           VARCHAR(30)     NOT NULL,
  phone               INT             NOT NULL,
  email               VARCHAR(50)     NOT NULL UNIQUE,
  position_percentage SMALLINT        NOT NULL,
  passwd              VARCHAR(60),
  enabled             BOOL                     DEFAULT TRUE,
  change_pw_on_logon  BOOL            NOT NULL DEFAULT TRUE,
  CONSTRAINT employee_category_fk FOREIGN KEY (category_id) REFERENCES employee_category (category_id)
);

CREATE TABLE IF NOT EXISTS shift (
  shift_id                INT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
  responsible_employee_id INT,
  from_time               DATETIME NOT NULL,
  to_time                 DATETIME NOT NULL,
  department_id           SMALLINT NOT NULL,
  required_employees      SMALLINT NOT NULL DEFAULT 5,
  CONSTRAINT shift_responsible_id_fk FOREIGN KEY (responsible_employee_id) REFERENCES employee (employee_id)
);

CREATE TABLE IF NOT EXISTS shift_assignment (
  id                  INT            AUTO_INCREMENT PRIMARY KEY,
  shift_id            INT  NOT NULL,
  employee_id         INT  NOT NULL,
  absent              BOOL NOT NULL  DEFAULT FALSE,
  assigned            BOOL NOT NULL  DEFAULT TRUE,
  available           BOOL NOT NULL  DEFAULT TRUE,
  comment_for_absence VARCHAR(100),
  CONSTRAINT shift_assignment_employee_id_fk FOREIGN KEY (employee_id) REFERENCES employee (employee_id),
  CONSTRAINT shift_assignment_shift_id_fk FOREIGN KEY (shift_id) REFERENCES shift (shift_id) ON DELETE CASCADE,
  CONSTRAINT unique_shift_employee UNIQUE (shift_id,employee_id)
  #   CONSTRAINT shift_assignment_status_id_fk FOREIGN KEY (status_id) REFERENCES shift_status (status_id)
);

/*CREATE TABLE IF NOT EXISTS shift_status (
  status_id                  INT            AUTO_INCREMENT PRIMARY KEY,

);*/

CREATE TABLE IF NOT EXISTS shift_overtime (
  id                  INT           AUTO_INCREMENT PRIMARY KEY,
  shift_assignment_id      INT         NOT NULL,
  minutes       MEDIUMINT   NOT NULL,
  CONSTRAINT shift_overtime_fk FOREIGN KEY (shift_assignment_id) REFERENCES shift_assignment (id) ON DELETE CASCADE
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
  department_id   SMALLINT         NOT NULL PRIMARY KEY AUTO_INCREMENT,
  department_name VARCHAR(30)     NOT NULL

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
  id                  INT           AUTO_INCREMENT PRIMARY KEY,
  department_id   SMALLINT       NOT NULL,
  category_id     SMALLINT       NOT NULL,
  amount          SMALLINT     NOT NULL,
  CONSTRAINT department_required_employees_department_id_fk FOREIGN KEY (department_id) REFERENCES department (department_id),
  CONSTRAINT department_required_employees_category_id_fk FOREIGN KEY (category_id) REFERENCES employee_category (category_id)
);

/*CREATE TABLE IF NOT EXISTS department_required_per_shift (
  id                  INT           AUTO_INCREMENT PRIMARY KEY,
  department_id   SMALLINT       NOT NULL,
  shift_id        INT           NOT NULL,
  amount          SMALLINT     NOT NULL,
  CONSTRAINT department_required_per_shift_department_id_fk FOREIGN KEY (department_id) REFERENCES department (department_id),
  CONSTRAINT department_required_per_shift_shift_id_fk FOREIGN KEY (shift_id) REFERENCES shift (shift_id),
  CONSTRAINT department_shift_unique UNIQUE (department_id,shift_id)
);*/

ALTER TABLE shift
  ADD CONSTRAINT shift_department_id_fk FOREIGN KEY (department_id) REFERENCES department (department_id);


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

INSERT INTO department (department_name) VALUES ('etg1');
INSERT INTO department (department_name) VALUES ('etg2');
INSERT INTO department (department_name) VALUES ('etg3');

/*INSERT INTO shift (responsible_employee_id, from_time, to_time, department_id) VALUES (4, '2017-01-17 07:30', '2017-01-17 15:30',1);
INSERT INTO shift (responsible_employee_id, from_time, to_time, department_id) VALUES (5, '2017-01-17 15:00', '2017-01-17 22:30',1);
INSERT INTO shift (responsible_employee_id, from_time, to_time, department_id) VALUES (6, '2017-01-17 22:00', '2017-01-18 08:00',1);
INSERT INTO shift (responsible_employee_id, from_time, to_time, department_id) VALUES (5, '2017-01-18 07:30', '2017-01-18 15:30',1);
INSERT INTO shift (responsible_employee_id, from_time, to_time, department_id) VALUES (4, '2017-01-18 15:00', '2017-01-18 22:30',1);
INSERT INTO shift (responsible_employee_id, from_time, to_time, department_id) VALUES (6, '2017-01-18 22:00', '2017-01-19 08:00',1);*/

INSERT INTO shift (from_time,to_time,department_id)
  select TIMESTAMP (a.Date,b.from_time) as from_time, if(GREATEST(to_time,from_time) = from_time,TIMESTAMP (DATE_ADD(a.Date,INTERVAL 1 DAY),b.to_time),TIMESTAMP (a.Date,b.to_time)) as to_time,2 as department
  from (
         select MAKEDATE(year(now()),1) + INTERVAL (a.a + (10 * b.a) + (100 * c.a)) DAY as Date
         from (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as a
           cross join (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as b
           cross join (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as c
       ) a
    JOIN (SELECT
            ('06:00') AS from_time,
            ('14:00') AS to_time
          UNION ALL
          SELECT
            ('14:00') AS from_time,
            ('22:00') AS to_time
          UNION ALL
          SELECT
            ('22:00') AS from_time,
            ('06:00') AS to_time) b
  where a.Date between '2017-01-01' and '2017-02-24';
#where a.Date between MAKEDATE(year(now()),1) and LAST_DAY(DATE_ADD(NOW(), INTERVAL 12-MONTH(NOW()) MONTH));


INSERT INTO shift_assignment (shift_id, employee_id) VALUES (1, 2);
INSERT INTO shift_assignment (shift_id, employee_id) VALUES (1, 3);
INSERT INTO shift_assignment (shift_id, employee_id) VALUES (2, 4);
INSERT INTO shift_assignment (shift_id, employee_id) VALUES (2, 5);

/*
INSERT INTO shift_department (shift_id, department_id) VALUES (1, 1);
INSERT INTO shift_department (shift_id, department_id) VALUES (2, 1);
INSERT INTO shift_department (shift_id, department_id) VALUES (3, 2);
INSERT INTO shift_department (shift_id, department_id) VALUES (4, 2);
INSERT INTO shift_department (shift_id, department_id) VALUES (5, 3);
INSERT INTO shift_department (shift_id, department_id) VALUES (6, 3);
*/

INSERT INTO shift_overtime (shift_assignment_id, minutes) VALUES (2, 60);
INSERT INTO shift_overtime (shift_assignment_id, minutes) VALUES (4, 75);

INSERT INTO change_request (shift_id, old_employee_id, new_employee_id) VALUES (2, 3, 4);

INSERT INTO department_required_employees_per_category (department_id, category_id, amount) VALUES (1, 2, 3);
INSERT INTO department_required_employees_per_category (department_id, category_id, amount) VALUES (2, 2, 3);
INSERT INTO department_required_employees_per_category (department_id, category_id, amount) VALUES (3, 2, 3);

#INSERT INTO department_required_per_shift (department_id, shift_id, amount) SELECT department.department_id, shift.shift_id,10 FROM shift JOIN department


CREATE OR REPLACE VIEW assigned_per_shift AS
  SELECT
    shift.*,
    count(shift_assignment.employee_id) AS num_assigned,
    GREATEST(0, required_employees - count(shift_assignment.employee_id)) as num_missing
  FROM shift
    LEFT JOIN shift_assignment ON (shift.shift_id,FALSE ,TRUE) = (shift_assignment.shift_id, shift_assignment.absent,shift_assignment.assigned)
  GROUP BY
    shift.shift_id;

CREATE OR REPLACE VIEW missing_per_shift_category AS
  SELECT
    shift.shift_id,
    employee_category.category_id,
    count(employee.employee_id)                                                                  AS count_assigned,
    ROUND(shift.required_employees *
          employee_category.required_per_shift / 100)                                            AS count_required,
    GREATEST(0, ROUND(shift.required_employees *
                      employee_category.required_per_shift / 100) - count(employee.employee_id)) AS missing
  FROM shift
    JOIN employee_category
    LEFT JOIN shift_assignment  ON (shift.shift_id,FALSE ,TRUE) = (shift_assignment.shift_id, shift_assignment.absent,shift_assignment.assigned)
    LEFT JOIN employee
      ON (employee_category.category_id, shift_assignment.employee_id) = (employee.category_id, employee.employee_id)
  WHERE available_for_shifts = TRUE
  GROUP BY shift.shift_id, employee_category.category_id;

CREATE OR REPLACE VIEW employee_time_worked_week as
  SELECT
    employee.employee_id,
    employee.position_percentage,
    TIME_FORMAT (sum(TIMEDIFF(to_time,from_time)),'%H:%i') as time_worked,
    WEEK(from_time,3) as week_num
  FROM employee
    LEFT JOIN shift_assignment ON employee.employee_id = shift_assignment.employee_id
    LEFT JOIN shift ON shift_assignment.shift_id = shift.shift_id
  #WHERE WEEK(from_time,3) = WEEK(NOW(),3) OR from_time is NULL
  GROUP BY employee.employee_id, WEEK(from_time,3);