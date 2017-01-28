package minvakt.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by magnu on 12.01.2017.
 */
public class InfoValidatorTest {

    @Test
    public void constructorTest() throws Exception {
        InfoValidator test = new InfoValidator();

        assertNotNull(test);
    }

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
        assertEquals(false, InfoValidator.checkEmailRequirements(null));
        assertEquals(false, InfoValidator.checkEmailRequirements("\0"));

        assertEquals(false, InfoValidator.checkEmailRequirements("email?"));
        assertEquals(false, InfoValidator.checkEmailRequirements("per-at-ost.no"));
        assertEquals(false, InfoValidator.checkEmailRequirements("norge krøllalfa norge dått no"));


        assert(InfoValidator.checkEmailRequirements("abc@xyz.com"));
        assert(InfoValidator.checkEmailRequirements("bob@gmail.com"));
        assert(InfoValidator.checkEmailRequirements("sindre@nsa.gov"));
        assert(InfoValidator.checkEmailRequirements("kent@tech.as"));
        assert(InfoValidator.checkEmailRequirements("artyom@fsb.ru"));
    }

}