package minvakt.controller;

import minvakt.datamodel.Shift;
import minvakt.datamodel.User;
import minvakt.managers.ReturnCode;
import minvakt.managers.ShiftManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by magnu on 11.01.2017.
 */

@RestController
@RequestMapping("/shifts")
public class ShiftController {

    private static ShiftManager manager = ShiftManager.getInstance();

    @GetMapping
    @ResponseBody
    public List getShiftsForUser(User user) {
        return manager.getShiftsForUser(user);
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
    public ReturnCode removeShiftFromUser (@RequestBody User user,Shift shift) {
        System.out.println("Removing shift from user: "+ user);

        return manager.removeShiftFromUser(user, shift);
    }


}

