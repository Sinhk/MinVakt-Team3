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
import org.springframework.http.ResponseEntity;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        Duration duration = Duration.ofHours(8);
        MissingPerShiftCategory missing = mock(MissingPerShiftCategory.class);


        // Stub
        when(changeRequestRepository.count()).thenReturn(2L);
        when(changeRequestRepository.findAll()).thenReturn(Arrays.asList(change1, change2));
        when(shiftRepo.findOne(change1.getShiftId())).thenReturn(shift1);
        when(shiftRepo.findOne(change2.getShiftId())).thenReturn(shift2);
        when(employeeRepo.findOne(change1.getNewEmployeeId())).thenReturn(emp2);
        when(employeeRepo.findOne(change2.getNewEmployeeId())).thenReturn(emp1);
        when(employeeRepo.findOne(change1.getOldEmployeeId())).thenReturn(emp1);
        when(employeeRepo.findOne(change2.getOldEmployeeId())).thenReturn(emp2);
        when(shiftAssignmentRepo.findByShiftIdAndEmployeeId(shift1.getShiftId(), emp2.getEmployeeId())).thenReturn(Optional.empty());
        when(shiftAssignmentRepo.findByShiftIdAndEmployeeId(shift2.getShiftId(), emp1.getEmployeeId())).thenReturn(Optional.of(shiftAssign2));
        when(jooqRepository.getHoursWorked(change1.getNewEmployeeId(), shift1.getFromTime().get(weekFields.weekOfWeekBasedYear()), shift1.getFromTime().getYear())).thenReturn(duration);
        //when(jooqRepository.getHoursWorked(change2.getNewEmployeeId(), shift2.getFromTime().get(weekFields.weekOfWeekBasedYear()), shift2.getFromTime().getYear())).thenReturn(duration);
        when(jooqRepository.getMissingForShift(shift1.getShiftId())).thenReturn(Arrays.asList(missing));
        //when(catRepo.findOne((short)1)).thenReturn(new EmployeeCategory((short)1, "admin", true, (short)5, true));
        //when(catRepo.findOne((short)2)).thenReturn(new EmployeeCategory((short)2, "Sykepleier", true, (short)5, true));

        // Get count - (if (count)) test
        ResponseEntity<?> response = changeRequestController.getChangeRequests(true);

        // Assert
        assertEquals(ResponseEntity.ok(2L), response);


        // Get on (return ResponseEntity.ok(requests))
        response = changeRequestController.getChangeRequests(false);

        // Assert
        assertEquals(ResponseEntity.ok(Arrays.asList(change1, change2)), response);


        // Verify
        verify(changeRequestRepository, atLeastOnce()).count();
        verify(changeRequestRepository, atLeastOnce()).findAll();
        verify(shiftRepo, atLeastOnce()).findOne(change1.getShiftId());
        verify(shiftRepo, atLeastOnce()).findOne(change2.getShiftId());
        verify(employeeRepo, atLeastOnce()).findOne(change1.getNewEmployeeId());
        verify(employeeRepo, atLeastOnce()).findOne(change2.getNewEmployeeId());
        verify(employeeRepo, atLeastOnce()).findOne(change1.getOldEmployeeId());
        verify(employeeRepo, atLeastOnce()).findOne(change2.getOldEmployeeId());
        verify(shiftAssignmentRepo, atLeastOnce()).findByShiftIdAndEmployeeId(shift1.getShiftId(), emp2.getEmployeeId());
        verify(shiftAssignmentRepo, atLeastOnce()).findByShiftIdAndEmployeeId(shift2.getShiftId(), emp1.getEmployeeId());
        //verify(jooqRepository, atLeastOnce()).getHoursWorked(change1.getNewEmployeeId(), shift1.getFromTime().get(weekFields.weekOfWeekBasedYear()), shift1.getFromTime().getYear());
        //verify(jooqRepository, atLeastOnce()).getHoursWorked(change2.getNewEmployeeId(), shift2.getFromTime().get(weekFields.weekOfWeekBasedYear()), shift2.getFromTime().getYear());
        verify(jooqRepository, atLeastOnce()).getMissingForShift(shift1.getShiftId());
        //verify(catRepo, atLeastOnce()).findOne((short)1);
        //verify(catRepo, atLeastOnce()).findOne((short)2);
    }
    @Test
    public void requestChangeForShift() throws Exception {
        // Stub
        when(employeeRepo.findOne(emp1.getEmployeeId())).thenReturn(emp1);
        when(employeeRepo.findOne(emp2.getEmployeeId())).thenReturn(emp2);
        when(shiftRepo.findOne(shift1.getShiftId())).thenReturn(shift1);
        when(employeeRepo.findAll()).thenReturn(Arrays.asList(emp1, emp2));

        // Run method
        changeRequestController.requestChangeForShift(shift1.getShiftId(), emp1.getEmployeeId(), emp2.getEmployeeId());

        // Verify
        verify(employeeRepo, atLeastOnce()).findOne(emp1.getEmployeeId());
        verify(employeeRepo, atLeastOnce()).findOne(emp2.getEmployeeId());
        verify(shiftRepo, atLeastOnce()).findOne(shift1.getShiftId());
        verify(employeeRepo, atLeastOnce()).findAll();
    }

    @Test
    public void acceptChangeRequest() throws Exception {
        // Stub
        when(changeRequestRepository.findOne(change1.getRequestId())).thenReturn(change1);
        when(employeeRepo.findOne(emp1.getEmployeeId())).thenReturn(emp1);
        when(employeeRepo.findOne(emp2.getEmployeeId())).thenReturn(emp2);
        when(shiftRepo.findOne(shift1.getShiftId())).thenReturn(shift1);

        // Run method
        changeRequestController.acceptChangeRequest(change1.getRequestId());

        // Verify
        verify(changeRequestRepository, atLeastOnce()).findOne(change1.getRequestId());
        verify(employeeRepo, atLeastOnce()).findOne(emp1.getEmployeeId());
        verify(employeeRepo, atLeastOnce()).findOne(emp1.getEmployeeId());
        verify(shiftRepo, atLeastOnce()).findOne(shift1.getShiftId());
    }

    @Test
    public void declineChangeRequest() throws Exception {
        // Stub
        when(changeRequestRepository.findOne(change1.getRequestId())).thenReturn(change1);
        when(employeeRepo.findOne(emp1.getEmployeeId())).thenReturn(emp1);
        when(employeeRepo.findOne(emp2.getEmployeeId())).thenReturn(emp2);
        when(shiftRepo.findOne(shift1.getShiftId())).thenReturn(shift1);


        // Run method
        changeRequestController.declineChangeRequest(change1.getRequestId());

        // Verify
        verify(changeRequestRepository, atLeastOnce()).findOne(change1.getRequestId());
        verify(employeeRepo, atLeastOnce()).findOne(emp1.getEmployeeId());
        verify(employeeRepo, atLeastOnce()).findOne(emp2.getEmployeeId());
    }

    @Test
    public void checkIsOkChangeRequest() throws Exception {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        Duration duration = Duration.ofHours(8);
        List<MissingPerShiftCategory> missingList = Arrays.asList(mock(MissingPerShiftCategory.class), mock(MissingPerShiftCategory.class));

        // Stub
        when(shiftAssignmentRepo.findByShiftIdAndEmployeeId(shift1.getShiftId(), emp1.getEmployeeId())).thenReturn(Optional.of(shiftAssign1));
        when(jooqRepository.getHoursWorked(change1.getNewEmployeeId(), shift1.getFromTime().get(weekFields.weekOfWeekBasedYear()), shift1.getFromTime().getYear())).thenReturn(duration);
        when(employeeRepo.findOne(emp1.getEmployeeId())).thenReturn(emp1);
        when(employeeRepo.findOne(emp2.getEmployeeId())).thenReturn(emp2);
        when(missingList.get(0).getCategoryId()).thenReturn((short) 1);
        when(jooqRepository.getMissingForShift(shift1.getShiftId())).thenReturn(missingList);

    }
}