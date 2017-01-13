package minvakt.controller;

import minvakt.controller.data.ChangePasswordInfo;
import minvakt.controller.data.TwoStringsData;
import minvakt.datamodel.Shift;
import minvakt.datamodel.User;
import minvakt.repos.ShiftRepository;
import minvakt.repos.UserRepository;
import minvakt.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("tests/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private UserRepository userRepo;
    private ShiftRepository shiftRepo;

    @Autowired
    public UserController(UserRepository userRepo, ShiftRepository shiftRepo) {
        this.userRepo = userRepo;
        this.shiftRepo = shiftRepo;
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
        return userRepo.findByEmail(email);
    }

    @PostMapping
    public Response addUser(@RequestBody User user) {

        userRepo.save(user);
        return Response.ok().build();

    }

    @DeleteMapping
    public Response removeUser(@RequestBody String user) {

        User byEmail = userRepo.findByEmail(user);

        if (byEmail != null){
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
    /*@GetMapping
    @RequestMapping("//login")
    public boolean logInUserWithEmail(@RequestBody LoginInfo info){

        Optional<User> user = manager.findUser(info.getEmail());

        if (user.isPresent()){

            return user.get().authenticatePassword(info.getPassword());

        }
        return false;

    }*/

    @PutMapping
    @RequestMapping("/{user_id}/changepassword")
    public Response changePasswordForUser(@PathVariable String user_id, @RequestBody ChangePasswordInfo info){

        User user = userRepo.findOne(Integer.valueOf(user_id));

        String oldPass = info.getOldPassAttempt();
        String newPass = info.getNewPassAttempt();

        if (user != null  && user.authenticatePassword(oldPass)){

            user.changePassword(oldPass, newPass);

            return user.authenticatePassword(newPass) ? Response.ok().build() : Response.notModified().build();

        }
        return Response.noContent().build();

    }

    @GetMapping
    @RequestMapping("/{user_id}/logon")
    public Response logonUser(@PathVariable String user_id, @RequestBody String password) {

        User user = userRepo.findOne(Integer.valueOf(user_id));

        return user != null && user.authenticatePassword(password) ? Response.ok().build() : Response.notModified().build(); // short circuit

    }

    @RequestMapping("/{user_id}/shifts")
    @GetMapping
    public Collection<Shift> getShiftsForUser(@PathVariable(value="user_id") String userId){

        User user = userRepo.findOne(Integer.valueOf(userId));

        return (user != null) ? user.getShiftsForUser() : Collections.emptyList();

    }

    @RequestMapping("/{user_id}/shifts/inrange")
    @GetMapping
    public Collection<Shift> getShiftsForUserInRange(@PathVariable String user_id, @RequestBody TwoStringsData stringsData){

        String startDate = stringsData.getString1();
        String endDate = stringsData.getString2();

        LocalDate start = TimeUtil.parseBadlyFormattedTime(startDate);
        LocalDate end = TimeUtil.parseBadlyFormattedTime(endDate);

        User user = userRepo.findOne(Integer.valueOf(user_id));

        if (user != null){

            return user.getShiftsInRange(start, end);

        }

        return Collections.emptyList();
    }



    @PostMapping
    @RequestMapping(value = "/{user_id}/shifts/{shift_id}", method = RequestMethod.POST)
    public Response addShiftToUser(@PathVariable(value = "user_id") String userId, @PathVariable(value = "shift_id") String shiftId){

        User user = userRepo.findOne(Integer.valueOf(userId));

        Shift shift = shiftRepo.findOne(Integer.valueOf(shiftId));

        if (user != null && shift != null) return user.getShiftsForUser().add(shift) ? Response.ok().build() : Response.notModified().build();

        return Response.noContent().build();
    }

    @DeleteMapping
    @RequestMapping(value = "/{user_id}/shifts/{shift_id}", method = RequestMethod.DELETE)
    public Response removeShiftFromUser (@PathVariable(value = "user_id") String userId, @PathVariable(value = "shift_id") String shiftId) {

        User user = userRepo.findOne(Integer.valueOf(userId));

        Shift shift = shiftRepo.findOne(Integer.valueOf(shiftId));

        if (user != null && shift != null) return user.removeShift(shift) ? Response.ok().build() : Response.notModified().build();

        return Response.noContent().build();
    }
}
