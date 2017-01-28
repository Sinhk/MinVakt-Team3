package minvakt.controller;

import minvakt.controller.data.CalenderResource;
import minvakt.controller.data.TwoStringsData;
import minvakt.datamodel.tables.pojos.Employee;
import minvakt.datamodel.tables.pojos.EmployeeCategory;
import minvakt.datamodel.tables.pojos.Shift;
import minvakt.repos.*;
import minvakt.util.RandomString;
import minvakt.util.SendMailTLS;
import minvakt.util.TimeUtil;
import minvakt.util.WeekDateInterval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controller to deal with employees in the database
 */
@RestController
@RequestMapping("/users")
public class EmployeeController {
    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    private EmployeeRepository employeeRepo;
    private ShiftRepository shiftRepo;
    private ShiftAssignmentRepository shiftAssignmentRepo;
    private ChangeRequestRepository changeRequestRepository;
    private CategoryRepository catRepo;
    private SendMailTLS sendMail = new SendMailTLS();
    private JooqRepository jooqRepo;

    private final UserDetailsManager userDetailsManager;

    private ShiftController shiftController;

    private String createPassword() {
        return new RandomString(8).nextString();
    }

    /**
     * Autowired by Spring to gain access to all the specified controllers and repos
     * @param employeeRepo
     * @param shiftRepo
     * @param userDetailsManager
     * @param shiftAssignmentRepo
     * @param changeRequestRepository
     * @param catRepo
     * @param jooqRepo
     * @param shiftController
     */
    @Autowired
    public EmployeeController(EmployeeRepository employeeRepo, ShiftRepository shiftRepo, UserDetailsManager userDetailsManager, ShiftAssignmentRepository shiftAssignmentRepo, ChangeRequestRepository changeRequestRepository, CategoryRepository catRepo, JooqRepository jooqRepo, ShiftController shiftController) {
        this.employeeRepo = employeeRepo;
        this.shiftRepo = shiftRepo;
        this.shiftAssignmentRepo = shiftAssignmentRepo;
        this.userDetailsManager = userDetailsManager;
        this.changeRequestRepository = changeRequestRepository;
        this.catRepo = catRepo;
        this.jooqRepo = jooqRepo;
        this.shiftController = shiftController;
    }

    /**
     * Gets the user that calls the method from the website
     * @return The user that uses this method
     */
    @GetMapping("/current")
    public Employee getCurrentUser() {

        UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Employee user = employeeRepo.findByEmail(details.getUsername());

        return user;

    }

    /**
     * Gets all the users
     * @return An iterable with all the users
     */
    @GetMapping
    public Iterable<Employee> getUsers() {//@RequestParam(value="name", defaultValue="World") String name) {
        return employeeRepo.findAll();
    }

    /**
     * Gets the users as a resource for FullCalendar
     * @return
     */
    @GetMapping("/resource")
    public List<CalenderResource> getAsResource() {
        List<CalenderResource> resources = new ArrayList<>();
        employeeRepo.findAll().forEach(e -> resources.add(new CalenderResource(e.getEmployeeId(), e.getFirstName() + " " + e.getLastName())));
        return resources;
    }

    /**
     * Adds an employee to the database
     * @param employee the employee to add, built by Spring and Jackson from a JSON object
     * @return
     */
    @PostMapping()
    @Secured({"ROLE_ADMIN"})
    public String addEmployee(@RequestBody Employee employee) {

        String password = createPassword();
        log.info("Generated password: {}", password);

        sendMail.sendPassword(employee.getEmail(), password);

        employeeRepo.saveAndFlush(employee);
        User user = new User(employee.getEmail(), password, new ArrayList<SimpleGrantedAuthority>() {{
            add(new SimpleGrantedAuthority("ROLE_USER"));
        }});

        userDetailsManager.updateUser(user);
        return password;
    }

    /**
     * Sets a user as inactive in the database
     * @param user_id The ID to delete
     * @return
     */
    @DeleteMapping("/{user_id}")
    @Secured({"ROLE_ADMIN"})
    public Response removeEmployee(@PathVariable int user_id) {
        Employee user = employeeRepo.findOne(user_id);

        if (user != null) {
            user.setEnabled(false);
            employeeRepo.save(user);
            return Response.ok().build();
        }
        return Response.noContent().build();
    }

    /**
     * Gets an user by their ID
     * @param user_id the user object
     * @return
     */
    @GetMapping("/{user_id}")
    public Employee getUserById(@PathVariable int user_id) {

        return employeeRepo.findOne(user_id);

    }

    // TODO: 19-Jan-17 validation

    /**
     * changes an employee into the new object
     * @param user_id
     * @param employee
     */
    @PutMapping("/{user_id}")
    public void changeEmployee(@PathVariable int user_id, @RequestBody Employee employee) {

        Employee user = employeeRepo.findOne(user_id);

        //Email is username, locked for now
        //if (employee.getEmail() != null) user.setEmail(employee.getEmail());
        if (employee.getPhone() != null) user.setPhone(employee.getPhone());
        if (employee.getFirstName() != null) user.setFirstName(employee.getFirstName());
        if (employee.getLastName() != null) user.setLastName(employee.getLastName());

        employeeRepo.save(user);
    }

