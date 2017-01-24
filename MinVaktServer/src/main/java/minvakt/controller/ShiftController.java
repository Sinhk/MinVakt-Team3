package minvakt.controller;

import minvakt.datamodel.tables.pojos.Employee;
import minvakt.datamodel.tables.pojos.Shift;
import minvakt.datamodel.tables.pojos.ShiftAssignment;
import minvakt.repos.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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


    @Autowired
    public ShiftController(ShiftRepository shiftRepo, EmployeeRepository employeeRepository, ShiftAssignmentRepository shiftAssignmentRepo, ChangeRequestRepository changeRequestRepository, JooqRepository jooqRepo) {
        this.shiftRepo = shiftRepo;
        this.employeeRepo = employeeRepository;
        this.shiftAssignmentRepo = shiftAssignmentRepo;
        this.changeRequestRepository = changeRequestRepository;
        this.jooqRepo = jooqRepo;
    }

    @GetMapping
    public Iterable<?> getShifts(@RequestParam(defaultValue = "false") boolean detailed) {
        if (detailed) {
            return jooqRepo.getShiftDetailed();
        }
        return shiftRepo.findAll();
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
    public ShiftAssignment getShiftAssignmentForShiftAndUser(@PathVariable int shift_id, @PathVariable int user_id){

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
    public void addUserToShift(@PathVariable int shift_id, @RequestParam int user_id) { // shift id and user id

        System.out.println(shift_id + " . " + user_id);

        ShiftAssignment assignment = new ShiftAssignment();
        assignment.setEmployeeId(user_id);
        assignment.setShiftId(shift_id);
        assignment.setAbsent(false);
        shiftAssignmentRepo.save(assignment);
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

    @GetMapping(value = "/{shift_id}/details") // TODO: 19-Jan-17 query
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

    // TODO: 19-Jan-17 fix, gjør ingenting
    @GetMapping(value = "/available")
    public List<Shift> getAvailableShifts() {
        return shiftRepo.findAvailable();
        //.filter(shiftAssignment -> shiftAssignment.getStatus() == ShiftStatus.AVAILABLE)
        //.map(ShiftAssignment::getShift)
    }

    // TODO: 19-Jan-17 Fix
    @GetMapping(value = "/{shift_id}/available")
    public boolean shiftIsAvailable(@PathVariable int shift_id) {

        return shiftAssignmentRepo.findByShiftId(shift_id).stream()
                //.filter(shiftAssignment -> shiftAssignment.getStatus() == ShiftStatus.AVAILABLE)
                //.map(ShiftAssignment::getShift)
                .collect(Collectors.toList()).contains(shiftAssignmentRepo.getOne(shift_id));

    }
}