package minvakt.controller;

import minvakt.datamodel.tables.pojos.ChangeRequest;
import minvakt.datamodel.tables.pojos.Employee;
import minvakt.datamodel.tables.pojos.Shift;
import minvakt.datamodel.tables.pojos.ShiftAssignment;
import minvakt.datamodel.enums.ShiftStatus;
import minvakt.datamodel.tables.pojos.ChangeRequest;
import minvakt.datamodel.tables.pojos.Employee;
import minvakt.datamodel.tables.pojos.Shift;
import minvakt.datamodel.tables.pojos.ShiftAssignment;
import minvakt.repos.ChangeRequestRepository;
import minvakt.repos.EmployeeRepository;
import minvakt.repos.ShiftAssignmentRepository;
import minvakt.repos.ShiftRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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


    @Autowired
    public ShiftController(
            ShiftRepository shiftRepo,
            EmployeeRepository employeeRepository,
            ShiftAssignmentRepository shiftAssignmentRepo,
            ChangeRequestRepository changeRequestRepository) {
        this.shiftRepo = shiftRepo;
        this.employeeRepo = employeeRepository;
        this.shiftAssignmentRepo = shiftAssignmentRepo;
        this.changeRequestRepository = changeRequestRepository;
    }

    @GetMapping
    public Iterable<Shift> getShifts(){

        return shiftRepo.findAll();
    }

    // TODO: 19.01.2017 do this automagically
    @PostMapping
    public void addShift(@RequestBody Shift shift) {
        shiftRepo.save(shift);
    }

    @GetMapping(value = "/{shift_id}")
    public Shift getShift(@PathVariable int shift_id){

        return shiftRepo.findOne(shift_id);

    }



    @GetMapping(value = "/{shift_id}/users")
    @Transactional
    public List<Employee> getUsersForShift(@PathVariable int shift_id) {

        Shift shift = shiftRepo.findOne(shift_id);

        return employeeRepo.findByShiftAssignments_Shift(shift);
    }

    @PostMapping(value="/{shift_id}/users")
    @Transactional
    public void addUserToShift(@PathVariable int shift_id , @RequestBody int user_id) { // shift id and user id

        ShiftAssignment assignment = new ShiftAssignment();
        assignment.setEmployeeId(user_id);
        assignment.setShiftId(shift_id);
        shiftAssignmentRepo.save(assignment);
    }

    @GetMapping(value = "/{shift_id}/details") // TODO: 19-Jan-17 query
    public List<ShiftAssignment> getShiftAssignmentsForShift(@PathVariable int shift_id){

        return shiftAssignmentRepo
                .findAll()
                .stream()
                .filter(shiftAssignment -> shiftAssignment.getShiftId() == shift_id)
                .collect(Collectors.toList());

    }

    /**
     * Lord forgive me
     */
    /*// TODO: 19-Jan-17 fix/refractor
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
                //.filter(s -> s.getStatus().equals(ShiftStatus.REQUESTCHANGE)) // FIXME: 20-Jan-17
                .map(shiftAssignment -> shiftRepo.findOne(shiftAssignment.getShiftId()))
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

        /*shiftsWithEmployeeCountMap.forEach((shift, integer) -> {
            if (integer < shift.getRequiredEmployees()) shiftsThatRequiresEmployees.add(shift);
        });*/
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
       *//* List<List<ShiftAssignment>> collect1 = shiftList
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

