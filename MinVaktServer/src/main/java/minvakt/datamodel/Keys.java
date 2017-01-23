/*
 * This file is generated by jOOQ.
*/
package minvakt.datamodel;


import minvakt.datamodel.tables.*;
import minvakt.datamodel.tables.records.*;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;

import javax.annotation.Generated;


/**
 * A class modelling foreign key relationships between tables of the <code>g_scrum03</code> 
 * schema
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<ChangeRequestRecord, Integer> IDENTITY_CHANGE_REQUEST = Identities0.IDENTITY_CHANGE_REQUEST;
    public static final Identity<DepartmentRecord, Short> IDENTITY_DEPARTMENT = Identities0.IDENTITY_DEPARTMENT;
    public static final Identity<DepartmentRequiredEmployeesPerCategoryRecord, Integer> IDENTITY_DEPARTMENT_REQUIRED_EMPLOYEES_PER_CATEGORY = Identities0.IDENTITY_DEPARTMENT_REQUIRED_EMPLOYEES_PER_CATEGORY;
    public static final Identity<DepartmentRequiredPerShiftRecord, Integer> IDENTITY_DEPARTMENT_REQUIRED_PER_SHIFT = Identities0.IDENTITY_DEPARTMENT_REQUIRED_PER_SHIFT;
    public static final Identity<EmployeeRecord, Integer> IDENTITY_EMPLOYEE = Identities0.IDENTITY_EMPLOYEE;
    public static final Identity<EmployeeCategoryRecord, Short> IDENTITY_EMPLOYEE_CATEGORY = Identities0.IDENTITY_EMPLOYEE_CATEGORY;
    public static final Identity<ShiftRecord, Integer> IDENTITY_SHIFT = Identities0.IDENTITY_SHIFT;
    public static final Identity<ShiftAssignmentRecord, Integer> IDENTITY_SHIFT_ASSIGNMENT = Identities0.IDENTITY_SHIFT_ASSIGNMENT;
    public static final Identity<ShiftOvertimeRecord, Integer> IDENTITY_SHIFT_OVERTIME = Identities0.IDENTITY_SHIFT_OVERTIME;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<ChangeRequestRecord> KEY_CHANGE_REQUEST_PRIMARY = UniqueKeys0.KEY_CHANGE_REQUEST_PRIMARY;
    public static final UniqueKey<DepartmentRecord> KEY_DEPARTMENT_PRIMARY = UniqueKeys0.KEY_DEPARTMENT_PRIMARY;
    public static final UniqueKey<DepartmentRequiredEmployeesPerCategoryRecord> KEY_DEPARTMENT_REQUIRED_EMPLOYEES_PER_CATEGORY_PRIMARY = UniqueKeys0.KEY_DEPARTMENT_REQUIRED_EMPLOYEES_PER_CATEGORY_PRIMARY;
    public static final UniqueKey<DepartmentRequiredPerShiftRecord> KEY_DEPARTMENT_REQUIRED_PER_SHIFT_PRIMARY = UniqueKeys0.KEY_DEPARTMENT_REQUIRED_PER_SHIFT_PRIMARY;
    public static final UniqueKey<EmployeeRecord> KEY_EMPLOYEE_PRIMARY = UniqueKeys0.KEY_EMPLOYEE_PRIMARY;
    public static final UniqueKey<EmployeeRecord> KEY_EMPLOYEE_EMAIL = UniqueKeys0.KEY_EMPLOYEE_EMAIL;
    public static final UniqueKey<EmployeeCategoryRecord> KEY_EMPLOYEE_CATEGORY_PRIMARY = UniqueKeys0.KEY_EMPLOYEE_CATEGORY_PRIMARY;
    public static final UniqueKey<ShiftRecord> KEY_SHIFT_PRIMARY = UniqueKeys0.KEY_SHIFT_PRIMARY;
    public static final UniqueKey<ShiftAssignmentRecord> KEY_SHIFT_ASSIGNMENT_PRIMARY = UniqueKeys0.KEY_SHIFT_ASSIGNMENT_PRIMARY;
    public static final UniqueKey<ShiftOvertimeRecord> KEY_SHIFT_OVERTIME_PRIMARY = UniqueKeys0.KEY_SHIFT_OVERTIME_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<ChangeRequestRecord, ShiftRecord> CHANGE_REQUEST_SHIFT_ID_FK = ForeignKeys0.CHANGE_REQUEST_SHIFT_ID_FK;
    public static final ForeignKey<ChangeRequestRecord, EmployeeRecord> CHANGE_REQUEST_OLD_EMPLOYEE_ID_FK = ForeignKeys0.CHANGE_REQUEST_OLD_EMPLOYEE_ID_FK;
    public static final ForeignKey<ChangeRequestRecord, EmployeeRecord> CHANGE_REQUEST_NEW_EMPLOYEE_ID_FK = ForeignKeys0.CHANGE_REQUEST_NEW_EMPLOYEE_ID_FK;
    public static final ForeignKey<DepartmentRequiredEmployeesPerCategoryRecord, DepartmentRecord> DEPARTMENT_REQUIRED_EMPLOYEES_DEPARTMENT_ID_FK = ForeignKeys0.DEPARTMENT_REQUIRED_EMPLOYEES_DEPARTMENT_ID_FK;
    public static final ForeignKey<DepartmentRequiredEmployeesPerCategoryRecord, EmployeeCategoryRecord> DEPARTMENT_REQUIRED_EMPLOYEES_CATEGORY_ID_FK = ForeignKeys0.DEPARTMENT_REQUIRED_EMPLOYEES_CATEGORY_ID_FK;
    public static final ForeignKey<DepartmentRequiredPerShiftRecord, DepartmentRecord> DEPARTMENT_REQUIRED_PER_SHIFT_DEPARTMENT_ID_FK = ForeignKeys0.DEPARTMENT_REQUIRED_PER_SHIFT_DEPARTMENT_ID_FK;
    public static final ForeignKey<DepartmentRequiredPerShiftRecord, ShiftRecord> DEPARTMENT_REQUIRED_PER_SHIFT_SHIFT_ID_FK = ForeignKeys0.DEPARTMENT_REQUIRED_PER_SHIFT_SHIFT_ID_FK;
    public static final ForeignKey<EmployeeRecord, EmployeeCategoryRecord> EMPLOYEE_CATEGORY_FK = ForeignKeys0.EMPLOYEE_CATEGORY_FK;
    public static final ForeignKey<ShiftRecord, EmployeeRecord> SHIFT_RESPONSIBLE_ID_FK = ForeignKeys0.SHIFT_RESPONSIBLE_ID_FK;
    public static final ForeignKey<ShiftAssignmentRecord, ShiftRecord> SHIFT_ASSIGNMENT_SHIFT_ID_FK = ForeignKeys0.SHIFT_ASSIGNMENT_SHIFT_ID_FK;
    public static final ForeignKey<ShiftAssignmentRecord, EmployeeRecord> SHIFT_ASSIGNMENT_EMPLOYEE_ID_FK = ForeignKeys0.SHIFT_ASSIGNMENT_EMPLOYEE_ID_FK;
    public static final ForeignKey<ShiftOvertimeRecord, ShiftAssignmentRecord> SHIFT_OVERTIME_FK = ForeignKeys0.SHIFT_OVERTIME_FK;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 extends AbstractKeys {
        public static Identity<ChangeRequestRecord, Integer> IDENTITY_CHANGE_REQUEST = createIdentity(ChangeRequest.CHANGE_REQUEST, ChangeRequest.CHANGE_REQUEST.REQUEST_ID);
        public static Identity<DepartmentRecord, Short> IDENTITY_DEPARTMENT = createIdentity(Department.DEPARTMENT, Department.DEPARTMENT.DEPARTMENT_ID);
        public static Identity<DepartmentRequiredEmployeesPerCategoryRecord, Integer> IDENTITY_DEPARTMENT_REQUIRED_EMPLOYEES_PER_CATEGORY = createIdentity(DepartmentRequiredEmployeesPerCategory.DEPARTMENT_REQUIRED_EMPLOYEES_PER_CATEGORY, DepartmentRequiredEmployeesPerCategory.DEPARTMENT_REQUIRED_EMPLOYEES_PER_CATEGORY.ID);
        public static Identity<DepartmentRequiredPerShiftRecord, Integer> IDENTITY_DEPARTMENT_REQUIRED_PER_SHIFT = createIdentity(DepartmentRequiredPerShift.DEPARTMENT_REQUIRED_PER_SHIFT, DepartmentRequiredPerShift.DEPARTMENT_REQUIRED_PER_SHIFT.ID);
        public static Identity<EmployeeRecord, Integer> IDENTITY_EMPLOYEE = createIdentity(Employee.EMPLOYEE, Employee.EMPLOYEE.EMPLOYEE_ID);
        public static Identity<EmployeeCategoryRecord, Short> IDENTITY_EMPLOYEE_CATEGORY = createIdentity(EmployeeCategory.EMPLOYEE_CATEGORY, EmployeeCategory.EMPLOYEE_CATEGORY.CATEGORY_ID);
        public static Identity<ShiftRecord, Integer> IDENTITY_SHIFT = createIdentity(Shift.SHIFT, Shift.SHIFT.SHIFT_ID);
        public static Identity<ShiftAssignmentRecord, Integer> IDENTITY_SHIFT_ASSIGNMENT = createIdentity(ShiftAssignment.SHIFT_ASSIGNMENT, ShiftAssignment.SHIFT_ASSIGNMENT.ID);
        public static Identity<ShiftOvertimeRecord, Integer> IDENTITY_SHIFT_OVERTIME = createIdentity(ShiftOvertime.SHIFT_OVERTIME, ShiftOvertime.SHIFT_OVERTIME.ID);
    }

    private static class UniqueKeys0 extends AbstractKeys {
        public static final UniqueKey<ChangeRequestRecord> KEY_CHANGE_REQUEST_PRIMARY = createUniqueKey(ChangeRequest.CHANGE_REQUEST, "KEY_change_request_PRIMARY", ChangeRequest.CHANGE_REQUEST.REQUEST_ID);
        public static final UniqueKey<DepartmentRecord> KEY_DEPARTMENT_PRIMARY = createUniqueKey(Department.DEPARTMENT, "KEY_department_PRIMARY", Department.DEPARTMENT.DEPARTMENT_ID);
        public static final UniqueKey<DepartmentRequiredEmployeesPerCategoryRecord> KEY_DEPARTMENT_REQUIRED_EMPLOYEES_PER_CATEGORY_PRIMARY = createUniqueKey(DepartmentRequiredEmployeesPerCategory.DEPARTMENT_REQUIRED_EMPLOYEES_PER_CATEGORY, "KEY_department_required_employees_per_category_PRIMARY", DepartmentRequiredEmployeesPerCategory.DEPARTMENT_REQUIRED_EMPLOYEES_PER_CATEGORY.ID);
        public static final UniqueKey<DepartmentRequiredPerShiftRecord> KEY_DEPARTMENT_REQUIRED_PER_SHIFT_PRIMARY = createUniqueKey(DepartmentRequiredPerShift.DEPARTMENT_REQUIRED_PER_SHIFT, "KEY_department_required_per_shift_PRIMARY", DepartmentRequiredPerShift.DEPARTMENT_REQUIRED_PER_SHIFT.ID);
        public static final UniqueKey<EmployeeRecord> KEY_EMPLOYEE_PRIMARY = createUniqueKey(Employee.EMPLOYEE, "KEY_employee_PRIMARY", Employee.EMPLOYEE.EMPLOYEE_ID);
        public static final UniqueKey<EmployeeRecord> KEY_EMPLOYEE_EMAIL = createUniqueKey(Employee.EMPLOYEE, "KEY_employee_email", Employee.EMPLOYEE.EMAIL);
        public static final UniqueKey<EmployeeCategoryRecord> KEY_EMPLOYEE_CATEGORY_PRIMARY = createUniqueKey(EmployeeCategory.EMPLOYEE_CATEGORY, "KEY_employee_category_PRIMARY", EmployeeCategory.EMPLOYEE_CATEGORY.CATEGORY_ID);
        public static final UniqueKey<ShiftRecord> KEY_SHIFT_PRIMARY = createUniqueKey(Shift.SHIFT, "KEY_shift_PRIMARY", Shift.SHIFT.SHIFT_ID);
        public static final UniqueKey<ShiftAssignmentRecord> KEY_SHIFT_ASSIGNMENT_PRIMARY = createUniqueKey(ShiftAssignment.SHIFT_ASSIGNMENT, "KEY_shift_assignment_PRIMARY", ShiftAssignment.SHIFT_ASSIGNMENT.ID);
        public static final UniqueKey<ShiftOvertimeRecord> KEY_SHIFT_OVERTIME_PRIMARY = createUniqueKey(ShiftOvertime.SHIFT_OVERTIME, "KEY_shift_overtime_PRIMARY", ShiftOvertime.SHIFT_OVERTIME.ID);
    }

    private static class ForeignKeys0 extends AbstractKeys {
        public static final ForeignKey<ChangeRequestRecord, ShiftRecord> CHANGE_REQUEST_SHIFT_ID_FK = createForeignKey(minvakt.datamodel.Keys.KEY_SHIFT_PRIMARY, ChangeRequest.CHANGE_REQUEST, "change_request_shift_id_fk", ChangeRequest.CHANGE_REQUEST.SHIFT_ID);
        public static final ForeignKey<ChangeRequestRecord, EmployeeRecord> CHANGE_REQUEST_OLD_EMPLOYEE_ID_FK = createForeignKey(minvakt.datamodel.Keys.KEY_EMPLOYEE_PRIMARY, ChangeRequest.CHANGE_REQUEST, "change_request_old_employee_id_fk", ChangeRequest.CHANGE_REQUEST.OLD_EMPLOYEE_ID);
        public static final ForeignKey<ChangeRequestRecord, EmployeeRecord> CHANGE_REQUEST_NEW_EMPLOYEE_ID_FK = createForeignKey(minvakt.datamodel.Keys.KEY_EMPLOYEE_PRIMARY, ChangeRequest.CHANGE_REQUEST, "change_request_new_employee_id_fk", ChangeRequest.CHANGE_REQUEST.NEW_EMPLOYEE_ID);
        public static final ForeignKey<DepartmentRequiredEmployeesPerCategoryRecord, DepartmentRecord> DEPARTMENT_REQUIRED_EMPLOYEES_DEPARTMENT_ID_FK = createForeignKey(minvakt.datamodel.Keys.KEY_DEPARTMENT_PRIMARY, DepartmentRequiredEmployeesPerCategory.DEPARTMENT_REQUIRED_EMPLOYEES_PER_CATEGORY, "department_required_employees_department_id_fk", DepartmentRequiredEmployeesPerCategory.DEPARTMENT_REQUIRED_EMPLOYEES_PER_CATEGORY.DEPARTMENT_ID);
        public static final ForeignKey<DepartmentRequiredEmployeesPerCategoryRecord, EmployeeCategoryRecord> DEPARTMENT_REQUIRED_EMPLOYEES_CATEGORY_ID_FK = createForeignKey(minvakt.datamodel.Keys.KEY_EMPLOYEE_CATEGORY_PRIMARY, DepartmentRequiredEmployeesPerCategory.DEPARTMENT_REQUIRED_EMPLOYEES_PER_CATEGORY, "department_required_employees_category_id_fk", DepartmentRequiredEmployeesPerCategory.DEPARTMENT_REQUIRED_EMPLOYEES_PER_CATEGORY.CATEGORY_ID);
        public static final ForeignKey<DepartmentRequiredPerShiftRecord, DepartmentRecord> DEPARTMENT_REQUIRED_PER_SHIFT_DEPARTMENT_ID_FK = createForeignKey(minvakt.datamodel.Keys.KEY_DEPARTMENT_PRIMARY, DepartmentRequiredPerShift.DEPARTMENT_REQUIRED_PER_SHIFT, "department_required_per_shift_department_id_fk", DepartmentRequiredPerShift.DEPARTMENT_REQUIRED_PER_SHIFT.DEPARTMENT_ID);
        public static final ForeignKey<DepartmentRequiredPerShiftRecord, ShiftRecord> DEPARTMENT_REQUIRED_PER_SHIFT_SHIFT_ID_FK = createForeignKey(minvakt.datamodel.Keys.KEY_SHIFT_PRIMARY, DepartmentRequiredPerShift.DEPARTMENT_REQUIRED_PER_SHIFT, "department_required_per_shift_shift_id_fk", DepartmentRequiredPerShift.DEPARTMENT_REQUIRED_PER_SHIFT.SHIFT_ID);
        public static final ForeignKey<EmployeeRecord, EmployeeCategoryRecord> EMPLOYEE_CATEGORY_FK = createForeignKey(minvakt.datamodel.Keys.KEY_EMPLOYEE_CATEGORY_PRIMARY, Employee.EMPLOYEE, "employee_category_fk", Employee.EMPLOYEE.CATEGORY_ID);
        public static final ForeignKey<ShiftRecord, EmployeeRecord> SHIFT_RESPONSIBLE_ID_FK = createForeignKey(minvakt.datamodel.Keys.KEY_EMPLOYEE_PRIMARY, Shift.SHIFT, "shift_responsible_id_fk", Shift.SHIFT.RESPONSIBLE_EMPLOYEE_ID);
        public static final ForeignKey<ShiftAssignmentRecord, ShiftRecord> SHIFT_ASSIGNMENT_SHIFT_ID_FK = createForeignKey(minvakt.datamodel.Keys.KEY_SHIFT_PRIMARY, ShiftAssignment.SHIFT_ASSIGNMENT, "shift_assignment_shift_id_fk", ShiftAssignment.SHIFT_ASSIGNMENT.SHIFT_ID);
        public static final ForeignKey<ShiftAssignmentRecord, EmployeeRecord> SHIFT_ASSIGNMENT_EMPLOYEE_ID_FK = createForeignKey(minvakt.datamodel.Keys.KEY_EMPLOYEE_PRIMARY, ShiftAssignment.SHIFT_ASSIGNMENT, "shift_assignment_employee_id_fk", ShiftAssignment.SHIFT_ASSIGNMENT.EMPLOYEE_ID);
        public static final ForeignKey<ShiftOvertimeRecord, ShiftAssignmentRecord> SHIFT_OVERTIME_FK = createForeignKey(minvakt.datamodel.Keys.KEY_SHIFT_ASSIGNMENT_PRIMARY, ShiftOvertime.SHIFT_OVERTIME, "shift_overtime_fk", ShiftOvertime.SHIFT_OVERTIME.SHIFT_ASSIGNMENT_ID);
    }
}