/*
 * This file is generated by jOOQ.
*/
package minvakt.datamodel.tables;


import minvakt.datamodel.GScrum03;
import minvakt.datamodel.Keys;
import minvakt.datamodel.tables.records.EmployeeRecord;
import org.jooq.*;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
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
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Employee extends TableImpl<EmployeeRecord> {

    private static final long serialVersionUID = -556015551;

    /**
     * The reference instance of <code>g_scrum03.employee</code>
     */
    public static final Employee EMPLOYEE = new Employee();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<EmployeeRecord> getRecordType() {
        return EmployeeRecord.class;
    }

    /**
     * The column <code>g_scrum03.employee.employee_id</code>.
     */
    public final TableField<EmployeeRecord, Integer> EMPLOYEE_ID = createField("employee_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>g_scrum03.employee.category_id</code>.
     */
    public final TableField<EmployeeRecord, Short> CATEGORY_ID = createField("category_id", org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * The column <code>g_scrum03.employee.first_name</code>.
     */
    public final TableField<EmployeeRecord, String> FIRST_NAME = createField("first_name", org.jooq.impl.SQLDataType.VARCHAR.length(30).nullable(false), this, "");

    /**
     * The column <code>g_scrum03.employee.last_name</code>.
     */
    public final TableField<EmployeeRecord, String> LAST_NAME = createField("last_name", org.jooq.impl.SQLDataType.VARCHAR.length(30).nullable(false), this, "");

    /**
     * The column <code>g_scrum03.employee.phone</code>.
     */
    public final TableField<EmployeeRecord, Integer> PHONE = createField("phone", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>g_scrum03.employee.email</code>.
     */
    public final TableField<EmployeeRecord, String> EMAIL = createField("email", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false), this, "");

    /**
     * The column <code>g_scrum03.employee.position_percentage</code>.
     */
    public final TableField<EmployeeRecord, Short> POSITION_PERCENTAGE = createField("position_percentage", org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * The column <code>g_scrum03.employee.passwd</code>.
     */
    public final TableField<EmployeeRecord, String> PASSWD = createField("passwd", org.jooq.impl.SQLDataType.VARCHAR.length(60), this, "");

    /**
     * The column <code>g_scrum03.employee.enabled</code>.
     */
    public final TableField<EmployeeRecord, Boolean> ENABLED = createField("enabled", org.jooq.impl.SQLDataType.BOOLEAN.defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.BOOLEAN)), this, "");

    /**
     * The column <code>g_scrum03.employee.change_pw_on_logon</code>.
     */
    public final TableField<EmployeeRecord, Boolean> CHANGE_PW_ON_LOGON = createField("change_pw_on_logon", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.BOOLEAN)), this, "");

    /**
     * Create a <code>g_scrum03.employee</code> table reference
     */
    public Employee() {
        this("employee", null);
    }

    /**
     * Create an aliased <code>g_scrum03.employee</code> table reference
     */
    public Employee(String alias) {
        this(alias, EMPLOYEE);
    }

    private Employee(String alias, Table<EmployeeRecord> aliased) {
        this(alias, aliased, null);
    }

    private Employee(String alias, Table<EmployeeRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return GScrum03.G_SCRUM03;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<EmployeeRecord, Integer> getIdentity() {
        return Keys.IDENTITY_EMPLOYEE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<EmployeeRecord> getPrimaryKey() {
        return Keys.KEY_EMPLOYEE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<EmployeeRecord>> getKeys() {
        return Arrays.<UniqueKey<EmployeeRecord>>asList(Keys.KEY_EMPLOYEE_PRIMARY, Keys.KEY_EMPLOYEE_EMAIL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<EmployeeRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<EmployeeRecord, ?>>asList(Keys.EMPLOYEE_CATEGORY_FK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Employee as(String alias) {
        return new Employee(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Employee rename(String name) {
        return new Employee(name, null);
    }
}
