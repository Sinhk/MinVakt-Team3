package minvakt.util;

import minvakt.datamodel.tables.pojos.Shift;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by OlavH on 09-Jan-17.
 */
public class TimeUtil {

    public static boolean isInDateInterval(LocalDate fromInc, LocalDate toEx, LocalDate toCheck){

        return toCheck.isAfter(fromInc) || toCheck.isEqual(fromInc) && toCheck.isBefore(toEx);

    }
}
