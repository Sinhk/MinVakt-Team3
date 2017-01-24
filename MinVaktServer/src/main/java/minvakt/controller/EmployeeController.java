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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

    private final UserDetailsManager userDetailsManager;

    private ShiftController shiftController;


    private String createPassword() {
        return new RandomString(8).nextString();
    }

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepo, ShiftRepository shiftRepo, UserDetailsManager userDetailsManager, ShiftAssignmentRepository shiftAssignmentRepo, ChangeRequestRepository changeRequestRepository, CategoryRepository catRepo, ShiftController shiftController) {
        this.employeeRepo = employeeRepo;
        this.shiftRepo = shiftRepo;
        this.shiftAssignmentRepo = shiftAssignmentRepo;
        this.userDetailsManager = userDetailsManager;
        this.changeRequestRepository = changeRequestRepository;
        this.catRepo = catRepo;
        this.shiftController = shiftController;
    }

    @GetMapping("/current")
    public Employee getCurrentUser() {
        UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Employee user = employeeRepo.findByEmail(details.getUsername());

        return user;

    }

    @GetMapping
    public Iterable<Employee> getUsers() {//@RequestParam(value="name", defaultValue="World") String name) {
        return employeeRepo.findAll();
    }

    // TODO: 19-Jan-17 endre
    @GetMapping("/resource")
    public List<CalenderResource> getAsResource() {
        List<CalenderResource> resources = new ArrayList<>();
        employeeRepo.findAll().forEach(e -> resources.add(new CalenderResource(e.getEmployeeId(), e.getFirstName() + " " + e.getLastName())));
        return resources;
    }

    @PostMapping()
    //@Secured({"ROLE_ADMIN"})
    public String addEmployee(@RequestBody Employee employee) {

        String password = createPassword();
        log.info("Generated password: {}", password);
        // TODO: 16-Jan-17 send email
        /*
        sendMail.sendPassword(employee.getEmail(),password);
        */

        employeeRepo.saveAndFlush(employee);
        User user = new User(employee.getEmail(), password, new ArrayList<SimpleGrantedAuthority>() {{
            add(new SimpleGrantedAuthority("ROLE_USER"));
        }});
        userDetailsManager.updateUser(user);
        return password;
        /*URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(employee.getEmail()).toUri();
        return ResponseEntity.created(location).build();*/
    }

    @DeleteMapping("/{user_id}")
    public Response removeEmployee(@PathVariable int user_id) {
        Employee user = employeeRepo.findOne(user_id);
        user.setEnabled(false);
        if (user != null){
            employeeRepo.save(user);
            return Response.ok().build();
        }

        return Response.noContent().build();
    }

    /*@RequestMapping(value = "{email}", method = RequestMethod.GET)
    public Employee findUser(@PathVariable String email) {
        System.out.println("Finding user on email: " + email);
        return employeeRepo.findByEmail(email);
    }*/

    @GetMapping("/{user_id}")
    public Employee getUserById(@PathVariable int user_id){

        return employeeRepo.findOne(user_id);

    }

    // TODO: 19-Jan-17 validation
    @PutMapping("/{user_id}")
    public void changeEmployee(@PathVariable int user_id, @RequestBody Employee employee){

        Employee user = employeeRepo.findOne(user_id);

        if (employee.getEmail() != null) user.setEmail(employee.getEmail());
        if (employee.getPhone() != null) user.setPhone(employee.getPhone());
        if (employee.getFirstName() != null) user.setFirstName(employee.getFirstName());
        if (employee.getLastName() != null) user.setLastName(employee.getLastName());

        //employeeRepo.save(employee);
    }

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


    @GetMapping(value = "/{user_id}/shifts")
    public Collection<Shift> getShiftsForUser(@PathVariable int user_id){

        return shiftRepo.findByShiftAssignments_Employee_id(user_id);
    }

    @GetMapping(value = "/{user_id}/shifts/assigned")
    public Collection<Shift> getAssignedShiftsForUser(@PathVariable int user_id){

        return shiftAssignmentRepo.findByAssignedTrue().stream().filter(shiftAssignment -> shiftAssignment.getEmployeeId() == user_id).map(shiftAssignment -> shiftRepo.findOne(shiftAssignment.getShiftId())).collect(Collectors.toList());

    }

    // FIXME: 15.01.2017
    @GetMapping("/{user_id}/shifts/inrange/")
    public Collection<Shift> getShiftsForUserInRange(@PathVariable int user_id, @RequestParam String startDate, @RequestParam String endDate){

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        List<Shift> byShiftAssignments_user = shiftRepo.findByShiftAssignments_Employee_id(user_id);

        List<Shift> shiftList = byShiftAssignments_user
                .stream()
                .filter(
                        shift -> shift.getFromTime().isAfter(start.atStartOfDay())
                                && shift.getToTime().isBefore(end.atTime(23, 59)))
                .collect(Collectors.toList());

        return shiftList;
    }


    /*// TODO: 19-Jan-17 EmployeeCategory, todo filtre ordentlig
    @GetMapping("/{shift_id}/responsible/")
    public List<Employee> getEmployeesThatCanBeResponsible(@PathVariable int shift_id){

        return collect;
    }*/


    // TODO: 19.01.2017 Repo metode
    @GetMapping(value = "/canberesponsible/{shift_id}")
    public List<Employee> getEmployeesThatCanBeResponsible(@PathVariable int shift_id) {


        //Shift shift = shiftRepo.findOne(shift_id);

        List<Employee> all = employeeRepo.findAll();

        return all
                .stream()
                .filter(employee -> (employee.getCategoryId()) == 2)
                .filter(employee -> shiftController.getUsersForShift(shift_id).contains(employee))
                .collect(Collectors.toList());

    }


    // TODO: 19-Jan-17 flytt
    /*@Transactional
    @PostMapping(value = "/{email1}/changeCategory")
    public void changeCategory(@PathVariable(value = "email1") String email1, @PathVariable(value = "EmployeeCategory") EmployeeCategory category){

        Employee employee = employeeRepo.findByEmail(email1);

        employee.setCategory(category);

        employeeRepo.save(employee);

    }*/

    @GetMapping(value = "/{email}/category")
    public EmployeeCategory getCategory(@PathVariable(value = "email") String email) {

        Employee employee = employeeRepo.findByEmail(email);

        return catRepo.findOne(employee.getCategoryId());
    }

    @GetMapping("/{user_id}/hours")
    public int getHoursThisWeekForUser(@PathVariable int user_id){

        //Employee user = employeeRepo.findOne(user_id);

        // TODO: 19-Jan-17 Sjekke om det funke med employee_id
        Collection<Shift> shiftsForUser = shiftRepo.findByShiftAssignments_Employee_id(user_id);

        return shiftsForUser
                .stream()
                .filter(shift -> {

                    WeekDateInterval of = WeekDateInterval.of(shift.getFromTime().toLocalDate());


                    return TimeUtil.isInDateInterval(of.getStart(), of.getEnd(), shift.getToTime().toLocalDate());
                })
                .mapToInt(shift -> (int) ChronoUnit.HOURS.between(shift.getFromTime(), shift.getToTime()))
                .sum();

    }
    @PutMapping(value = "/{email}/getNewPassword")
    public boolean sendNewPassword(@PathVariable(value = "email") String email) {
        Employee employee = employeeRepo.findByEmail(email);
        if(employee==null) {
            System.out.println("No user with email: "+ email);
            return false;
        } else {
            String password = createPassword();
            /// TODO: 20.01.2017 SendMail
            //  sendMail.sendPassword(email,password);
            User user = new User(email, password, new ArrayList<SimpleGrantedAuthority>() {{
                add(new SimpleGrantedAuthority("ROLE_USER"));
            }});
            userDetailsManager.updateUser(user);
            return true;
        }

    }
}
