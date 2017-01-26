package minvakt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);
    @GetMapping("/")
    String index(Model model) {
        model.addAttribute("now", LocalDateTime.now());
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        log.info("Username: {}, Auths: {}", principal.getUsername(), principal.getAuthorities());
        return "homepage";
    }
}
