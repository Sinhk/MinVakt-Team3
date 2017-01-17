package minvakt.controller.data;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by magnu on 17.01.2017.
 */
public class TwoIntDataTest {

    // Create objects for use in tests
    TwoIntData testObject1 = new TwoIntData(), testObject2 = new TwoIntData();


    @Before
    public void setUp() throws Exception {
        testObject1.setInt1(100);
        testObject1.setInt2(200);
    }

    @Test
    public void notNull() throws Exception {
        assertNotNull(testObject1);
        assertNotNull(testObject2);
    }

    @Test
    public void getInt1() throws Exception {
        assertEquals(100, testObject1.getInt1());

        assertNotEquals(200, testObject1.getInt1());
        assertNotEquals(0, testObject1.getInt1());
    }

    @Test
    public void setInt1() throws Exception {
        int expected = 123, unexpected = -4;

        testObject2.setInt1(expected);

        assertEquals(expected, testObject2.getInt1());
        assertNotEquals(unexpected, testObject2.getInt1());
    }

    @Test
    public void getInt2() throws Exception {
        assertEquals(200, testObject1.getInt2());

        assertNotEquals(100, testObject1.getInt2());
        assertNotEquals(0, testObject1.getInt2());
    }

    @Test
    public void setInt2() throws Exception {
        int expected = 123, unexpected = -4;

        testObject2.setInt2(expected);

        assertEquals(expected, testObject2.getInt2());
        assertNotEquals(unexpected, testObject2.getInt2());
    }

}