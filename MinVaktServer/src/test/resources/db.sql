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
  category_id          SMALLINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  category_name        VARCHAR(30)     NOT NULL,
  admin                BOOL                     DEFAULT FALSE,
  required_per_shift   SMALLINT                  DEFAULT 0,
  available_for_shifts BOOL                     DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS employee (
  employee_id         INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
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
  shift_id                    INT                   NOT NULL AUTO_INCREMENT PRIMARY KEY,
  responsible_employee_id     INT                   NOT NULL,
  from_time                   DATETIME              NOT NULL,
  to_time                     DATETIME              NOT NULL,
  CONSTRAINT shift_responsible_id_fk FOREIGN KEY (responsible_employee_id) REFERENCES employee (employee_id)
);

CREATE TABLE IF NOT EXISTS shift_assignment (
  id                  INT           AUTO_INCREMENT PRIMARY KEY,
  shift_id            INT  NOT NULL,
  employee_id         INT  NOT NULL,
  absent              BOOL NOT NULL DEFAULT FALSE,
  assigned            BOOL NOT NULL DEFAULT FALSE,
  comment_for_absence VARCHAR(100),
  CONSTRAINT shift_assignment_employee_id_fk FOREIGN KEY (employee_id) REFERENCES employee (employee_id),
  CONSTRAINT shift_assignment_shift_id_fk FOREIGN KEY (shift_id) REFERENCES shift (shift_id)
  --CONSTRAINT shift_assignment_status_id_fk FOREIGN KEY (status_id) REFERENCES shift_status (status_id)
);

CREATE TABLE IF NOT EXISTS shift_overtime (
  id                  INT           AUTO_INCREMENT PRIMARY KEY,
  shift_assignment_id      INT         NOT NULL,
  minutes       MEDIUMINT   NOT NULL,
  CONSTRAINT shift_overtime_fk FOREIGN KEY (shift_assignment_id) REFERENCES shift_assignment (id)
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
  department_id   SMALLINT         AUTO_INCREMENT NOT NULL PRIMARY KEY,
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

CREATE TABLE IF NOT EXISTS department_required_per_shift (
  id                  INT           AUTO_INCREMENT PRIMARY KEY,
  department_id   SMALLINT       NOT NULL,
  shift_id        INT           NOT NULL,
  amount          SMALLINT     NOT NULL,
  CONSTRAINT department_required_per_shift_department_id_fk FOREIGN KEY (department_id) REFERENCES department (department_id),
  CONSTRAINT department_required_per_shift_shift_id_fk FOREIGN KEY (shift_id) REFERENCES shift (shift_id)
);