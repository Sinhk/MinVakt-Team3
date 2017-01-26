package minvakt.controller;

import minvakt.datamodel.tables.pojos.Employee;
import minvakt.datamodel.tables.pojos.Shift;
import minvakt.datamodel.tables.pojos.ShiftAssignment;
import minvakt.repos.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/shifts")
public class ShiftController {

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    private ShiftRepository shiftRepo;
    private EmployeeRepository employeeRepo;
    private ShiftAssignmentRepository shiftAssignmentRepo;
    private ChangeRequestRepository changeRequestRepository;
    private JooqRepository jooqRepo;
    private DepartmentRepository departmentRepo;

    @Autowired
    public ShiftController(ShiftRepository shiftRepo, EmployeeRepository employeeRepository, ShiftAssignmentRepository shiftAssignmentRepo, ChangeRequestRepository changeRequestRepository, JooqRepository jooqRepo,DepartmentRepository departmentRepo) {
        this.shiftRepo = shiftRepo;
        this.employeeRepo = employeeRepository;
        this.shiftAssignmentRepo = shiftAssignmentRepo;
        this.changeRequestRepository = changeRequestRepository;
        this.jooqRepo = jooqRepo;
        this.departmentRepo = departmentRepo;
    }

    @GetMapping
    public Iterable<?> getShifts(@RequestParam(defaultValue = "false") boolean detailed){
        if (detailed) {
            return jooqRepo.getShiftDetailed();
        }
        return shiftRepo.findAll();
    }

    @GetMapping("/limited")
    public Iterable<?> getShiftsBetween(@RequestParam("from")
                                            @DateTimeFormat( iso = DateTimeFormat.ISO.DATE )LocalDate from,
                                         @RequestParam("to") @DateTimeFormat( iso = DateTimeFormat.ISO.DATE ) LocalDate to) {

            return shiftRepo.findBetweenDates(from.atStartOfDay(), to.atStartOfDay());
    }


    // TODO: 19.01.2017 do this automagically
    @PostMapping
    public void addShift(@RequestBody Shift shift) {
        shiftRepo.save(shift);
    }

    @GetMapping(value = "/{shift_id}")
    public Shift getShift(@PathVariable int shift_id) {

        return shiftRepo.findOne(shift_id);
    }

    @GetMapping("/{shift_id}/details/{user_id}")
    public ShiftAssignment getShiftAssignmentForShiftAndUser(@PathVariable int shift_id, @PathVariable int user_id) {

        return shiftAssignmentRepo.findByShiftIdAndEmployeeId(shift_id, user_id).get();
    }

    @GetMapping(value = "/assigned")
    public List<ShiftAssignment> getAllAssignedShifts() {

        return shiftAssignmentRepo.findByAssignedTrue();
    }

    @GetMapping(value = "/nonassigned")
    public List<ShiftAssignment> getAllNonAssignedShifts() {

        return shiftAssignmentRepo.findByAssignedFalse();
    }

    @GetMapping(value = "/{shift_id}/users")
    @Transactional
    public List<Employee> getUsersForShift(@PathVariable int shift_id) {

        return employeeRepo.findByShiftAssignments_Shift(shift_id);
    }

    @PostMapping(value = "/{shift_id}/users")
    @Transactional
    public void addUserToShift(@PathVariable int shift_id, @RequestParam int user_id, @RequestParam boolean available, @RequestParam boolean responsible) { // shift id and user id

        System.out.println(shift_id + " . " + user_id);

        ShiftAssignment assignment = new ShiftAssignment();
        assignment.setEmployeeId(user_id);
        assignment.setShiftId(shift_id);
        assignment.setAbsent(false);
        assignment.setAssigned(false);
        assignment.setAvailable(available);

        if (responsible) setUserIsResponsibleForShift(shift_id, user_id);

        shiftAssignmentRepo.save(assignment);
    }

