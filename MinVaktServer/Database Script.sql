DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Shifts;
DROP TABLE IF EXISTS Type;
DROP TABLE IF EXISTS Department;
DROP TABLE IF EXISTS Shift_Department;

CREATE TABLE IF NOT EXISTS Users (
    user_id INT NOT NULL,
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NOT NULL,
    phone INT NOT NULL,
    email VARCHAR(50) NOT NULL,
    type_id INT NOT NULL,
    salt VARCHAR(100) NOT NULL,
    hash VARCHAR(100) NOT NULL,
    PRIMARY KEY (user_id),
    FOREIGN KEY (type_id) REFERENCES Type(type_id)
    );

CREATE TABLE IF NOT EXISTS Shifts (
    shift_id INT NOT NULL,
    from_time DATETIME NOT NULL,
    to_time DATETIME NOT NULL,
    user_id INT NOT NULL,
    responsible INT NOT NULL,
    PRIMARY KEY (shift_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
    );

CREATE TABLE IF NOT EXISTS Type (
    type_id INT NOT NULL,
    name VARCHAR(30) NOT NULL,
    PRIMARY KEY (type_id)
    );

CREATE TABLE IF NOT EXISTS Department (
    department_id INT NOT NULL,
    department_name VARCHAR(30) NOT NULL,
    PRIMARY KEY(department_id)
    );

CREATE TABLE IF NOT EXISTS Shift_Department (
    shift_id INT NOT NULL,
    department_id INT NOT NULL,
    PRIMARY KEY(shift_id),
    FOREIGN KEY(department_id) REFERENCES Department(department_id)
    );