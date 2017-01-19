/*
 * This file is generated by jOOQ.
*/
package minvakt.datamodel;


import minvakt.datamodel.tables.*;
import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * This class is generated by jOOQ.
 */
@Generated(
        value = {
                "http://www.jooq.org",
                "jOOQ version:3.9.0"
        },
        comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class GScrum03 extends SchemaImpl {

    private static final long serialVersionUID = 1670301921;

    /**
     * The reference instance of <code>g_scrum03</code>
     */
    public static final GScrum03 G_SCRUM03 = new GScrum03();

    /**
     * The table <code>g_scrum03.change_request</code>.
     */
    public final ChangeRequest CHANGE_REQUEST = minvakt.datamodel.tables.ChangeRequest.CHANGE_REQUEST;

    /**
     * The table <code>g_scrum03.department</code>.
     */
    public final Department DEPARTMENT = minvakt.datamodel.tables.Department.DEPARTMENT;

    /**
     * The table <code>g_scrum03.department_required_employees_per_category</code>.
     */
    public final DepartmentRequiredEmployeesPerCategory DEPARTMENT_REQUIRED_EMPLOYEES_PER_CATEGORY = minvakt.datamodel.tables.DepartmentRequiredEmployeesPerCategory.DEPARTMENT_REQUIRED_EMPLOYEES_PER_CATEGORY;

    /**
     * The table <code>g_scrum03.department_required_per_shift</code>.
     */
    public final DepartmentRequiredPerShift DEPARTMENT_REQUIRED_PER_SHIFT = minvakt.datamodel.tables.DepartmentRequiredPerShift.DEPARTMENT_REQUIRED_PER_SHIFT;

    /**
     * The table <code>g_scrum03.employee</code>.
     */
    public final Employee EMPLOYEE = minvakt.datamodel.tables.Employee.EMPLOYEE;

    /**
     * The table <code>g_scrum03.employee_category</code>.
     */
    public final EmployeeCategory EMPLOYEE_CATEGORY = minvakt.datamodel.tables.EmployeeCategory.EMPLOYEE_CATEGORY;

    /**
     * The table <code>g_scrum03.shift</code>.
     */
    public final Shift SHIFT = minvakt.datamodel.tables.Shift.SHIFT;

    /**
     * The table <code>g_scrum03.shift_assignment</code>.
     */
    public final ShiftAssignment SHIFT_ASSIGNMENT = minvakt.datamodel.tables.ShiftAssignment.SHIFT_ASSIGNMENT;

    /**
     * The table <code>g_scrum03.shift_overtime</code>.
     */
    public final ShiftOvertime SHIFT_OVERTIME = minvakt.datamodel.tables.ShiftOvertime.SHIFT_OVERTIME;

    /**
     * No further instances allowed
     */
    private GScrum03() {
        super("g_scrum03", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
                ChangeRequest.CHANGE_REQUEST,
                Department.DEPARTMENT,
                DepartmentRequiredEmployeesPerCategory.DEPARTMENT_REQUIRED_EMPLOYEES_PER_CATEGORY,
                DepartmentRequiredPerShift.DEPARTMENT_REQUIRED_PER_SHIFT,
                Employee.EMPLOYEE,
                EmployeeCategory.EMPLOYEE_CATEGORY,
                Shift.SHIFT,
                ShiftAssignment.SHIFT_ASSIGNMENT,
                ShiftOvertime.SHIFT_OVERTIME);
    }
}
