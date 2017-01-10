package minvakt.util;

import minvakt.datamodel.Shift;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by OlavH on 09-Jan-17.
 */
public class TimeUtil {

    /**
     * @param date
     * @return DayOfWeek of the date
     */
    public static DayOfWeek dayOfWeekOf(LocalDate date){return DayOfWeek.from(date);}

    public static boolean shiftsOverlap(List<Shift> shiftList, Shift shift){

        TimeInterval interval = TimeInterval.of(shift);

        for (Shift shiftToCheck : shiftList) {

            TimeInterval toCheck = TimeInterval.of(shiftToCheck);

            if (interval.overlaps(toCheck)) return true;

        }
        return false;

    }



}
