package util;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * Created by OlavH on 09-Jan-17.
 */
public class TimeUtil {

    public static DayOfWeek dayOfWeekOf(LocalDate date){return DayOfWeek.from(date);}

}
