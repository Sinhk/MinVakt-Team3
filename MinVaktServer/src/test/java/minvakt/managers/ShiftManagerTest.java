package minvakt.managers;

import minvakt.datamodel.Employee;
import minvakt.datamodel.Shift;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Iterator;

import static minvakt.managers.ReturnCode.OK;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by magnu on 09.01.2017.
 */
public class ShiftManagerTest {
    ShiftManager manager;
    Employee per;
    Employee ole;
    Employee sara;

    Shift shift1;
    Shift shift2;

    @Before
    public void setUp() throws Exception {

        per = new Employee("per@persen.no", 12345678, "asdASD,.-", 100);
        ole = new Employee("ole@olsen.no", 12345678, "asdASD,.-", 100);
        sara = new Employee("sara@sara.no", 12345678, "asdASD,.-", 100);

        LocalDate date1 = LocalDate.parse("24.12.2014");
        LocalTime time1from = LocalTime.parse("08:00");
        LocalTime time1to = LocalTime.parse("16:00");
        Shift shift1 = new Shift(date1,time1from,time1to);

        LocalDate date2 = LocalDate.parse("24.12.2014");
        LocalTime time2from = LocalTime.parse("10:00");
        LocalTime time2to = LocalTime.parse("16:00");
        Shift shift2 = new Shift(date1,time1from,time1to);

        manager = ShiftManager.getInstance();

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void addShiftToUser() throws Exception {
        assertEquals(OK, manager.addShiftToUser(per, shift1));
        assertEquals(false, manager.addShiftToUser(per, shift1));
        assertEquals(false, manager.addShiftToUser(ole, null));
        assertEquals(OK, manager.addShiftToUser(sara, shift1));

        //arrange
        Iterator i=mock(Iterator.class);
        when(i.next()).thenReturn("Hello").thenReturn("World");
        //act
        String result=i.next()+" "+i.next();
        //assert
        assertEquals("Hello World", OK);
    }

    @Test
    public void changeShiftFromUserToUser() throws Exception {
        assertEquals(OK, manager.changeShiftFromUserToUser(shift1,per,ole));
        assertEquals(false, manager.changeShiftFromUserToUser(shift1,per,ole));

    }

    @Test
    public void removeShiftFromUser() throws Exception {
        assertEquals(OK, manager.removeShiftFromUser(ole,shift1));
        assertEquals(false, manager.removeShiftFromUser(ole,shift1));
    }

    @Test
    public void getShiftsForUser() throws Exception {
        assertEquals(per, manager.getShiftsForUser(per));


    }



}