    @PutMapping(value = "/{shift_id}/users")
    @Transactional
    public void changeUserAssignment(@PathVariable int shift_id, @RequestParam int user_id, @RequestParam(required = false) Boolean available, @RequestParam(required = false) Boolean responsible, @RequestParam(required = false) Boolean assigned, @RequestParam(required = false) Boolean absent, @RequestParam(required = false) String comment) { // shift id and user id

        Optional<ShiftAssignment> oAssignment = shiftAssignmentRepo.findByShiftIdAndEmployeeId(shift_id, user_id);

        if (oAssignment.isPresent()) {

            ShiftAssignment assignment = oAssignment.get();

            assignment.setAbsent(absent != null ? absent : false);
            assignment.setAvailable(available != null ? available : false);
            assignment.setAssigned(assigned != null ? assigned : false);

            assignment.setCommentForAbsence(comment != null ? comment : "");

            if (responsible) setUserIsResponsibleForShift(shift_id, user_id);

            shiftAssignmentRepo.save(assignment);
        }
        else {
            ShiftAssignment assignment = new ShiftAssignment();

            assignment.setEmployeeId(user_id);
            assignment.setShiftId(shift_id);
            assignment.setAssigned(assigned != null ? assigned : false);
            assignment.setAvailable(available != null ? available : false);
            assignment.setCommentForAbsence(comment != null ? comment : "");
            assignment.setAbsent(absent != null ? absent : false);

            if (responsible) setUserIsResponsibleForShift(shift_id, user_id);

            shiftAssignmentRepo.save(assignment);
        }
    }

    @GetMapping(value = "/{shift_id}/users/{user_id}/assigned")
    @Transactional
    public boolean getUserIsAssignedForShift(@PathVariable int shift_id, @PathVariable int user_id) {

        return shiftAssignmentRepo.findByShiftIdAndEmployeeId(shift_id, user_id).filter(ShiftAssignment::getAssigned).isPresent();

    }

    @PutMapping(value = "/{shift_id}/users/{user_id}/assigned")
    @Transactional
    public boolean setUserAssignedForShift(@PathVariable int shift_id, @PathVariable int user_id) {

        Optional<ShiftAssignment> byShiftIdAndEmployeeId = shiftAssignmentRepo.findByShiftIdAndEmployeeId(shift_id, user_id);

        if (byShiftIdAndEmployeeId.isPresent()) {

            byShiftIdAndEmployeeId.get().setAssigned(true);
            return true;
        }
        return false;
    }

    @GetMapping(value = "/{shift_id}/details")
    public List<ShiftAssignment> getShiftAssignmentsForShift(@PathVariable int shift_id) {

        return shiftAssignmentRepo.findByShiftId(shift_id);
    }

    @GetMapping(value = "/{shift_id}/responsible")
    @Transactional
    public Employee getResponsibleUserForShift(@PathVariable int shift_id) {

        return employeeRepo.findResponsibleForShift(shift_id);
    }

    @PutMapping(value = "/{shift_id}/responsible")
    @Transactional
    public void setUserIsResponsibleForShift(@PathVariable int shift_id, @RequestBody int employee_id) {

        Shift shift = shiftRepo.findOne(shift_id);

        shift.setResponsibleEmployeeId(employee_id);

        shiftRepo.save(shift);
    }

    @Transactional
    @PutMapping(value = "/{shift_id}/users/{user_id}")
    public void changeShiftFromUserToUser(@PathVariable int shift_id, @PathVariable int user_id, @RequestBody int toUser_id) {

        System.out.println("Changing shift: " + shift_id + " from " + user_id + " to " + toUser_id);

        Optional<ShiftAssignment> assignment = shiftAssignmentRepo.findByShiftIdAndEmployeeId(shift_id, user_id);
        assignment.ifPresent(a -> {

            System.out.println(assignment + " - " + a);
            a.setAssigned(true);
            a.setEmployeeId(toUser_id);
            shiftAssignmentRepo.save(a);
        });
    }

    /*@GetMapping(value = "/byday")
    public List<Shift> getShiftsByDay(@RequestBody String day){

        LocalDate date = LocalDate.parse(day);

        Iterable<Shift> all = shiftRepo.findAll();

        List<Shift> shiftList = new ArrayList<>((Collection<? extends Shift>) all);

        return shiftList
                .stream()
                .filter(shift -> shift.getStartDateTime().toLocalDate().isEqual(date))
                .collect(Collectors.toList());

    }*/

