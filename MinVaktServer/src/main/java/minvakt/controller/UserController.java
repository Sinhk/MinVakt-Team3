package minvakt.controller;

import minvakt.controller.data.LoginInfo;
import minvakt.datamodel.User;
import minvakt.datamodel.enums.EmployeeType;
import minvakt.managers.UserManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private static UserManager manager = UserManager.getInstance();

    static {
        manager.addUser(new User("olavh96@gmail.com", 93240605, "Ostostost--", 100, EmployeeType.ADMIN));
    }

    @GetMapping
    public List<User> getUsers() { //@RequestParam(value="name", defaultValue="World") String name) {

        return manager.getUserList();

    }

    @PostMapping
    public boolean addUser(@RequestBody User user) {

        return manager.addUser(user);

    }

    @PostMapping
    @RequestMapping("/login")
    public boolean logInUserWithEmail(@RequestBody LoginInfo info){

        Optional<User> user = manager.findUser(info.getEmail());

        if (user.isPresent()){

            return user.get().authenticatePassword(info.getPassword());

        }
        return false;

    }


}
