package minvakt.repos;

import minvakt.datamodel.tables.pojos.Employee;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static minvakt.datamodel.Tables.*;

@Component
public class JooqTest {

    private final DSLContext create;

    @Autowired
    public JooqTest(DSLContext dslContext) {
        this.create = dslContext;
    }

    public List<Employee> testGetShiftAssigned(int id) {
        return this.create
                .select()
                .from(SHIFT).naturalJoin(SHIFT_ASSIGNMENT).naturalJoin(EMPLOYEE)
                .where(SHIFT.SHIFT_ID.eq(id)).fetchInto(Employee.class);
    }
}