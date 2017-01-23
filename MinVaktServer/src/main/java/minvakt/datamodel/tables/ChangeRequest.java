/*
 * This file is generated by jOOQ.
*/
package minvakt.datamodel.tables;


import minvakt.datamodel.GScrum03;
import minvakt.datamodel.Keys;
import minvakt.datamodel.tables.records.ChangeRequestRecord;
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
public class ChangeRequest extends TableImpl<ChangeRequestRecord> {

    private static final long serialVersionUID = -2012423260;

    /**
     * The reference instance of <code>g_scrum03.change_request</code>
     */
    public static final ChangeRequest CHANGE_REQUEST = new ChangeRequest();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ChangeRequestRecord> getRecordType() {
        return ChangeRequestRecord.class;
    }

    /**
     * The column <code>g_scrum03.change_request.request_id</code>.
     */
    public final TableField<ChangeRequestRecord, Integer> REQUEST_ID = createField("request_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>g_scrum03.change_request.shift_id</code>.
     */
    public final TableField<ChangeRequestRecord, Integer> SHIFT_ID = createField("shift_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>g_scrum03.change_request.old_employee_id</code>.
     */
    public final TableField<ChangeRequestRecord, Integer> OLD_EMPLOYEE_ID = createField("old_employee_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>g_scrum03.change_request.new_employee_id</code>.
     */
    public final TableField<ChangeRequestRecord, Integer> NEW_EMPLOYEE_ID = createField("new_employee_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>g_scrum03.change_request</code> table reference
     */
    public ChangeRequest() {
        this("change_request", null);
    }

    /**
     * Create an aliased <code>g_scrum03.change_request</code> table reference
     */
    public ChangeRequest(String alias) {
        this(alias, CHANGE_REQUEST);
    }

    private ChangeRequest(String alias, Table<ChangeRequestRecord> aliased) {
        this(alias, aliased, null);
    }

    private ChangeRequest(String alias, Table<ChangeRequestRecord> aliased, Field<?>[] parameters) {
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
    public Identity<ChangeRequestRecord, Integer> getIdentity() {
        return Keys.IDENTITY_CHANGE_REQUEST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ChangeRequestRecord> getPrimaryKey() {
        return Keys.KEY_CHANGE_REQUEST_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ChangeRequestRecord>> getKeys() {
        return Arrays.<UniqueKey<ChangeRequestRecord>>asList(Keys.KEY_CHANGE_REQUEST_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ChangeRequestRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ChangeRequestRecord, ?>>asList(Keys.CHANGE_REQUEST_SHIFT_ID_FK, Keys.CHANGE_REQUEST_OLD_EMPLOYEE_ID_FK, Keys.CHANGE_REQUEST_NEW_EMPLOYEE_ID_FK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChangeRequest as(String alias) {
        return new ChangeRequest(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ChangeRequest rename(String name) {
        return new ChangeRequest(name, null);
    }
}