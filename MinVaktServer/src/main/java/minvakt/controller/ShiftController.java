package minvakt.controller;

import minvakt.datamodel.tables.pojos.Employee;
import minvakt.datamodel.tables.pojos.MissingPerShiftCategory;
import minvakt.datamodel.tables.pojos.Shift;
import minvakt.datamodel.tables.pojos.ShiftAssignment;
import minvakt.repos.*;
import minvakt.util.SendMailTLS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controller for interacting directly with the shifts and the assigned shifts in the database
 */
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

    /**
     * Autowired by Spring to gain access to all the specified controllers and repos
     * @param shiftRepo
     * @param employeeRepository
     * @param shiftAssignmentRepo
     * @param changeRequestRepository
     * @param jooqRepo
     * @param departmentRepo
     */
    @Autowired
    public ShiftController(ShiftRepository shiftRepo, EmployeeRepository employeeRepository, ShiftAssignmentRepository shiftAssignmentRepo, ChangeRequestRepository changeRequestRepository, JooqRepository jooqRepo, DepartmentRepository departmentRepo) {
        this.shiftRepo = shiftRepo;
        this.employeeRepo = employeeRepository;
        this.shiftAssignmentRepo = shiftAssignmentRepo;
        this.changeRequestRepository = changeRequestRepository;
        this.jooqRepo = jooqRepo;
        this.departmentRepo = departmentRepo;
    }

    /**
     * Gets all the shifts in the database
     * @param detailed if you want detailed shift (extra info)
     * @return an iterable with Shift or DetailedShift
     */
    @GetMapping
    public Iterable<?> getShifts(@RequestParam(defaultValue = "false") boolean detailed) {
        if (detailed) {
            return jooqRepo.getShiftDetailed();
        }
        return shiftRepo.findAll();
    }

    /**
     * Gets shifts within the specified range, in order to prevent too many shifts getting loaded at the same time
     * @param from From date, as specified by LocalDate
     * @param to To date
     * @param detailed If you want detailed shifts or not
     * @return Iterable of the shifts
     */
    @GetMapping("/limited")
    public Iterable<?> getShiftsBetween(@RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from, @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,@RequestParam(defaultValue = "false") boolean detailed) {
        if (detailed) {
            return jooqRepo.getShiftDetailed(from,to);
        }
        return shiftRepo.findBetweenDates(from.atStartOfDay(), to.atStartOfDay());
    }


    /**
     * Adds a shift to the database
     * @param shift The shift, constructed from JSON by Jacksjon
     */
    @PostMapping
    public void addShift(@RequestBody Shift shift) {
        shiftRepo.save(shift);
    }

    /**
     * Gets the shift with the specified ID
     * @param shift_id The shift ID
     * @return the shift
     */
    @GetMapping(value = "/{shift_id}")
    public Shift getShift(@PathVariable int shift_id,@RequestParam(defaultValue = "false") boolean detailed) {
        if (detailed) {
            return jooqRepo.getShiftDetailed(shift_id);
        }
        return shiftRepo.findOne(shift_id);
    }

    /**
     * Gets the ShiftAssignment with the specified user Id and shift Id, whom are composite primary keys for ShiftAssignment
     * @param shift_id The shift id
     * @param user_id The user id
     * @return
     */
    @GetMapping("/{shift_id}/details/{user_id}")
    public ShiftAssignment getShiftAssignmentForShiftAndUser(@PathVariable int shift_id, @PathVariable int user_id) {

        return shiftAssignmentRepo.findByShiftIdAndEmployeeId(shift_id, user_id).get();
    }

    /**
     * Gets all ShiftAssigned with assigned = 1 in the database
     * @return List of shifts with assigned = 1
     */
    @GetMapping(value = "/assigned")
    public List<ShiftAssignment> getAllAssignedShifts() {

        return shiftAssignmentRepo.findByAssignedTrue();
    }

    /**
     * Gets all ShiftAssigned with assigned = 0 in the database
     * @return List of shifts with assigned = 0
     */
    @GetMapping(value = "/nonassigned")
    public List<ShiftAssignment> getAllNonAssignedShifts() {

        return shiftAssignmentRepo.findByAssignedFalse();
    }

    /**
     * Gets all the users connected to the specified shift with a ShiftAssignment
     * @param shift_id the shift
     * @return A list of the employees with connections to the shift via ShiftAssignment
     */
    @GetMapping(value = "/{shift_id}/users")
    @Transactional
    public List<Employee> getUsersForShift(@PathVariable int shift_id) {

        return employeeRepo.findByShiftAssignments_Shift(shift_id);
    }

    /**
     * Sets the specified shift for the current user to available, which means that the user can work that day
     * @param request Request used to get the current user
     * @param shift_id The shift
     * @return
     */
    @PostMapping("/{shift_id}/wish")
    @Transactional
    public ResponseEntity<?> addWish(HttpServletRequest request, @PathVariable int shift_id) {
        Employee employee = employeeRepo.findByEmail(request.getUserPrincipal().getName());
        ShiftAssignment assignment = new ShiftAssignment();
        assignment.setAssigned(false);
        assignment.setAbsent(false);
        assignment.setAvailable(true);
        assignment.setEmployeeId(employee.getEmployeeId());
        assignment.setShiftId(shift_id);
        shiftAssignmentRepo.save(assignment);
        return ResponseEntity.ok().build();
    }

    /**
     * Adds the specified user to the specified shift
     * @param shift_id The shift
     * @param user_id The user
     * @param available Whether they are available or not
     * @param responsible Wherther they are going to be the responsible user for the shift or not
     */
    @PostMapping(value = "/{shift_id}/users")
    @Transactional
    public void addUserToShift(@PathVariable int shift_id, @RequestParam int user_id, @RequestParam(defaultValue = "true") boolean available, @RequestParam(defaultValue = "false") boolean responsible) { // shift id and user id

        ShiftAssignment assignment = new ShiftAssignment();
        assignment.setEmployeeId(user_id);
        assignment.setShiftId(shift_id);
        assignment.setAbsent(false);
        assignment.setAssigned(false);
        assignment.setAvailable(available);

        if (responsible) setUserIsResponsibleForShift(shift_id, user_id);

        shiftAssignmentRepo.save(assignment);
    }

    /**
     * Changes a ShiftAssignment with the provided fields
     * @param shift_id The shift
     * @param user_id The user
     * @param available Whether they are available for the shift or not
     * @param responsible Whether they are going to be the responsible user for the shift or not
     * @param assigned Whether they are going to be assigned to the shift or not
     * @param absent Whether they were absent from the shift or not
     * @param comment Comment for absence
     */
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
        if (absent) {
            try {
                SendMailTLS sendMailTLS = new SendMailTLS();
                List<Employee> eMails = getAvailableForShift(shift_id);
                sendMailTLS.sendFreeShiftToGroup("https://minvakt.herokuapp.com/ledigeVakter", eMails);
                String text = "Bruker: " + employeeRepo.findOne(user_id).getFirstName() + " " + employeeRepo.findOne(user_id).getLastName() + "\nHar tatt fri på vakten: " + shiftRepo.findOne(shift_id).getFromTime().toString() + "Kommentar: " + comment;
                List<Employee> all = employeeRepo.findAll();
                for (Employee one : all) {
                    if (one.getCategoryId() == 1) {
                        sendMailTLS.sendMessageAbsent(one.getEmail(), text);
                    }
                }
            }catch (Exception e ){} // FIXME: 27-Jan-17 Hack

        }

    }

    /**
     * Checks whether the user is assigned to the shift or not
     * @param shift_id the shift
     * @param user_id the user
     * @return if they are assigned or not
     */
    @GetMapping(value = "/{shift_id}/users/{user_id}/assigned")
    @Transactional
    public boolean getUserIsAssignedForShift(@PathVariable int shift_id, @PathVariable int user_id) {

        return shiftAssignmentRepo.findByShiftIdAndEmployeeId(shift_id, user_id).filter(ShiftAssignment::getAssigned).isPresent();

    }

    /**
     * Sets the user as assigned for the specified shift
     * @param shift_id the shift
     * @param user_id the user
     * @return if they are assigned or not
     */
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

    /**
     * Gets the ShiftAssignments for the specified shift, mainly used to easy server load
     * @param shift_id
     * @return List of shiftassignments
     */
    @GetMapping(value = "/{shift_id}/details")
    public List<ShiftAssignment> getShiftAssignmentsForShift(@PathVariable int shift_id) {

        return shiftAssignmentRepo.findByShiftId(shift_id);
    }

    /**
     * Gets the responsible user for the specified shift
     * @param shift_id the shift
     * @return The user whom is responsible for the shift
     */
    @GetMapping(value = "/{shift_id}/responsible")
    @Transactional
    public ResponseEntity<?> getResponsibleUserForShift(@PathVariable int shift_id) {
        Optional<Employee> employee = employeeRepo.findResponsibleForShift(shift_id);
        return employee.isPresent() ? ResponseEntity.ok(employee.get()) : ResponseEntity.ok().build();
    }

    /**
     * Sets the responsible user for the specified shift
     * @param shift_id the shift
     * @param employee_id the user to set as responsible
     */
    @PutMapping(value = "/{shift_id}/responsible")
    @Transactional
    public void setUserIsResponsibleForShift(@PathVariable int shift_id, @RequestBody int employee_id) {

        Shift shift = shiftRepo.findOne(shift_id);

        shift.setResponsibleEmployeeId(employee_id);

        shiftRepo.save(shift);
    }

    /**
     * Changes a shift from a user to another user, after it has been confirmed by an admin
     * @param shift_id The shift to change
     * @param user_id The user who wants to change
     * @param toUser_id The user to change to
     */
    @Transactional
    @PutMapping(value = "/{shift_id}/users/{user_id}")
    public void changeShiftFromUserToUser(@PathVariable int shift_id, @PathVariable int user_id, @RequestBody int toUser_id) {
        Optional<ShiftAssignment> assignment = shiftAssignmentRepo.findByShiftIdAndEmployeeId(shift_id, user_id);
        assignment.ifPresent(a -> {

            System.out.println(assignment + " - " + a);
            a.setAssigned(true);
            a.setEmployeeId(toUser_id);
            shiftAssignmentRepo.save(a);
        });
    }

    /**
     * Gets the users whom are set to available for the specified shift
     * @param shift_id The shift
     * @return List of user with available = 1 for the shift
     */
    @GetMapping(value = "/{shift_id}/possible_users")
    public List<Employee> getAvailableForShift(@PathVariable int shift_id) {
        Shift shift = shiftRepo.findOne(shift_id);
        List<Employee> availableForShift = jooqRepo.getCandidatesForShift(shift);
        LocalDate date = shift.getFromTime().toLocalDate();
        List<Employee> shiftDate = employeeRepo.findByShiftDate(Date.valueOf(date));
        availableForShift.removeAll(shiftDate);
        return availableForShift;
    }

    /**
     * Gets all shifts that are not filled with the correct amount or types of users
     * @param request
     * @param category_id
     * @return
     */
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

        return availableShifts.stream().filter(shift -> assignedShifts.stream().noneMatch(shift1 -> shift1.getFromTime().toLocalDate().equals(shift.getFromTime().toLocalDate()))).collect(Collectors.toList());
    }

    /**
     * Checks whether or not a shift is available
     * @param shift_id The shift to check
     * @return If it is available or not
     */
    @GetMapping(value = "/{shift_id}/available")
    public boolean shiftIsAvailable(@PathVariable int shift_id) {
        return shiftAssignmentRepo.findByShiftId(shift_id).stream()
                //.filter(shiftAssignment -> shiftAssignment.getStatus() == ShiftStatus.AVAILABLE)
                //.map(ShiftAssignment::getShift)
                .collect(Collectors.toList()).contains(shiftAssignmentRepo.getOne(shift_id));

    }

    /**
     * Gets the department for the specified shift
     * @param shift_id
     * @return The name of the department for the shift
     */
    @GetMapping(value = "/{shift_id}/department")
    public String getDepartmentofShift(@PathVariable int shift_id) {
        return departmentRepo.findOne(shiftRepo.findOne(shift_id).getDepartmentId()).getDepartmentName();
    }

    /**
     * Method to fill the database with a lot of shifts
     * @param start From. Adds 30 days into the future from this date
     */
    // Winner of the Worst Method of 2017 Award goes to:
    protected void addManyShifts(LocalDateTime start) {
        LocalDateTime morning = LocalDateTime.of(start.toLocalDate(), LocalTime.of(6, 0)), evening = LocalDateTime.of(start.toLocalDate(), LocalTime.of(14, 0)), night = LocalDateTime.of(start.toLocalDate(), LocalTime.of(22, 0));
        Shift shift;

        for (int i = 0; i < 30; i++) {
            shift = new Shift();
            shift.setDepartmentId((short) 1);
            shift.setRequiredEmployees((short) 10);

            shift.setFromTime(morning.plusDays(i));
            shift.setToTime(evening.plusDays(i));

            shiftRepo.save(shift);


            shift = new Shift();
            shift.setDepartmentId((short) 1);
            shift.setRequiredEmployees((short) 10);

            shift.setFromTime(evening.plusDays(i));
            shift.setToTime(night.plusDays(i));

            shiftRepo.save(shift);


            shift = new Shift();
            shift.setDepartmentId((short) 1);
            shift.setRequiredEmployees((short) 10);

            shift.setFromTime(night.plusDays(i));
            shift.setFromTime(morning.plusDays(i + 1));

            shiftRepo.save(shift);
        }
    }

    /**
     * Gets all the shiftassignments
     * @return
     */
    @GetMapping("/shiftassignments")
    public List<ShiftAssignment> getAllShiftAssignments() {
        return shiftAssignmentRepo.findAll();
    }

    /**
     * Gets the shiftassignment with the specified ID
     * @param shiftAssignment_id the id
     */
    @GetMapping("/shiftassignments/{shiftAssignment_id}")
    public ShiftAssignment getShiftAssignmentByShiftAssignmentId(@PathVariable int shiftAssignment_id) {


        return shiftAssignmentRepo.findOne(shiftAssignment_id);

    }

    /**
     * Deletes the shiftassignment with the specified id
     * @param shiftAssignment_id the id
     */
    @DeleteMapping("/shiftassignments/{shiftAssignment_id}")
    public void removeShiftAssignment(@PathVariable int shiftAssignment_id) {

        shiftAssignmentRepo.delete(shiftAssignment_id);

    }

    /**
     * Gets the shiftassignments for the specified user
     * @param user_id the user
     * @return a list of shiftassignments for the user
     */
    @GetMapping("/shiftassignments/")
    public List<ShiftAssignment> getShiftAssignmentsForUser(@RequestParam int user_id) {

        return shiftAssignmentRepo.findByEmployeeId(user_id);
    }

    /**
     * How many people are missing on a shift by category
     * @param shift_id the shift
     * @return list of how many are missing by category
     */
    @GetMapping("/{shift_id}/getAmountOnShiftWithRequired/")
    public List<MissingPerShiftCategory> getAmountOnShift(@PathVariable int shift_id) {
        return jooqRepo.getMissingForShift(shift_id);
    }

    /**
     * Returns a map with the userId mapped to how many hours they have worked in the specified month
     * @param month the month in question
     * @return Map with user ids and hours worked
     */
    @GetMapping("/totalhours")
    public Map<Integer, Long> getTotalHoursForMonth(@RequestParam int month) {

        LocalDate startOfMonth = LocalDate.of(LocalDate.now().getYear(), month, 1);

        Map<Integer, Duration> integerDurationMap = jooqRepo.getHoursWorked(startOfMonth, startOfMonth.plusDays(startOfMonth.getMonth().length(LocalDate.now().isLeapYear())));

        Map<Integer, Long> integerLongMap = new HashMap<>();

        integerDurationMap.forEach((integer, duration) -> {

            integerLongMap.put(integer, duration.toHours());

        });

        return integerLongMap;

    }

    /**
     * Sends an email with the monthly report to the specified email
     * @param eMail email account of the accounting office hopefully
     * @param text Text to send, hopefully the report
     * @return
     */
    @PostMapping("/{eMail}/sendTotalHours")
    public boolean sendTotalHours(@PathVariable String eMail, @RequestBody String text ){
        SendMailTLS sendMail = new SendMailTLS();
        sendMail.sendTotalHoursToThePayrollOffice(eMail,text);
        return true;
    }

}