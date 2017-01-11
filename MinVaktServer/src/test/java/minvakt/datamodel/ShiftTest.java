package minvakt.datamodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

/**
 * Created by magnu on 09.01.2017.
 */
public class ShiftTest {

    LocalDate date;
    LocalTime from, to;
    Shift shift1, shift2;

    @Before
    public void setUp() throws Exception {
        date = LocalDate.parse("2016-01-10");
        from = LocalTime.parse("10:00");
        to = LocalTime.parse("16:00");

        shift1 = new Shift(date, from);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getDate() throws Exception {
        assertEquals(date, shift1.getStartDateTime());
    }

    @Test
    public void getStart() throws Exception {

    }

    @Test
    public void getEnd() throws Exception {

    }

    @Test
    public void isResponsible() throws Exception {

    }

    @Test
    public void testToString() throws Exception {

    }
}