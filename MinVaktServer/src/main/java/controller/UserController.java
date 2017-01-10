package controller;

import datamodel.User;
import managers.UserManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private static UserManager manager = UserManager.getInstance();

    static {
        manager.addUser(new User("olavh96@gmail.com",93240605,"Ostostost--",100));
    }
    @RequestMapping("/users")
    public List<User> getUsers() {//@RequestParam(value="name", defaultValue="World") String name) {

        return manager.getUserList();

    }

    @RequestMapping("/users")
    public boolean addUser(User user){

        return manager.addUser(user);

    }

}
