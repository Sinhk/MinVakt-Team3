package minvakt.controller;

import minvakt.datamodel.UserEntity;
import minvakt.repos.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import minvakt.datamodel.User;
import minvakt.managers.UserManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private UserRepository userRepo;

    @Autowired
    public UserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public Iterable<UserEntity> getUsers() {//@RequestParam(value="name", defaultValue="World") String name) {
        Iterable<UserEntity> users = userRepo.findAll();
        users.forEach(userEntity -> log.info(userEntity.toString()));
        return users;
    }

    @PostMapping
    public boolean addUser(@RequestBody User user) {

        System.out.println("Adding user: "+user);

        userRepo.save(user);
        return true;

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
