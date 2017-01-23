/*
 * This file is generated by jOOQ.
*/
package minvakt.datamodel.tables;


import minvakt.datamodel.GScrum03;
import minvakt.datamodel.Keys;
import minvakt.datamodel.tables.records.EmployeeCategoryRecord;
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
public class EmployeeCategory extends TableImpl<EmployeeCategoryRecord> {

    private static final long serialVersionUID = -774183160;

    /**
     * The reference instance of <code>g_scrum03.employee_category</code>
     */
    public static final EmployeeCategory EMPLOYEE_CATEGORY = new EmployeeCategory();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<EmployeeCategoryRecord> getRecordType() {
        return EmployeeCategoryRecord.class;
    }

    /**
     * The column <code>g_scrum03.employee_category.category_id</code>.
     */
    public final TableField<EmployeeCategoryRecord, Short> CATEGORY_ID = createField("category_id", org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * The column <code>g_scrum03.employee_category.category_name</code>.
     */
    public final TableField<EmployeeCategoryRecord, String> CATEGORY_NAME = createField("category_name", org.jooq.impl.SQLDataType.VARCHAR.length(30).nullable(false), this, "");

    /**
     * The column <code>g_scrum03.employee_category.admin</code>.
     */
    public final TableField<EmployeeCategoryRecord, Boolean> ADMIN = createField("admin", org.jooq.impl.SQLDataType.BOOLEAN.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BOOLEAN)), this, "");

    /**
     * The column <code>g_scrum03.employee_category.required_per_shift</code>.
     */
    public final TableField<EmployeeCategoryRecord, Short> REQUIRED_PER_SHIFT = createField("required_per_shift", org.jooq.impl.SQLDataType.SMALLINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.SMALLINT)), this, "");

    /**
     * The column <code>g_scrum03.employee_category.available_for_shifts</code>.
     */
    public final TableField<EmployeeCategoryRecord, Boolean> AVAILABLE_FOR_SHIFTS = createField("available_for_shifts", org.jooq.impl.SQLDataType.BOOLEAN.defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.BOOLEAN)), this, "");

    /**
     * Create a <code>g_scrum03.employee_category</code> table reference
     */
    public EmployeeCategory() {
        this("employee_category", null);
    }

    /**
     * Create an aliased <code>g_scrum03.employee_category</code> table reference
     */
    public EmployeeCategory(String alias) {
        this(alias, EMPLOYEE_CATEGORY);
    }

    private EmployeeCategory(String alias, Table<EmployeeCategoryRecord> aliased) {
        this(alias, aliased, null);
    }

    private EmployeeCategory(String alias, Table<EmployeeCategoryRecord> aliased, Field<?>[] parameters) {
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
    public Identity<EmployeeCategoryRecord, Short> getIdentity() {
        return Keys.IDENTITY_EMPLOYEE_CATEGORY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<EmployeeCategoryRecord> getPrimaryKey() {
        return Keys.KEY_EMPLOYEE_CATEGORY_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<EmployeeCategoryRecord>> getKeys() {
        return Arrays.<UniqueKey<EmployeeCategoryRecord>>asList(Keys.KEY_EMPLOYEE_CATEGORY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployeeCategory as(String alias) {
        return new EmployeeCategory(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public EmployeeCategory rename(String name) {
        return new EmployeeCategory(name, null);
    }
}