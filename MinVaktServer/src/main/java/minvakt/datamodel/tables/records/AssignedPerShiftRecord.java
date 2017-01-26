/*
 * This file is generated by jOOQ.
*/
package minvakt.datamodel.tables.records;


import java.time.LocalDateTime;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import minvakt.datamodel.tables.AssignedPerShift;

import org.jooq.Field;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.TableRecordImpl;


/**
 * VIEW
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Table(name = "assigned_per_shift", schema = "g_scrum03")
public class AssignedPerShiftRecord extends TableRecordImpl<AssignedPerShiftRecord> implements Record8<Integer, Integer, LocalDateTime, LocalDateTime, Short, Short, Long, Long> {

    private static final long serialVersionUID = -750448977;

    /**
     * Setter for <code>g_scrum03.assigned_per_shift.shift_id</code>.
     */
    public void setShiftId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>g_scrum03.assigned_per_shift.shift_id</code>.
     */
    @Column(name = "shift_id", nullable = false, precision = 10)
    public Integer getShiftId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>g_scrum03.assigned_per_shift.responsible_employee_id</code>.
     */
    public void setResponsibleEmployeeId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>g_scrum03.assigned_per_shift.responsible_employee_id</code>.
     */
    @Column(name = "responsible_employee_id", precision = 10)
    public Integer getResponsibleEmployeeId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>g_scrum03.assigned_per_shift.from_time</code>.
     */
    public void setFromTime(LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>g_scrum03.assigned_per_shift.from_time</code>.
     */
    @Column(name = "from_time", nullable = false)
    public LocalDateTime getFromTime() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for <code>g_scrum03.assigned_per_shift.to_time</code>.
     */
    public void setToTime(LocalDateTime value) {
        set(3, value);
    }

    /**
     * Getter for <code>g_scrum03.assigned_per_shift.to_time</code>.
     */
    @Column(name = "to_time", nullable = false)
    public LocalDateTime getToTime() {
        return (LocalDateTime) get(3);
    }

    /**
     * Setter for <code>g_scrum03.assigned_per_shift.department_id</code>.
     */
    public void setDepartmentId(Short value) {
        set(4, value);
    }

    /**
     * Getter for <code>g_scrum03.assigned_per_shift.department_id</code>.
     */
    @Column(name = "department_id", nullable = false, precision = 5)
    public Short getDepartmentId() {
        return (Short) get(4);
    }

    /**
     * Setter for <code>g_scrum03.assigned_per_shift.required_employees</code>.
     */
    public void setRequiredEmployees(Short value) {
        set(5, value);
    }

    /**
     * Getter for <code>g_scrum03.assigned_per_shift.required_employees</code>.
     */
    @Column(name = "required_employees", nullable = false, precision = 5)
    public Short getRequiredEmployees() {
        return (Short) get(5);
    }

    /**
     * Setter for <code>g_scrum03.assigned_per_shift.num_assigned</code>.
     */
    public void setNumAssigned(Long value) {
        set(6, value);
    }

    /**
     * Getter for <code>g_scrum03.assigned_per_shift.num_assigned</code>.
     */
    @Column(name = "num_assigned", nullable = false, precision = 19)
    public Long getNumAssigned() {
        return (Long) get(6);
    }

    /**
     * Setter for <code>g_scrum03.assigned_per_shift.num_missing</code>.
     */
    public void setNumMissing(Long value) {
        set(7, value);
    }

    /**
     * Getter for <code>g_scrum03.assigned_per_shift.num_missing</code>.
     */
    @Column(name = "num_missing", nullable = false, precision = 19)
    public Long getNumMissing() {
        return (Long) get(7);
    }

    // -------------------------------------------------------------------------
    // Record8 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, Integer, LocalDateTime, LocalDateTime, Short, Short, Long, Long> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, Integer, LocalDateTime, LocalDateTime, Short, Short, Long, Long> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return AssignedPerShift.ASSIGNED_PER_SHIFT.SHIFT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return AssignedPerShift.ASSIGNED_PER_SHIFT.RESPONSIBLE_EMPLOYEE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<LocalDateTime> field3() {
        return AssignedPerShift.ASSIGNED_PER_SHIFT.FROM_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<LocalDateTime> field4() {
        return AssignedPerShift.ASSIGNED_PER_SHIFT.TO_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Short> field5() {
        return AssignedPerShift.ASSIGNED_PER_SHIFT.DEPARTMENT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Short> field6() {
        return AssignedPerShift.ASSIGNED_PER_SHIFT.REQUIRED_EMPLOYEES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field7() {
        return AssignedPerShift.ASSIGNED_PER_SHIFT.NUM_ASSIGNED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field8() {
        return AssignedPerShift.ASSIGNED_PER_SHIFT.NUM_MISSING;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getShiftId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getResponsibleEmployeeId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime value3() {
        return getFromTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime value4() {
        return getToTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short value5() {
        return getDepartmentId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short value6() {
        return getRequiredEmployees();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value7() {
        return getNumAssigned();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value8() {
        return getNumMissing();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AssignedPerShiftRecord value1(Integer value) {
        setShiftId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AssignedPerShiftRecord value2(Integer value) {
        setResponsibleEmployeeId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AssignedPerShiftRecord value3(LocalDateTime value) {
        setFromTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AssignedPerShiftRecord value4(LocalDateTime value) {
        setToTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AssignedPerShiftRecord value5(Short value) {
        setDepartmentId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AssignedPerShiftRecord value6(Short value) {
        setRequiredEmployees(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AssignedPerShiftRecord value7(Long value) {
        setNumAssigned(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AssignedPerShiftRecord value8(Long value) {
        setNumMissing(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AssignedPerShiftRecord values(Integer value1, Integer value2, LocalDateTime value3, LocalDateTime value4, Short value5, Short value6, Long value7, Long value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AssignedPerShiftRecord
     */
    public AssignedPerShiftRecord() {
        super(AssignedPerShift.ASSIGNED_PER_SHIFT);
    }

    /**
     * Create a detached, initialised AssignedPerShiftRecord
     */
    public AssignedPerShiftRecord(Integer shiftId, Integer responsibleEmployeeId, LocalDateTime fromTime, LocalDateTime toTime, Short departmentId, Short requiredEmployees, Long numAssigned, Long numMissing) {
        super(AssignedPerShift.ASSIGNED_PER_SHIFT);

        set(0, shiftId);
        set(1, responsibleEmployeeId);
        set(2, fromTime);
        set(3, toTime);
        set(4, departmentId);
        set(5, requiredEmployees);
        set(6, numAssigned);
        set(7, numMissing);
    }
}