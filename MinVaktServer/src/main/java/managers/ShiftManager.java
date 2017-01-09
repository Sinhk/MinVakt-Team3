package managers;

import datamodel.Shift;
import datamodel.User;

import java.util.*;

/**
 * Created by OlavH on 09-Jan-17.
 */
public class ShiftManager {
    private static ShiftManager ourInstance = new ShiftManager();

    public static ShiftManager getInstance() {
        return ourInstance;
    }

    private ShiftManager() {
    }

    private Map<User, List<Shift>> userShiftMap = new HashMap<>();


    public ReturnCode addShiftToUser(User user, Shift shift){
        Objects.requireNonNull(user); Objects.requireNonNull(shift);

        UserManager userManager = UserManager.getInstance();

        if (!userManager.getUserList().contains(user)) return ReturnCode.USER_NOT_FOUND;

        if (userManager.getUserList().contains(user) && !userShiftMap.containsKey(user)){
            userShiftMap.put(user, new ArrayList<>());
        }

        List<Shift> shifts = userShiftMap.get(user);
        shifts.add(shift);

        return ReturnCode.OK;
    }
    public ReturnCode changeShiftFromUserToUser(Shift fromShift, User fromUser, User toUser){
        Objects.requireNonNull(fromShift); Objects.requireNonNull(toUser); Objects.requireNonNull(fromUser);

        UserManager manager = UserManager.getInstance();
        // forgive me
        if (!(manager.getUserList().contains(fromUser) && manager.getUserList().contains(toUser))) return ReturnCode.USER_NOT_FOUND; // both users are not added


        if (!userShiftMap.containsKey(fromUser)){ // manager.getUserList().contains(fromUser) &&
            userShiftMap.put(fromUser, new ArrayList<>()); // adds the user to the map, has no shifts from before
            return ReturnCode.HAS_NO_SHIFTS;
        }
        if (!userShiftMap.get(fromUser).contains(fromShift)) return ReturnCode.SHIFT_NOT_FOUND;

        // toUser has no shifts from before, makes them and adds the fromUser shift it if exists
        if (!userShiftMap.containsKey(toUser)){ // manager.getUserList().contains(toUser) &&
            List<Shift> shifts = new ArrayList<>();

            // gets the shift from the argument from the fromUser if it exists
            Optional<Shift> shiftOptional = userShiftMap.get(fromUser).stream().filter(shift -> shift == fromShift).findFirst();

            if (shiftOptional.isPresent()){
                shiftOptional.ifPresent(shifts::add);
                shiftOptional.ifPresent(userShiftMap.get(fromUser)::remove);

                userShiftMap.put(toUser, shifts);

                return ReturnCode.OK;
            }
            return ReturnCode.SHIFT_NOT_FOUND;
        }

        userShiftMap.get(fromUser).remove(fromShift);
        userShiftMap.get(toUser).add(fromShift);

        return ReturnCode.OK;
    }

}
