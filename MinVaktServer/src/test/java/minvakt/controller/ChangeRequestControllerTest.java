package minvakt.controller;

import minvakt.datamodel.ShiftDetailed;
import minvakt.datamodel.tables.pojos.*;
import minvakt.repos.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * Created by magnu on 27.01.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ChangeRequestControllerTest {

    @InjectMocks
    private ChangeRequestController changeRequestController;


    @Mock
    private ShiftRepository shiftRepo;

    @Mock
    private EmployeeRepository employeeRepo;

    @Mock
    private ShiftAssignmentRepository shiftAssignmentRepo;

    @Mock
    private ChangeRequestRepository changeRequestRepository;

    @Mock
    private JooqRepository jooqRepository;

    @Mock
    private CategoryRepository catRepo;

    @Mock
    private ShiftController shiftController;


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

    @Test
    public void getChangeRequests() throws Exception {

    }

    @Test
    public void requestChangeForShift() throws Exception {

    }

    @Test
    public void acceptChangeRequest() throws Exception {

    }

    @Test
    public void declineChangeRequest() throws Exception {

    }

}