package minvakt.controller;

import minvakt.datamodel.User;
import minvakt.managers.UserManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static UserManager manager = UserManager.getInstance();

    static {
        manager.addUser(new User("olavh96@gmail.com", 93240605, "Ostostost--", 100));
    }

    @GetMapping
    public List<User> getUsers() {//@RequestParam(value="name", defaultValue="World") String name) {

        return manager.getUserList();

    }

    @PostMapping
    public boolean addUser(User user) {

        return manager.addUser(user);

    }

}
