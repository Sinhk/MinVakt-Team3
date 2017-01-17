package minvakt.controller;

import minvakt.controller.data.DateWrapper;
import minvakt.controller.data.TwoIntData;
import minvakt.datamodel.Employee;
import minvakt.datamodel.Shift;
import minvakt.datamodel.ShiftAssignment;
import minvakt.datamodel.enums.ShiftStatus;
import minvakt.repos.EmployeeRepository;
import minvakt.repos.ShiftAssignmentRepository;
import minvakt.repos.ShiftRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/shifts")
public class ShiftController {

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    private ShiftRepository shiftRepo;
    private EmployeeRepository employeeRepo;
    private ShiftAssignmentRepository shiftAssignmentRepo;


    @Autowired
    public ShiftController(ShiftRepository shiftRepo, EmployeeRepository employeeRepository, ShiftAssignmentRepository shiftAssignmentRepo) {
        this.shiftRepo = shiftRepo;
        this.employeeRepo = employeeRepository;
        this.shiftAssignmentRepo = shiftAssignmentRepo;
    }

    @GetMapping
    public Iterable<Shift> getShifts(){
        return shiftRepo.findAll();
    }

    @RequestMapping("/{shift_id}")
    @GetMapping
    public Shift getShift(@PathVariable int shift_id){

        return shiftRepo.findOne(shift_id);

    }

    @PostMapping
    public void addShift(@RequestBody DateWrapper shift){

        shiftRepo.save(shift.toShift());

    }


    @RequestMapping(value = "/{shift_id}", method = RequestMethod.PUT)
    @Transactional
    public Response addUserToShift(@RequestBody TwoIntData intData) { // shift id and user id

        Shift shift = shiftRepo.findOne(intData.getInt1());

        Employee employee = employeeRepo.findOne(intData.getInt2());

        ShiftAssignment shiftAssignment = new ShiftAssignment(shift, employee);

        shift.getShiftAssignments().add(shiftAssignment);

        shiftRepo.save(shift);
        return Response.ok().build();
    }

    @RequestMapping(value = "/{shift_id}/users", method = RequestMethod.GET)
    @Transactional
    public List<Employee> getUsersForShift(@PathVariable int shift_id) {

        Shift shift = shiftRepo.findOne(shift_id);

        return employeeRepo.findByShiftAssignments_Shift(shift);
        /*List<User> users = new ArrayList<>();
        for (ShiftAssignment assignment : shift.getShiftAssignments()) {
            users.add(assignment.getUser());
        }
        return users;*/
    }

    /**
     * Lord forgive me
     */
    @GetMapping
    @RequestMapping(value = "/suitable", method = RequestMethod.GET)
    public Iterable<Shift> getSuitableShiftsForUser(){

        // Details for logged in user
        UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Employee user = employeeRepo.findByEmail(details.getUsername());

        System.out.println(user);

        // All shift that are have changerequests
        List<Shift> changeRequestShifts = shiftAssignmentRepo
                .findAll()
                .stream()
                .filter(s -> s.getStatus().equals(ShiftStatus.REQUESTCHANGE))
                .map(ShiftAssignment::getShift)
                .collect(Collectors.toList());
        //

        // Shifts that require employees
        Iterable<Shift> req = shiftRepo.findAll();

        List<Shift> requiresEmployees = new ArrayList<>((Collection<? extends Shift>) req);

        Map<Shift, Integer> shiftsWithEmployeeCountMap = new HashMap<>();

        requiresEmployees.forEach(
                shiftAssignment ->{
                    if (shiftsWithEmployeeCountMap.containsKey(shiftAssignment)){shiftsWithEmployeeCountMap.put(shiftAssignment, shiftsWithEmployeeCountMap.get(shiftAssignment));}
                    else shiftsWithEmployeeCountMap.put(shiftAssignment,1);
                }
        );

        List<Shift> shiftsThatRequiresEmployees = new ArrayList<>();

        shiftsWithEmployeeCountMap.forEach((shift, integer) -> {
            if (integer < shift.getRequiredEmployees()) shiftsThatRequiresEmployees.add(shift);
        });
        //
        // Shifts that belong to the user
        List<Shift> allShiftsForUser = shiftAssignmentRepo
                .findAll()
                .stream()
                .filter(shiftAssignment -> shiftAssignment.getEmployee() != user)
                .map(ShiftAssignment::getShift)
                .collect(Collectors.toList());

        System.out.println("ChangeRequests: "+changeRequestShifts);
        System.out.println("Shifts that belong to the user: "+allShiftsForUser);
        System.out.println("Shift that require employees: "+shiftsThatRequiresEmployees);

        changeRequestShifts.addAll(allShiftsForUser);
        changeRequestShifts.addAll(shiftsThatRequiresEmployees);

        // looks through all the all the shifts, all their assignments, filters the ones not
        // connected to the user
       /* List<List<ShiftAssignment>> collect1 = shiftList
                .stream()
                .map(Shift::getShiftAssignments)
                .filter(shiftAssignments -> shiftAssignments
                        .stream()
                        .filter(shiftAssignment -> shiftAssignment.getEmployee() != user)
                        .collect(Collectors.toList()).contains(user))
                .collect(Collectors.toList());

        collect1
                .forEach(shiftAssignments -> shiftAssignments
                        .forEach(shiftAssignment -> changeRequestShifts.add(shiftAssignment.getShift())));

*/

       // removes duplicates, just a bit of a hack
        HashSet<Shift> shifts = new HashSet<>(changeRequestShifts);
        ArrayList<Shift> shifts1 = new ArrayList<>(shifts);
        return shifts1.stream().filter(shift -> shift.getStartDateTime().isAfter(LocalDateTime.now())).collect(Collectors.toList());

    }

