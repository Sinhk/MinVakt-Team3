package minvakt.controller;

import minvakt.datamodel.tables.pojos.Employee;
import minvakt.datamodel.tables.pojos.ChangeRequest;
import minvakt.repos.*;
import minvakt.util.SendMailTLS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import minvakt.datamodel.tables.pojos.*;
import java.util.List;

/**
 * Created by OlavH on 23-Jan-17.
 */

@RestController
@RequestMapping("/requestchange")
public class RequestChangeController {

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    private ShiftRepository shiftRepo;
    private EmployeeRepository employeeRepo;
    private ShiftAssignmentRepository shiftAssignmentRepo;
    private ChangeRequestRepository changeRequestRepository;
    private JooqRepository jooqRepository;

    private ShiftController shiftController;
    private SendMailTLS sendMail = new SendMailTLS();

    @Autowired
    public RequestChangeController(JooqRepository jooqRepository,ShiftRepository shiftRepo, EmployeeRepository employeeRepository, ShiftAssignmentRepository shiftAssignmentRepo, ChangeRequestRepository changeRequestRepository, ShiftController shiftController) {
        this.shiftRepo = shiftRepo;
        this.employeeRepo = employeeRepository;
        this.shiftAssignmentRepo = shiftAssignmentRepo;
        this.changeRequestRepository = changeRequestRepository;
        this.shiftController = shiftController;
        this.jooqRepository = jooqRepository;
    }

    @GetMapping
    public List<ChangeRequest> getChangeRequests() {

        return (List<ChangeRequest>) changeRequestRepository.findAll();
    }

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
                // TODO: 19.01.2017 Send mail
                // sendMail.sendChangeRequstToAdmin(text);
            }
        }
    }

    private String toDateString(Shift shift) {
        return "\n" + shift.getDepartmentId() + ":\nFra " + shift.getFromTime().toString() +
                    "\nTil " + shift.getToTime().toString();
    }

    @PutMapping("/{request_id}")
    public void acceptChangeRequest(@PathVariable int request_id) {

        System.out.println("accept change: " + request_id);
        Employee fromEmployee = employeeRepo.findOne(changeRequestRepository.findOne(request_id).getOldEmployeeId());
        String emailOld = fromEmployee.getEmail();
        Employee toEmployee = employeeRepo.findOne(changeRequestRepository.findOne(request_id).getNewEmployeeId());
        String emailNew = toEmployee.getEmail();
        int shift_id = changeRequestRepository.findOne(request_id).getShiftId();
        String shift = toDateString(shiftRepo.findOne(shift_id));

        String text = "Ditt vaktbytte ble godkjent:\n" + shift + "\n";
        // TODO: 19.01.2017 Send mail
        //sendMail.sendAnswerOnShiftChange(emailOld,text);
        //sendMail.sendAnswerOnShiftChange(emailNew,text);

        changeRequestRepository.findOne(request_id).getOldEmployeeId();
        ChangeRequest one = changeRequestRepository.findOne(request_id);

        changeRequestRepository.delete(one);

        shiftController.changeShiftFromUserToUser(one.getShiftId(), one.getOldEmployeeId(), one.getNewEmployeeId());

    }

    @DeleteMapping("/{request_id}")
    public void declineChangeRequest(@PathVariable int request_id) {
        String emailOld = employeeRepo.findOne(changeRequestRepository.findOne(request_id).getOldEmployeeId()).getEmail();
        String emailNew = employeeRepo.findOne(changeRequestRepository.findOne(request_id).getNewEmployeeId()).getEmail();
        int shift_id = changeRequestRepository.findOne(request_id).getShiftId();
        String shift = toDateString(shiftRepo.findOne(shift_id));
        String text = "Ditt vaktbytte ble ikke godkjent:\n" +
                shift;
        // TODO: 19.01.2017 Send mail
        //sendMail.sendAnswerOnShiftChange(emailOld,text);
        //sendMail.sendAnswerOnShiftChange(emailNew,text);

        ChangeRequest one = changeRequestRepository.findOne(request_id);

        changeRequestRepository.delete(one);
    }

    @GetMapping(value = "/{request_id}/isOkChangeRequest")
    public String getIsOkChangeRequest(@PathVariable int request_id) {
        ChangeRequest one = changeRequestRepository.findOne(request_id);
        int oldUserCategory = employeeRepo.findOne(one.getOldEmployeeId()).getCategoryId();
        int newUserCategory = employeeRepo.findOne(one.getOldEmployeeId()).getCategoryId();
        Shift shift = shiftRepo.findOne(changeRequestRepository.findOne(request_id).getShiftId());
        int shift_id = shift.getShiftId();
        int amount = shiftRepo.findOne(one.getShiftId()).getRequiredEmployees();
        int nurses = (int) (amount * 0.2);
        int healthWorkers = (int) (amount * 0.3);
        if (jooqRepository.getHoursWorked(one.getNewEmployeeId()) > 40) {
            return "Dette fører til overtid";
        } else if (oldUserCategory == newUserCategory) {
            return "Denne godkjenningen går bra";
        } else if (oldUserCategory == 3 && jooqRepository.getHealthWorkersOnShift(shift_id) - 1 < healthWorkers) {
            return "Dette fører til for lite helsefagarbeidere";
        } else if (oldUserCategory == 2 && jooqRepository.getNursesOnShift(shift_id) - 1 < nurses) {
            return "Dette fører til for lite sykepleiere";
        }else{
            return "Denne godkjenningen går bra";
        }
    }

}