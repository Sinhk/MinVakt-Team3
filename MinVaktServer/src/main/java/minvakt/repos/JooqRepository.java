package minvakt.repos;

import minvakt.datamodel.AssignedEmployee;
import minvakt.datamodel.ShiftDetailed;
import minvakt.datamodel.tables.pojos.Shift;
import org.jooq.DSLContext;
import org.jooq.Record10;
import org.jooq.Result;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.modelmapper.jooq.RecordValueReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static minvakt.datamodel.Tables.*;
import static org.jooq.impl.DSL.count;

@Component
public class JooqRepository {

    private final DSLContext create;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public JooqRepository(DSLContext dslContext) {
        this.create = dslContext;
        modelMapper.getConfiguration().addValueReader(new RecordValueReader());
        modelMapper.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE);
    }

    public List<ShiftDetailed> getShiftDetailed() {
        List<ShiftDetailed> shifts = new ArrayList<>();
        Map<Integer, Result<Record10<Integer, LocalDateTime, LocalDateTime, Integer, Short, Short, Boolean, Integer, String, String>>> resultMap = this.create
                .select(SHIFT.SHIFT_ID, SHIFT.TO_TIME, SHIFT.FROM_TIME, SHIFT.RESPONSIBLE_EMPLOYEE_ID, SHIFT.DEPARTMENT_ID, SHIFT.REQUIRED_EMPLOYEES, SHIFT_ASSIGNMENT.ABSENT,
                        EMPLOYEE.EMPLOYEE_ID, EMPLOYEE.FIRST_NAME, EMPLOYEE.LAST_NAME)
                .from(SHIFT).naturalJoin(SHIFT_ASSIGNMENT).naturalJoin(EMPLOYEE)
                .fetchGroups(SHIFT.SHIFT_ID);

        resultMap.values().forEach(records -> {
            ShiftDetailed shiftDetailed = modelMapper.map(records.get(0), ShiftDetailed.class);
            System.out.println(shiftDetailed);
            records.forEach(record -> {
                AssignedEmployee employee = modelMapper.map(record, AssignedEmployee.class);
                shiftDetailed.addEmployee(employee);
            });
            shifts.add(shiftDetailed);
        });

        return shifts;
    }

    public List<Shift> getAvailableShifts() {
        return create
                .select(SHIFT.SHIFT_ID, SHIFT.FROM_TIME, SHIFT.TO_TIME, SHIFT.DEPARTMENT_ID,
                        SHIFT.RESPONSIBLE_EMPLOYEE_ID, SHIFT.REQUIRED_EMPLOYEES, count(SHIFT_ASSIGNMENT.EMPLOYEE_ID)).from(SHIFT)
                .leftJoin(SHIFT_ASSIGNMENT).on(SHIFT_ASSIGNMENT.SHIFT_ID.eq(SHIFT.SHIFT_ID))
                .where(SHIFT_ASSIGNMENT.ABSENT.eq(Boolean.FALSE), SHIFT_ASSIGNMENT.ASSIGNED.eq(Boolean.TRUE))
                .groupBy(SHIFT.SHIFT_ID).having(count().lt(SHIFT.REQUIRED_EMPLOYEES.cast(Integer.class)))
                .fetchInto(Shift.class);
    }

    public List<Shift> getAvailableShiftsForUser(String email) {
        return getAvailableShifts().stream().filter()
        /*return create
                .select(SHIFT.SHIFT_ID, SHIFT.FROM_TIME, SHIFT.TO_TIME, SHIFT.DEPARTMENT_ID,
                        SHIFT.RESPONSIBLE_EMPLOYEE_ID, SHIFT.REQUIRED_EMPLOYEES, count(SHIFT_ASSIGNMENT.EMPLOYEE_ID)).from(SHIFT)
                .leftJoin(SHIFT_ASSIGNMENT).on(SHIFT_ASSIGNMENT.SHIFT_ID.eq(SHIFT.SHIFT_ID))
                .leftJoin(EMPLOYEE).on(EMPLOYEE.EMPLOYEE_ID).eq()
                .leftJoin(DEPARTMENT_REQUIRED_EMPLOYEES_PER_CATEGORY).on(DEPARTMENT_REQUIRED_EMPLOYEES_PER_CATEGORY.CATEGORY_ID)
                .where(SHIFT_ASSIGNMENT.ABSENT.eq(Boolean.FALSE), SHIFT_ASSIGNMENT.ASSIGNED.eq(Boolean.TRUE))
                .groupBy(SHIFT.SHIFT_ID).having(count().lt(SHIFT.REQUIRED_EMPLOYEES.cast(Integer.class)))
                .fetchInto(Shift.class);
    */
    }
}