    int getEmployeesOnShift(int shiftId){

        return (int) shiftAssignmentRepo.findAll().stream().filter(shiftAssignment -> shiftAssignment.getShift().getShiftId() == shiftId).count();

    }

    @GetMapping
    @RequestMapping(value = "/{shift_id}/responsible")
    public Employee getResponsibleEmployeeForShift(@PathVariable int shift_id){

        return shiftAssignmentRepo
                .findAll()
                .stream()
                .filter(shiftAssignment -> shiftAssignment.getShift().getShiftId() == shift_id)
                .filter(ShiftAssignment::isResponsible)
                .map(ShiftAssignment::getEmployee)
                .findFirst()
                .get();

    }

    @GetMapping(value = "/{shift_id}/{employee_id}/status")
    public ShiftStatus getShiftStatusForUserAndShift(@PathVariable int shift_id, @PathVariable int employee_id) {

        return shiftAssignmentRepo
                .findAll()
                .stream()
                .filter(shiftAssignment -> shiftAssignment.getShift().getShiftId() == shift_id)
                .filter(shiftAssignment -> shiftAssignment.getEmployee().getEmployeeId() == employee_id)
                .map(ShiftAssignment::getStatus)
                .findFirst()
                .get();
    }

    @PutMapping(value = "/{shift_id}/{employee_id}/status")
    public void setShiftStatusForUserAndShift(@PathVariable int shift_id, @PathVariable int employee_id, @RequestBody ShiftStatus status) {

        shiftAssignmentRepo
                .findAll()
                .stream()
                .filter(shiftAssignment -> shiftAssignment.getShift().getShiftId() == shift_id)
                .filter(shiftAssignment -> shiftAssignment.getEmployee().getEmployeeId() == employee_id)
                .findFirst()
                .ifPresent(shiftAssignment -> shiftAssignment.setStatus(status));
    }

    @GetMapping(value = "/{shift_id}/{employee_id}/responsible")
    public boolean userIsResponsibleForShift(@PathVariable int shift_id, @PathVariable int employee_id) {

        return shiftAssignmentRepo
                .findAll()
                .stream()
                .filter(shiftAssignment -> shiftAssignment.getShift().getShiftId() == shift_id)
                .filter(shiftAssignment -> shiftAssignment.getEmployee().getEmployeeId() == employee_id)
                .map(ShiftAssignment::isResponsible)
                .findFirst()
                .get();
    }

    @PutMapping(value = "/{shift_id}/{employee_id}/responsible")
    public void setUserIsResponsibleForShift(@PathVariable int shift_id, @PathVariable int employee_id, @RequestBody boolean resp) {

         shiftAssignmentRepo
                .findAll()
                .stream()
                .filter(shiftAssignment -> shiftAssignment.getShift().getShiftId() == shift_id)
                .filter(shiftAssignment -> shiftAssignment.getEmployee().getEmployeeId() == employee_id)
                .findFirst()
                .ifPresent(shiftAssignment -> shiftAssignment.setResponsible(resp));
    }
}

