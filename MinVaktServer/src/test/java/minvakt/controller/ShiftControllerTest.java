package minvakt.controller;

import minvakt.datamodel.tables.pojos.ChangeRequest;
import minvakt.datamodel.tables.pojos.Employee;
import minvakt.datamodel.tables.pojos.Shift;
import minvakt.datamodel.tables.pojos.ShiftAssignment;
import minvakt.repos.ChangeRequestRepository;
import minvakt.repos.EmployeeRepository;
import minvakt.repos.ShiftAssignmentRepository;
import minvakt.repos.ShiftRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ShiftControllerTest {
    @InjectMocks
    private ShiftController shiftController;

    @Mock
    private ShiftRepository shiftRepo;

    @Mock
    private EmployeeRepository employeeRepo;

    @Mock
    private ShiftAssignmentRepository shiftAssignmentRepo;

    @Mock
    private ChangeRequestRepository changeRequestRepository;


    private Shift shift1, shift2, nonAssignedShift;
    private Employee emp1, emp2;
    private ShiftAssignment shiftAssign1, shiftAssign2, nonAssigned;
    private ChangeRequest change1, change2;

    @Before
    public void setUp() throws Exception {
        // Setup LocalDateTimes for shifts
        LocalDateTime fromTime1 = LocalDateTime.of(2017, 1, 17, 6, 0), toTime1 = LocalDateTime.of(2017, 1, 17, 14, 0), toTime2 = LocalDateTime.of(2017, 1, 17, 22, 0, 0);

        // Setup shifts based on data from database script
        shift1 = new Shift(1, 1, fromTime1, toTime1);
        shift2 = new Shift(2, 2, toTime1, toTime2);
        nonAssignedShift = new Shift(3, 2, fromTime1, toTime1);

        // Setup employees based on data from database script
        emp1 = new Employee(1, (short)1, "admin", "", 12345678, "admin@minvakt.no", (short)100, "$2a$04$c7YTJkh8TVGsmCNNWW7pXu0f/dmy6E6TdsCgX7dnZlJQP7DBfuKjq", true, true);
        emp2 = new Employee(2, (short)2, "user", "", 12345679, "user@minvakt.no", (short)100, "$2a$06$vMO32hhPzSrnvM8tRYwMZ.mzxkrrtXHtsYmRNxESKiClLPtZGRtF6", true, true);

        // Setup shift assignments based on data from database script
        shiftAssign1 = new ShiftAssignment(1, 1, 1, false, true, "");
        shiftAssign2 = new ShiftAssignment(2, 2, 2, true, true, "Diarrhea");
        nonAssigned = new ShiftAssignment(3, 3, 2, false, false, "");

        // Setup change requests based on data from database script
        change1 = new ChangeRequest(1, 1, 1, 2);
        change2 = new ChangeRequest(2, 2, 2, 1);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getShifts() throws Exception {
        // Stubbing methods
        when(shiftRepo.findAll()).thenReturn(Arrays.asList(shift1, shift2));

        // Get all shifts
        List<Shift> allShifts = (List<Shift>) shiftController.getShifts();

        // Test if shifts are equal
        assertEquals(shift1, allShifts.get(0));
        assertEquals(shift2, allShifts.get(1));
        assertNotEquals(shift2, allShifts.get(0));

        // Test shiftIDs
        assertEquals(shift1.getShiftId(), allShifts.get(0).getShiftId());
        assertNotEquals(shift2.getShiftId(), allShifts.get(0).getShiftId());

        // Test start times and end times
        assertEquals(shift1.getFromTime(), allShifts.get(0).getFromTime());
        assertNotEquals(shift1.getToTime(), allShifts.get(1).getToTime());
    }

    @Test
    public void getShift() throws Exception {
        // Stubbing method
        when(shiftRepo.findOne(2)).thenReturn(shift2);

        // Get Shift for test
        Shift testShift = shiftController.getShift(2);

        // Verify method use
        verify(shiftRepo, atLeastOnce()).findOne(2);

        // Compare
        assertEquals(shift2, testShift);
    }

    @Test
    public void addShift() throws Exception {
        // Stubbing method
        when(shiftRepo.save(shift2)).thenReturn(shift2);

        // Add shift
        shiftController.addShift(shift2);

        // Verify method use
        verify(shiftRepo, atLeastOnce()).save(shift2);
    }

    @Test
    public void getAllAssignedShifts() throws Exception {
        // Stubbing methods
        when(shiftAssignmentRepo.findByAssignedTrue()).thenReturn(Arrays.asList(shiftAssign1, shiftAssign2));
        when(shiftRepo.findOne(shiftAssign1.getId())).thenReturn(shift1);
        when(shiftRepo.findOne(shiftAssign2.getId())).thenReturn(shift2);

        // Get list of assigned shifts
        List<Shift> list = shiftController.getAllAssignedShifts();

        // Verify method use
        verify(shiftAssignmentRepo, atLeastOnce()).findByAssignedTrue();
        verify(shiftRepo, atLeastOnce()).findOne(shiftAssign1.getId());
        verify(shiftRepo, atLeastOnce()).findOne(shiftAssign2.getId());

        // Assert stuff
        assertEquals(shift1, list.get(0));
        assertEquals(shift2, list.get(1));
        assertEquals(2, list.size());
    }

    @Test
    public void getAllNonAssignedShifts() throws Exception {
        // Stubbing methods
        when(shiftAssignmentRepo.findByAssignedFalse()).thenReturn(Arrays.asList(nonAssigned));
        when(shiftRepo.findOne(nonAssigned.getId())).thenReturn(nonAssignedShift);

        // Get list of assigned shifts
        List<Shift> list = shiftController.getAllNonAssignedShifts();

        // Verify method use
        verify(shiftAssignmentRepo, atLeastOnce()).findByAssignedFalse();
        verify(shiftRepo, atLeastOnce()).findOne(nonAssigned.getId());

        // Assert
        assertEquals(nonAssignedShift, list.get(0));
    }

    @Test
    public void getUsersForShift() throws Exception {
        // Stubbing methods
        when(shiftRepo.findOne(2)).thenReturn(shift2);
        when(employeeRepo.findByShiftAssignments_Shift(shift2)).thenReturn(Arrays.asList(emp2));

        // Get users assigned to shift with ID 2
        List<Employee> userList = shiftController.getUsersForShift(2);

        // Verify method use
        verify(shiftRepo, atLeastOnce()).findOne(2);
        verify(employeeRepo, atLeastOnce()).findByShiftAssignments_Shift(shift2);

        // Check if correct employees are assigned to shift with ID 2
        assertEquals(emp2, userList.get(0));
        assertNotEquals(emp1, userList.get(0));
    }

    @Test
    public void addUserToShift() throws Exception {
        // Stub
        when(shiftAssignmentRepo.findByAssignedTrue()).thenReturn(Arrays.asList(shiftAssign1));
        when(shiftRepo.findOne(shiftAssign1.getId())).thenReturn(shift1);

        // Attempt to add user to shift
        shiftController.addUserToShift(1, "1");

        // Get list of assigned shifts
        List<Shift> list = shiftController.getAllAssignedShifts();

        // Assert
        assertEquals(shift1, list.get(0));

        // Verify
        verify(shiftAssignmentRepo).findByAssignedTrue();
        verify(shiftRepo).findOne(shiftAssign1.getId());
    }

    @Test
    public void getUserIsAssignedForShift() throws Exception {
        // Stub
        when(shiftAssignmentRepo.findByShiftIdAndEmployeeId(shift1.getShiftId(), emp1.getEmployeeId())).thenReturn(Optional.of(shiftAssign1));

        // Assert
        assert(shiftController.getUserIsAssignedForShift(shift1.getShiftId(), emp1.getEmployeeId()));
    }

    @Test
    public void setUserAssignedForShift() throws Exception {
        // Stub
        when(shiftAssignmentRepo.findByShiftIdAndEmployeeId(nonAssignedShift.getShiftId(), emp1.getEmployeeId())).thenReturn(Optional.of(nonAssigned));

        // Set assigned shift
        shiftController.setUserAssignedForShift(nonAssignedShift.getShiftId(), emp1.getEmployeeId());

        // Assert
        assert(shiftController.getUserIsAssignedForShift(nonAssignedShift.getShiftId(), emp1.getEmployeeId()));


        // if else thing, should be false
        assert(!(shiftController.setUserAssignedForShift(100, 100)));

        // Verify
        verify(shiftAssignmentRepo, atLeastOnce()).findByShiftIdAndEmployeeId(nonAssignedShift.getShiftId(), emp1.getEmployeeId());
        verify(shiftAssignmentRepo, atLeastOnce()).findByShiftIdAndEmployeeId(100, 100);
    }

    @Test
    public void getAllShiftsForCurrentUser() throws Exception {
        // Fancy Spring Security stuff, untestable??
    }

    @Test
    public void getShiftAssignmentsForShift() throws Exception {
        // Stub
        when(shiftAssignmentRepo.findAll()).thenReturn(Arrays.asList(shiftAssign1));

        // Get list
        List<ShiftAssignment> list = shiftController.getShiftAssignmentsForShift(shift1.getShiftId());

        // Verify
        verify(shiftAssignmentRepo, atLeastOnce()).findAll();
    }

    @Test
    public void getEmployeesOnShift() throws Exception {
        // Stub
        when(shiftAssignmentRepo.findAll()).thenReturn(Arrays.asList(shiftAssign1));

        // Get number
        int test = shiftController.getEmployeesOnShift(shiftAssign1.getShiftId());

        // Assert
        assertEquals(1, test);

        // Verify
        verify(shiftAssignmentRepo).findAll();
    }

    @Test
    public void setUserIsResponsibleForShift() throws Exception {
        // Stub
        when(shiftRepo.findOne(shift1.getShiftId())).thenReturn(shift1);
        when(shiftRepo.save(shift1)).thenReturn(shift1);
        when(employeeRepo.findResponsibleForShift(shift1.getShiftId())).thenReturn(emp2);

        // Set responsible
        shiftController.setUserIsResponsibleForShift(shift1.getShiftId(), emp2.getEmployeeId());

        // Assert
        assertEquals(emp2, shiftController.getResponsibleUserForShift(shift1.getShiftId()));

        // Verify
        verify(shiftRepo).findOne(shift1.getShiftId());
        verify(shiftRepo).save(shift1);
        verify(employeeRepo).findResponsibleForShift(shift1.getShiftId());
    }

    @Test
    public void getResponsibleUserForShift() throws Exception {
        // Stub
        when(employeeRepo.findResponsibleForShift(shift1.getShiftId())).thenReturn(emp1);

        // Assert
        assertEquals(emp1, shiftController.getResponsibleUserForShift(shift1.getShiftId()));
    }

    @Test
    public void changeShiftFromUserToUser() throws Exception {
        // Stub
        when(shiftAssignmentRepo.findByShiftIdAndEmployeeId(shift1.getShiftId(), emp1.getEmployeeId())).thenReturn(Optional.of(shiftAssign1));
        when(shiftAssignmentRepo.save(shiftAssign1)).thenReturn(shiftAssign1);

        // Change shift
        shiftController.changeShiftFromUserToUser(shift1.getShiftId(), emp1.getEmployeeId(), emp2.getEmployeeId());

        // Verify
        verify(shiftAssignmentRepo).findByShiftIdAndEmployeeId(shift1.getShiftId(), emp1.getEmployeeId());
        verify(shiftAssignmentRepo).save(shiftAssign1);
    }

    @Test
    public void requestChangeForShift() throws Exception {

    }

    @Test
    public void getAvailableShifts() throws Exception {

    }

    @Test
    public void shiftIsAvailable() throws Exception {

    }
}