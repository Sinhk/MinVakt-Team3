package minvakt.repos;

import minvakt.Application;
import minvakt.datamodel.AssignedEmployee;
import minvakt.datamodel.ShiftDetailed;
import minvakt.datamodel.tables.AssignedPerShift;
import minvakt.datamodel.tables.EmployeeTimeWorkedWeek;
import minvakt.datamodel.tables.pojos.MissingPerShiftCategory;
import minvakt.datamodel.tables.ShiftAssignment;
import minvakt.datamodel.tables.pojos.Employee;
import minvakt.datamodel.tables.pojos.Shift;
import org.jooq.DSLContext;
import org.jooq.Record10;
import org.jooq.Result;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.modelmapper.jooq.RecordValueReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static minvakt.Application.*;
import static minvakt.datamodel.Tables.*;
import static org.jooq.impl.DSL.*;

@Controller
public class JooqRepository {

    private static final Logger log = LoggerFactory.getLogger(JooqRepository.class);

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
        return create.select().from(ASSIGNED_PER_SHIFT)
                .where(ASSIGNED_PER_SHIFT.NUM_ASSIGNED
                        .lt(ASSIGNED_PER_SHIFT.REQUIRED_EMPLOYEES.cast(Long.class)))
                .fetchInto(Shift.class);
    }

    public List<Shift> getAvailableShiftsForCategory(int categoryId) {
        AssignedPerShift assi = ASSIGNED_PER_SHIFT.as("assi");
        minvakt.datamodel.tables.MissingPerShiftCategory mis = MISSING_PER_SHIFT_CATEGORY.as("mis");
        return create
                .select().from(mis)
                .leftJoin(assi).on(assi.SHIFT_ID.eq(mis.SHIFT_ID))
                .where(mis.CATEGORY_ID.notEqual((short) categoryId))
                .groupBy(assi.SHIFT_ID)
                .having(sum(mis.MISSING).lt(assi.NUM_MISSING.cast(BigDecimal.class)))
                .fetchInto(Shift.class);

    }

    public List<Employee> getEmployeesAvailableForShift(Shift shift) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekNumber = shift.getFromTime().get(weekFields.weekOfWeekBasedYear());
        minvakt.datamodel.tables.MissingPerShiftCategory mis = MISSING_PER_SHIFT_CATEGORY.as("mis");
        EmployeeTimeWorkedWeek etww = EMPLOYEE_TIME_WORKED_WEEK.as("etww");
        minvakt.datamodel.tables.Shift theShift = SHIFT.as("theshift");
        ShiftAssignment wish = SHIFT_ASSIGNMENT.as("wish");
        /*create.select().from(EMPLOYEE)
                .leftJoin(SHIFT_ASSIGNMENT).on(SHIFT_ASSIGNMENT.EMPLOYEE_ID.eq(EMPLOYEE.EMPLOYEE_ID))
                .leftJoin(SHIFT).on(SHIFT.SHIFT_ID.eq(SHIFT_ASSIGNMENT.SHIFT_ID))
                .where(SHIFT.FROM_TIME.between())*/

        return create.selectDistinct(EMPLOYEE.EMPLOYEE_ID).select(EMPLOYEE.fields())
                .from(EMPLOYEE)
                .leftJoin(theShift).on(theShift.SHIFT_ID.eq(shift.getShiftId()))
                .leftJoin(EMPLOYEE_CATEGORY).on(EMPLOYEE.CATEGORY_ID.eq(EMPLOYEE_CATEGORY.CATEGORY_ID))
                .leftJoin(etww).on(etww.EMPLOYEE_ID.eq(EMPLOYEE.EMPLOYEE_ID), etww.WEEK_NUM.eq(weekNumber))
                .leftJoin(mis).on(mis.SHIFT_ID.eq(shift.getShiftId()), mis.CATEGORY_ID.eq(EMPLOYEE.CATEGORY_ID))
                .leftJoin(wish).on(wish.EMPLOYEE_ID.eq(EMPLOYEE.EMPLOYEE_ID), wish.SHIFT_ID.eq(shift.getShiftId()))
                .leftJoin(SHIFT_ASSIGNMENT).on(SHIFT_ASSIGNMENT.EMPLOYEE_ID.eq(EMPLOYEE.EMPLOYEE_ID), SHIFT_ASSIGNMENT.ASSIGNED.eq(Boolean.TRUE))
                .leftJoin(SHIFT).on(SHIFT.SHIFT_ID.eq(SHIFT_ASSIGNMENT.SHIFT_ID))
                .where(EMPLOYEE_CATEGORY.AVAILABLE_FOR_SHIFTS.eq(Boolean.TRUE)).and(not(SHIFT.FROM_TIME.between(getStartOfDay(shift.getFromTime()), getEndOfDay(shift.getToTime()))))
                .orderBy(mis.MISSING.desc(), field((wish.SHIFT_ID).isNotNull()).desc(), field(ifnull(EMPLOYEE.POSITION_PERCENTAGE.mul(HOURS_IN_WEEK / 100).minus(etww.TIME_WORKED), EMPLOYEE.POSITION_PERCENTAGE.mul(HOURS_IN_WEEK / 100))).desc())
                .fetchInto(Employee.class);
    }

    private static LocalDateTime getEndOfDay(LocalDateTime date) {
        return date.with(LocalTime.MAX);
    }

    private static LocalDateTime getStartOfDay(LocalDateTime date) {
        return date.with(LocalTime.MIN);
    }

    public Duration getHoursWorked(int employee_id, int week) {
        EmployeeTimeWorkedWeek et = EMPLOYEE_TIME_WORKED_WEEK.as("et");
        String time = create.select(et.TIME_WORKED)
                .from(et)
                .where(et.EMPLOYEE_ID.eq(employee_id)
                        .and(et.WEEK_NUM.eq(week)))
                .fetchOneInto(String.class);
        String[] split = time.split(":");

        return Duration.ofHours(Long.parseLong(split[0])).plusMinutes(Long.parseLong(split[1]));
    }

    public List<MissingPerShiftCategory> getMissingForShift(int shift_id){
        return create.selectFrom(MISSING_PER_SHIFT_CATEGORY)
                .where(MISSING_PER_SHIFT_CATEGORY.SHIFT_ID.eq(shift_id))
                .fetchInto(MissingPerShiftCategory.class);
    }
}