    /**
     * Changes the password of the current user
     * @param info two Strings wrapped into one object because REST works that way, automatically done by Jackson. Takes in old and new password attempts
     * @return
     */
    @PutMapping("/password")
    public ResponseEntity changePasswordForUser(@RequestBody TwoStringsData info) {

        String oldPass = info.getString1();
        String newPass = info.getString2();

        try {
            userDetailsManager.changePassword(oldPass, newPass);
        } catch (AccessDeniedException ade) {
            //Not signed in
            return ResponseEntity.status(403).build();
        } catch (BadCredentialsException bce) {
            //Wrong oldPass
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("Wrong password");
        }

        return ResponseEntity.ok().build();
    }


    /**
     * Gets the shift for the specified user
     * @param user_id The user to get the shifts for
     * @return A list of the shifts for the user
     */
    @GetMapping(value = "/{user_id}/shifts")
    public List<Shift> getShiftsForUser(@PathVariable int user_id) {

        return shiftRepo.findByShiftEmployeeId(user_id);
    }

    /**
     * Gets the shift for the user with assigned = 1 in the database
     * @param user_id the user_id
     * @return List of shifts with assigned = 1
     */
    @GetMapping(value = "/{user_id}/shifts/assigned")
    public List<Shift> getAssignedShiftsForUser(@PathVariable int user_id) {

        return shiftRepo.findAssignedByShiftEmployeeId(user_id);

    }

    /**
     * Gets the shift for the user with available = 1 in the database
     * @param user_id the user_id
     * @return List of shifts with available = 1
     */
    @GetMapping(value = "/{user_id}/shifts/available")
    public Collection<Shift> getAvailableShiftsForUser(@PathVariable int user_id) {

        return shiftAssignmentRepo.findByAvailableTrue().stream().filter(shiftAssignment -> shiftAssignment.getEmployeeId() == user_id).map(shiftAssignment -> shiftRepo.findOne(shiftAssignment.getShiftId())).collect(Collectors.toList());

    }

    /**
     * Gets shifts for user within a range, used so you dont get so many shifts at the same time
     * @param user_id user in question
     * @param startDate Start, inclusive
     * @param endDate End, exclusive
     * @return
     */
    @GetMapping("/{user_id}/shifts/inrange/")
    public Collection<Shift> getShiftsForUserInRange(@PathVariable int user_id, @RequestParam String startDate, @RequestParam String endDate) {

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        List<Shift> byShiftAssignments_user = shiftRepo.findByShiftEmployeeId(user_id);

        List<Shift> shiftList = byShiftAssignments_user
                .stream()
                .filter(shift -> shift.getFromTime().isAfter(start.atStartOfDay())
                        && shift.getToTime().isBefore(end.atTime(23, 59)))
                .collect(Collectors.toList());

        return shiftList;
    }

    /**
     * Gets the category for the user with the specified email
     * @param email The email
     * @return Category
     */
    @GetMapping(value = "/{email}/category")
    public EmployeeCategory getCategory(@PathVariable(value = "email") String email) {

        Employee employee = employeeRepo.findByEmail(email);

        return catRepo.findOne(employee.getCategoryId());
    }


    /**
     * Gets the map with duration worked for each user this week specified by their id in the map
      * @return
     */
    @GetMapping("/hours")
    public Map<Integer, Duration> getHoursThisWeek() {
        LocalDate now = LocalDate.now();
        LocalDate date = now.with(DayOfWeek.MONDAY);
        return jooqRepo.getHoursWorked(date, date.plus(6, ChronoUnit.DAYS));
    }
    /**
     * Gets the hours worked for the specified user in the current week, from monday to sunday
     * @param user_id the user
     * @return amount of hours
     */
    @GetMapping("/{user_id}/hours")
    public int getHoursThisWeekForUser(@PathVariable int user_id){
        Collection<Shift> shiftsForUser = shiftRepo.findAssignedByShiftEmployeeId(user_id);
        return shiftsForUser
                .stream()
                .filter(shift -> {
                    WeekDateInterval of = WeekDateInterval.of(shift.getFromTime().toLocalDate());

                    return TimeUtil.isInDateInterval(of.getStart(), of.getEnd(), shift.getToTime().toLocalDate());
                })
                .mapToInt(shift -> (int) ChronoUnit.HOURS.between(shift.getFromTime(), shift.getToTime()))
                .sum();
    }

    /**
     * Sends an email to the specified email-account with a new password for the current user, in case they forget it
     * @param email Email accout to send the new password to
     * @return boolean, if the account is in the system or not
     */
    @PutMapping(value = "/{email}/getNewPassword")
    public boolean sendNewPassword(@PathVariable(value = "email") String email) {
        Employee employee = employeeRepo.findByEmail(email);
        if (employee == null) {
            return false;
        } else {
            String password = createPassword();
            sendMail.sendPassword(email, password);
            User user = new User(email, password, new ArrayList<SimpleGrantedAuthority>() {{
                add(new SimpleGrantedAuthority("ROLE_USER"));
            }});
            userDetailsManager.updateUser(user);
            return true;
        }
    }
}
