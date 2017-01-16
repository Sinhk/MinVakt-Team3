package minvakt.controller;

import minvakt.controller.data.ChangePasswordInfo;
import minvakt.controller.data.TwoStringsData;
import minvakt.datamodel.Shift;
import minvakt.datamodel.ShiftAssignment;
import minvakt.datamodel.User;
import minvakt.repos.ShiftRepository;
import minvakt.repos.UserRepository;
import minvakt.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Response removeUser(@RequestBody String email) {
        User byEmail = userRepo.findByEmail(email);

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
    public Response changePasswordForUser(@PathVariable int user_id, @RequestBody ChangePasswordInfo info){
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
    public Collection<Shift> getShiftsForUser(@PathVariable(value="user_id") int userId){

        User user = userRepo.findOne(userId);

        return (user != null) ? shiftRepo.findByShiftAssignments_User(user) : Collections.emptyList();

    }

    // FIXME: 15.01.2017
    @RequestMapping("/{user_id}/shifts/inrange")
    @GetMapping
    public Collection<Shift> getShiftsForUserInRange(@PathVariable int user_id, @RequestBody TwoStringsData stringsData){

        String startDate = stringsData.getString1();
        String endDate = stringsData.getString2();

        LocalDate start = TimeUtil.parseBadlyFormattedTime(startDate);
        LocalDate end = TimeUtil.parseBadlyFormattedTime(endDate);

        User user = userRepo.findOne(user_id);

        List<Shift> byShiftAssignments_user = shiftRepo.findByShiftAssignments_User(user);

        List<Shift> shiftList = byShiftAssignments_user
                .stream()
                .filter(
                        shift -> shift.getStartDateTime().isAfter(start.atStartOfDay())
                                && shift.getEndDateTime().isBefore(end.atTime(23, 59)))
                .collect(Collectors.toList());

        return shiftList;
    }



    @Transactional
    @PostMapping
    @RequestMapping(value = "/{user_id}/shifts/{shift_id}", method = RequestMethod.POST)
    public Response addShiftToUser(@PathVariable(value = "user_id") int userId, @PathVariable(value = "shift_id") int shiftId){

        User user = userRepo.findOne(userId);

        Shift shift = shiftRepo.findOne(shiftId);

        ShiftAssignment shiftAssignment = new ShiftAssignment(user, shift);

        user.getShiftAssignments().add(shiftAssignment);

        userRepo.save(user);


        return Response.noContent().build();
    }

    @Transactional
    @DeleteMapping
    @RequestMapping(value = "/{user_id}/shifts/{shift_id}", method = RequestMethod.DELETE)
    public Response removeShiftFromUser (@PathVariable(value = "user_id") String userId, @PathVariable(value = "shift_id") String shiftId) {

        User user = userRepo.findOne(Integer.valueOf(userId));

        Shift shift = shiftRepo.findOne(Integer.valueOf(shiftId));

        Optional<ShiftAssignment> first = user.getShiftAssignments()
                .stream().filter(shiftAssignment -> shiftAssignment.getShift() == shift)
                .findFirst();

        first.ifPresent(user.getShiftAssignments()::remove);

        userRepo.save(user);

        return Response.noContent().build();
    }
}
