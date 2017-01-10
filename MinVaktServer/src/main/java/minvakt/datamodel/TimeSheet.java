package minvakt.datamodel;

import minvakt.managers.ShiftManager;
import minvakt.managers.UserManager;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * Created by OlavH on 09-Jan-17.
 */
@Deprecated // Do with UI, use ShiftManager
public class TimeSheet {

    private List<DayOfWeek> dayOfWeekList = Arrays.asList(DayOfWeek.values());

    public Map<User,List<Shift>> getTimeSheetForAllUsers(){

        List<User> userList = UserManager.getInstance().getUserList();

        Map<User, List<Shift>> userListMap = new HashMap<>();

        for (User user : userList) {

            userListMap.put(user, getShiftsForUser(user));

        }
        return userListMap;
    }

    public List<Shift> getShiftsForUser(User user){

        List<Shift> list = new ArrayList<>(7); // 1 week

        List<Shift> shiftsForUser = ShiftManager.getInstance().getShiftsForUser(user);

        LocalDate now = LocalDate.now();

        boolean added = false;
        for (int i = 1; i < 8; i++) {

            for (Shift shift : shiftsForUser) {

                if (/*TimeUtil.dayOfWeekOf(shift.getDate()) == DayOfWeek.of(i) && */shift.getDate().getDayOfMonth()==now.getDayOfMonth()+(i-1)){

                    list.add(shift);
                    added = true;
                }


            }
            if (!added) list.add(null);
            added = false;

        }

        return list;

    }

    public static void main(String[] args) {

        List<User> users = new ArrayList<>();
        User user = new User("olavh96@gmail.com", 78912978, "ostostO--", 100);
        User user1 = new User("stinesoien@hotmail.com", 971238712, "ostostO--", 100);
        
        UserManager.getInstance().addUser(user);
        UserManager.getInstance().addUser(user1);
        
        users.addAll(Arrays.asList(user, user1));
        
        ShiftManager shiftManager = ShiftManager.getInstance();

        shiftManager.addShiftToUser(user, new Shift(LocalDate.of(2017,1,10), LocalTime.of(6,0), LocalTime.of(14,0)));
        shiftManager.addShiftToUser(user, new Shift(LocalDate.of(2017,1,11), LocalTime.of(6,0), LocalTime.of(14,0)));
        shiftManager.addShiftToUser(user, new Shift(LocalDate.of(2017,1,12), LocalTime.of(6,0), LocalTime.of(14,0)));
        shiftManager.addShiftToUser(user, new Shift(LocalDate.of(2017,1,13), LocalTime.of(6,0), LocalTime.of(14,0)));

        shiftManager.addShiftToUser(user1, new Shift(LocalDate.of(2017,1,10), LocalTime.of(10,0), LocalTime.of(18,0)));
        shiftManager.addShiftToUser(user1, new Shift(LocalDate.of(2017,1,11), LocalTime.of(10,0), LocalTime.of(18,0)));
        shiftManager.addShiftToUser(user1, new Shift(LocalDate.of(2017,1,12), LocalTime.of(10,0), LocalTime.of(18,0)));
        shiftManager.addShiftToUser(user1, new Shift(LocalDate.of(2017,1,13), LocalTime.of(10,0), LocalTime.of(18,0)));

        TimeSheet timeSheet = new TimeSheet();

        System.out.println("Shifts for user: "+shiftManager.getShiftsForUser(user));
        System.out.println("Shifts for user1: "+shiftManager.getShiftsForUser(user1));

        List<Shift> timeSheetForUser = timeSheet.getShiftsForUser(user);

        timeSheetForUser.forEach(System.out::println);

        Map<User, List<Shift>> timeSheetForAllUsers = timeSheet.getTimeSheetForAllUsers();

        timeSheetForAllUsers.forEach((user2, shifts) -> System.out.println(user2+" - "+shifts));

    }
}
