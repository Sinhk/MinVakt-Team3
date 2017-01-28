package minvakt.util;

import minvakt.datamodel.tables.pojos.Employee;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.mail.MessagingException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by magnu on 28.01.2017.
 */
public class SendMailTLSTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    SendMailTLS mail;
    Employee emp1, emp2;

    @Before
    public void setUp() throws Exception {
        mail = new SendMailTLS();

        emp1 = new Employee(1, (short)1, "admin", "", 12345678, "admin@minvakt.no", (short)100, "$2a$04$c7YTJkh8TVGsmCNNWW7pXu0f/dmy6E6TdsCgX7dnZlJQP7DBfuKjq", true, true);
        emp2 = new Employee(2, (short)2, "user", "", 12345679, null, (short)100, "$2a$06$vMO32hhPzSrnvM8tRYwMZ.mzxkrrtXHtsYmRNxESKiClLPtZGRtF6", true, true);

    }

    @Test
    public void sendFreeShiftToGroup() throws Exception {
        String test = "123.no";

        List<Employee> list = Arrays.asList(emp1, emp2);

        exception.expect(NullPointerException.class);
        mail.sendFreeShiftToGroup(test, list);
    }

}