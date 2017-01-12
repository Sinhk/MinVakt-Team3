package minvakt.controller;

import minvakt.controller.data.TwoUsersData;
import minvakt.datamodel.Shift;
import minvakt.datamodel.User;
import minvakt.managers.ShiftManager;
import minvakt.repos.ShiftRepository;
import minvakt.repos.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by magnu on 11.01.2017.
 */

@RestController
@RequestMapping("/shifts")
public class ShiftController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private ShiftRepository shiftRepo;
    private UserRepository userRepo;


    ShiftManager manager = ShiftManager.getInstance();

    @Autowired
    public ShiftController(ShiftRepository shiftRepo, UserRepository userRepo) {

        this.shiftRepo = shiftRepo;
        this.userRepo = userRepo;
    }

    @PostMapping
    public void addShift(@RequestBody Shift shift){

        shiftRepo.save(shift);

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
    public void changeShiftFromUserToUser(@RequestParam(value = "shift_id") String shift_id, @RequestBody TwoUsersData usersData){

        User firstUser = userRepo.findOne(Integer.valueOf(usersData.getUserId1()));

        User secondUser = userRepo.findOne(Integer.valueOf(usersData.getUserId2()));

        Shift shift = shiftRepo.findOne(Integer.valueOf(shift_id));

        shift.changeShiftFromUserToUser(firstUser, secondUser);

        ShiftManager.getInstance().changeShiftFromUserToUser(shift, firstUser, secondUser);
    }






}

