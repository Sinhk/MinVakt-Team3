package minvakt.controller;

import minvakt.datamodel.Shift;
import minvakt.datamodel.User;
import minvakt.repos.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.util.Collection;

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

    @GetMapping
    @RequestMapping("/user")
    public User getUser(String email){
        User user = userRepo.findByEmail(email);
        return user;
    }

    @PostMapping
    public boolean addUser(@RequestBody User user) {

        System.out.println("Adding user: "+user);

        userRepo.save(user);
        return true;

    }

    @DeleteMapping
    public Response removeUser(@RequestBody String user) {

        User byEmail = userRepo.findByEmail(user);

        if (byEmail != null){
            System.out.println("Removing user: " + byEmail);
            userRepo.delete(byEmail);
            return Response.ok().build();
        }

        return Response.noContent().build();
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

 /*   @PostMapping
    @RequestMapping("/user/changepassword")
    public boolean changePasswordForUser(@RequestBody ChangePasswordInfo info){

        return info.getUser().changePassword(info.getOldPassAttempt(), info.getNewPassAttempt());
    }*/
    @RequestMapping("/{user_id}/shifts")
    @GetMapping
    public Collection<Shift> getShiftsForUser(@RequestParam(value="user_id") String userId){
        System.out.println("test");
        User user = userRepo.findOne(Integer.valueOf(userId));

        return user.getShiftsForUser();
    }



    @RequestMapping("/{user_id}/shifts/{shift_id}")
    @PostMapping
    public void addShiftToUser(@RequestParam(value = "user_id") String userId, @RequestParam(value = "shift_id") String shiftId){

        User user = userRepo.findOne(Integer.valueOf(userId));



    }


    }
