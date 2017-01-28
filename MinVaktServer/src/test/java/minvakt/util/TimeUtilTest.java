package minvakt.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by magnu on 10.01.2017.
 */
public class TimeUtilTest {

    private TimeUtil timeUtil;

    @Before
    public void setUp() throws Exception {
        timeUtil = new TimeUtil();
    }

    @Test
    public void testConstructor() throws Exception {
        assertNotNull(timeUtil);
    }

}