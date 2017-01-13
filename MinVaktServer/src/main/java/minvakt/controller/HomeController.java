package minvakt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

/**
 * Created by sindr on 12.01.2017.
 * in project: MinVakt-Team3
 */
@Controller
public class HomeController {
    @GetMapping("/")
    String index(Model model) {
        model.addAttribute("now", LocalDateTime.now());
        return "index";
    }

}
