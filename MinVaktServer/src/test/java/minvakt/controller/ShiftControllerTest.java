package minvakt.controller;

import minvakt.datamodel.ShiftDetailed;
import minvakt.datamodel.tables.pojos.ChangeRequest;
import minvakt.datamodel.tables.pojos.Employee;
import minvakt.datamodel.tables.pojos.Shift;
import minvakt.datamodel.tables.pojos.ShiftAssignment;
import minvakt.repos.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
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

    @Mock
    private JooqRepository jooqRepo;

    private Shift shift1, shift2, nonAssignedShift;
    private ShiftDetailed detailed;
    private Employee emp1, emp2;
    private ShiftAssignment shiftAssign1, shiftAssign2, nonAssigned;
    private ChangeRequest change1, change2;

    @Before
    public void setUp() throws Exception {
        // Setup LocalDateTimes for shifts
        LocalDateTime fromTime1 = LocalDateTime.of(2017, 1, 17, 6, 0), toTime1 = LocalDateTime.of(2018, 1, 17, 14, 0), toTime2 = LocalDateTime.of(2019, 1, 17, 22, 0, 0);

        // Setup shifts based on data from database script
        shift1 = new Shift(1, 1, fromTime1, toTime1, (short)1, (short)5);
        shift2 = new Shift(2, 2, toTime1, toTime2, (short)1, (short)5);
        nonAssignedShift = new Shift(3, 2, fromTime1, toTime1, (short)1, (short)5);

        // Setup DetailedShift
        detailed = new ShiftDetailed(4, 1, fromTime1, toTime1,(short) 1, (short) 5);

        // Setup employees based on data from database script
        emp1 = new Employee(1, (short)1, "admin", "", 12345678, "admin@minvakt.no", (short)100, "$2a$04$c7YTJkh8TVGsmCNNWW7pXu0f/dmy6E6TdsCgX7dnZlJQP7DBfuKjq", true, true);
        emp2 = new Employee(2, (short)2, "user", "", 12345679, "user@minvakt.no", (short)100, "$2a$06$vMO32hhPzSrnvM8tRYwMZ.mzxkrrtXHtsYmRNxESKiClLPtZGRtF6", true, true);

        // Setup shift assignments based on data from database script
        shiftAssign1 = new ShiftAssignment(1, 1, 1, false, true, false, "");
        shiftAssign2 = new ShiftAssignment(2, 2, 2, true, true, false, "Diarrhea");
        nonAssigned = new ShiftAssignment(3, 3, 2, false, false, true , "");

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
        when(jooqRepo.getShiftDetailed()).thenReturn(Arrays.asList(detailed));

        // Get all shifts
        List<Shift> allShifts = (List<Shift>) shiftController.getShifts(false);

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

        // Get detailed shift
        List<ShiftDetailed> list2 = (List<ShiftDetailed>) shiftController.getShifts(true);

        // Test detailed shift
        assertEquals(detailed, list2.get(0));

        // Verify
        verify(shiftRepo, atLeastOnce()).findAll();
        verify(jooqRepo, atLeastOnce()).getShiftDetailed();
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

        // Get list of assigned shifts
        List<ShiftAssignment> list = shiftController.getAllAssignedShifts();

        // Verify method use
        verify(shiftAssignmentRepo, atLeastOnce()).findByAssignedTrue();

        // Assert stuff
        assertEquals(shiftAssign1, list.get(0));
        assertEquals(shiftAssign2, list.get(1));
        assertEquals(2, list.size());
    }

    @Test
    public void getAllNonAssignedShifts() throws Exception {
        // Stubbing methods
        when(shiftAssignmentRepo.findByAssignedFalse()).thenReturn(Arrays.asList(nonAssigned));

        // Get list of assigned shifts
        List<ShiftAssignment> list = shiftController.getAllNonAssignedShifts();

        // Verify method use
        verify(shiftAssignmentRepo, atLeastOnce()).findByAssignedFalse();

        // Assert
        assertEquals(nonAssigned, list.get(0));
    }

    @Test
    public void getUsersForShift() throws Exception {
        // Stubbing methods
        when(employeeRepo.findByShiftAssignments_Shift(shift2.getShiftId())).thenReturn(Arrays.asList(emp2));

        // Get users assigned to shift with ID 2
        List<Employee> userList = shiftController.getUsersForShift(2);

        // Verify method use
        verify(employeeRepo, atLeastOnce()).findByShiftAssignments_Shift(shift2.getShiftId());

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
        shiftController.addUserToShift(1, 1, false, true);

        // Get list of assigned shifts
        List<ShiftAssignment> list = shiftController.getAllAssignedShifts();

        // Assert
        assertEquals(shiftAssign1, list.get(0));

        // Verify
        verify(shiftAssignmentRepo, atLeastOnce()).findByAssignedTrue();
        verify(shiftRepo, atLeastOnce()).findOne(shiftAssign1.getId());
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
        when(shiftAssignmentRepo.findByShiftId(shift1.getShiftId())).thenReturn(Arrays.asList(shiftAssign1));

        // Get list
        List<ShiftAssignment> list = shiftController.getShiftAssignmentsForShift(shift1.getShiftId());

        // Verify
        verify(shiftAssignmentRepo, atLeastOnce()).findByShiftId(shift1.getShiftId());
    }

    /*  Method removed???
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
    */

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
        verify(shiftRepo, atLeastOnce()).findOne(shift1.getShiftId());
        verify(shiftRepo, atLeastOnce()).save(shift1);
        verify(employeeRepo, atLeastOnce()).findResponsibleForShift(shift1.getShiftId());
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
        verify(shiftAssignmentRepo, atLeastOnce()).findByShiftIdAndEmployeeId(shift1.getShiftId(), emp1.getEmployeeId());
        verify(shiftAssignmentRepo, atLeastOnce()).save(shiftAssign1);
    }


    @Test
    public void getAvailableShifts() throws Exception {
        HttpServletRequest request1 = mock(HttpServletRequest.class);
        HttpServletRequest request2 = mock(HttpServletRequest.class);
        java.security.Principal principal = mock(java.security.Principal.class);

        // Stub
        when(jooqRepo.getAvailableShiftsForCategory(1)).thenReturn(new LinkedList<Shift>(Arrays.asList(shift1, shift2)));
        when(jooqRepo.getAvailableShifts()).thenReturn(Arrays.asList(shift2));
        when(employeeRepo.findByEmail("admin@minvakt.no")).thenReturn(emp1);
        when(request1.isUserInRole("ROLE_ADMIN")).thenReturn(true);
        when(request2.isUserInRole("ROLE_ADMIN")).thenReturn(false);
        when(shiftRepo.findByShiftEmployeeId(emp1.getEmployeeId())).thenReturn(Arrays.asList(shift2));
        when(principal.getName()).thenReturn("admin@minvakt.no");
        when(request2.getUserPrincipal()).thenReturn(principal);

        // Get shifts
        List<Shift> list1 = shiftController.getAvailableShifts(request1, 1);
        List<Shift> list2 = shiftController.getAvailableShifts(request1, -1);
        List<Shift> list3 = shiftController.getAvailableShifts(request2, -1);

        // Assert list1
        assertEquals(shift1, list1.get(0));

        // Assert list2
        assertEquals(shift2, list2.get(0));

        // Assert list3
        assertEquals(shift1, list3.get(0));


        // Verify
        verify(jooqRepo, atLeastOnce()).getAvailableShiftsForCategory(1);
        verify(employeeRepo, atLeastOnce()).findByEmail("admin@minvakt.no");
        verify(request1, atLeastOnce()).isUserInRole("ROLE_ADMIN");
        verify(request2, atLeastOnce()).isUserInRole("ROLE_ADMIN");
        verify(shiftRepo, atLeastOnce()).findByShiftEmployeeId(emp1.getEmployeeId());
        verify(principal, atLeastOnce()).getName();
        verify(request2, atLeastOnce()).getUserPrincipal();
    }

    @Test
    public void shiftIsAvailable() throws Exception {
        // Stub
        when(shiftAssignmentRepo.findByShiftId(shift1.getShiftId())).thenReturn(Arrays.asList(shiftAssign1));
        when(shiftAssignmentRepo.getOne(shift1.getShiftId())).thenReturn(shiftAssign1);

        // Run method and assert
        assert(shiftController.shiftIsAvailable(shift1.getShiftId()));
    }

    @Test
    public void getShiftAssignmentForShiftAndUser() throws Exception {

    }

    @Test
    public void changeUserAssignment() throws Exception {

    }

    @Test
    public void getAvailableForShift() throws Exception {

    }

    @Test
    public void getDepartmentofShift() throws Exception {

    }

    @Test
    public void removeShiftAssignment() throws Exception {

    }
}