DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Shifts;
DROP TABLE IF EXISTS Users_Shifts;
DROP TABLE IF EXISTS Shift_type;
DROP TABLE IF EXISTS Type;
DROP TABLE IF EXISTS Department;
DROP TABLE IF EXISTS Shift_Department;

CREATE TABLE IF NOT EXISTS Users (
    user_id INT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NOT NULL,
    phone INT NOT NULL,
    email VARCHAR(50) NOT NULL,
    user_type_id INT NOT NULL,
    position_percentage INT NOT NULL,
    password BINARY(60) NOT NULL,
    CONSTRAINT user_pk PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS Shifts (
    shift_id INT NOT NULL AUTO_INCREMENT,
    from_time DATETIME NOT NULL,
    to_time DATETIME NOT NULL,
    shift_type_id INT NOT NULL,
    responsible_id INT NOT NULL,
    CONSTRAINT shift_pk PRIMARY KEY (shift_id)
);

CREATE TABLE IF NOT EXISTS Users_Shifts (
    user_id INT NOT NULL,
    shift_id INT NOT NULL,
    CONSTRAINT user_shift_pk PRIMARY KEY (user_id, shift_id)
);

CREATE TABLE IF NOT EXISTS Shift_type (
    shift_type_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL,
    CONSTRAINT shift_type_pk PRIMARY KEY (shift_type_id)
);

CREATE TABLE IF NOT EXISTS User_type (
    user_type_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL,
    CONSTRAINT type_pk PRIMARY KEY (user_type_id)
);

CREATE TABLE IF NOT EXISTS Department (
    department_id INT NOT NULL AUTO_INCREMENT,
    department_name VARCHAR(30) NOT NULL,
    CONSTRAINT department_pk PRIMARY KEY(department_id)
);

CREATE TABLE IF NOT EXISTS Shift_Department (
    shift_id INT NOT NULL,
    department_id INT NOT NULL,
    CONSTRAINT shift_department_pk PRIMARY KEY (shift_id, department_id)
);

-- Foreign keys
ALTER TABLE Users
    ADD CONSTRAINT user_fk FOREIGN KEY (user_type_id) REFERENCES User_type(user_type_id);

ALTER TABLE Users_Shifts
    ADD CONSTRAINT user_shift_fk1 FOREIGN KEY (user_id) REFERENCES Users(user_id);

ALTER TABLE Users_Shifts
    ADD CONSTRAINT user_shift_fk2 FOREIGN KEY (shift_id) REFERENCES Shifts(shift_id);

ALTER TABLE Shift_Department
    ADD CONSTRAINT shift_department_fk1 FOREIGN KEY(department_id) REFERENCES Department(department_id);

ALTER TABLE Shift_Department
    ADD CONSTRAINT shift_department_fk2 FOREIGN KEY(shift_id) REFERENCES Shifts(shift_id);

ALTER TABLE Shifts
    ADD CONSTRAINT shift_fk1 FOREIGN KEY (shift_type_id) REFERENCES Shift_type(shift_type_id);

ALTER TABLE Shifts
    ADD CONSTRAINT shift_fk2 FOREIGN KEY (responsible_id) REFERENCES Users(user_id);