package minvakt.controller;

import minvakt.repos.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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


    @Before
    public void setUp() throws Exception {

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