package datamodel;

import Util.TimeUtil;
import managers.ShiftManager;
import managers.UserManager;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * Created by OlavH on 09-Jan-17.
 */
public class TimeSheet {

    private List<DayOfWeek> dayOfWeekList = Arrays.asList(DayOfWeek.values());

    public List<List<Shift>> getTimeSheetForUser(User user) {
        Objects.requireNonNull(user);

        ShiftManager shiftManager = ShiftManager.getInstance();

        int daysInThisMonth = LocalDate.now().getMonth().length(LocalDate.now().isLeapYear());

        List<List<Shift>> timeSheet = new ArrayList<>();

        List<Shift> shiftsForUser = shiftManager.getShiftsForUser(user);

        for (int i = 0; i < daysInThisMonth; i++) {

            timeSheet.add(new ArrayList<>());
            List<Shift> thisWeek = timeSheet.get(i);

            for (int j = 0; j < dayOfWeekList.size(); j++) {
                thisWeek = new ArrayList<>(7);
                for (Shift shift : shiftsForUser) {

                    DayOfWeek day = TimeUtil.of(shift.getDate());

                    if (day.ordinal() == j && shift.getDate().getDayOfMonth() > i && shift.getDate().getDayOfMonth() < i + dayOfWeekList.size()) {
                        thisWeek.add(shift);
                    }
                    else {
                        thisWeek.add(null);
                    }


                }


            }

        }

        return timeSheet;
    }

    public static void main(String[] args) {

        List<User> users = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            users.add(new User(i+"Olavh96@gmail.com", 912391291, i+"Ostoshot--", 100) );
        }
        users.forEach(UserManager.getInstance()::addUser);

        users.forEach(user -> ShiftManager.getInstance()
                .addShiftToUser(user, new Shift(LocalDate.now().plusDays(new Random().nextInt(10)), LocalTime.of(new Random().nextInt(24), 0), LocalTime.of(new Random().nextInt(24), 0))));


        System.out.println(users);

        TimeSheet timeSheet = new TimeSheet();

        List<List<Shift>> timeSheetForUser = timeSheet.getTimeSheetForUser(users.get(0));

        for (List<Shift> list : timeSheetForUser) {

            System.out.println(list);


        }
    }


}
