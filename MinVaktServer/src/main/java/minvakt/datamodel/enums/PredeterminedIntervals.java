package minvakt.datamodel.enums;

import minvakt.datamodel.Shift;
import minvakt.util.TimeInterval;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by OlavH on 10-Jan-17.
 */
public enum PredeterminedIntervals {

    MORNING(new TimeInterval(LocalTime.of(6,0),LocalTime.of(14,0))), // 6-14
    DAYTIME(new TimeInterval(LocalTime.of(14,0),LocalTime.of(22,0))), // 14-22
    NIGHT(new TimeInterval(LocalTime.of(22,0),LocalTime.of(6,0)));     // 22-6


    private TimeInterval interval;
    PredeterminedIntervals(TimeInterval interval){
        this.interval = interval;
    }

    public TimeInterval getInterval() {
        return interval;
    }
    public static Shift shiftForDate(LocalDateTime date, PredeterminedIntervals shift){

        return new Shift(date, shift.getInterval().getStart());

    }
}
