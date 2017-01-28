package minvakt.repos;

import minvakt.datamodel.AssignedEmployee;
import minvakt.datamodel.ShiftDetailed;
import minvakt.datamodel.tables.AssignedPerShift;
import minvakt.datamodel.tables.EmployeeTimeWorkedWeek;
import minvakt.datamodel.tables.ShiftAssignment;
import minvakt.datamodel.tables.pojos.Employee;
import minvakt.datamodel.tables.pojos.MissingPerShiftCategory;
import minvakt.datamodel.tables.pojos.Shift;
import org.jooq.DSLContext;
import org.jooq.Record10;
import org.jooq.Record2;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.*;

import static minvakt.Application.HOURS_IN_WEEK;
import static minvakt.datamodel.Tables.*;
import static org.jooq.impl.DSL.*;

@Controller
public class JooqRepository {

    private static final Logger log = LoggerFactory.getLogger(JooqRepository.class);

    private final DSLContext create;

    @Autowired
    public JooqRepository(DSLContext dslContext) {
        this.create = dslContext;
    }

    public List<ShiftDetailed> getShiftDetailed() {
        List<ShiftDetailed> shifts = new ArrayList<>();
        Map<Integer, Result<Record10<Integer, LocalDateTime, LocalDateTime, Integer, Short, Short, Boolean, Integer, String, String>>> resultMap = this.create
                .select(SHIFT.SHIFT_ID, SHIFT.TO_TIME, SHIFT.FROM_TIME, SHIFT.RESPONSIBLE_EMPLOYEE_ID, SHIFT.DEPARTMENT_ID, SHIFT.REQUIRED_EMPLOYEES, SHIFT_ASSIGNMENT.ABSENT,
                        EMPLOYEE.EMPLOYEE_ID, EMPLOYEE.FIRST_NAME, EMPLOYEE.LAST_NAME)
                .from(SHIFT).naturalJoin(SHIFT_ASSIGNMENT).naturalJoin(EMPLOYEE)
                .fetchGroups(SHIFT.SHIFT_ID);

        resultMap.values().forEach(records -> shifts.add(mapShiftDetailed(records)));

        return shifts;
    }

    private ShiftDetailed mapShiftDetailed(Result<Record10<Integer, LocalDateTime, LocalDateTime, Integer, Short, Short, Boolean, Integer, String, String>> records) {
        if(records.isEmpty()) return new ShiftDetailed();
        Record10<Integer, LocalDateTime, LocalDateTime, Integer, Short, Short, Boolean, Integer, String, String> aRecord = records.get(0);
        ShiftDetailed shiftDetailed = new ShiftDetailed(aRecord.value1(), aRecord.value4(), aRecord.value2(), aRecord.value3(), aRecord.value5(), aRecord.value6());
        records.forEach(record -> {
            AssignedEmployee employee = new AssignedEmployee();
            employee.setEmployeeId(record.value8());
            employee.setFirstName(record.value9());
            employee.setLastName(record.value10());
            if (employee.getEmployeeId().equals(shiftDetailed.getResponsibleEmployeeId())) {
                shiftDetailed.setResponsible(employee);
            }
            shiftDetailed.addEmployee(employee);
        });
        return shiftDetailed;
    }

    public ShiftDetailed getShiftDetailed(int shift_id) {
        Result<Record10<Integer, LocalDateTime, LocalDateTime, Integer, Short, Short, Boolean, Integer, String, String>> result = this.create
                .select(SHIFT.SHIFT_ID, SHIFT.TO_TIME, SHIFT.FROM_TIME, SHIFT.RESPONSIBLE_EMPLOYEE_ID, SHIFT.DEPARTMENT_ID, SHIFT.REQUIRED_EMPLOYEES, SHIFT_ASSIGNMENT.ABSENT,
                        EMPLOYEE.EMPLOYEE_ID, EMPLOYEE.FIRST_NAME, EMPLOYEE.LAST_NAME)
                .from(SHIFT).naturalJoin(SHIFT_ASSIGNMENT).naturalJoin(EMPLOYEE)
                .where(SHIFT.SHIFT_ID.eq(shift_id))
                .fetch();

        return mapShiftDetailed(result);
    }

    public Iterable<?> getShiftDetailed(LocalDate from, LocalDate to) {
        List<ShiftDetailed> shifts = new ArrayList<>();
        Map<Integer, Result<Record10<Integer, LocalDateTime, LocalDateTime, Integer, Short, Short, Boolean, Integer, String, String>>> resultMap = this.create
                .select(SHIFT.SHIFT_ID, SHIFT.TO_TIME, SHIFT.FROM_TIME, SHIFT.RESPONSIBLE_EMPLOYEE_ID, SHIFT.DEPARTMENT_ID, SHIFT.REQUIRED_EMPLOYEES, SHIFT_ASSIGNMENT.ABSENT,
                        EMPLOYEE.EMPLOYEE_ID, EMPLOYEE.FIRST_NAME, EMPLOYEE.LAST_NAME)
                .from(SHIFT).naturalJoin(SHIFT_ASSIGNMENT).naturalJoin(EMPLOYEE)
                .where(SHIFT.FROM_TIME.between(from.atStartOfDay(),to.atStartOfDay()))
                .fetchGroups(SHIFT.SHIFT_ID);

        resultMap.values().forEach(records -> shifts.add(mapShiftDetailed(records)));

        return shifts;
    }

