package minvakt.controller;

import minvakt.datamodel.Shift;
import minvakt.managers.ShiftManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by magnu on 11.01.2017.
 */

@RestController
@RequestMapping("/shifts")
public class ShiftController {

    private static ShiftManager manager = ShiftManager.getInstance();


}
