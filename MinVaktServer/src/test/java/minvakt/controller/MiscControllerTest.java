package minvakt.controller;

import minvakt.datamodel.tables.pojos.Department;
import minvakt.repos.DepartmentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by magnu on 28.01.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class MiscControllerTest {

    @InjectMocks
    MiscController miscController;

    @Mock
    DepartmentRepository depRepo;

    Department dep1, dep2;

    @Before
    public void setUp() throws Exception {
        // Setup department for test
        dep1 = new Department();
        dep2 = new Department((short)1, "Potetavdelingen");
    }

    @Test
    public void getDepartments() throws Exception {
        // Stub
        when(depRepo.findAll()).thenReturn(Arrays.asList(dep1, dep2));

        // Get list
        List<Department> list = miscController.getDepartments();

        // Assert
        assertEquals(dep1, list.get(0));
        assertEquals(dep2, list.get(1));

    }

}