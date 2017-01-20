/*
 * This file is generated by jOOQ.
*/
package minvakt.datamodel.tables.records;


import minvakt.datamodel.tables.DepartmentRequiredEmployeesPerCategory;
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
@Table(name = "department_required_employees_per_category", schema = "g_scrum03")
public class DepartmentRequiredEmployeesPerCategoryRecord extends UpdatableRecordImpl<DepartmentRequiredEmployeesPerCategoryRecord> implements Record4<Integer, Integer, Integer, Integer> {

    private static final long serialVersionUID = 2023808451;

    /**
     * Setter for <code>g_scrum03.department_required_employees_per_category.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>g_scrum03.department_required_employees_per_category.id</code>.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, precision = 10)
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>g_scrum03.department_required_employees_per_category.department_id</code>.
     */
    public void setDepartmentId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>g_scrum03.department_required_employees_per_category.department_id</code>.
     */
    @Column(name = "department_id", nullable = false, precision = 10)
    public Integer getDepartmentId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>g_scrum03.department_required_employees_per_category.category_id</code>.
     */
    public void setCategoryId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>g_scrum03.department_required_employees_per_category.category_id</code>.
     */
    @Column(name = "category_id", nullable = false, precision = 10)
    public Integer getCategoryId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>g_scrum03.department_required_employees_per_category.amount</code>.
     */
    public void setAmount(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>g_scrum03.department_required_employees_per_category.amount</code>.
     */
    @Column(name = "amount", nullable = false, precision = 7)
    public Integer getAmount() {
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
        return DepartmentRequiredEmployeesPerCategory.DEPARTMENT_REQUIRED_EMPLOYEES_PER_CATEGORY.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return DepartmentRequiredEmployeesPerCategory.DEPARTMENT_REQUIRED_EMPLOYEES_PER_CATEGORY.DEPARTMENT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return DepartmentRequiredEmployeesPerCategory.DEPARTMENT_REQUIRED_EMPLOYEES_PER_CATEGORY.CATEGORY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return DepartmentRequiredEmployeesPerCategory.DEPARTMENT_REQUIRED_EMPLOYEES_PER_CATEGORY.AMOUNT;
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
        return getDepartmentId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getCategoryId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getAmount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DepartmentRequiredEmployeesPerCategoryRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DepartmentRequiredEmployeesPerCategoryRecord value2(Integer value) {
        setDepartmentId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DepartmentRequiredEmployeesPerCategoryRecord value3(Integer value) {
        setCategoryId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DepartmentRequiredEmployeesPerCategoryRecord value4(Integer value) {
        setAmount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DepartmentRequiredEmployeesPerCategoryRecord values(Integer value1, Integer value2, Integer value3, Integer value4) {
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
     * Create a detached DepartmentRequiredEmployeesPerCategoryRecord
     */
    public DepartmentRequiredEmployeesPerCategoryRecord() {
        super(DepartmentRequiredEmployeesPerCategory.DEPARTMENT_REQUIRED_EMPLOYEES_PER_CATEGORY);
    }

    /**
     * Create a detached, initialised DepartmentRequiredEmployeesPerCategoryRecord
     */
    public DepartmentRequiredEmployeesPerCategoryRecord(Integer id, Integer departmentId, Integer categoryId, Integer amount) {
        super(DepartmentRequiredEmployeesPerCategory.DEPARTMENT_REQUIRED_EMPLOYEES_PER_CATEGORY);

        set(0, id);
        set(1, departmentId);
        set(2, categoryId);
        set(3, amount);
    }
}
