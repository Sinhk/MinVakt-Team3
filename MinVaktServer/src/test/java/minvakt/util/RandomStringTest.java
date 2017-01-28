package minvakt.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

/**
 * Created by magnu on 12.01.2017.
 */
public class RandomStringTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();


    @Test
    public void testConstructor() throws Exception {
        exception.expect(IllegalArgumentException.class);
        RandomString test = new RandomString(-1);
    }

}