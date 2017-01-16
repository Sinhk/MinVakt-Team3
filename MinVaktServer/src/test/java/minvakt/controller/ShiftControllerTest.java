package minvakt.controller;

import minvakt.datamodel.Shift;
import minvakt.datamodel.Employee;
import minvakt.controller.data.TwoIntData;

import minvakt.datamodel.enums.PredeterminedIntervals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by magnu on 11.01.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ShiftControllerTest {

    @Mock
    private ShiftController mockedShiftController;

    private Employee user1, user2;
    private Shift shift1, shift2;
    private TwoIntData twoIntThing1, twoIntThing2;

    @Before
    public void setUp() throws Exception {
        user1 = new Employee("Bob", "Bobsen", "bob@bob.bob", 12345678, 100);
        user2 = new Employee("Per", "Persson", "per@sverige.se", 11223344, 50);

        user1.setEmployeeId(1);
        user2.setEmployeeId(2);

        LocalDateTime date1 = LocalDateTime.of(2017, 1, 10, 10, 0, 0);
        LocalDateTime date2 = LocalDateTime.of(2017, 1, 10, 14, 0, 0);

        shift1 = new Shift(date1, date2);
        shift2 = new Shift(date1.toLocalDate(), PredeterminedIntervals.DAYTIME);

        shift1.setShiftId(1);
        shift2.setShiftId(2);

        twoIntThing1 = new TwoIntData(); twoIntThing2 = new TwoIntData();

        twoIntThing1.setInt1(2);
        twoIntThing1.setInt2(2);
        twoIntThing2.setInt1(1);
        twoIntThing2.setInt2(1);

        mockedShiftController.addUserToShift(twoIntThing1);

        when(mockedShiftController.getShifts()).thenReturn(Arrays.asList(shift1, shift2));
        when(mockedShiftController.addUserToShift(twoIntThing2)).thenReturn(Response.ok().build());
        when(mockedShiftController.getUsersForShift(2)).thenReturn(Arrays.asList(user2));
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testMock() throws Exception {
        assertNotNull(mockedShiftController);
    }

    @Test
    public void getShifts() throws Exception {
        List<Shift> allShifts = (List) mockedShiftController.getShifts();
        assertEquals(shift1, allShifts.get(0));
        assertEquals(shift2, allShifts.get(1));

        assertEquals(shift1.getShiftId(), allShifts.get(0).getShiftId());
        assertNotEquals(shift2.getShiftId(), allShifts.get(0).getShiftId());

        assertEquals(shift1.getStartDateTime(), allShifts.get(0).getStartDateTime());
        assertNotEquals(shift1.getEndDateTime(), allShifts.get(1).getEndDateTime());

    }

    @Test
    public void addUserToShift() throws Exception {
        Response response = mockedShiftController.addUserToShift(twoIntThing2);

        assertEquals(Response.ok().build().getStatus(), response.getStatus());
        assertEquals(Response.ok().build().getLength(), response.getLength());
        assertEquals(Response.ok().build().getEntity(), response.getEntity());

        assertNotEquals(404, response.getStatus());

    }

    @Test
    public void getUsersForShift() throws Exception {
        List<Employee> userList = mockedShiftController.getUsersForShift(2);

        assertEquals(user2, userList.get(0));

        assertNotEquals(user1, userList.get(0));

    }
}