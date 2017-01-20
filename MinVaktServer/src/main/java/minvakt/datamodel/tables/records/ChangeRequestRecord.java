/*
 * This file is generated by jOOQ.
*/
package minvakt.datamodel.tables.records;


import minvakt.datamodel.tables.ChangeRequest;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
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
@Table(name = "change_request", schema = "g_scrum03")
public class ChangeRequestRecord extends UpdatableRecordImpl<ChangeRequestRecord> implements Record4<Integer, Integer, Integer, Integer> {

    private static final long serialVersionUID = 2043497238;

    /**
     * Setter for <code>g_scrum03.change_request.request_id</code>.
     */
    public void setRequestId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>g_scrum03.change_request.request_id</code>.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id", unique = true, nullable = false, precision = 10)
    public Integer getRequestId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>g_scrum03.change_request.shift_id</code>.
     */
    public void setShiftId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>g_scrum03.change_request.shift_id</code>.
     */
    @Column(name = "shift_id", nullable = false, precision = 10)
    public Integer getShiftId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>g_scrum03.change_request.old_employee_id</code>.
     */
    public void setOldEmployeeId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>g_scrum03.change_request.old_employee_id</code>.
     */
    @Column(name = "old_employee_id", nullable = false, precision = 10)
    public Integer getOldEmployeeId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>g_scrum03.change_request.new_employee_id</code>.
     */
    public void setNewEmployeeId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>g_scrum03.change_request.new_employee_id</code>.
     */
    @Column(name = "new_employee_id", nullable = false, precision = 10)
    public Integer getNewEmployeeId() {
        return (Integer) get(3);
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
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Integer, Integer, Integer, Integer> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Integer, Integer, Integer, Integer> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return ChangeRequest.CHANGE_REQUEST.REQUEST_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return ChangeRequest.CHANGE_REQUEST.SHIFT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return ChangeRequest.CHANGE_REQUEST.OLD_EMPLOYEE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return ChangeRequest.CHANGE_REQUEST.NEW_EMPLOYEE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getRequestId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getShiftId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getOldEmployeeId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getNewEmployeeId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChangeRequestRecord value1(Integer value) {
        setRequestId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChangeRequestRecord value2(Integer value) {
        setShiftId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChangeRequestRecord value3(Integer value) {
        setOldEmployeeId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChangeRequestRecord value4(Integer value) {
        setNewEmployeeId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChangeRequestRecord values(Integer value1, Integer value2, Integer value3, Integer value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ChangeRequestRecord
     */
    public ChangeRequestRecord() {
        super(ChangeRequest.CHANGE_REQUEST);
    }

    /**
     * Create a detached, initialised ChangeRequestRecord
     */
    public ChangeRequestRecord(Integer requestId, Integer shiftId, Integer oldEmployeeId, Integer newEmployeeId) {
        super(ChangeRequest.CHANGE_REQUEST);

        set(0, requestId);
        set(1, shiftId);
        set(2, oldEmployeeId);
        set(3, newEmployeeId);
    }
}
