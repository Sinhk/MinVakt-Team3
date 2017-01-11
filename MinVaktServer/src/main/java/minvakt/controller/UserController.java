package minvakt.controller;

import minvakt.datamodel.User;
import minvakt.repos.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;

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
    public Iterable<User> getUsers() {//@RequestParam(value="name", defaultValue="World") String name) {
        Iterable<User> users = userRepo.findAll();
        //users.forEach(user -> log.info(user.toString()));
        return users;
    }

    @PostMapping
    public boolean addUser(@RequestBody User user) {

        System.out.println("Adding user: "+user);

        userRepo.save(user);
        return true;

    }

    @DeleteMapping
    public Response removeUser(@RequestBody User user) {

        System.out.println("Removing user: " + user);
        userRepo.delete(user);
        return Response.ok().build();
    }

    /*@GetMapping
    public User findUser(@RequestBody String email) {

        System.out.println("Finding user on email: "+ email);
        return userRepo.findByEmail(email);
    }*/
    // TODO: 11.01.2017 Figure out how to integrate with Spring Security
    /*@PostMapping

    @RequestMapping("/login")
    public boolean logInUserWithEmail(@RequestBody LoginInfo info){

        Optional<User> user = manager.findUser(info.getEmail());

        if (user.isPresent()){

            return user.get().authenticatePassword(info.getPassword());

        }
        return false;

    }*/

}