    public List<Shift> getAvailableShifts() {
        return create.select().from(ASSIGNED_PER_SHIFT)
                .leftJoin(SHIFT).on(SHIFT.SHIFT_ID.eq(ASSIGNED_PER_SHIFT.SHIFT_ID))
                .where(ASSIGNED_PER_SHIFT.NUM_ASSIGNED
                        .lt(ASSIGNED_PER_SHIFT.REQUIRED_EMPLOYEES.cast(Long.class)))
                .and(SHIFT.FROM_TIME.greaterThan(LocalDateTime.now()))
                .fetchInto(Shift.class);
    }

    public List<Shift> getAvailableShiftsForCategory(int categoryId) {
        AssignedPerShift assi = ASSIGNED_PER_SHIFT.as("assi");
        minvakt.datamodel.tables.MissingPerShiftCategory mis = MISSING_PER_SHIFT_CATEGORY.as("mis");
        return create
                .select().from(mis)
                .leftJoin(assi).on(assi.SHIFT_ID.eq(mis.SHIFT_ID))
                .leftJoin(SHIFT).on(SHIFT.SHIFT_ID.eq(ASSIGNED_PER_SHIFT.SHIFT_ID))
                .where(mis.CATEGORY_ID.notEqual((short) categoryId))
                .and(SHIFT.FROM_TIME.greaterThan(LocalDateTime.now()))
                .groupBy(assi.SHIFT_ID)
                .having(sum(mis.MISSING).lt(assi.NUM_MISSING.cast(BigDecimal.class)))
                .fetchInto(Shift.class);

    }

    public List<Employee> getCandidatesForShift(Shift shift) {
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
                .where(EMPLOYEE_CATEGORY.AVAILABLE_FOR_SHIFTS.eq(Boolean.TRUE))
                .orderBy(mis.MISSING.desc(), field((wish.SHIFT_ID).isNotNull()).desc(), field(ifnull(EMPLOYEE.POSITION_PERCENTAGE.mul(HOURS_IN_WEEK / 100).minus(etww.TIME_WORKED), EMPLOYEE.POSITION_PERCENTAGE.mul(HOURS_IN_WEEK / 100))).desc())
                .fetchInto(Employee.class);
    }

    private static LocalDateTime getEndOfDay(LocalDateTime date) {
        return date.with(LocalTime.MAX);
    }

    private static LocalDateTime getStartOfDay(LocalDateTime date) {
        return date.with(LocalTime.MIN);
    }

    public Duration getHoursWorked(int employee_id, int week, int year) {
        EmployeeTimeWorkedWeek et = EMPLOYEE_TIME_WORKED_WEEK.as("et");
        String time = create.select(et.TIME_WORKED)
                .from(et)
                .where(et.EMPLOYEE_ID.eq(employee_id)
                        .and(et.WEEK_NUM.eq(week)).and(et.YEAR_FIELD.eq(year)))
                .fetchOneInto(String.class);

        if (time == null) {
            return Duration.ZERO;
        }

        String[] split = time.split(":");

        return Duration.ofHours(Long.parseLong(split[0])).plusMinutes(Long.parseLong(split[1]));
    }

    public Map<Integer, Duration> getHoursWorked(LocalDate start, LocalDate end) {
        Map<Integer, Duration> hourMap = new HashMap<>();
        EmployeeTimeWorkedWeek et = EMPLOYEE_TIME_WORKED_WEEK.as("et");
        Result<Record2<Integer, String>> record2s = create.select(et.EMPLOYEE_ID, et.TIME_WORKED)
                .from(et)
                .where(et.START_OF_WEEK.between(start, end))
                .groupBy(et.EMPLOYEE_ID)
                .fetch();
        record2s.forEach(record -> {
                    String time = record.value2();
                    Duration duration;
                    if (time == null) {
                        duration = Duration.ZERO;
                    } else {
                        String[] split = time.split(":");
                        duration = Duration.ofHours(Long.parseLong(split[0])).plusMinutes(Long.parseLong(split[1]));
                    }
                    Integer employee = record.value1();
                    hourMap.put(employee, duration);
                });

        return hourMap;
    }

    public List<MissingPerShiftCategory> getMissingForShift(int shift_id) {
        return create.selectFrom(MISSING_PER_SHIFT_CATEGORY)
                .where(MISSING_PER_SHIFT_CATEGORY.SHIFT_ID.eq(shift_id))
                .fetchInto(MissingPerShiftCategory.class);
    }
}