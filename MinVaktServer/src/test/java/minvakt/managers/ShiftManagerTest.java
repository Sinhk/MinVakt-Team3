package minvakt.managers;

import minvakt.datamodel.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Iterator;

import static minvakt.datamodel.enums.EmployeeType.*;
import static minvakt.datamodel.enums.PredeterminedIntervals.*;
import static minvakt.datamodel.enums.ShiftType.*;
import static minvakt.managers.ReturnCode.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by magnu on 09.01.2017.
 */
public class ShiftManagerTest {
    ShiftManager shiftManager;
    UserManager userManager;
    User per;
    User ole;
    User sara;

    Shift shift1;
    Shift shift2;

    LocalDate date1;
    LocalDate date2;


/*

    @Before
    public void setUp() throws Exception {
        shiftManager = mock(ShiftManager.class);



        userManager = UserManager.getInstance();
        shiftManager = ShiftManager.getInstance();

        per = new User("per@persen.no", 12345678, "asdASD,.-", 100, ASSISTENT);
        ole = new User("ole@olsen.no", 12345678, "asdASD,.-", 100,ASSISTENT );
        sara = new User("sara@sara.no", 12345678, "asdASD,.-", 100, NURSE);

        userManager.addUser(per);
        userManager.addUser(ole);
        userManager.addUser(sara);

        date1 = LocalDate.parse("2014-10-10");
        date2 =  LocalDate.parse("2014-11-10");

        shift1 = new Shift(date1,MORNING,AVAILABLE);
        shift2 = new Shift(date1,MORNING,SCHEDULED);



        when(shiftManager.addShiftToUser(ole,shift1)).thenReturn(OK);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void addShiftToUser() throws Exception {

        assertEquals(OK, shiftManager.addShiftToUser(ole, shift1));
        //assertEquals(SHIFT_ALREADY_IN_LIST, shiftManager.addShiftToUser(per, shift1));
      //  assertEquals(OK, shiftManager.addShiftToUser(sara, shift1));

    }

    @Test
    public void changeShiftFromUserToUser() throws Exception {
        assertEquals(OK, shiftManager.changeShiftFromUserToUser(shift2,ole,per));
        assertEquals(SHIFT_NOT_FOUND, shiftManager.changeShiftFromUserToUser(shift2,ole,per));

    }

    @Test
    public void removeShiftFromUser() throws Exception {
        assertEquals(OK, shiftManager.removeShiftFromUser(ole,shift2));
        assertEquals(SHIFT_NOT_FOUND, shiftManager.removeShiftFromUser(ole,shift2));
    }

    @Test
    public void getShiftsForUser1() throws Exception {

    }

    @Test
    public void getMinutesForUsersInInterval() throws Exception {
        assertEquals(480, shiftManager.getMinutesForUsersInInterval(ole,date1,date2));
        assertEquals(0, shiftManager.getMinutesForUsersInInterval(per,date1,date2));

    }

    @Test
    public void getMinutesForWeek() throws Exception {
        assertEquals(0, shiftManager.getMinutesForWeek(per,date2));

    }

    @Test
    public void isValidForShift() throws Exception {
        assertEquals(false, shiftManager.isValidForShift(ole,sara,shift2));
        shiftManager.addShiftToUser(per,shift1);
        assertEquals(true, shiftManager.isValidForShift(ole,per,shift2));

    }
*/


}