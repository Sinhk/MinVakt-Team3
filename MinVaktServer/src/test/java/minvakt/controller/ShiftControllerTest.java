package minvakt.controller;

import minvakt.datamodel.ShiftDetailed;
import minvakt.datamodel.tables.pojos.*;
import minvakt.repos.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
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

    @Mock
    private DepartmentRepository departmentRepo;

    private Shift shift1, shift2, nonAssignedShift;
    private ShiftDetailed detailed;
    private Employee emp1, emp2;
    private ShiftAssignment shiftAssign1, shiftAssign2, nonAssigned;
    private ChangeRequest change1, change2;
    private Department dep;

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

        // Setup department
        dep = new Department((short) 1, "Potet");
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
        when(shiftRepo.findOne(shift2.getShiftId())).thenReturn(shift2);
        when(jooqRepo.getShiftDetailed(shift2.getShiftId())).thenReturn(detailed);

        // Get Shift for test
        Shift testShift = shiftController.getShift(2, false);

        // Verify method use
        verify(shiftRepo, atLeastOnce()).findOne(2);

        // Assert
        assertEquals(shift2, testShift);


        // Get Detailed
        testShift = shiftController.getShift(2, true);

        // Assert
        assertEquals(detailed, testShift);

        // Verify
        verify(jooqRepo, atLeastOnce()).getShiftDetailed(shift2.getShiftId());
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
        shiftController.addUserToShift(1, 1, false, false);

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

        // Set responsible
        shiftController.setUserIsResponsibleForShift(shift1.getShiftId(), emp2.getEmployeeId());

        // Assert
        assertEquals(emp2.getEmployeeId(), shift1.getResponsibleEmployeeId());

        // Verify
        verify(shiftRepo, atLeastOnce()).findOne(shift1.getShiftId());
        verify(shiftRepo, atLeastOnce()).save(shift1);
    }

    @Test
    public void getResponsibleUserForShift() throws Exception {
        // Stub
        when(employeeRepo.findResponsibleForShift(shift1.getShiftId())).thenReturn(Optional.of(emp1));
        when(employeeRepo.findResponsibleForShift(shift2.getShiftId())).thenReturn(Optional.empty());

        // Assert
        assertEquals(ResponseEntity.ok(emp1), shiftController.getResponsibleUserForShift(shift1.getShiftId()));
        assertEquals(ResponseEntity.ok().build(), shiftController.getResponsibleUserForShift(shift2.getShiftId()));
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
        // Mock
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
        // Stub
        when(shiftAssignmentRepo.findByShiftIdAndEmployeeId(shift1.getShiftId(), emp1.getEmployeeId())).thenReturn(Optional.of(shiftAssign1));

        // Get Shift Assignment
        ShiftAssignment testAssign = shiftController.getShiftAssignmentForShiftAndUser(shift1.getShiftId(), emp1.getEmployeeId());

        // Assert
        assertEquals(shiftAssign1, testAssign);

        // Verify
        verify(shiftAssignmentRepo, atLeastOnce()).findByShiftIdAndEmployeeId(shift1.getShiftId(), emp1.getEmployeeId());
    }

    @Test
    public void changeUserAssignment() throws Exception {
        LocalDateTime fromTime = LocalDateTime.of(2017, 1, 17, 6, 0), toTime = LocalDateTime.of(2017, 1, 17, 14, 0);
        Shift testShift = new Shift(4, 1, fromTime, toTime, (short) 1, (short) 5);
        ShiftAssignment testAssign = new ShiftAssignment(4, 1, 1, null, null, null, null);

        // Stub
        when(shiftAssignmentRepo.findByShiftIdAndEmployeeId(shift1.getShiftId(), emp1.getEmployeeId())).thenReturn(Optional.of(shiftAssign1));
        when(shiftRepo.findOne(shift1.getShiftId())).thenReturn(shift1);
        when(shiftRepo.findOne(testShift.getShiftId())).thenReturn(testShift);
        when(shiftRepo.save(shift1)).thenReturn(shift1);
        when(shiftAssignmentRepo.save(shiftAssign1)).thenReturn(shiftAssign1);
        when(employeeRepo.findOne(emp1.getEmployeeId())).thenReturn(emp1);
        when(employeeRepo.findAll()).thenReturn(Arrays.asList(emp1, emp2));
        when(shiftAssignmentRepo.findByShiftIdAndEmployeeId(testShift.getShiftId(), emp1.getEmployeeId())).thenReturn(Optional.of(testAssign));

        // Call method
        shiftController.changeUserAssignment(shift1.getShiftId(),emp1.getEmployeeId(), true, true, true, true, "");
        shiftController.changeUserAssignment(testShift.getShiftId(), emp1.getEmployeeId(), true, true, true, false, "");

        // Verify
        verify(shiftAssignmentRepo, atLeastOnce()).findByShiftIdAndEmployeeId(shift1.getShiftId(), emp1.getEmployeeId());
        verify(shiftRepo, atLeastOnce()).findOne(shift1.getShiftId());
        verify(shiftRepo, atLeastOnce()).save(shift1);
        verify(shiftAssignmentRepo, atLeastOnce()).save(shiftAssign1);
        verify(employeeRepo, atLeastOnce()).findOne(emp1.getEmployeeId());
        verify(employeeRepo, atLeastOnce()).findAll();



        // If else stuff

        // Stub
        when(shiftAssignmentRepo.findByShiftIdAndEmployeeId(100, emp1.getEmployeeId())).thenReturn(Optional.empty());
        when(shiftRepo.findOne(100)).thenReturn(shift1);
        when(shiftRepo.save(shift1)).thenReturn(shift1);
        when(shiftAssignmentRepo.findByShiftIdAndEmployeeId(testShift.getShiftId(), emp2.getEmployeeId())).thenReturn(Optional.empty());


        // Call method
        shiftController.changeUserAssignment(100, emp1.getEmployeeId(), true, true, true, false, "");
        shiftController.changeUserAssignment(testShift.getShiftId(), emp2.getEmployeeId(), true, true, true, true, "");

        // Verify
        verify(shiftAssignmentRepo, atLeastOnce()).findByShiftIdAndEmployeeId(100, emp1.getEmployeeId());
        verify(shiftRepo, atLeastOnce()).findOne(100);
        verify(shiftRepo, atLeastOnce()).save(shift1);
    }

    @Test
    public void getAvailableForShift() throws Exception {
        // Stub
        when(shiftRepo.findOne(shift1.getShiftId())).thenReturn(shift1);
        when(jooqRepo.getCandidatesForShift(shift1)).thenReturn(Arrays.asList(emp1, emp2));

        // Get list
        List<Employee> list = shiftController.getAvailableForShift(shift1.getShiftId());

        // Assert
        assertEquals(emp1, list.get(0));
        assertEquals(emp2, list.get(1));
        assertNotEquals(emp1, list.get(1));

    }

    @Test
    public void getDepartmentofShift() throws Exception {
        // Stub
        when(shiftRepo.findOne(shift1.getShiftId())).thenReturn(shift1);
        when(departmentRepo.findOne(shift1.getDepartmentId())).thenReturn(dep);

        // Get department
        String testDep = shiftController.getDepartmentofShift(shift1.getShiftId());

        // Assert
        assertEquals(dep.getDepartmentName(), testDep);

        // Verify
        verify(shiftRepo, atLeastOnce()).findOne(shift1.getShiftId());
        verify(departmentRepo, atLeastOnce()).findOne(shift1.getDepartmentId());
    }

    @Test
    public void addManyShifts() throws Exception {
        LocalDateTime fromTime = LocalDateTime.of(2017, 1, 17, 6, 0);

        // Run method
        shiftController.addManyShifts(fromTime);
    }

    @Test
    public void removeShiftAssignment() throws Exception {
        shiftController.removeShiftAssignment(shiftAssign1.getId());

        // Verify
        verify(shiftAssignmentRepo, atLeastOnce()).delete(shiftAssign1.getId());
    }

    @Test
    public void getShiftsBetween() throws Exception {
        // Setup LocalDateTimes for shifts
        LocalDateTime fromTime = LocalDateTime.of(2017, 1, 17, 6, 0), toTime = LocalDateTime.of(2019, 1, 17, 22, 0, 0);

        // Stub
        when(jooqRepo.getShiftDetailed(fromTime.toLocalDate(), toTime.toLocalDate())).thenReturn((Iterable)Arrays.asList(detailed));
        when(shiftRepo.findBetweenDates(fromTime.toLocalDate().atStartOfDay(), toTime.toLocalDate().atStartOfDay())).thenReturn(Arrays.asList(shift1, shift2, nonAssignedShift));

        // Get Shifts
        List<ShiftDetailed> listDetail = (List<ShiftDetailed>) shiftController.getShiftsBetween(fromTime.toLocalDate(), toTime.toLocalDate(), true);
        List<Shift> listShift = (List<Shift>) shiftController.getShiftsBetween(fromTime.toLocalDate(), toTime.toLocalDate(), false);

        // Assert
        assertEquals(detailed, listDetail.get(0));

        assertEquals(shift1, listShift.get(0));
        assertEquals(shift2, listShift.get(1));
        assertEquals(nonAssignedShift, listShift.get(2));


        // Verify
        verify(jooqRepo, atLeastOnce()).getShiftDetailed(fromTime.toLocalDate(), toTime.toLocalDate());
        verify(shiftRepo, atLeastOnce()).findBetweenDates(fromTime.toLocalDate().atStartOfDay(), toTime.toLocalDate().atStartOfDay());
    }

    @Test
    public void addWish() throws Exception {
        // Mock
        HttpServletRequest request = mock(HttpServletRequest.class);
        java.security.Principal principal = mock(java.security.Principal.class);

        // Stub
        when(request.getUserPrincipal()).thenReturn(principal);
        when(principal.getName()).thenReturn("admin@minvakt.no");
        when(employeeRepo.findByEmail(principal.getName())).thenReturn(emp1);

        // Run method
        ResponseEntity<?> response = shiftController.addWish(request, shift2.getShiftId());

        // Assert
        assertEquals(ResponseEntity.ok().build(), response);


        // Verify
        verify(request, atLeastOnce()).getUserPrincipal();
        verify(principal, atLeastOnce()).getName();
        verify(employeeRepo, atLeastOnce()).findByEmail(principal.getName());
    }

    @Test
    public void getAllShiftAssignments() throws Exception {
        // Stub
        when(shiftAssignmentRepo.findAll()).thenReturn(Arrays.asList(shiftAssign1, shiftAssign2, nonAssigned));

        // Get list of Shift Assignments
        List<ShiftAssignment> list = shiftController.getAllShiftAssignments();

        // Assert
        assertNotNull(list);
        assertEquals(shiftAssign1, list.get(0));
        assertEquals(shiftAssign2, list.get(1));
        assertEquals(nonAssigned, list.get(2));
        assertEquals(3, list.size());

        // Verify
        verify(shiftAssignmentRepo).findAll();
    }

    @Test
    public void getShiftAssignmentByShiftAssignmentId() throws Exception {
        // Stub
        when(shiftAssignmentRepo.findOne(shiftAssign1.getId())).thenReturn(shiftAssign1);

        // Get Shift Assignment
        ShiftAssignment test = shiftController.getShiftAssignmentByShiftAssignmentId(shiftAssign1.getId());

        // Verify
        verify(shiftAssignmentRepo).findOne(shiftAssign1.getId());

    }

    @Test
    public void getShiftAssignmentsForUser() throws Exception {
        // Stub
        when(shiftAssignmentRepo.findByEmployeeId(emp1.getEmployeeId())).thenReturn(Arrays.asList(shiftAssign1));

        // Get Shift Assignments
        List<ShiftAssignment> list = shiftController.getShiftAssignmentsForUser(emp1.getEmployeeId());

        // Assert
        assertEquals(shiftAssign1, list.get(0));

        // Verify
        verify(shiftAssignmentRepo, atLeastOnce()).findByEmployeeId(emp1.getEmployeeId());

    }

    @Test
    public void getAmountOnShift() throws Exception {
        // Mock
        MissingPerShiftCategory missing = mock(MissingPerShiftCategory.class);

        // Stub
        when(jooqRepo.getMissingForShift(shift1.getShiftId())).thenReturn(Arrays.asList(missing));

        // Get list
        List<MissingPerShiftCategory> list = shiftController.getAmountOnShift(shift1.getShiftId());

        // Assert
        assertEquals(missing, list.get(0));


        // Verify
        verify(jooqRepo, atLeastOnce()).getMissingForShift(shift1.getShiftId());
    }

    @Test
    public void getTotalHoursForMonth() throws Exception {
        LocalDate startOfMonth = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        Map<Integer, Duration> map = new HashMap<>();

        map.put(1, Duration.ofSeconds(28800));         // 8 hours in seconds

        // Stub
        when(jooqRepo.getHoursWorked(startOfMonth, startOfMonth.plusDays(startOfMonth.getMonth().length(LocalDate.now().isLeapYear())))).thenReturn(map);

        // Get map
        Map<Integer, Long> testMap = shiftController.getTotalHoursForMonth(1);

        // Assert
        assertEquals(8, (long)testMap.get(1));
    }

    @Test
    public void sendTotalHours() throws Exception {
        assert(shiftController.sendTotalHours("123@123.123", "hei"));
    }
}