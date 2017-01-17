package minvakt.controller;

import minvakt.datamodel.Shift;
import minvakt.datamodel.Employee;
import minvakt.controller.data.TwoIntData;

import minvakt.datamodel.enums.PredeterminedIntervals;
import minvakt.repos.EmployeeRepository;
import minvakt.repos.ShiftRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by magnu on 11.01.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ShiftControllerTest {

    @InjectMocks
    private ShiftController shiftController = mock(ShiftController.class);


    @Mock
    private ShiftRepository shiftRepo;
    @Mock
    private EmployeeRepository empRepo;


    // Objects for use in tests
    private Employee employee1, employee2;
    private Shift shift1, shift2;

    private TwoIntData twoIntThing1, twoIntThing2;

    @Before
    public void setUp() throws Exception {
        // Init Mocks
        MockitoAnnotations.initMocks(this);

        // Setup of users for tests
        employee1 = new Employee("Bob", "Bobsen", "bob@bob.bob", 12345678, 100);
        employee2 = new Employee("Per", "Persson", "per@sverige.se", 11223344, 50);

        // Assign employeeID to test-employees (needed for tests)
        employee1.setEmployeeId(1);
        employee2.setEmployeeId(2);

        // LocalDateTime objects for initializing Shift objects
        LocalDateTime date1 = LocalDateTime.of(2017, 1, 10, 10, 0, 0);
        LocalDateTime date2 = LocalDateTime.of(2017, 1, 10, 14, 0, 0);

        shift1 = new Shift(date1, date2);
        shift2 = new Shift(date1.toLocalDate(), PredeterminedIntervals.DAYTIME);

        // Assign ID to shifts, needed for tests
        shift1.setShiftId(1);
        shift2.setShiftId(2);

        // TwoIntData objects for use with addUserToShift
        twoIntThing1 = new TwoIntData(); twoIntThing2 = new TwoIntData();

        twoIntThing1.setInt1(2);
        twoIntThing1.setInt2(2);
        twoIntThing2.setInt1(1);
        twoIntThing2.setInt2(1);

        // Add a shift to employee2 for tests
        shiftController.addUserToShift(twoIntThing1);

        // Stubbing methods
        /*
        when(shiftController.getShifts()).thenReturn(Arrays.asList(shift1, shift2));
        when(shiftController.addUserToShift(twoIntThing2)).thenReturn(Response.ok().build());
        when(shiftController.getUsersForShift(2)).thenReturn(Arrays.asList(employee2));
        */
    }

    @Test
    public void testMock() throws Exception {
        // Does mocked object actually exist?
        assertNotNull(shiftController);
    }

    @Test
    public void getShifts() throws Exception {
        // Stubbing methods
        when(shiftRepo.findAll()).thenReturn(Arrays.asList(shift1, shift2));

        // Get all shifts
        List<Shift> allShifts = (List) shiftController.getShifts();

        // Verify methods use
        verify(shiftController, atLeastOnce()).getShifts();


        /*
        // Test if shifts are equal
        assertEquals(shift1, allShifts.get(0));
        assertEquals(shift2, allShifts.get(1));
        assertNotEquals(shift2, allShifts.get(0));

        // Test shiftIDs
        assertEquals(shift1.getShiftId(), allShifts.get(0).getShiftId());
        assertNotEquals(shift2.getShiftId(), allShifts.get(0).getShiftId());

        // Test start times and end times
        assertEquals(shift1.getStartDateTime(), allShifts.get(0).getStartDateTime());
        assertNotEquals(shift1.getEndDateTime(), allShifts.get(1).getEndDateTime());

        */
    }

    @Test
    public void getShift() throws Exception {
        //Stubbing method
        when(shiftRepo.findOne(twoIntThing1.getInt1())).thenReturn(shift2);

        // Get Shift for test
        Shift testShift = shiftController.getShift(twoIntThing1.getInt1());

        // Verify method use
        verify(shiftController, atLeastOnce()).getShift(twoIntThing1.getInt1());

        // Compare
        //assertEquals(shift2, testShift);

    }

    @Test
    public void addShift() throws Exception {

    }

    @Test
    public void addUserToShift() throws Exception {
        // Stubbing methods
        when(shiftRepo.findOne(twoIntThing2.getInt1())).thenReturn(shift1);
        when(empRepo.findOne(twoIntThing2.getInt2())).thenReturn(employee1);

        // Attempt to add user to shift
        shiftController.addUserToShift(twoIntThing2);

        // Verify method use
        verify(shiftController, atLeastOnce()).addUserToShift(twoIntThing2);

        // Compare response with expected response (can't directly compare Response)
        /*
        assertEquals(Response.ok().build().getStatus(), response.getStatus());
        assertEquals(Response.ok().build().getLength(), response.getLength());
        assertEquals(Response.ok().build().getEntity(), response.getEntity());

        assertNotEquals(404, response.getStatus());
        */
    }

    @Test
    public void getUsersForShift() throws Exception {
        // Stubbing methods
        when(shiftRepo.findOne(twoIntThing1.getInt1())).thenReturn(shift2);
        when(empRepo.findByShiftAssignments_Shift(shift2)).thenReturn(Arrays.asList(employee2));

        // Get users assigned to shift with ID 2
        List<Employee> userList = shiftController.getUsersForShift(twoIntThing1.getInt1());

        // Verify method use
        verify(shiftController, atLeastOnce()).getUsersForShift(twoIntThing1.getInt1());

        // Check if correct employees are assigned to shift with ID 2

        /*
        assertEquals(employee2, userList.get(0));

        assertNotEquals(employee1, userList.get(0));
        */

    }
}