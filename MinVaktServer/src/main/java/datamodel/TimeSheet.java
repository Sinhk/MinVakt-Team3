package datamodel;

import util.TimeUtil;
import managers.ShiftManager;
import managers.UserManager;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by OlavH on 09-Jan-17.
 */
public class TimeSheet {

    private List<DayOfWeek> dayOfWeekList = Arrays.asList(DayOfWeek.values());

    public List<List<Shift>> getTimeSheetForAllUsers(){

        List<User> userList = UserManager.getInstance().getUserList();

        List<List<Shift>> timeSheet = new ArrayList<>();

        for (User user : userList) {

            timeSheet.add(getShiftsForUser(user));

        }
        return timeSheet;
    }

    public List<Shift> getShiftsForUser(User user){

        List<Shift> list = new ArrayList<>(7); // 1 week

        List<Shift> shiftsForUser = ShiftManager.getInstance().getShiftsForUser(user);

        LocalDate now = LocalDate.now();

        boolean added = false;
        for (int i = 1; i < 8; i++) {

            for (Shift shift : shiftsForUser) {

                if (TimeUtil.dayOfWeekOf(shift.getDate()) == DayOfWeek.of(i) && shift.getDate().getDayOfMonth()==now.getDayOfMonth()+(i-1)){

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
        UserManager.getInstance().addUser(user);
        users.add(user);
        ShiftManager shiftManager = ShiftManager.getInstance();

        shiftManager.addShiftToUser(users.get(0), new Shift(LocalDate.of(2017,1,10), LocalTime.of(6,0), LocalTime.of(7,0)));
        shiftManager.addShiftToUser(users.get(0), new Shift(LocalDate.of(2017,1,11), LocalTime.of(6,0), LocalTime.of(7,0)));
        shiftManager.addShiftToUser(users.get(0), new Shift(LocalDate.of(2017,1,12), LocalTime.of(6,0), LocalTime.of(7,0)));
        shiftManager.addShiftToUser(users.get(0), new Shift(LocalDate.of(2017,1,13), LocalTime.of(6,0), LocalTime.of(7,0)));

        TimeSheet timeSheet = new TimeSheet();

        System.out.println(shiftManager.getShiftsForUser(users.get(0)));

        List<Shift> timeSheetForUser = timeSheet.getShiftsForUser(users.get(0));

        for (Shift shifts : timeSheetForUser) {

            System.out.println(shifts);

        }

    }
}
