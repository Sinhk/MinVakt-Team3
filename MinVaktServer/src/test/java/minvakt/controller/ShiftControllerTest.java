package minvakt.controller;

import minvakt.datamodel.tables.pojos.Employee;
import minvakt.datamodel.tables.pojos.Shift;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;

/**
 * Created by magnu on 11.01.2017.
 */
@DataJpaTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ShiftControllerTest {

    private EmbeddedDatabase db;

    @Autowired
    private ShiftController shiftController;

    Shift shift1, shift2;

    @Before
    public void setUp() throws Exception {
        // Setup embedded database
        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("db.sql")
                .build();

        // Setup LocalDateTimes for shifts
        LocalDateTime fromTime1 = LocalDateTime.of(2017, 1, 17, 6, 0), toTime1 = LocalDateTime.of(2017, 1, 17, 14, 0), toTime2 = LocalDateTime.of(2017, 1, 17, 22, 0, 0);

        // Setup shifts based on data from database script
        Shift shift1 = new Shift(1, 4, fromTime1, toTime1);
        Shift shift2 = new Shift(2, 5, toTime1, toTime2);
    }

    @After
    public void tearDown() throws Exception {
        // Shutdown Embedded database
        db.shutdown();
    }

    @Test
    public void getShifts() throws Exception {
         // Get all shifts
        List<Shift> allShifts = (List) shiftController.getShifts();

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
        // Get Shift for test
        Shift testShift = shiftController.getShift(2);

        // Compare
        assertEquals(shift2, testShift);
    }

    @Test
    public void addShift() throws Exception {

    }

    @Test
    public void addUserToShift() throws Exception {
        // Attempt to add user to shift
        shiftController.addUserToShift(1, "1");

    }
/*
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


    }
    */
}