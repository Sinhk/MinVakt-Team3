/*
 * This file is generated by jOOQ.
*/
package minvakt.datamodel;


import minvakt.datamodel.tables.*;

import javax.annotation.Generated;


/**
 * Convenience access to all tables in g_scrum03
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>g_scrum03.change_request</code>.
     */
    public static final ChangeRequest CHANGE_REQUEST = minvakt.datamodel.tables.ChangeRequest.CHANGE_REQUEST;

    /**
     * The table <code>g_scrum03.department</code>.
     */
    public static final Department DEPARTMENT = minvakt.datamodel.tables.Department.DEPARTMENT;

    /**
     * The table <code>g_scrum03.department_required_employees_per_category</code>.
     */
    public static final DepartmentRequiredEmployeesPerCategory DEPARTMENT_REQUIRED_EMPLOYEES_PER_CATEGORY = minvakt.datamodel.tables.DepartmentRequiredEmployeesPerCategory.DEPARTMENT_REQUIRED_EMPLOYEES_PER_CATEGORY;

    /**
     * The table <code>g_scrum03.department_required_per_shift</code>.
     */
    public static final DepartmentRequiredPerShift DEPARTMENT_REQUIRED_PER_SHIFT = minvakt.datamodel.tables.DepartmentRequiredPerShift.DEPARTMENT_REQUIRED_PER_SHIFT;

    /**
     * The table <code>g_scrum03.employee</code>.
     */
    public static final Employee EMPLOYEE = minvakt.datamodel.tables.Employee.EMPLOYEE;

    /**
     * The table <code>g_scrum03.employee_category</code>.
     */
    public static final EmployeeCategory EMPLOYEE_CATEGORY = minvakt.datamodel.tables.EmployeeCategory.EMPLOYEE_CATEGORY;

    /**
     * The table <code>g_scrum03.shift</code>.
     */
    public static final Shift SHIFT = minvakt.datamodel.tables.Shift.SHIFT;

    /**
     * The table <code>g_scrum03.shift_assignment</code>.
     */
    public static final ShiftAssignment SHIFT_ASSIGNMENT = minvakt.datamodel.tables.ShiftAssignment.SHIFT_ASSIGNMENT;

    /**
     * The table <code>g_scrum03.shift_overtime</code>.
     */
    public static final ShiftOvertime SHIFT_OVERTIME = minvakt.datamodel.tables.ShiftOvertime.SHIFT_OVERTIME;
}
