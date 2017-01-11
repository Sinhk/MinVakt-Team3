package minvakt.controller;

import minvakt.datamodel.User;
import minvakt.managers.UserManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private static UserManager manager = UserManager.getInstance();

    static {
        manager.addUser(new User("olavh96@gmail.com", 93240605, "Ostostost--", 100));
    }

    @GetMapping
    public List<User> getUsers() { //@RequestParam(value="name", defaultValue="World") String name) {

        return manager.getUserList();

    }

    @PostMapping
    public boolean addUser(@RequestBody User user) {

        System.out.println("Adding user: "+user);

        return manager.addUser(user);

    }

    @DeleteMapping
    public boolean removeUser(@RequestBody User user) {

        System.out.println("Removing user: " + user);

        return manager.removeUser(user);
    }

    @GetMapping
    public Optional<User> findUser(@RequestBody String email) {

        System.out.println("Finding user on email: "+ email);

        return manager.findUser(email);
    }
}
