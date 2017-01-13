package minvakt.controller;

import minvakt.controller.data.TwoUsersData;
import minvakt.datamodel.Shift;
import minvakt.datamodel.User;
import minvakt.repos.ShiftRepository;
import minvakt.repos.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;

/**
 * Created by magnu on 11.01.2017.
 */

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

    @PostMapping
    public Response addShift(@RequestBody Shift shift){

        shiftRepo.save(shift);

        return Response.ok().build();

    }
    @GetMapping
    public Iterable<Shift> getShifts(){
        return shiftRepo.findAll();
    }

    /*@PostMapping
    public ReturnCode changeShiftFromUserToUser(@RequestBody Shift shift, User fromUser, User toUser) {
        System.out.println("Giving "+ toUser +" Shift from "+ fromUser);

        return manager.changeShiftFromUserToUser(shift,fromUser,toUser);
    }*/



    @PutMapping
    @RequestMapping("/{shift_id}")
    public Response changeShiftFromUserToUser(@PathVariable String shift_id, @RequestBody TwoUsersData usersData){

        User firstUser = userRepo.findOne(Integer.valueOf(usersData.getUserId1()));

        User secondUser = userRepo.findOne(Integer.valueOf(usersData.getUserId2()));

        Shift shift = shiftRepo.findOne(Integer.valueOf(shift_id));

        if (firstUser != null && secondUser != null && shift != null)
            return shift.changeShiftFromUserToUser(firstUser, secondUser) ? Response.ok().build() : Response.notModified().build();

        return Response.noContent().build();

    }






}

