package minvakt.controller;

import minvakt.datamodel.Shift;
import minvakt.datamodel.User;
import minvakt.managers.ReturnCode;
import minvakt.managers.ShiftManager;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

    @PostMapping
    public ReturnCode changeShiftFromUserToUser(@RequestBody Shift shift, User fromUser, User toUser) {
        System.out.println("Giving "+ toUser +" Shift from "+ fromUser);

        return manager.changeShiftFromUserToUser(shift,fromUser,toUser);
    }

    @DeleteMapping
    public ReturnCode removeShiftFromUser (@RequestBody User user,Shift shift) {
        System.out.println("Removing shift from user: "+ user);

        return manager.removeShiftFromUser(user, shift);
    }

    @GetMapping
    public List<Shift> getShiftForUser(@RequestBody User user) {
        System.out.println("Getting list with shifts for user: "+ user);

        return manager.getShiftsForUser(user);
    }

    @GetMapping
    public int getMinutesForUsersInInterval(@RequestBody User user, LocalDate from,LocalDate to) {
        System.out.println("Getting minutes in the interval "+ from +"-"+ to +"for user: "+ user);

        return manager.getMinutesForUsersInInterval(user,from,to);
    }

    @GetMapping
    public int getMinutesForWeek(@RequestBody User user,LocalDate dateInWeek ) {
        System.out.println("Get minutes for user: "+ user +" in the week with the date: "+ dateInWeek);

        return manager.getMinutesForWeek(user,dateInWeek);
    }

}

