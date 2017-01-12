package minvakt.controller;

import minvakt.datamodel.Shift;
import minvakt.datamodel.User;
import minvakt.managers.ReturnCode;
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
    public ReturnCode addShiftToUser(@RequestBody User user, Shift shift) {
        System.out.println("Adding shift to user: "+ user);

        return manager.addShiftToUser(user,shift);
    }

    /*@PostMapping
    public ReturnCode changeShiftFromUserToUser(@RequestBody Shift shift, User fromUser, User toUser) {
        System.out.println("Giving "+ toUser +" Shift from "+ fromUser);

        return manager.changeShiftFromUserToUser(shift,fromUser,toUser);
    }*/

    @DeleteMapping
    public ReturnCode removeShiftFromUser (@RequestBody User user, Shift shift) {
        System.out.println("Removing shift from user: "+ user);

        return manager.removeShiftFromUser(user, shift);
    }


}