    @GetMapping(value = "/{shift_id}/possible_users")
    public List<Employee> getAvailableForShift(@PathVariable int shift_id) {
        Shift shift = shiftRepo.findOne(shift_id);
        return jooqRepo.getEmployeesAvailableForShift(shift);
    }

    @GetMapping(value = "/available")
    public List<Shift> getAvailableShifts(HttpServletRequest request, @RequestParam(name = "category", defaultValue = "-1") int category_id) {

        if (category_id != -1) {
            return jooqRepo.getAvailableShiftsForCategory(category_id);
        }
        Principal principal = request.getUserPrincipal();
        if (request.isUserInRole("ROLE_ADMIN")) {
            return jooqRepo.getAvailableShifts();
        }

        Employee employee = employeeRepo.findByEmail(principal.getName());
        Short categoryId = employee.getCategoryId();
        List<Shift> assignedShifts = shiftRepo.findByShiftEmployeeId(employee.getEmployeeId());
        List<Shift> availableShifts = jooqRepo.getAvailableShiftsForCategory(categoryId);
        availableShifts.removeAll(assignedShifts);

        return availableShifts.stream()
                .filter(shift -> assignedShifts.stream()
                        .noneMatch(shift1 -> shift1.getFromTime().toLocalDate()
                                .equals(shift.getFromTime().toLocalDate())))
                .collect(Collectors.toList());
    }

    // TODO: 19-Jan-17 Fix/Remove :  Do we ever need just one?
    @GetMapping(value = "/{shift_id}/available")
    public boolean shiftIsAvailable(@PathVariable int shift_id) {
        return shiftAssignmentRepo.findByShiftId(shift_id).stream()
                //.filter(shiftAssignment -> shiftAssignment.getStatus() == ShiftStatus.AVAILABLE)
                //.map(ShiftAssignment::getShift)
                .collect(Collectors.toList()).contains(shiftAssignmentRepo.getOne(shift_id));

    }

    @GetMapping(value = "/{shift_id}/department")
    public String getDepartmentofShift(@PathVariable int shift_id) {
        return departmentRepo.findOne(shiftRepo.findOne(shift_id).getDepartmentId()).getDepartmentName();
    }

    // Winner of the Worst Method of 2017 Award goes to:
    private void addManyShifts(LocalDateTime start) {
        LocalDateTime morning = LocalDateTime.of(start.toLocalDate(), LocalTime.of(6,0)),
                      evening = LocalDateTime.of(start.toLocalDate(), LocalTime.of(14,0)),
                      night = LocalDateTime.of(start.toLocalDate(), LocalTime.of(22,0));
        Shift shift;

        for (int i = 0; i < 30; i++) {
            shift = new Shift();
            shift.setDepartmentId((short) 1);
            shift.setRequiredEmployees((short)10);

            shift.setFromTime(morning.plusDays(i));
            shift.setToTime(evening.plusDays(i));

            shiftRepo.save(shift);


            shift = new Shift();
            shift.setDepartmentId((short) 1);
            shift.setRequiredEmployees((short)10);

            shift.setFromTime(evening.plusDays(i));
            shift.setToTime(night.plusDays(i));

            shiftRepo.save(shift);


            shift = new Shift();
            shift.setDepartmentId((short) 1);
            shift.setRequiredEmployees((short)10);

            shift.setFromTime(night.plusDays(i));
            shift.setFromTime(morning.plusDays(i+1));

            shiftRepo.save(shift);
        }
    }

    @DeleteMapping("/shiftassignments/{shiftAssignment_id}")
    public void removeShiftAssignment(@PathVariable int shiftAssignment_id){

        shiftAssignmentRepo.delete(shiftAssignment_id);

    }
    @GetMapping("/shiftassignments/{shiftAssignment_id}")
    public ShiftAssignment getShiftByShiftAssignmentId(@PathVariable int shiftAssignment_id){


        return shiftAssignmentRepo.findOne(shiftAssignment_id);

    }
    @GetMapping("/shiftassignments/")
    public List<ShiftAssignment> getShiftAssignmentsForUser(@RequestParam int user_id){


        return shiftAssignmentRepo.findByEmployeeId(user_id);

    }
}