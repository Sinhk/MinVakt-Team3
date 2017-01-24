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

SELECT *
FROM (
       SELECT
         ass.shift_id,
         num_missing,
         sum(missing) AS spesific_missing
       FROM missing_per_shift_category mis
         LEFT JOIN assigned_per_shift ass ON mis.shift_id = ass.shift_id
       WHERE category_id != 3
       GROUP BY ass.shift_id
       HAVING spesific_missing < num_missing) AS stuff RIGHT JOIN shift_assignment on stuff.shift_id = shift_assignment.shift_id
WHERE employee_id != 9
GROUP BY shift_assignment.shift_id;





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