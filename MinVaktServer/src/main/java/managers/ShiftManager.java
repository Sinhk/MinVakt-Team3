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
    //Singelton, do not modify
    private ShiftManager() {
    }

    private Map<User, List<Shift>> userShiftMap = new HashMap<>();

    private void addEmptyUser(User user){
        userShiftMap.put(user, new ArrayList<>());
    }
    private boolean isEmptyUser(User user){
        return userShiftMap.get(user).isEmpty();
    }
    private boolean containsUser(User user){
        return userShiftMap.containsKey(user);
    }
    private boolean containsNonEmptyUser(User user){
        return containsUser(user) && !isEmptyUser(user);
    }
    private boolean userHasShift(User user, Shift shift){
        return containsNonEmptyUser(user) && userShiftMap.get(user).contains(shift);
    }

    public ReturnCode addShiftToUser(User user, Shift shift){
        Objects.requireNonNull(user); Objects.requireNonNull(shift);

        UserManager userManager = UserManager.getInstance();

        if (!userManager.getUserList().contains(user)) return ReturnCode.USER_NOT_FOUND;

        if (userManager.getUserList().contains(user) && !containsUser(user)){
            addEmptyUser(user);
        }

        List<Shift> shifts = userShiftMap.get(user);
        shifts.add(shift);

        return ReturnCode.OK;
    }

    // TODO: 09-Jan-17 test
    public ReturnCode changeShiftFromUserToUser(Shift fromShift, User fromUser, User toUser){
        Objects.requireNonNull(fromShift); Objects.requireNonNull(toUser); Objects.requireNonNull(fromUser);

        UserManager manager = UserManager.getInstance();
        // forgive me
        if (!(manager.getUserList().contains(fromUser) && manager.getUserList().contains(toUser))) return ReturnCode.USER_NOT_FOUND; // both users are not added

        if (!containsUser(fromUser)){ // manager.getUserList().contains(fromUser) &&
            addEmptyUser(fromUser);// adds the user to the map, has no shifts from before
            return ReturnCode.HAS_NO_SHIFTS;
        }
        if (!userHasShift(fromUser, fromShift)) return ReturnCode.SHIFT_NOT_FOUND;

        // toUser has no shifts from before, makes them and adds the fromUser shift it if exists
        if (!containsUser(toUser)){ // manager.getUserList().contains(toUser) &&
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

    public ReturnCode removeShiftFromUser(User user, Shift shift){

        if (!userShiftMap.get(user).contains(shift)) return ReturnCode.SHIFT_NOT_FOUND;

        userShiftMap.get(user).remove(shift);
        return ReturnCode.OK;

    }
    public List<Shift> getShiftsForUser(User user){

        return userShiftMap.get(user);

    }

}
