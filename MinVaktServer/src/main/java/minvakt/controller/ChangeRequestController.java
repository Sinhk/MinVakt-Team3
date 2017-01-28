package minvakt.controller;

import minvakt.datamodel.tables.pojos.Employee;
import minvakt.datamodel.tables.pojos.ChangeRequest;
import minvakt.datamodel.tables.pojos.MissingPerShiftCategory;
import minvakt.datamodel.tables.pojos.Shift;
import minvakt.repos.*;
import minvakt.util.SendMailTLS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

import static minvakt.Application.HOURS_IN_WEEK;

/**
 * Created by OlavH on 23-Jan-17.
 * Controller to handle change-requests. AKA requests to change shifts from one user to another
 */
@RestController
    @RequestMapping("/requestchange")
public class ChangeRequestController {

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    private ShiftRepository shiftRepo;
    private EmployeeRepository employeeRepo;
    private ShiftAssignmentRepository shiftAssignmentRepo;
    private ChangeRequestRepository changeRequestRepository;
    private JooqRepository jooqRepository;
    private CategoryRepository catRepo;

    private ShiftController shiftController;
    private SendMailTLS sendMail;

    /**
     * Autowired by Spring to gain access to all the specified controllers and repos
     * @param jooqRepository
     * @param shiftRepo
     * @param employeeRepository
     * @param shiftAssignmentRepo
     * @param changeRequestRepository
     * @param catRepo
     * @param shiftController
     */
    @Autowired
    public ChangeRequestController(JooqRepository jooqRepository, ShiftRepository shiftRepo, EmployeeRepository employeeRepository, ShiftAssignmentRepository shiftAssignmentRepo, ChangeRequestRepository changeRequestRepository, CategoryRepository catRepo, ShiftController shiftController) {
        this.shiftRepo = shiftRepo;
        this.employeeRepo = employeeRepository;
        this.shiftAssignmentRepo = shiftAssignmentRepo;
        this.changeRequestRepository = changeRequestRepository;
        this.catRepo = catRepo;
        this.shiftController = shiftController;
        this.jooqRepository = jooqRepository;
        sendMail  = new SendMailTLS();
    }

    /**
     * Gets all the changerequests
     * @param count
     * @return
     */
    @GetMapping
    public ResponseEntity<?> getChangeRequests(@RequestParam(defaultValue = "false") boolean count) {
        if (count) {
            return ResponseEntity.ok(changeRequestRepository.count());
        }
        List<ChangeRequest> requests = (List<ChangeRequest>) changeRequestRepository.findAll();
        requests.forEach(req -> {
            Shift shift = shiftRepo.findOne(req.getShiftId());
            req.setShift(shift);
            checkIsOkChangeRequest(req, shift);
            req.setNewEmployee(employeeRepo.findOne(req.getNewEmployeeId()));
            req.setOldEmployee(employeeRepo.findOne(req.getOldEmployeeId()));
        });
        requests.sort((o1, o2) -> o1.getShift().getFromTime().compareTo(o2.getShift().getToTime()));
        return ResponseEntity.ok(requests);
    }

    /**
     * Makes a changerequest and saves it to the repo
     * @param shift_id Shift to change
     * @param user1_id User that wants to change
     * @param user2_id User to recieve shift
     */
    @Transactional
    @PostMapping
    void requestChangeForShift(@RequestParam int shift_id, @RequestParam int user1_id, @RequestParam int user2_id) {

        ChangeRequest request = new ChangeRequest();
        request.setShiftId(shift_id);
        request.setOldEmployeeId(user1_id);
        request.setNewEmployeeId(user2_id);
        changeRequestRepository.save(request);

        Employee fromEmployee = employeeRepo.findOne(user1_id);
        String employee1 = fromEmployee.getFirstName() + " " + fromEmployee.getLastName();
        Employee toEmployee = employeeRepo.findOne(user2_id);
        String employee2 = toEmployee.getFirstName() + " " + fromEmployee.getLastName();

        Shift shift1 = shiftRepo.findOne(shift_id);
        String shift = toDateString(shift1);


        String text = employee1 + " ønsker bytte vakt med " + employee2 +
                "\nVakt:" + shift;
        List<Employee> all = employeeRepo.findAll();
        for (Employee one : all) {
            if (one.getCategoryId() == 1) {
                sendMail.sendChangeRequstToAdmin(one.getEmail(),text);
            }
        }
    }

