package managers;

import datamodel.Shift;
import datamodel.User;
import datamodel.enums.ShiftType;
import util.TimeUtil;
import util.WeekDateInterval;

import java.time.LocalDate;
import java.util.*;

/**
 * Created by OlavH on 09-Jan-17.
 */
public class ShiftManager {

    private static final int MAX_MINUTES = 2400; // 60*8*5

    private static ShiftManager ourInstance = new ShiftManager();

    public static ShiftManager getInstance() {
        return ourInstance;
    }
    //Singelton, do not modify
    private ShiftManager() {
    }

    private Map<User, List<Shift>> userShiftMap = new HashMap<>();

    private void addEmptyUser(User user){userShiftMap.put(user, new ArrayList<>());}
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

        if (TimeUtil.shiftsOverlap(shifts, shift)) {
            return ReturnCode.SHIFT_OVERLAPS;
        }

        if (shifts.contains(shift)) return ReturnCode.SHIFT_ALREADY_IN_LIST;

        shifts.add(shift);
        user.changeTotalMinutes((int) shift.getTimeInterval().getMinutes());
        return ReturnCode.OK;
    }

    public ReturnCode changeShiftFromUserToUser(Shift fromShift, User fromUser, User toUser){
        Objects.requireNonNull(fromShift); Objects.requireNonNull(toUser); Objects.requireNonNull(fromUser);

        UserManager manager = UserManager.getInstance();

        long minutes = fromShift.getTimeInterval().getMinutes();

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

                fromUser.changeTotalMinutes((int) -minutes);
                toUser.changeTotalMinutes((int) minutes);

                return ReturnCode.OK;
            }
            return ReturnCode.SHIFT_NOT_FOUND;
        }

        userShiftMap.get(fromUser).remove(fromShift);
        userShiftMap.get(toUser).add(fromShift);

        fromUser.changeTotalMinutes((int) -minutes);
        toUser.changeTotalMinutes((int) minutes);

        return ReturnCode.OK;
    }

    public ReturnCode removeShiftFromUser(User user, Shift shift){
        Objects.requireNonNull(user); Objects.requireNonNull(shift);

        if (!userShiftMap.get(user).contains(shift)) return ReturnCode.SHIFT_NOT_FOUND;

        userShiftMap.get(user).remove(shift);
        user.changeTotalMinutes((int) shift.getTimeInterval().getMinutes());
        return ReturnCode.OK;

    }
    public List<Shift> getShiftsForUser(User user){
        Objects.requireNonNull(user);

        return userShiftMap.get(user);

    }

    /**
     *
     * @param user
     * @param from Inclusive
     * @param to Ecxlusive
     * @return
     */
    public int getMinutesForUsersInInterval(User user, LocalDate from, LocalDate to){
        Objects.requireNonNull(user); Objects.requireNonNull(from); Objects.requireNonNull(to);

        if (!containsNonEmptyUser(user)) return 0;

        int total = 0;
        List<Shift> shiftsForUser = getShiftsForUser(user);

        for (Shift shift : shiftsForUser) {

            if (isInDateInterval(from, to, shift.getDate()) && shift.getShiftType() != ShiftType.ERROR){

                total += shift.getTimeInterval().getMinutes();

            }
        }

        return total;
    }
    public int getMinutesForWeek(User user, LocalDate dateInThatWeek){
        Objects.requireNonNull(user); Objects.requireNonNull(dateInThatWeek);

        WeekDateInterval thatWeek = WeekDateInterval.of(dateInThatWeek); // that week

        return getMinutesForUsersInInterval(user, thatWeek.getStart(), thatWeek.getEnd());

    }

    /**
     * @param userToReplace The user that is to be replaced
     * @param userReplacement The potential user to replace the user
     * @param shift This shift in question
     * @return true if the user can replace the other user. Type must be the same, the user must have an availible shift
     */
    public boolean isValidForShift(User userToReplace, User userReplacement, Shift shift){

        if (userToReplace.getEmployeeType() != userReplacement.getEmployeeType()) return false;

        if (getMinutesForWeek(userReplacement, shift.getDate())+shift.getTimeInterval().getMinutes() > MAX_MINUTES)
            return false;

        List<Shift> shiftsForUser = getShiftsForUser(userReplacement);

        for (Shift shift1 : shiftsForUser) {

            if (shift1.getDate().isEqual(shift.getDate())
                    && shift1.getShiftType() == ShiftType.AVAILABLE
                    && shift.getPredeterminedInterval() == shift1.getPredeterminedInterval()) return true;

        }

        return false;
    }



    private boolean isInDateInterval(LocalDate fromInc, LocalDate toEx, LocalDate toCheck){

        return toCheck.isAfter(fromInc) || toCheck.isEqual(fromInc) && toCheck.isBefore(toEx);

    }

    public static void main(String[] args) {

        ShiftManager instance = ShiftManager.getInstance();



    }

}
