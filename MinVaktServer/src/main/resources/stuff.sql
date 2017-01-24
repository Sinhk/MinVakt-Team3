SELECT
  shift.shift_id,
  shift.responsible_employee_id,
  department_required_per_shift.department_id,
  shift_assignment.employee_id,
  count(shift_assignment.employee_id)
FROM (shift
  LEFT JOIN shift_assignment ON shift.shift_id = shift_assignment.shift_id) LEFT JOIN department_required_per_shift
    ON shift_assignment.department_id = department_required_per_shift.department_id
GROUP BY shift.shift_id, department_required_per_shift.department_id;

SELECT
  shift.shift_id,
  shift_assignment.employee_id,
  shift_assignment.department_id,
  department_required_per_shift.amount,
  count(shift_assignment.employee_id) AS assigned
FROM shift
  NATURAL JOIN shift_assignment
  LEFT JOIN department_required_per_shift
    ON (department_required_per_shift.shift_id, department_required_per_shift.department_id) =
       (shift.shift_id, shift_assignment.department_id)
GROUP BY
  shift.shift_id,
  department_required_per_shift.department_id
HAVING
  assigned < department_required_per_shift.amount;

CREATE FUNCTION hoursThisWeek()
  RETURNS INT
  BEGIN

  END;

/*CREATE VIEW assigned_per_shift AS
SELECT
  shift.shift_id,
  shift.department_id,
  shift.required_employees,
  count(shift_assignment.employee_id) as assigned
FROM shift
  LEFT JOIN  shift_assignment ON shift.shift_id = shift_assignment.shift_id
GROUP BY
  shift.shift_id
HAVING
  assigned < shift.required_employees;
*/

SELECT
  assigned_per_shift.shift_id,
  assigned_per_shift.department_id,
  required_employees - assigned_per_shift.assigned AS missing,
  deq.amount,
  deq.category_id
FROM
  assigned_per_shift
  LEFT JOIN employee ON employee.
LEFT
  JOIN department_required_employees_per_category deq ON deq.department_id = assigned_per_shift.department_id
GROUP BY
  shift_id,
  deq.category_id;


SELECT
  *,
  GREATEST(0, depreq - em) AS missCat
#sum(missing- (depreq -em))
FROM (
       SELECT
         assigned_per_shift.shift_id,
         assigned_per_shift.department_id,
         employee.category_id,
         required_employees - assigned_per_shift.num_assigned                   AS missing,
         count(employee.employee_id)                                            AS em,
         ROUND(required_employees * employee_category.required_per_shift / 100) AS depreq
       FROM assigned_per_shift
         LEFT JOIN shift_assignment ON shift_assignment.shift_id = assigned_per_shift.shift_id
         LEFT JOIN employee ON employee.employee_id = shift_assignment.employee_id
         LEFT JOIN employee_category ON employee.category_id = employee_category.category_id
       GROUP BY assigned_per_shift.shift_id, employee.category_id) AS reqd
WHERE missing > 0
GROUP BY shift_id; #AND category_id = 2 or category_id is NULL ;

#HAVING missing > 0;


SELECT
  shift.shift_id,
  shift.department_id,
  shift.required_employees,
  employee.category_id,
  depreq.amount,
  count(shift_assignment.employee_id) AS assigned
FROM shift
  LEFT JOIN shift_assignment ON shift.shift_id = shift_assignment.shift_id
  LEFT JOIN employee ON shift_assignment.employee_id = employee.employee_id
  LEFT JOIN department_required_employees_per_category depreq
    ON (depreq.category_id, depreq.department_id) = (employee.category_id, shift.department_id)
GROUP BY
  shift.shift_id,
  employee.category_id
HAVING
  assigned < shift.required_employees AND (assigned < depreq.amount);

SELECT *
FROM shift
  NATURAL JOIN shift_assignment
  NATURAL JOIN employee
  NATURAL JOIN employee_category
  NATURAL JOIN department;