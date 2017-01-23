/*
 * This file is generated by jOOQ.
*/
package minvakt.datamodel.tables.records;


import minvakt.datamodel.tables.ShiftOvertime;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.Generated;
import javax.persistence.*;


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
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Entity
@Table(name = "shift_overtime", schema = "g_scrum03")
public class ShiftOvertimeRecord extends UpdatableRecordImpl<ShiftOvertimeRecord> implements Record3<Integer, Integer, Integer> {

    private static final long serialVersionUID = 1333439021;

    /**
     * Setter for <code>g_scrum03.shift_overtime.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>g_scrum03.shift_overtime.id</code>.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, precision = 10)
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>g_scrum03.shift_overtime.shift_assignment_id</code>.
     */
    public void setShiftAssignmentId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>g_scrum03.shift_overtime.shift_assignment_id</code>.
     */
    @Column(name = "shift_assignment_id", nullable = false, precision = 10)
    public Integer getShiftAssignmentId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>g_scrum03.shift_overtime.minutes</code>.
     */
    public void setMinutes(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>g_scrum03.shift_overtime.minutes</code>.
     */
    @Column(name = "minutes", nullable = false, precision = 7)
    public Integer getMinutes() {
        return (Integer) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Integer, Integer, Integer> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Integer, Integer, Integer> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return ShiftOvertime.SHIFT_OVERTIME.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return ShiftOvertime.SHIFT_OVERTIME.SHIFT_ASSIGNMENT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return ShiftOvertime.SHIFT_OVERTIME.MINUTES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getShiftAssignmentId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getMinutes();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShiftOvertimeRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShiftOvertimeRecord value2(Integer value) {
        setShiftAssignmentId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShiftOvertimeRecord value3(Integer value) {
        setMinutes(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShiftOvertimeRecord values(Integer value1, Integer value2, Integer value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ShiftOvertimeRecord
     */
    public ShiftOvertimeRecord() {
        super(ShiftOvertime.SHIFT_OVERTIME);
    }

    /**
     * Create a detached, initialised ShiftOvertimeRecord
     */
    public ShiftOvertimeRecord(Integer id, Integer shiftAssignmentId, Integer minutes) {
        super(ShiftOvertime.SHIFT_OVERTIME);

        set(0, id);
        set(1, shiftAssignmentId);
        set(2, minutes);
    }
}