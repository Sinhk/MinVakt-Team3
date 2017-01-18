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
import java.time.LocalDate;
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
        //System.out.println("Shift that require employees: "+shiftsThatRequiresEmployees);

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
    @Transactional
    public void setShiftStatusForUserAndShift(@PathVariable int shift_id, @PathVariable int employee_id, @RequestBody String status) {

        ShiftAssignment shiftAssign = shiftAssignmentRepo
                .findAll()
                .stream()
                .filter(shiftAssignment -> shiftAssignment.getShift().getShiftId() == shift_id)
                .filter(shiftAssignment -> shiftAssignment.getEmployee().getEmployeeId() == employee_id)
                .findFirst()
                .get();

        shiftAssign.setStatus(ShiftStatus.valueOf(status));

        //shiftAssignmentRepo.save(shiftAssign);

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
    @Transactional
    public void setUserIsResponsibleForShift(@PathVariable int shift_id, @PathVariable int employee_id, @RequestBody boolean resp) {

         ShiftAssignment shiftAssign = shiftAssignmentRepo
                .findAll()
                .stream()
                .filter(shiftAssignment -> shiftAssignment.getShift().getShiftId() == shift_id)
                .filter(shiftAssignment -> shiftAssignment.getEmployee().getEmployeeId() == employee_id)
                .findFirst()
                .get();

         shiftAssign.setResponsible(resp);

         shiftAssignmentRepo.save(shiftAssign);
    }

    @Transactional
    @PutMapping(value = "/{shift_id}/changeUser")
    public void changeShiftFromUserToUser(@PathVariable int shift_id, @RequestBody TwoIntData userIds){

        Shift shift = shiftRepo.findOne(shift_id);

        Employee fromUser = employeeRepo.findOne(userIds.getInt1());
        Employee toUser = employeeRepo.findOne(userIds.getInt2());

        //ShiftAssignment shiftAssignment = shiftAssignmentRepo.findOne(new ShiftAssignmentPK(shift.getShiftId(), fromUser.getEmployeeId()));

        Optional<ShiftAssignment> first = shiftAssignmentRepo
                .findAll()
                .stream()
                .filter(shiftAssignment -> shiftAssignment.getEmployee().getEmployeeId() == fromUser.getEmployeeId())
                .filter(shiftAssignment ->  shiftAssignment.getShift().getShiftId() == shift_id)
                .findFirst();

        if (first.isPresent()){

            fromUser.getShiftAssignments().remove(first.get());

            toUser.getShiftAssignments().add(new ShiftAssignment(shift, toUser));

            //first.get().setEmployee(toUser); // Kan ikke endre PK
        }
    }

    @PostMapping(value = "/{shift_id}/users/{user_id}/requestchange/{user2_id}")
    void requestChangeForShift(@PathVariable int shift_id, @PathVariable int user_id, @PathVariable int user2_id){

        Shift shift = shiftRepo.findOne(shift_id);
        Employee fromUser = employeeRepo.findOne(user_id);
        Employee toUser = employeeRepo.findOne(user2_id);


    }

    void getChangeRequests(){



    }

    @GetMapping(value = "/byday")
    public List<Shift> getShiftsByDay(@RequestBody String day){

        LocalDate date = LocalDate.parse(day);

        Iterable<Shift> all = shiftRepo.findAll();

        List<Shift> shiftList = new ArrayList<>((Collection<? extends Shift>) all);

        return shiftList
                .stream()
                .filter(shift -> shift.getStartDateTime().toLocalDate().isEqual(date))
                .collect(Collectors.toList());

    }


    @GetMapping(value = "/{shift_id}/isAvailable")
    public boolean shiftIsAvailable(@PathVariable int shift_id){

        return shiftAssignmentRepo
                .findAll()
                .stream()
                .filter(shiftAssignment -> shiftAssignment.getShift().getShiftId() == shift_id)
                .filter(shiftAssignment -> shiftAssignment.getStatus() == ShiftStatus.REQUESTCHANGE)
                .map(ShiftAssignment::getShift)
                .collect(Collectors.toList())
                .contains(shiftRepo.findOne(shift_id));

    }

    @GetMapping(value = "/requestchange")
    public List<Shift> getShiftsWithRequestChangeStatus(){

        return shiftAssignmentRepo.findAll().stream().filter(shiftAssignment -> shiftAssignment.getStatus() == ShiftStatus.REQUESTCHANGE).map(ShiftAssignment::getShift).collect(Collectors.toList());

    }
}