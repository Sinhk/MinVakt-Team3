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

INSERT INTO shift (responsible_employee_id, from_time, to_time)
VALUES (4, '2017-01-17 06:00:00', '2017-01-17 14:00:00');
INSERT INTO shift (responsible_employee_id, from_time, to_time)
VALUES (5, '2017-01-17 14:00:00', '2017-01-17 22:00:00');
INSERT INTO shift (responsible_employee_id, from_time, to_time)
VALUES (6, '2017-01-17 22:00:00', '2017-01-18 06:00:00');
INSERT INTO shift (responsible_employee_id, from_time, to_time)
VALUES (5, '2017-01-18 06:00:00', '2017-01-18 14:00:00');
INSERT INTO shift (responsible_employee_id, from_time, to_time)
VALUES (4, '2017-01-18 14:00:00', '2017-01-18 22:00:00');
INSERT INTO shift (responsible_employee_id, from_time, to_time)
VALUES (6, '2017-01-18 22:00:00', '2017-01-19 06:00:00');

INSERT INTO shift_assignment (shift_id, employee_id) VALUES (1, 2);
INSERT INTO shift_assignment (shift_id, employee_id) VALUES (1, 3);
INSERT INTO shift_assignment (shift_id, employee_id) VALUES (2, 4);
INSERT INTO shift_assignment (shift_id, employee_id) VALUES (2, 5);

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

INSERT INTO shift_overtime (shift_assignment_id, minutes) VALUES (2, 60);
INSERT INTO shift_overtime (shift_assignment_id, minutes) VALUES (4, 75);

INSERT INTO change_request (shift_id, old_employee_id, new_employee_id) VALUES (2, 3, 4);

INSERT INTO department_required_employees_per_category (department_id, category_id, amount) VALUES (1, 2, 3);
INSERT INTO department_required_employees_per_category (department_id, category_id, amount) VALUES (2, 2, 3);
INSERT INTO department_required_employees_per_category (department_id, category_id, amount) VALUES (3, 2, 3);

INSERT INTO department_required_per_shift (department_id, shift_id, amount) VALUES (1, 1, 10);
INSERT INTO department_required_per_shift (department_id, shift_id, amount) VALUES (2, 1, 10);
INSERT INTO department_required_per_shift (department_id, shift_id, amount) VALUES (3, 1, 10);
