package minvakt.controller;

import minvakt.datamodel.Shift;
import minvakt.datamodel.User;
import minvakt.managers.ShiftManager;
import org.springframework.web.bind.annotation.*;

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
}