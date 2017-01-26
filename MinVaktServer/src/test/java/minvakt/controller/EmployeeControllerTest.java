package minvakt.controller;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * Created by magnu on 16.01.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerTest {

    // Mock controller
    @InjectMocks
    private EmployeeController employeeController;

    // Employees and Shifts for tests
    /*
    private Employee emp1, emp2;
    private Shift shift1, shift2;
    */
    @Before
    public void setUp() throws Exception {
        /*
        // Initialization of Employee objects
        emp1 = new Employee("Bob", "Bobsen", "bob@bob.bob", 12345678, 100);
        emp2 = new Employee("Per", "Persson", "per@sverige.se", 11223344, 50);

        // Assign employeeID to test-employees (needed for tests)
        emp1.setEmployeeId(1);
        emp2.setEmployeeId(2);


        // LocalDateTime objects for initializing Shift objects
        LocalDateTime date1 = LocalDateTime.of(2017, 1, 10, 10, 0, 0);
        LocalDateTime date2 = LocalDateTime.of(2017, 1, 10, 14, 0, 0);

        shift1 = new Shift(date1, date2);
        shift2 = new Shift(date1.toLocalDate(), PredeterminedIntervals.DAYTIME);

        // Assign ID to shifts, needed for tests
        shift1.setShiftId(1);
        shift2.setShiftId(2);
        */
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getUsers() throws Exception {

    }

    @Test
    public void addEmployee() throws Exception {

    }

    @Test
    public void removeEmployee() throws Exception {

    }

    @Test
    public void findUser() throws Exception {

    }

    @Test
    public void changePasswordForUser() throws Exception {

    }

    @Test
    public void getShiftsForUser() throws Exception {

    }

    @Test
    public void getShiftsForUserInRange() throws Exception {

    }

    @Test
    public void addShiftToUser() throws Exception {

    }

    @Test
    public void removeShiftFromUser() throws Exception {

    }

}