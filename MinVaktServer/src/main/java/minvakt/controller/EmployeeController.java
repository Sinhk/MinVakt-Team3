package minvakt.controller;

import minvakt.controller.data.ChangePasswordInfo;
import minvakt.controller.data.TwoStringsData;
import minvakt.datamodel.Employee;
import minvakt.datamodel.Shift;
import minvakt.datamodel.ShiftAssignment;
import minvakt.datamodel.enums.ShiftStatus;
import minvakt.repos.EmployeeRepository;
import minvakt.repos.ShiftAssignmentRepository;
import minvakt.repos.ShiftRepository;
import minvakt.util.RandomString;
import minvakt.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class EmployeeController {
    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    private EmployeeRepository employeeRepo;
    private ShiftRepository shiftRepo;
    private ShiftAssignmentRepository shiftAssignmentRepo;
    private final UserDetailsManager userDetailsManager;


    @Autowired
    public EmployeeController(EmployeeRepository employeeRepo, ShiftRepository shiftRepo, UserDetailsManager userDetailsManager, ShiftAssignmentRepository shiftAssignmentRepo) {
        this.employeeRepo = employeeRepo;
        this.shiftRepo = shiftRepo;
        this.shiftAssignmentRepo = shiftAssignmentRepo;
        this.userDetailsManager = userDetailsManager;
    }

    @GetMapping
    public Iterable<Employee> getUsers() {//@RequestParam(value="name", defaultValue="World") String name) {
        return employeeRepo.findAll();
    }

    @PostMapping
    public Response addEmployee(@RequestBody Employee employee) {

        String password = new RandomString(8).nextString();
        log.info("Generated password: {}", employee);
        // TODO: 16-Jan-17 send email
        employeeRepo.saveAndFlush(employee);
        userDetailsManager.updateUser(new User(employee.getEmail(), password, new ArrayList<SimpleGrantedAuthority>() {{add(new SimpleGrantedAuthority("ROLE_USER"));}}));
        return Response.ok().build();
    }

    @DeleteMapping
    public Response removeEmployee(@RequestBody String email) {
        Employee byEmail = employeeRepo.findByEmail(email);

        if (byEmail != null){
            employeeRepo.delete(byEmail);
            return Response.ok().build();
        }

        return Response.noContent().build();
    }

    @RequestMapping(value = "{email}", method = RequestMethod.GET)
    public Employee findUser(@PathVariable String email) {
        System.out.println("Finding user on email: "+ email);
        return employeeRepo.findByEmail(email);
    }

    @RequestMapping(value = "/{user_id}/changepassword", method = RequestMethod.PUT)
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


    @RequestMapping(value = "/{user_id}/shifts", method = RequestMethod.GET)
    public Collection<Shift> getShiftsForUser(@PathVariable(value="user_id") int userId){

        Employee employee = employeeRepo.findOne(userId);

        return (employee != null) ? shiftRepo.findByShiftAssignments_Employee(employee) : Collections.emptyList();

    }

    // FIXME: 15.01.2017
    @RequestMapping("/{user_id}/shifts/inrange")
    @GetMapping
    public Collection<Shift> getShiftsForUserInRange(@PathVariable int user_id, @RequestBody TwoStringsData stringsData){

        String startDate = stringsData.getString1();
        String endDate = stringsData.getString2();

        LocalDate start = TimeUtil.parseBadlyFormattedTime(startDate);
        LocalDate end = TimeUtil.parseBadlyFormattedTime(endDate);

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

    @Transactional
    @PostMapping
    @RequestMapping(value = "/{user_id}/shifts/{shift_id}", method = RequestMethod.POST)
    public Response addShiftToUser(@PathVariable(value = "user_id") int userId, @PathVariable(value = "shift_id") int shiftId){

        Employee employee = employeeRepo.findOne(userId);

        Shift shift = shiftRepo.findOne(shiftId);

        ShiftAssignment shiftAssignment = new ShiftAssignment(shift, employee);

        employee.getShiftAssignments().add(shiftAssignment);

        employeeRepo.save(employee);


        return Response.noContent().build();
    }

    @Transactional
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
    }


    @GetMapping
    @RequestMapping("/scheduled")
    public List<Shift> getScheduledShiftsForUser(){

        UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Employee user = employeeRepo.findByEmail(details.getUsername());

        List<Shift> collect = shiftAssignmentRepo.findAll().stream().filter(shiftAssignment -> shiftAssignment.getEmployee().getEmployeeId() == user.getEmployeeId()).filter(shiftAssignment -> {

                    System.out.println(shiftAssignment.getStatus());

                    return shiftAssignment.getStatus() == ShiftStatus.SCHEDULED || shiftAssignment.getStatus() == ShiftStatus.AVAILABLE || shiftAssignment.getStatus() == ShiftStatus.REQUESTCHANGE;
                }

        ).map(ShiftAssignment::getShift).collect(Collectors.toList());

        System.out.println(collect);

        return collect;
    }

}
