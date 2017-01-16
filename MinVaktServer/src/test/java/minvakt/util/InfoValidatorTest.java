package minvakt.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by magnu on 12.01.2017.
 */
public class InfoValidatorTest {
    @Test
    public void checkPasswordRequirements() throws Exception {
        assertEquals(false, InfoValidator.checkPasswordRequirements("Hei"));
        assertEquals(false, InfoValidator.checkPasswordRequirements("?"));
        assertEquals(false, InfoValidator.checkPasswordRequirements("\0"));
        assertEquals(false, InfoValidator.checkPasswordRequirements(null));
        assertEquals(false, InfoValidator.checkPasswordRequirements("!?!?!?!?"));

        assertEquals(true, InfoValidator.checkPasswordRequirements("HHei1!!!"));
        assertEquals(true, InfoValidator.checkPasswordRequirements("Hei123!!!!!! OST"));
    }

    @Test
    public void checkEmailRequirements() throws Exception {
        assertEquals(false, InfoValidator.checkEmailRequirements("email?"));
        assertEquals(false, InfoValidator.checkEmailRequirements("per-at-ost.no"));
        assertEquals(false, InfoValidator.checkEmailRequirements("norge krøllalfa norge dått no"));

        assertEquals(true, InfoValidator.checkEmailRequirements("abc@xyz.com"));
        assertEquals(true, InfoValidator.checkEmailRequirements("bob@gmail.com"));
        assertEquals(true, InfoValidator.checkEmailRequirements("sindre@nsa.gov"));
        assertEquals(true, InfoValidator.checkEmailRequirements("kent@tech.as"));
        assertEquals(true, InfoValidator.checkEmailRequirements("artyom@fsb.ru"));
    }

}