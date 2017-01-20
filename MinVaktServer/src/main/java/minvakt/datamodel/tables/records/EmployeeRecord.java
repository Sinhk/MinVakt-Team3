/*
 * This file is generated by jOOQ.
*/
package minvakt.datamodel.tables.records;


import minvakt.datamodel.tables.Employee;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Row10;
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
@Table(name = "employee", schema = "g_scrum03")
public class EmployeeRecord extends UpdatableRecordImpl<EmployeeRecord> implements Record10<Integer, Integer, String, String, Integer, String, Short, String, Boolean, Boolean> {

    private static final long serialVersionUID = 1655128340;

    /**
     * Setter for <code>g_scrum03.employee.employee_id</code>.
     */
    public void setEmployeeId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>g_scrum03.employee.employee_id</code>.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", unique = true, nullable = false, precision = 10)
    public Integer getEmployeeId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>g_scrum03.employee.category_id</code>.
     */
    public void setCategoryId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>g_scrum03.employee.category_id</code>.
     */
    @Column(name = "category_id", nullable = false, precision = 10)
    public Integer getCategoryId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>g_scrum03.employee.first_name</code>.
     */
    public void setFirstName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>g_scrum03.employee.first_name</code>.
     */
    @Column(name = "first_name", nullable = false, length = 30)
    public String getFirstName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>g_scrum03.employee.last_name</code>.
     */
    public void setLastName(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>g_scrum03.employee.last_name</code>.
     */
    @Column(name = "last_name", nullable = false, length = 30)
    public String getLastName() {
        return (String) get(3);
    }

    /**
     * Setter for <code>g_scrum03.employee.phone</code>.
     */
    public void setPhone(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>g_scrum03.employee.phone</code>.
     */
    @Column(name = "phone", nullable = false, precision = 10)
    public Integer getPhone() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>g_scrum03.employee.email</code>.
     */
    public void setEmail(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>g_scrum03.employee.email</code>.
     */
    @Column(name = "email", unique = true, nullable = false, length = 50)
    public String getEmail() {
        return (String) get(5);
    }

    /**
     * Setter for <code>g_scrum03.employee.position_percentage</code>.
     */
    public void setPositionPercentage(Short value) {
        set(6, value);
    }

    /**
     * Getter for <code>g_scrum03.employee.position_percentage</code>.
     */
    @Column(name = "position_percentage", nullable = false, precision = 5)
    public Short getPositionPercentage() {
        return (Short) get(6);
    }

    /**
     * Setter for <code>g_scrum03.employee.passwd</code>.
     */
    public void setPasswd(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>g_scrum03.employee.passwd</code>.
     */
    @Column(name = "passwd", length = 60)
    public String getPasswd() {
        return (String) get(7);
    }

    /**
     * Setter for <code>g_scrum03.employee.enabled</code>.
     */
    public void setEnabled(Boolean value) {
        set(8, value);
    }

    /**
     * Getter for <code>g_scrum03.employee.enabled</code>.
     */
    @Column(name = "enabled")
    public Boolean getEnabled() {
        return (Boolean) get(8);
    }

    /**
     * Setter for <code>g_scrum03.employee.change_pw_on_logon</code>.
     */
    public void setChangePwOnLogon(Boolean value) {
        set(9, value);
    }

    /**
     * Getter for <code>g_scrum03.employee.change_pw_on_logon</code>.
     */
    @Column(name = "change_pw_on_logon", nullable = false)
    public Boolean getChangePwOnLogon() {
        return (Boolean) get(9);
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
    // Record10 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Integer, Integer, String, String, Integer, String, Short, String, Boolean, Boolean> fieldsRow() {
        return (Row10) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Integer, Integer, String, String, Integer, String, Short, String, Boolean, Boolean> valuesRow() {
        return (Row10) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Employee.EMPLOYEE.EMPLOYEE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return Employee.EMPLOYEE.CATEGORY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Employee.EMPLOYEE.FIRST_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Employee.EMPLOYEE.LAST_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return Employee.EMPLOYEE.PHONE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return Employee.EMPLOYEE.EMAIL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Short> field7() {
        return Employee.EMPLOYEE.POSITION_PERCENTAGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return Employee.EMPLOYEE.PASSWD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field9() {
        return Employee.EMPLOYEE.ENABLED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field10() {
        return Employee.EMPLOYEE.CHANGE_PW_ON_LOGON;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getEmployeeId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getCategoryId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getFirstName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getLastName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getPhone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getEmail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short value7() {
        return getPositionPercentage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getPasswd();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value9() {
        return getEnabled();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value10() {
        return getChangePwOnLogon();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployeeRecord value1(Integer value) {
        setEmployeeId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployeeRecord value2(Integer value) {
        setCategoryId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployeeRecord value3(String value) {
        setFirstName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployeeRecord value4(String value) {
        setLastName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployeeRecord value5(Integer value) {
        setPhone(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployeeRecord value6(String value) {
        setEmail(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployeeRecord value7(Short value) {
        setPositionPercentage(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployeeRecord value8(String value) {
        setPasswd(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployeeRecord value9(Boolean value) {
        setEnabled(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployeeRecord value10(Boolean value) {
        setChangePwOnLogon(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployeeRecord values(Integer value1, Integer value2, String value3, String value4, Integer value5, String value6, Short value7, String value8, Boolean value9, Boolean value10) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached EmployeeRecord
     */
    public EmployeeRecord() {
        super(Employee.EMPLOYEE);
    }

    /**
     * Create a detached, initialised EmployeeRecord
     */
    public EmployeeRecord(Integer employeeId, Integer categoryId, String firstName, String lastName, Integer phone, String email, Short positionPercentage, String passwd, Boolean enabled, Boolean changePwOnLogon) {
        super(Employee.EMPLOYEE);

        set(0, employeeId);
        set(1, categoryId);
        set(2, firstName);
        set(3, lastName);
        set(4, phone);
        set(5, email);
        set(6, positionPercentage);
        set(7, passwd);
        set(8, enabled);
        set(9, changePwOnLogon);
    }
}
