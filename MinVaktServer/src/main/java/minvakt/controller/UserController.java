package minvakt.controller;

import minvakt.controller.data.ChangePasswordInfo;
import minvakt.datamodel.Shift;
import minvakt.datamodel.User;
import minvakt.repos.ShiftRepository;
import minvakt.repos.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private UserRepository userRepo;
    private ShiftRepository shiftRepo;
    private final UserDetailsManager userDetailsManager;

    @Autowired
    public UserController(UserRepository userRepo, ShiftRepository shiftRepo, UserDetailsManager userDetailsManager) {
        this.userRepo = userRepo;
        this.shiftRepo = shiftRepo;
        this.userDetailsManager = userDetailsManager;
    }

    @GetMapping
    public Iterable<User> getUsers() {//@RequestParam(value="name", defaultValue="World") String name) {
        return userRepo.findAll();
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

    @RequestMapping(value = "/{email}/", method = RequestMethod.GET)
    public User findUser(@PathVariable String email) {
        System.out.println("Finding user on email: "+ email);
        return userRepo.findByEmail(email);
    }

    @RequestMapping(value = "/{user_id}/changepassword", method = RequestMethod.PUT)
    public Response changePasswordForUser(@PathVariable String user_id, @RequestBody ChangePasswordInfo info){
        String oldPass = info.getOldPassAttempt();
        String newPass = info.getNewPassAttempt();

        try {
            userDetailsManager.changePassword(oldPass, newPass);
        } catch (AccessDeniedException ade) {
            //Not signed in
            return Response.status(403).build();
        } catch (BadCredentialsException bce) {
            //Wrong oldPass
            return Response.notModified("Wrong password").build();
        }

        return Response.ok().build();
    }


    @RequestMapping(value = "/{user_id}/shifts", method = RequestMethod.GET)
    public Collection<Shift> getShiftsForUser(@PathVariable(value="user_id") String userId){

        User user = userRepo.findOne(Integer.valueOf(userId));

        return (user != null) ? shiftRepo.findByShiftAssignments_User(user) : Collections.emptyList();

    }

    // FIXME: 15.01.2017
    /*@RequestMapping("/{user_id}/shifts/inrange")
    @GetMapping
    public Collection<Shift> getShiftsForUserInRange(@PathVariable String user_id, @RequestBody TwoStringsData stringsData){

        String startDate = stringsData.getString1();
        String endDate = stringsData.getString2();

        LocalDate start = TimeUtil.parseBadlyFormattedTime(startDate);
        LocalDate end = TimeUtil.parseBadlyFormattedTime(endDate);

        User user = userRepo.findOne(Integer.valueOf(user_id));

        if (user != null){
            return user.shiftsInRange(start, end);
        }

        return Collections.emptyList();
    }*/



  /*  @PostMapping
    @RequestMapping(value = "/{user_id}/shifts/{shift_id}", method = RequestMethod.POST)
    public Response addShiftToUser(@PathVariable(value = "user_id") String userId, @PathVariable(value = "shift_id") String shiftId){

        User user = userRepo.findOne(Integer.valueOf(userId));

        Shift shift = shiftRepo.findOne(Integer.valueOf(shiftId));

        if (user != null && shift != null)
            return shift.getUsers().add(user) ? Response.ok().build() : Response.notModified().build();

        return Response.noContent().build();
    }*/

   /* @DeleteMapping
    @RequestMapping(value = "/{user_id}/shifts/{shift_id}", method = RequestMethod.DELETE)
    public Response removeShiftFromUser (@PathVariable(value = "user_id") String userId, @PathVariable(value = "shift_id") String shiftId) {

        User user = userRepo.findOne(Integer.valueOf(userId));

        Shift shift = shiftRepo.findOne(Integer.valueOf(shiftId));

        if (user != null && shift != null) return shift.getUsers().remove(user) ? Response.ok().build() : Response.notModified().build();

        return Response.noContent().build();
    }*/
}
