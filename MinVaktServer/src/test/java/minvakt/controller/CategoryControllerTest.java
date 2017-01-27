package minvakt.controller;

import minvakt.datamodel.tables.pojos.EmployeeCategory;
import minvakt.repos.CategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by magnu on 20.01.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class CategoryControllerTest {
    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryRepository categoryRepository;


    @Test
    public void getCategories() throws Exception {
        // Mock
        EmployeeCategory empCat = mock(EmployeeCategory.class);

        // Stub
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(empCat));

        // Get list
        List<EmployeeCategory> list = categoryController.getCategories();

        // Assert
        assertEquals(empCat, list.get(0));
        assertEquals(1, list.size());


        // Verify
        verify(categoryRepository).findAll();
    }

}