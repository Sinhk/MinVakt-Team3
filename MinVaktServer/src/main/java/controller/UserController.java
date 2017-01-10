package controller;

import datamodel.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @RequestMapping("/users")
    public User getUsers() {//@RequestParam(value="name", defaultValue="World") String name) {
        return new User("test@test.com", 12345678, 12);
    }
}
