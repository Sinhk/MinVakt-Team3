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
WHERE employee_id != 15
GROUP BY shift_assignment.shift_id;

SELECT * FROM (SELECT
                 ('07:30') AS from_time,
                 ('15:30') AS to_time
               UNION ALL
               SELECT
                 ('15:00') AS from_time,
                 ('22:30') AS to_time
               UNION ALL
               SELECT
                 ('22:00') AS from_time,
                 ('08:00') AS to_time) b;


SELECT
  DISTINCT employee.employee_id,
  ifnull((employee.position_percentage * 40 / 100) -  time_worked, employee.position_percentage * 40 / 100) as time_missing,
  missing,
  NOT ISNULL(wish_asig.shift_id) as wish
  FROM employee
    LEFT JOIN shift theshift on theshift.shift_id = 341
    LEFT JOIN employee_category on employee.category_id = employee_category.category_id
    LEFT JOIN employee_time_worked_week etww on (etww.employee_id, etww.week_num) = (employee.employee_id, WEEK(theshift.from_time,3))
    LEFT JOIN missing_per_shift_category mpsc on (mpsc.shift_id, mpsc.category_id) = (341, employee.category_id)
    LEFT JOIN shift_assignment wish_asig on (wish_asig.employee_id, wish_asig.shift_id) = (employee.employee_id,341)
WHERE available_for_shifts = TRUE
ORDER BY mpsc.missing DESC, wish DESC, time_missing DESC;

SELECT employee

SELECT
  *
FROM shift_assignment
LEFT JOIN shift on shift_assignment.shift_id = shift.shift_id
WHERE shift.from_time

SELECT
  employee.employee_id,
  employee.position_percentage,
  TIME_FORMAT (sum(TIMEDIFF(to_time,from_time)),'%H:%i') as time_worked,
  ifnull((employee.position_percentage * 35 / 100) -  TIME_FORMAT(sum(TIMEDIFF(to_time,from_time)),'%H:%i'),employee.position_percentage * 35 / 100) as time_missing,
  WEEK(from_time,3) as week_num
FROM employee
  LEFT JOIN shift_assignment ON employee.employee_id = shift_assignment.employee_id
  LEFT JOIN shift ON shift_assignment.shift_id = shift.shift_id
#WHERE WEEK(from_time,3) = WEEK(NOW(),3) OR from_time is NULL
GROUP BY employee.employee_id, WEEK(from_time,3)
ORDER BY time_missing DESC;



SELECT *,
FROM employee
  LEFT JOIN employee_category ON employee.category_id = employee_category.category_id
  LEFT JOIN shift_assignment ON employee.employee_id = shift_assignment.employee_id
  LEFT JOIN shift on shift_assignment.shift_id = shift.shift_id
WHERE employee_category.available_for_shifts = TRUE AND shift.shift_id != 3 AND NOT shift.from_time BETWEEN '$today 00:00:00' AND '$today 23:59:59'



SELECT
  ass.shift_id,
  num_missing,
  sum(missing) AS spesific_missing
FROM missing_per_shift_category mis
  LEFT JOIN assigned_per_shift ass ON mis.shift_id = ass.shift_id
WHERE ass.shift_id = 3
GROUP BY ass.shift_id
HAVING (spesific_missing < num_missing) AS stuff RIGHT JOIN shift_assignment on stuff.shift_id = shift_assignment.shift_id


SELECT *
FROM shift
  LEFT JOIN missing_per_shift_category mis on mis.shift_id = shift.shift_id
WHERE shift.shift_id = 2;


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