/*
 * This file is generated by jOOQ.
*/
package minvakt.datamodel.tables;


import java.time.LocalDateTime;

import javax.annotation.Generated;

import minvakt.datamodel.GScrum03;
import minvakt.datamodel.tables.records.AssignedPerShiftRecord;

import org.jooq.Field;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.TableImpl;


/**
 * VIEW
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AssignedPerShift extends TableImpl<AssignedPerShiftRecord> {

    private static final long serialVersionUID = 71888416;

    /**
     * The reference instance of <code>g_scrum03.assigned_per_shift</code>
     */
    public static final AssignedPerShift ASSIGNED_PER_SHIFT = new AssignedPerShift();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AssignedPerShiftRecord> getRecordType() {
        return AssignedPerShiftRecord.class;
    }

    /**
     * The column <code>g_scrum03.assigned_per_shift.shift_id</code>.
     */
    public final TableField<AssignedPerShiftRecord, Integer> SHIFT_ID = createField("shift_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>g_scrum03.assigned_per_shift.responsible_employee_id</code>.
     */
    public final TableField<AssignedPerShiftRecord, Integer> RESPONSIBLE_EMPLOYEE_ID = createField("responsible_employee_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>g_scrum03.assigned_per_shift.from_time</code>.
     */
    public final TableField<AssignedPerShiftRecord, LocalDateTime> FROM_TIME = createField("from_time", org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false), this, "");

    /**
     * The column <code>g_scrum03.assigned_per_shift.to_time</code>.
     */
    public final TableField<AssignedPerShiftRecord, LocalDateTime> TO_TIME = createField("to_time", org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false), this, "");

    /**
     * The column <code>g_scrum03.assigned_per_shift.department_id</code>.
     */
    public final TableField<AssignedPerShiftRecord, Short> DEPARTMENT_ID = createField("department_id", org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * The column <code>g_scrum03.assigned_per_shift.required_employees</code>.
     */
    public final TableField<AssignedPerShiftRecord, Short> REQUIRED_EMPLOYEES = createField("required_employees", org.jooq.impl.SQLDataType.SMALLINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("5", org.jooq.impl.SQLDataType.SMALLINT)), this, "");

    /**
     * The column <code>g_scrum03.assigned_per_shift.num_assigned</code>.
     */
    public final TableField<AssignedPerShiftRecord, Long> NUM_ASSIGNED = createField("num_assigned", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * Create a <code>g_scrum03.assigned_per_shift</code> table reference
     */
    public AssignedPerShift() {
        this("assigned_per_shift", null);
    }

    /**
     * Create an aliased <code>g_scrum03.assigned_per_shift</code> table reference
     */
    public AssignedPerShift(String alias) {
        this(alias, ASSIGNED_PER_SHIFT);
    }

    private AssignedPerShift(String alias, Table<AssignedPerShiftRecord> aliased) {
        this(alias, aliased, null);
    }

    private AssignedPerShift(String alias, Table<AssignedPerShiftRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "VIEW");
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
    public AssignedPerShift as(String alias) {
        return new AssignedPerShift(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public AssignedPerShift rename(String name) {
        return new AssignedPerShift(name, null);
    }
}
