package minvakt.datamodel.tables.pojos;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * Created by magnu on 25.01.2017.
 */
public class ShiftTest {
    Shift shift1, shift2 = new Shift();

    LocalDateTime fromDate, toDate;

    @Before
    public void setUp() throws Exception {
        fromDate = LocalDateTime.of(2017, 1, 1, 1, 1, 1);
        toDate = LocalDateTime.of(2017, 2, 2, 2, 2, 2);
        shift1 = new Shift(1, 1, fromDate, toDate, (short)1, (short)5);
    }

    @Test
    public void testConstructor() throws Exception {
        assertNotNull(new Shift(shift1));
        assertEquals(shift1, new Shift(shift1));
    }

    @Test
    public void getShiftId() throws Exception {
        assertEquals(1, (int)shift1.getShiftId());
        assertNotEquals(2, (int)shift1.getShiftId());
}

    @Test
    public void setShiftId() throws Exception {
        shift2.setShiftId(2);

        assertEquals(2, (int)shift2.getShiftId());
        assertNotEquals(1, (int)shift2.getShiftId());
    }

    @Test
    public void getResponsibleEmployeeId() throws Exception {
        assertEquals(1, (int)shift1.getResponsibleEmployeeId());
        assertNotEquals(2, (int)shift1.getResponsibleEmployeeId());
    }

    @Test
    public void setResponsibleEmployeeId() throws Exception {
        shift2.setResponsibleEmployeeId(2);

        assertEquals(2, (int)shift2.getResponsibleEmployeeId());
        assertNotEquals(1, (int)shift2.getResponsibleEmployeeId());
    }

    @Test
    public void getFromTime() throws Exception {
        assertEquals(fromDate, shift1.getFromTime());
        assertNotEquals(toDate, shift1.getFromTime());
    }

    @Test
    public void setFromTime() throws Exception {
        LocalDateTime testDate = LocalDateTime.of(2018,1,1,1,1,1);
        shift2.setFromTime(testDate);

        assertEquals(testDate, shift2.getFromTime());
        assertNotEquals(testDate, shift1.getToTime());
    }

    @Test
    public void getToTime() throws Exception {
        assertEquals(toDate, shift1.getToTime());
        assertNotEquals(toDate, shift1.getFromTime());
    }

    @Test
    public void setToTime() throws Exception {
        LocalDateTime testDate = LocalDateTime.of(2019,1,1,1,1,1);
        shift2.setToTime(testDate);

        assertEquals(testDate, shift2.getToTime());
        assertNotEquals(testDate, shift1.getFromTime());
    }

    @Test
    public void getDepartmentId() throws Exception {
        assertEquals(1, (int)shift1.getDepartmentId());
        assertNotEquals(2, (int)shift1.getDepartmentId());
    }

    @Test
    public void setDepartmentId() throws Exception {
        shift2.setDepartmentId((short)2);

        assertEquals(1, (int)shift1.getDepartmentId());
        assertNotEquals(2, (int)shift1.getDepartmentId());
    }

    @Test
    public void getRequiredEmployees() throws Exception {
        assertEquals((short) 5, (short)shift1.getRequiredEmployees());
        assertNotEquals((short) -1, (short)shift1.getRequiredEmployees());
    }

    @Test
    public void setRequiredEmployees() throws Exception {
        shift2.setRequiredEmployees((short)10);

        assertEquals(10, (int)shift2.getRequiredEmployees());
        assertNotEquals(-1, (int)shift2.getRequiredEmployees());
    }


    @Test
    public void testToString() throws Exception {
        StringBuilder sb = new StringBuilder("Shift (");

        sb.append(shift1.getShiftId());
        sb.append(", ").append(shift1.getResponsibleEmployeeId());
        sb.append(", ").append(shift1.getFromTime());
        sb.append(", ").append(shift1.getToTime());
        sb.append(", ").append(shift1.getDepartmentId());
        sb.append(", ").append(shift1.getRequiredEmployees());

        sb.append(")");

        assertEquals(sb.toString(), shift1.toString());
    }

    @Test
    public void testEquals() throws Exception {
        Shift testShift = new Shift(1, 1, fromDate, toDate, (short)1, (short)5);

        assertEquals(shift1, testShift);
    }

    @Test
    public void testHashCode() throws Exception {
        int result = shift1.getShiftId().hashCode();
        result = 31 * result + shift1.getFromTime().hashCode();
        result = 31 * result + shift1.getToTime().hashCode();

        assertEquals(result, shift1.hashCode());
    }
}