    /**
     * Makes a shift into a readable date String
     * @param shift
     * @return
     */
    private String toDateString(Shift shift) {
        return "\n" + shift.getDepartmentId() + ":\nFra " + shift.getFromTime().toString() +
                    "\nTil " + shift.getToTime().toString();
    }

    /**
     * Accepts the already created changerequest
     * @param request_id The ID to accept
     */
    @PutMapping("/{request_id}")
    public void acceptChangeRequest(@PathVariable int request_id) {

        Employee fromEmployee = employeeRepo.findOne(changeRequestRepository.findOne(request_id).getOldEmployeeId());
        String emailOld = fromEmployee.getEmail();
        Employee toEmployee = employeeRepo.findOne(changeRequestRepository.findOne(request_id).getNewEmployeeId());
        String emailNew = toEmployee.getEmail();
        int shift_id = changeRequestRepository.findOne(request_id).getShiftId();
        String shift = toDateString(shiftRepo.findOne(shift_id));

        String text = "Ditt vaktbytte ble godkjent:\n" + shift + "\n";

        sendMail.sendAnswerOnShiftChange(emailOld,text);
        sendMail.sendAnswerOnShiftChange(emailNew,text);

        changeRequestRepository.findOne(request_id).getOldEmployeeId();
        ChangeRequest one = changeRequestRepository.findOne(request_id);

        changeRequestRepository.delete(one);

        shiftController.changeShiftFromUserToUser(one.getShiftId(), one.getOldEmployeeId(), one.getNewEmployeeId());

    }

    /**
     * Denies the already created changerequest
     * @param request_id Request to deny
     */
    @DeleteMapping("/{request_id}")
    public void declineChangeRequest(@PathVariable int request_id) {
        String emailOld = employeeRepo.findOne(changeRequestRepository.findOne(request_id).getOldEmployeeId()).getEmail();
        String emailNew = employeeRepo.findOne(changeRequestRepository.findOne(request_id).getNewEmployeeId()).getEmail();
        int shift_id = changeRequestRepository.findOne(request_id).getShiftId();
        String shift = toDateString(shiftRepo.findOne(shift_id));
        String text = "Ditt vaktbytte ble ikke godkjent:\n" +
                shift;
        sendMail.sendAnswerOnShiftChange(emailOld,text);
        sendMail.sendAnswerOnShiftChange(emailNew,text);

        ChangeRequest one = changeRequestRepository.findOne(request_id);

        changeRequestRepository.delete(one);
    }


    /**
     *
     * @param one The changerequest to check
     * @param shift The shift to check
     */
    protected void checkIsOkChangeRequest(ChangeRequest one, Shift shift) {
        if(shiftAssignmentRepo.findByShiftIdAndEmployeeId(shift.getShiftId(), one.getNewEmployeeId()).isPresent()){
            one.setAllowed(false);
            return;
        }
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int week = shift.getFromTime().get(weekFields.weekOfWeekBasedYear());
        int year = shift.getFromTime().getYear();
        if (jooqRepository.getHoursWorked(one.getNewEmployeeId(), week,year)
                .plus(Duration.between(shift.getFromTime(),shift.getToTime()))
                .compareTo(Duration.of(HOURS_IN_WEEK, ChronoUnit.HOURS))>0) {
            one.setOvertime(true);
        }

        int oldUserCategory = employeeRepo.findOne(one.getOldEmployeeId()).getCategoryId();
        int newUserCategory = employeeRepo.findOne(one.getNewEmployeeId()).getCategoryId();
        if (oldUserCategory != newUserCategory) {
            List<MissingPerShiftCategory> missingForShift = jooqRepository.getMissingForShift(shift.getShiftId());
            for (MissingPerShiftCategory perShiftCategory : missingForShift) {
                if (oldUserCategory == perShiftCategory.getCategoryId()) {
                    if (perShiftCategory.getCountAssigned() - 1 < perShiftCategory.getCountRequired()) {
                        one.setMissing(catRepo.findOne((short) oldUserCategory).getCategoryName());
                        one.setAllowed(false);
                        return;
                    }
                }
            }
        }
        one.setAllowed(true);
    }

}