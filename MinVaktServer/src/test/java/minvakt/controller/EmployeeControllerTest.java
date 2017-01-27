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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by magnu on 16.01.2017.
 */
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



    // Employees and Shifts for tests
    private Employee emp1, emp2;
    private Shift shift1, shift2,nonAssignedShift;


    @Before
    public void setUp() throws Exception {

        // Setup employees based on data from database script
        emp1 = new Employee(1, (short)1, "admin", "", 12345678, "admin@minvakt.no", (short)100, "$2a$04$c7YTJkh8TVGsmCNNWW7pXu0f/dmy6E6TdsCgX7dnZlJQP7DBfuKjq", true, true);
        emp2 = new Employee(2, (short)2, "user", "", 12345679, "user@minvakt.no", (short)100, "$2a$06$vMO32hhPzSrnvM8tRYwMZ.mzxkrrtXHtsYmRNxESKiClLPtZGRtF6", true, true);

        //UserDetailsManager

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getCurrentUser() throws Exception {
    //    HttpServletRequest request = mock(HttpServletRequest.class);
      //  java.security.Principal principal = mock(java.security.Principal.class);

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

    }

    @Test //TODO Sindre fix
    public void addEmployee() throws Exception {
 /*
        // Stubbing method
        when(employeeRepo.saveAndFlush(emp2)).thenReturn(emp2);

        // Add employee
        String test = employeeController.addEmployee(emp2);
        System.out.println(test);
        // assert
        assertNotNull(test);

        // Verify method use
        verify(employeeRepo, atLeastOnce()).save(emp2);
*/
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

    }

    @Test
    public void changeEmployee() throws Exception {

    }


    @Test
    public void changePasswordForUser() throws Exception {

    }

    @Test
    public void getShiftsForUser() throws Exception {

    }

    @Test
    public void getAssignedShiftsForUser() throws Exception {

    }

    @Test
    public void getAvailableShiftsForUser() throws Exception {

    }

    @Test
    public void getShiftsForUserInRange() throws Exception {

    }

    @Test
    public void getEmployeesThatCanBeResponsible() throws Exception {

    }

    @Test
    public void getCategory() throws Exception {

    }

    @Test
    public void getHoursThisWeekForUser() throws Exception {

    }

    @Test
    public void removeShiftFromUser() throws Exception {

    }

    @Test
    public void sendNewPassword() throws  Exception{

    }



}