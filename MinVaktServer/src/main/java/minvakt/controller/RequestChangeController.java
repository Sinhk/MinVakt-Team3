package minvakt.controller;

import minvakt.datamodel.tables.pojos.Employee;
import minvakt.datamodel.tables.pojos.ChangeRequest;
import minvakt.repos.ChangeRequestRepository;
import minvakt.repos.EmployeeRepository;
import minvakt.repos.ShiftAssignmentRepository;
import minvakt.repos.ShiftRepository;
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

    private ShiftController shiftController;
    private SendMailTLS sendMail = new SendMailTLS();

    @Autowired
    public RequestChangeController(ShiftRepository shiftRepo, EmployeeRepository employeeRepository, ShiftAssignmentRepository shiftAssignmentRepo, ChangeRequestRepository changeRequestRepository, ShiftController shiftController) {
        this.shiftRepo = shiftRepo;
        this.employeeRepo = employeeRepository;
        this.shiftAssignmentRepo = shiftAssignmentRepo;
        this.changeRequestRepository = changeRequestRepository;
        this.shiftController = shiftController;
    }

    @GetMapping
    public List<ChangeRequest> getChangeRequests(){

        return (List<ChangeRequest>) changeRequestRepository.findAll();


    }

    @Transactional
    @PostMapping
    void requestChangeForShift(@RequestParam int shift_id, @RequestParam int user1_id, @RequestParam int user2_id){

        ChangeRequest request = new ChangeRequest();
        request.setShiftId(shift_id);
        request.setOldEmployeeId(user1_id);
        request.setNewEmployeeId(user2_id);
        String employee1 = employeeRepo.findOne(user1_id).getFirstName()+" "+employeeRepo.findOne(user1_id).getLastName();
        String employee2 = employeeRepo.findOne(user2_id).getFirstName()+" "+employeeRepo.findOne(user1_id).getLastName();
        String shift = "\n"+shiftRepo.findOne(shift_id).getDepartmentId() + ":\nFra " + shiftRepo.findOne(shift_id).getFromTime().getYear()+
                "/"+shiftRepo.findOne(shift_id).getFromTime().getMonth() +
                "/"+shiftRepo.findOne(shift_id).getFromTime().getDayOfMonth() +
                " "+shiftRepo.findOne(shift_id).getFromTime().getHour()+
                ":"+shiftRepo.findOne(shift_id).getFromTime().getMinute() +
                "\nFra " + shiftRepo.findOne(shift_id).getToTime().getYear()+
                "/"+shiftRepo.findOne(shift_id).getToTime().getMonth() +
                "/"+shiftRepo.findOne(shift_id).getToTime().getDayOfMonth() +
                " "+shiftRepo.findOne(shift_id).getToTime().getHour()+
                ":"+shiftRepo.findOne(shift_id).getToTime().getMinute();
        changeRequestRepository.save(request);


        String text = employee1 + " ønsker bytte vakt med " + employee2+
                "\nShift:" + shift;
        List<Employee> all = employeeRepo.findAll();
        for(Employee one:all){
            if(one.getCategoryId() ==1) {
                // TODO: 19.01.2017 Send mail
               // sendMail.sendChangeRequstToAdmin(text);
            }
        }
    }

    @PutMapping("/{request_id}")
    public void acceptChangeRequest(@PathVariable int request_id){

        System.out.println("accept change: "+request_id);
        String emailOld =employeeRepo.findOne(changeRequestRepository.findOne(request_id).getOldEmployeeId()).getEmail();
        String emailNew =employeeRepo.findOne(changeRequestRepository.findOne(request_id).getNewEmployeeId()).getEmail();
        int shift_id = changeRequestRepository.findOne(request_id).getShiftId();
        String shift = "\n"+shiftRepo.findOne(shift_id).getDepartmentId() + ":\nFra " + shiftRepo.findOne(shift_id).getFromTime().getYear()+
                "/"+shiftRepo.findOne(shift_id).getFromTime().getMonth() +
                "/"+shiftRepo.findOne(shift_id).getFromTime().getDayOfMonth() +
                " "+shiftRepo.findOne(shift_id).getFromTime().getHour()+
                ":"+shiftRepo.findOne(shift_id).getFromTime().getMinute() +
                "\nFra " + shiftRepo.findOne(shift_id).getToTime().getYear()+
                "/"+shiftRepo.findOne(shift_id).getToTime().getMonth() +
                "/"+shiftRepo.findOne(shift_id).getToTime().getDayOfMonth() +
                " "+shiftRepo.findOne(shift_id).getToTime().getHour()+
                ":"+shiftRepo.findOne(shift_id).getToTime().getMinute();
        String text ="Ditt vaktbytte ble godkjent:\n"+
                shift;
        // TODO: 19.01.2017 Send mail
        //sendMail.sendAnswerOnShiftChange(emailOld,text);
        //sendMail.sendAnswerOnShiftChange(emailNew,text);

        changeRequestRepository.findOne(request_id).getOldEmployeeId();
        ChangeRequest one = changeRequestRepository.findOne(request_id);

        changeRequestRepository.delete(one);

        System.out.println(shiftController);

        shiftController.changeShiftFromUserToUser(one.getShiftId(), one.getOldEmployeeId(), one.getNewEmployeeId());

    }

    @DeleteMapping("/{request_id}")
    public void declineChangeRequest(@PathVariable int request_id){
        String emailOld =employeeRepo.findOne(changeRequestRepository.findOne(request_id).getOldEmployeeId()).getEmail();
        String emailNew =employeeRepo.findOne(changeRequestRepository.findOne(request_id).getNewEmployeeId()).getEmail();
        int shift_id = changeRequestRepository.findOne(request_id).getShiftId();
        String shift = "\n"+shiftRepo.findOne(shift_id).getDepartmentId() + ":\nFra " + shiftRepo.findOne(shift_id).getFromTime().getYear()+
                "/"+shiftRepo.findOne(shift_id).getFromTime().getMonth() +
                "/"+shiftRepo.findOne(shift_id).getFromTime().getDayOfMonth() +
                " "+shiftRepo.findOne(shift_id).getFromTime().getHour()+
                ":"+shiftRepo.findOne(shift_id).getFromTime().getMinute() +
                "\nFra " + shiftRepo.findOne(shift_id).getToTime().getYear()+
                "/"+shiftRepo.findOne(shift_id).getToTime().getMonth() +
                "/"+shiftRepo.findOne(shift_id).getToTime().getDayOfMonth() +
                " "+shiftRepo.findOne(shift_id).getToTime().getHour()+
                ":"+shiftRepo.findOne(shift_id).getToTime().getMinute();
        String text ="Ditt vaktbytte ble ikke godkjent:\n"+
                shift;
        // TODO: 19.01.2017 Send mail
        //sendMail.sendAnswerOnShiftChange(emailOld,text);
        //sendMail.sendAnswerOnShiftChange(emailNew,text);

        ChangeRequest one = changeRequestRepository.findOne(request_id);

        changeRequestRepository.delete(one);

        // TODO send mail
    }
}
