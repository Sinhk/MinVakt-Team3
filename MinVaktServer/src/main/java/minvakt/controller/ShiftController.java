package minvakt.controller;

import minvakt.controller.data.TwoIntData;
import minvakt.datamodel.Shift;
import minvakt.datamodel.ShiftAssignment;
import minvakt.datamodel.User;
import minvakt.repos.ShiftRepository;
import minvakt.repos.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.util.List;


@RestController
@RequestMapping("/shifts")
public class ShiftController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private ShiftRepository shiftRepo;
    private UserRepository userRepo;


    @Autowired
    public ShiftController(ShiftRepository shiftRepo, UserRepository userRepo) {
        this.shiftRepo = shiftRepo;
        this.userRepo = userRepo;
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


    @RequestMapping(value = "/{shift_id}", method = RequestMethod.PUT)
    @Transactional
    public Response addUserToShift(@RequestBody TwoIntData intData) { // shift id and user id

        Shift shift = shiftRepo.findOne(intData.getInt1());

        User user = userRepo.findOne(intData.getInt2());

        ShiftAssignment shiftAssignment = new ShiftAssignment(shift, user);

        shift.getShiftAssignments().add(shiftAssignment);

        shiftRepo.save(shift);
        return Response.ok().build();
    }

    @RequestMapping(value = "/{shift_id}/users", method = RequestMethod.GET)
    @Transactional
    public List<User> getUsersForShift(@PathVariable int shift_id) {

        Shift shift = shiftRepo.findOne(shift_id);

        return userRepo.findByShiftAssignments_User(shift);
        /*List<User> users = new ArrayList<>();
        for (ShiftAssignment assignment : shift.getShiftAssignments()) {
            users.add(assignment.getUser());
        }
        return users;*/
    }
}

