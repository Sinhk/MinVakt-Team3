package minvakt.controller;

import minvakt.datamodel.tables.pojos.ChangeRequest;
import minvakt.repos.ChangeRequestRepository;
import minvakt.repos.EmployeeRepository;
import minvakt.repos.ShiftAssignmentRepository;
import minvakt.repos.ShiftRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{request_id}")
    public void acceptChangeRequest(@PathVariable int request_id){

        System.out.println("accept change: "+request_id);

        ChangeRequest one = changeRequestRepository.findOne(request_id);

        changeRequestRepository.delete(one);

        System.out.println(shiftController);

        shiftController.changeShiftFromUserToUser(one.getShiftId(), one.getOldEmployeeId(), one.getNewEmployeeId());

    }


}
