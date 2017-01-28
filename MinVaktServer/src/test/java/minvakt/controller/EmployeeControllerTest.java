package minvakt.controller;


import minvakt.controller.data.CalenderResource;
import minvakt.controller.data.TwoStringsData;
import minvakt.datamodel.ShiftDetailed;
import minvakt.datamodel.tables.pojos.*;

import minvakt.repos.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerTest {
    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeRepository employeeRepo;

    @Mock
    private ShiftRepository shiftRepo;

    @Mock
    private ShiftAssignmentRepository shiftAssignmentRepo;

    @Mock
    private ChangeRequestRepository changeRequestRepository;

    @Mock
    private CategoryRepository catRepo;

    @Mock
    private UserDetailsManager userDetailsManager;

    @Mock
    private JooqRepository jooqRepo;


    @Captor
    ArgumentCaptor<User> captor;


    // Employees and Shifts for tests
    private Employee emp1, emp2;
    private Shift shift1, shift2,nonAssignedShift;
    private ShiftAssignment shiftAssign1, shiftAssign2,shiftAssign3;
    private EmployeeCategory empCat;

    @Before
    public void setUp() throws Exception {
        // Setup LocalDateTimes for shifts
        LocalDateTime fromTime1 = LocalDateTime.of(2017, 1, 17, 6, 0), toTime1 = LocalDateTime.of(2017, 1, 17, 14, 0), toTime2 = LocalDateTime.of(2019, 1, 17, 22, 0, 0);

        // Setup employees based on data from database script
        emp1 = new Employee(1, (short)1, "admin", "min", 12345678, "admin@minvakt.no", (short)100, "$2a$04$c7YTJkh8TVGsmCNNWW7pXu0f/dmy6E6TdsCgX7dnZlJQP7DBfuKjq", true, true);
        emp2 = new Employee(2, (short)2, "user", "er", 12345679, "user@minvakt.no", (short)100, "$2a$06$vMO32hhPzSrnvM8tRYwMZ.mzxkrrtXHtsYmRNxESKiClLPtZGRtF6", true, true);

        // Setup shifts based on data from database script
        shift1 = new Shift(1, 1, fromTime1, toTime1, (short)1, (short)5);
        shift2 = new Shift(2, 2, toTime1, toTime2, (short)1, (short)5);

        // Setup shift assignments based on data from database script
        shiftAssign1 = new ShiftAssignment(1, 1, 1, false, true, false, "");
        shiftAssign2 = new ShiftAssignment(2, 2, 2, true, true, false, "Diarrhea");
        shiftAssign3 = new ShiftAssignment(3, 1, 1, false, false, true, "");

        empCat = new EmployeeCategory((short)1,"Administrasjon",true,(short)2,true);
    }

    @Test
    public void getUsers() throws Exception {
        // Stubbing methods
        when(employeeRepo.findAll()).thenReturn(Arrays.asList(emp1, emp2));

        // Get all employees
        List<Employee> allEmp = (List<Employee>) employeeController.getUsers();

        // Assert
        assertEquals(emp1, allEmp.get(0));
        assertEquals(emp2, allEmp.get(1));
        assertNotEquals(emp2, allEmp.get(0));

        // Verify
        verify(employeeRepo, atLeastOnce()).findAll();
    }

    @Test
    public void getAsResource() throws Exception {
        // Stub
        when(employeeRepo.findAll()).thenReturn(Arrays.asList(emp1, emp2));

        // Get list
        List<CalenderResource> list = employeeController.getAsResource();

        // Assert
        assertNotNull(list);
        // Verify
        verify(employeeRepo, atLeastOnce()).findAll();
    }

    @Test
    public void addEmployee() throws Exception {

        // Stubbing method
        when(employeeRepo.saveAndFlush(emp2)).thenReturn(emp2);

        // Add employee
        String test = employeeController.addEmployee(emp2);
        System.out.println(test);
        // assert
        assertNotNull(test);

        verify(userDetailsManager).updateUser(captor.capture());
        assertEquals(captor.getValue().getUsername(),emp2.getEmail());
        // Verify method use
        verify(employeeRepo, atLeastOnce()).saveAndFlush(emp2);
    }

    @Test
    public void removeEmployee() throws Exception {
        // Stubbing method
        when(employeeRepo.save(emp2)).thenReturn(emp2);
        when(employeeRepo.findOne(emp2.getEmployeeId())).thenReturn(emp2);
        when(employeeRepo.findOne(emp1.getEmployeeId())).thenReturn(null);

        // remove employee

        Response response =employeeController.removeEmployee(emp2.getEmployeeId());
        Response test = Response.ok().build();

        //assert
        assertEquals(test.getStatus(),response.getStatus());

        // if else
        response =employeeController.removeEmployee(emp1.getEmployeeId());
        test = Response.noContent().build();

        //assert
        assertEquals(test.getStatus(),response.getStatus());

        // Verify method use
        verify(employeeRepo, atLeastOnce()).save(emp2);

    }

    @Test
    public void getUserById() throws Exception {
        // Stubbing method
        when(employeeRepo.findOne(emp1.getEmployeeId())).thenReturn(emp1);

        // Get employee by id
        Employee empTest = employeeController.getUserById(emp1.getEmployeeId());


        //assert
        assertEquals(emp1,empTest);

        // Verify method use
        verify(employeeRepo, atLeastOnce()).findOne(emp1.getEmployeeId());

    }

    @Test
    public void changeEmployee() throws Exception {
        Employee testEmp = new Employee();

        // Stubbing method
        when(employeeRepo.findOne(emp1.getEmployeeId())).thenReturn(emp1);


        // Get employee by id
        employeeController.changeEmployee(emp1.getEmployeeId(),emp2);
        employeeController.changeEmployee(emp1.getEmployeeId(),testEmp);



        //assert
        assertEquals(emp1.getPhone(),emp2.getPhone());
        assertEquals(emp1.getFirstName(),emp2.getFirstName());
        assertEquals(emp1.getLastName(),emp2.getLastName());


        // Verify method use
        verify(employeeRepo, atLeastOnce()).findOne(emp1.getEmployeeId());

    }


    @Test
    public void changePasswordForUser() throws Exception {
        // Setup string things
        TwoStringsData strings1 = new TwoStringsData(), strings2 = new TwoStringsData(), strings3 = new TwoStringsData();

        strings1.setString1("123");
        strings1.setString2("1234");

        strings2.setString1("Ost");
        strings2.setString2("Potet");

        strings3.setString1("Melk");
        strings3.setString2("Eple");

        // Stub
        doThrow(new AccessDeniedException("ok")).when(userDetailsManager).changePassword(strings2.getString1(), strings2.getString2());
        doThrow(new BadCredentialsException("ok")).when(userDetailsManager).changePassword(strings3.getString1(), strings3.getString2());


        // Setup ResponseEntities
        ResponseEntity response1 = ResponseEntity.ok().build();
        ResponseEntity response2 = ResponseEntity.status(403).build();
        ResponseEntity response3 = ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("Wrong password");

        // Run method and assert
        assertEquals(response1, employeeController.changePasswordForUser(strings1));
        assertEquals(response2, employeeController.changePasswordForUser(strings2));
        assertEquals(response3, employeeController.changePasswordForUser(strings3));
    }

    @Test
    public void getShiftsForUser() throws Exception {
        //stubbing methods
        when(shiftRepo.findByShiftEmployeeId(emp1.getEmployeeId())).thenReturn(Arrays.asList(shift1));

        //Getting list
        List<Shift> testList = employeeController.getShiftsForUser(emp1.getEmployeeId());

        //asserting
        assertEquals(shift1,testList.get(0));

        //verify
        verify(shiftRepo, atLeastOnce()).findByShiftEmployeeId(emp1.getEmployeeId());
    }

    @Test
    public void getAssignedShiftsForUser() throws Exception {
        //stubbing methods
        when(shiftRepo.findAssignedByShiftEmployeeId(emp1.getEmployeeId())).thenReturn(Arrays.asList(shift1));

        //Getting list
        List<Shift> testList = employeeController.getAssignedShiftsForUser(emp1.getEmployeeId());

        //asserting
        assertEquals(shift1,testList.get(0));

        //verify
        verify(shiftRepo, atLeastOnce()).findAssignedByShiftEmployeeId(emp1.getEmployeeId());
    }

    @Test
    public void getAvailableShiftsForUser() throws Exception {
        //stubbing methods
        when(shiftAssignmentRepo.findByAvailableTrue()).thenReturn(Arrays.asList(shiftAssign3));
        when(shiftRepo.findOne(shiftAssign3.getShiftId())).thenReturn(shift1);
        //Getting list
        Collection<Shift> testColl = employeeController.getAvailableShiftsForUser(emp1.getEmployeeId());
        Object[] testList = testColl.toArray();
        //asserting
        assertEquals(shift1, testList[0]);
        //verify
        verify(shiftAssignmentRepo, atLeastOnce()).findByAvailableTrue();
        verify(shiftRepo, atLeastOnce()).findOne(shiftAssign3.getShiftId());

    }

    @Test
    public void getShiftsForUserInRange() throws Exception {
        //stubbing methods
        when(shiftRepo.findByShiftEmployeeId(emp1.getEmployeeId())).thenReturn(Arrays.asList(shift1));

        //Getting list
        Collection<Shift> testColl = employeeController.getShiftsForUserInRange(emp1.getEmployeeId(),"2017-01-17","2017-01-17");
        Object[] testList = testColl.toArray();
        //asserting
        assertEquals(shift1,testList[0]);
        //verify
        verify(shiftRepo,atLeastOnce()).findByShiftEmployeeId(emp1.getEmployeeId());
    }

    @Test
    public void getCategory() throws Exception {
        //stubbing methods
        when(employeeRepo.findByEmail(emp1.getEmail())).thenReturn(emp1);
        when(catRepo.findOne(emp1.getCategoryId())).thenReturn(empCat);
        //Getting list
        EmployeeCategory catTest = employeeController.getCategory(emp1.getEmail());
        //asserting
        assertEquals(empCat,catTest);
        //verify
        verify(employeeRepo, atLeastOnce()).findByEmail(emp1.getEmail());
        verify(catRepo, atLeastOnce()).findOne(emp1.getCategoryId());

    }

    @Test
    public void getHoursThisWeek() throws Exception {
        Map<Integer,Duration> mockMap = new HashMap<>();

        Duration dur = Duration.ofHours(8L);
        mockMap.put(1,dur);

        LocalDate date = LocalDate.now().with(DayOfWeek.MONDAY);
        //stubbing methods
        when(jooqRepo.getHoursWorked(date,date.plus(6, ChronoUnit.DAYS))).thenReturn(mockMap);

        //Getting map
        Map<Integer,Duration> testMap = employeeController.getHoursThisWeek();
        //asserting
        assertEquals(mockMap,testMap);
        //verify
        verify(jooqRepo, atLeastOnce()).getHoursWorked(date,date.plus(6, ChronoUnit.DAYS));

    }

    @Test
    public void getHoursThisWeekForUser() throws Exception {
        //stubbing methods
        when(shiftRepo.findAssignedByShiftEmployeeId(emp1.getEmployeeId())).thenReturn(Arrays.asList(shift1));

        //Getting map
        int hours = employeeController.getHoursThisWeekForUser(emp1.getEmployeeId());
        //asserting
        assertEquals(8,hours);
        //verify
        verify(shiftRepo, atLeastOnce()).findAssignedByShiftEmployeeId(emp1.getEmployeeId());
    }

    @Test
    public void sendNewPassword() throws  Exception{
        // Stub
        when(employeeRepo.findByEmail(emp1.getEmail())).thenReturn(emp1);
        when(employeeRepo.findByEmail(emp2.getEmail())).thenReturn(null);

        // Run method and assert
        assert(employeeController.sendNewPassword(emp1.getEmail()));
        assertEquals(false, employeeController.sendNewPassword(emp2.getEmail()));
    }
}