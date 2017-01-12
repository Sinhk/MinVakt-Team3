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
        assertEquals(true, InfoValidator.checkPasswordRequirements("HHei1!!!"));
        assertEquals(true, InfoValidator.checkPasswordRequirements("Hei123!!!!!! OST"));
        assertEquals(false, InfoValidator.checkPasswordRequirements("?"));
        assertEquals(false, InfoValidator.checkPasswordRequirements("\0"));
        assertEquals(false, InfoValidator.checkPasswordRequirements(null));
        assertEquals(false, InfoValidator.checkPasswordRequirements("!?!?!?!?"));
    }

    @Test
    public void checkEmailRequirements() throws Exception {

    }

}