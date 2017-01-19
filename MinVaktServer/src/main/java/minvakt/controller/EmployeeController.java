package minvakt.controller;

import minvakt.controller.data.ChangePasswordInfo;
import minvakt.datamodel.Employee;
import minvakt.datamodel.EmployeeCategory;
import minvakt.datamodel.Shift;
import minvakt.datamodel.enums.EmployeeType;
import minvakt.repos.*;
import minvakt.util.RandomString;
import minvakt.util.TimeUtil;
import minvakt.util.WeekDateInterval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

    private final UserDetailsManager userDetailsManager;

    private ShiftController shiftController = new ShiftController(shiftRepo, employeeRepo, shiftAssignmentRepo, changeRequestRepository);

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepo, ShiftRepository shiftRepo, UserDetailsManager userDetailsManager, ShiftAssignmentRepository shiftAssignmentRepo, ChangeRequestRepository changeRequestRepository, CategoryRepository catRepo) {
        this.employeeRepo = employeeRepo;
        this.shiftRepo = shiftRepo;
        this.shiftAssignmentRepo = shiftAssignmentRepo;
        this.userDetailsManager = userDetailsManager;
        this.changeRequestRepository = changeRequestRepository;
        this.catRepo = catRepo;
    }

    @GetMapping
    public Iterable<Employee> getUsers() {//@RequestParam(value="name", defaultValue="World") String name) {
        return employeeRepo.findAll();
    }

    // TODO: 19-Jan-17 endre
    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public String addEmployee(@RequestBody Employee employee, @RequestBody int category_id) {

        EmployeeCategory cat = catRepo.getOne(category_id);
        employee.setCategory(cat);
        String password = new RandomString(8).nextString();
        log.info("Generated password: {}", password);
        // TODO: 16-Jan-17 send email

        employeeRepo.saveAndFlush(employee);
        User user = new User(employee.getEmail(), password, new ArrayList<SimpleGrantedAuthority>() {{
            add(new SimpleGrantedAuthority("ROLE_USER"));
        }});
        userDetailsManager.updateUser(user);
        return password;
    }

    @DeleteMapping
    public Response removeEmployee(@RequestBody int user_id) {
        Employee user = employeeRepo.findOne(user_id);

        if (user != null){
            employeeRepo.delete(user);
            return Response.ok().build();
        }

        return Response.noContent().build();
    }

    /*@RequestMapping(value = "{email}", method = RequestMethod.GET)
    public Employee findUser(@PathVariable String email) {
        System.out.println("Finding user on email: "+ email);
        return employeeRepo.findByEmail(email);
    }*/

    @GetMapping("/{user_id}")
    public Employee getUserById(@PathVariable int user_id){

        return employeeRepo.findOne(user_id);

    }

    // TODO: 19-Jan-17 validation
    @PutMapping("/{user_id}")
    public void changeEmployee(@PathVariable int user_id, @RequestBody Employee employee){

        if (employee.getEmployeeId() == user_id)
            employeeRepo.save(employee);

    }

    @PutMapping("/{user_id}/password")
    public Response changePasswordForUser(@PathVariable int user_id, @RequestBody ChangePasswordInfo info){
        String oldPass = info.getOldPassAttempt();
        String newPass = info.getNewPassAttempt();

        try {
            userDetailsManager.changePassword(oldPass, newPass);
        } catch (AccessDeniedException ade) {
            //Not signed in
            return Response.status(403).build();
        } catch (BadCredentialsException bce) {
            //Wrong oldPass
            return Response.notModified("Wrong password").build();
        }

        return Response.ok().build();
    }


    @GetMapping(value = "/{user_id}/shifts")
    public Collection<Shift> getShiftsForUser(@PathVariable int userId){

        Employee employee = employeeRepo.findOne(userId);

        return (employee != null) ? shiftRepo.findByShiftAssignments_Employee(employee) : Collections.emptyList();

    }

    // FIXME: 15.01.2017
    @RequestMapping("/{user_id}/shifts/inrange/")
    @GetMapping
    public Collection<Shift> getShiftsForUserInRange(@PathVariable int user_id, @RequestParam String startDate, @RequestParam String endDate){

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        Employee employee = employeeRepo.findOne(user_id);

        List<Shift> byShiftAssignments_user = shiftRepo.findByShiftAssignments_Employee(employee);

        List<Shift> shiftList = byShiftAssignments_user
                .stream()
                .filter(
                        shift -> shift.getStartDateTime().isAfter(start.atStartOfDay())
                                && shift.getEndDateTime().isBefore(end.atTime(23, 59)))
                .collect(Collectors.toList());

        return shiftList;
    }

   /* @Transactional
    @PostMapping
    @RequestMapping(value = "/{user_id}/shifts/{shift_id}", method = RequestMethod.POST)
    public Response addShiftToUser(@PathVariable(value = "user_id") int userId, @PathVariable(value = "shift_id") int shiftId){

        Employee employee = employeeRepo.findOne(userId);

        Shift shift = shiftRepo.findOne(shiftId);

        ShiftAssignment shiftAssignment = new ShiftAssignment(shift, employee);

        employee.getShiftAssignments().add(shiftAssignment);

        employeeRepo.save(employee);


        return Response.noContent().build();
    }*/

    /*@Transactional
    @DeleteMapping
    @RequestMapping(value = "/{user_id}/shifts/{shift_id}", method = RequestMethod.DELETE)
    public Response removeShiftFromUser (@PathVariable(value = "user_id") String userId, @PathVariable(value = "shift_id") String shiftId) {

        Employee employee = employeeRepo.findOne(Integer.valueOf(userId));

        Shift shift = shiftRepo.findOne(Integer.valueOf(shiftId));

        Optional<ShiftAssignment> first = employee.getShiftAssignments()
                .stream()
                .filter(shiftAssignment -> shiftAssignment.getShift() == shift)
                .findFirst();

        first.ifPresent(employee.getShiftAssignments()::remove);

        employeeRepo.save(employee);

        return Response.noContent().build();
    }*/


    // TODO: 19-Jan-17 fix
    @GetMapping
    @RequestMapping("/scheduled")
    public List<Shift> getScheduledShiftsForUser(){

        UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Employee user = employeeRepo.findByEmail(details.getUsername());

        return shiftRepo.findByShiftAssignments_Employee(user);
    }


    // TODO: 19-Jan-17 EmployeeCategory
    @GetMapping("/canberesponsible/")
    public List<Employee> getEmployeesThatCanBeResponsible(@RequestParam int shift_id){

        //Shift shift = shiftRepo.findOne(shift_id);

        List<Employee> all = employeeRepo.findAll();

        return  all
                .stream()
                .filter(employee -> EmployeeType.of(employee.getCategory()) == EmployeeType.NURSE)
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

    /*@GetMapping
    @RequestMapping(value = "/{email}/getCategory", method = RequestMethod.GET)
    public EmployeeCategory getCategory(@PathVariable(value = "email") String email){

        Employee employee = employeeRepo.findByEmail(email);


        return employee.getCategory();
    }*/

    @GetMapping("/{user_id}/hours")
    public int getHoursThisWeekForUser(@PathVariable int user_id){

        //Employee user = employeeRepo.findOne(user_id);

        // TODO: 19-Jan-17 Sjekke om det funke med employee_id
        Collection<Shift> shiftsForUser = shiftRepo.findByShiftAssignments_Employee_id(user_id);

        return shiftsForUser
                .stream()
                .filter(shift -> {

                    WeekDateInterval of = WeekDateInterval.of(shift.getStartDateTime().toLocalDate());


                    return TimeUtil.isInDateInterval(of.getStart(), of.getEnd(),shift.getStartDateTime().toLocalDate());
                })
                .mapToInt(shift -> (int)ChronoUnit.HOURS.between(shift.getStartDateTime(), shift.getEndDateTime()))
                .sum();

    }
}
