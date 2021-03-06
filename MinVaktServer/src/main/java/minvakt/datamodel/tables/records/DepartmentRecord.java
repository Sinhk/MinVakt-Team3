/*
 * This file is generated by jOOQ.
*/
package minvakt.datamodel.tables.records;


import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import minvakt.datamodel.tables.Department;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Entity
@Table(name = "department", schema = "g_scrum03")
public class DepartmentRecord extends UpdatableRecordImpl<DepartmentRecord> implements Record2<Short, String> {

    private static final long serialVersionUID = -1640485701;

    /**
     * Setter for <code>g_scrum03.department.department_id</code>.
     */
    public void setDepartmentId(Short value) {
        set(0, value);
    }

    /**
     * Getter for <code>g_scrum03.department.department_id</code>.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id", unique = true, nullable = false, precision = 5)
    public Short getDepartmentId() {
        return (Short) get(0);
    }

    /**
     * Setter for <code>g_scrum03.department.department_name</code>.
     */
    public void setDepartmentName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>g_scrum03.department.department_name</code>.
     */
    @Column(name = "department_name", nullable = false, length = 30)
    public String getDepartmentName() {
        return (String) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Short> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<Short, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<Short, String> valuesRow() {
        return (Row2) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Short> field1() {
        return Department.DEPARTMENT.DEPARTMENT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Department.DEPARTMENT.DEPARTMENT_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short value1() {
        return getDepartmentId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getDepartmentName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DepartmentRecord value1(Short value) {
        setDepartmentId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DepartmentRecord value2(String value) {
        setDepartmentName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DepartmentRecord values(Short value1, String value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached DepartmentRecord
     */
    public DepartmentRecord() {
        super(Department.DEPARTMENT);
    }

    /**
     * Create a detached, initialised DepartmentRecord
     */
    public DepartmentRecord(Short departmentId, String departmentName) {
        super(Department.DEPARTMENT);

        set(0, departmentId);
        set(1, departmentName);
    }
}