*//*

       // removes duplicates, just a bit of a hack
        HashSet<Shift> shifts = new HashSet<>(changeRequestShifts);
        ArrayList<Shift> shifts1 = new ArrayList<>(shifts);
        return shifts1.stream().filter(shift -> shift.getStartDateTime().isAfter(LocalDateTime.now())).collect(Collectors.toList());

    }*/

    int getEmployeesOnShift(int shiftId){

        return (int) shiftAssignmentRepo.findAll().stream().filter(shiftAssignment -> shiftAssignment.getShiftId() == shiftId).count();

    }

    /*@GetMapping
    @RequestMapping(value = "/{shift_id}/responsible")
    public ResponseEntity<?> getResponsibleEmployeeForShift(@PathVariable int shift_id) {

        Optional<Employee> employee = employeeRepo.findByShiftAssignments_ShiftAndShiftAssignments_ResponsibleTrue(shiftRepo.getOne(shift_id));
        if (employee.isPresent()) {
            return ResponseEntity.ok(employee.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    /*@GetMapping(value = "/{shift_id}/users/{user_id}/status")
    public ShiftStatus getShiftStatusForUserAndShift(@PathVariable int shift_id, @PathVariable int user_id) {

        return shiftAssignmentRepo
                .findAll()
                .stream()
                .filter(shiftAssignment -> shiftAssignment.getShift().getShiftId() == shift_id)
                .filter(shiftAssignment -> shiftAssignment.getEmployee().getEmployeeId() == user_id)
                .map(ShiftAssignment::getStatus)
                .findFirst()
                .get();
    }*/

    /*@PutMapping(value = "/{shift_id}/users/{user_id}/status")
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

    }*/

    /*@GetMapping(value = "/{shift_id}/users/{employee_id}/responsible")
    public boolean userIsResponsibleForShift(@PathVariable int shift_id, @PathVariable int employee_id) {

        return shiftAssignmentRepo
                .findAll()
                .stream()
                .filter(shiftAssignment -> shiftAssignment.getShift().getShiftId() == shift_id)
                .filter(shiftAssignment -> shiftAssignment.getEmployee().getEmployeeId() == employee_id)
                .map(ShiftAssignment::isResponsible)
                .findFirst()
                .get();
    }*/

    @PutMapping(value = "/{shift_id}/responsible")
    @Transactional
    public void setUserIsResponsibleForShift(@PathVariable int shift_id, @RequestBody int employee_id) {

        Shift shift = shiftRepo.findOne(shift_id);

        shift.setResponsibleEmployeeId(employee_id);

        shiftRepo.save(shift);
    }
    @GetMapping(value = "/{shift_id}/responsible")
    @Transactional
    public Employee getResponsibleUserForShift(@PathVariable int shift_id) {
        return employeeRepo.findResponsibleForShift(shift_id);
    }

    @Transactional
    @PutMapping(value = "/{shift_id}/users/{user_id}")
    public void changeShiftFromUserToUser(@PathVariable int shift_id, @PathVariable int user_id, @RequestBody int toUser_id){

        Optional<ShiftAssignment> assignment = shiftAssignmentRepo.getByShiftIdAndEmployeeId(shift_id, user_id);
        assignment.ifPresent(a -> {
            a.setEmployeeId(toUser_id);
            shiftAssignmentRepo.save(a);
        });
    }

    // TODO: 19-Jan-17 Flytt til requestchangecontroller
    @Transactional
    @PostMapping(value = "/{shift_id}/users/{user_id}/requestchange/{user2_id}")
    void requestChangeForShift(@PathVariable int shift_id, @PathVariable int user_id, @PathVariable int user2_id){

        ChangeRequest request = new ChangeRequest();
        request.setShiftId(shift_id);
        request.setOldEmployeeId(user_id);
        request.setNewEmployeeId(user2_id);
        changeRequestRepository.save(request);

        // TODO: 19.01.2017 Send mail
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

    // TODO: 19-Jan-17 fix
    @GetMapping(value = "/available")
    public List<Shift> getAvailableShifts(){
        return shiftRepo
                .findAvailable();
                //.filter(shiftAssignment -> shiftAssignment.getStatus() == ShiftStatus.AVAILABLE)
                //.map(ShiftAssignment::getShift)
    }

    // TODO: 19-Jan-17 fix
    @GetMapping(value = "/available")
    public List<Shift> getAvailableShifts(){
        return new ArrayList<>( shiftAssignmentRepo
                .findAll()
                .stream()
                //.filter(shiftAssignment -> shiftAssignment.getStatus() == ShiftStatus.AVAILABLE)
                .map(ShiftAssignment::getShift)
                .collect(Collectors.toList()));
    }

    // TODO: 19-Jan-17 Fix
    @GetMapping(value = "/{shift_id}/available")
    public boolean shiftIsAvailable(@PathVariable int shift_id){

        return shiftAssignmentRepo.findByShiftId(shift_id)
                .stream()
                //.filter(shiftAssignment -> shiftAssignment.getStatus() == ShiftStatus.AVAILABLE)
                //.map(ShiftAssignment::getShift)
                .collect(Collectors.toList())
                .contains(shiftAssignmentRepo.getOne(shift_id));

    }
    //// TODO: 19-Jan-17 flytt and repo thing
    /*@GetMapping(value = "/requestchange")
    public List<Shift> getShiftsWithRequestChangeStatus(){

        return new ArrayList<ChangeRequest>((Collection<? extends ChangeRequest>) changeRequestRepository
                .findAll())
                .stream()
                .map(ChangeRequest::getShift)
                .collect(Collectors.toList());
    }*/

    /*@Transactional
    @PostMapping(value = "/requestchange")
    public void requestChange(@RequestBody ThreeIntsData data){

        Shift shift = shiftRepo.findOne(data.getInt1());
        Employee from = employeeRepo.findOne(data.getInt2());
        Employee to = employeeRepo.findOne(data.getInt3());

        ChangeRequest changeRequest = new ChangeRequest(shift, from, to);

        changeRequestRepository.save(changeRequest);


    }*/
}