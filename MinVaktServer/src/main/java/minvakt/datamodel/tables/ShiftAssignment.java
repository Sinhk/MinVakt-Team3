/*
 * This file is generated by jOOQ.
*/
package minvakt.datamodel.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import minvakt.datamodel.GScrum03;
import minvakt.datamodel.Keys;
import minvakt.datamodel.tables.records.ShiftAssignmentRecord;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


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
public class ShiftAssignment extends TableImpl<ShiftAssignmentRecord> {

    private static final long serialVersionUID = 94993055;

    /**
     * The reference instance of <code>g_scrum03.shift_assignment</code>
     */
    public static final ShiftAssignment SHIFT_ASSIGNMENT = new ShiftAssignment();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ShiftAssignmentRecord> getRecordType() {
        return ShiftAssignmentRecord.class;
    }

    /**
     * The column <code>g_scrum03.shift_assignment.id</code>.
     */
    public final TableField<ShiftAssignmentRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>g_scrum03.shift_assignment.shift_id</code>.
     */
    public final TableField<ShiftAssignmentRecord, Integer> SHIFT_ID = createField("shift_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>g_scrum03.shift_assignment.employee_id</code>.
     */
    public final TableField<ShiftAssignmentRecord, Integer> EMPLOYEE_ID = createField("employee_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>g_scrum03.shift_assignment.absent</code>.
     */
    public final TableField<ShiftAssignmentRecord, Boolean> ABSENT = createField("absent", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BOOLEAN)), this, "");

    /**
     * The column <code>g_scrum03.shift_assignment.assigned</code>.
     */
    public final TableField<ShiftAssignmentRecord, Boolean> ASSIGNED = createField("assigned", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.BOOLEAN)), this, "");

    /**
     * The column <code>g_scrum03.shift_assignment.available</code>.
     */
    public final TableField<ShiftAssignmentRecord, Boolean> AVAILABLE = createField("available", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.BOOLEAN)), this, "");

    /**
     * The column <code>g_scrum03.shift_assignment.comment_for_absence</code>.
     */
    public final TableField<ShiftAssignmentRecord, String> COMMENT_FOR_ABSENCE = createField("comment_for_absence", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "");

    /**
     * Create a <code>g_scrum03.shift_assignment</code> table reference
     */
    public ShiftAssignment() {
        this("shift_assignment", null);
    }

    /**
     * Create an aliased <code>g_scrum03.shift_assignment</code> table reference
     */
    public ShiftAssignment(String alias) {
        this(alias, SHIFT_ASSIGNMENT);
    }

    private ShiftAssignment(String alias, Table<ShiftAssignmentRecord> aliased) {
        this(alias, aliased, null);
    }

    private ShiftAssignment(String alias, Table<ShiftAssignmentRecord> aliased, Field<?>[] parameters) {
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
    public Identity<ShiftAssignmentRecord, Integer> getIdentity() {
        return Keys.IDENTITY_SHIFT_ASSIGNMENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ShiftAssignmentRecord> getPrimaryKey() {
        return Keys.KEY_SHIFT_ASSIGNMENT_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ShiftAssignmentRecord>> getKeys() {
        return Arrays.<UniqueKey<ShiftAssignmentRecord>>asList(Keys.KEY_SHIFT_ASSIGNMENT_PRIMARY, Keys.KEY_SHIFT_ASSIGNMENT_UNIQUE_SHIFT_EMPLOYEE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ShiftAssignmentRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ShiftAssignmentRecord, ?>>asList(Keys.SHIFT_ASSIGNMENT_SHIFT_ID_FK, Keys.SHIFT_ASSIGNMENT_EMPLOYEE_ID_FK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShiftAssignment as(String alias) {
        return new ShiftAssignment(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ShiftAssignment rename(String name) {
        return new ShiftAssignment(name, null);
    }
}
