package datamodel.enums;

import datamodel.Shift;
import util.TimeInterval;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by OlavH on 10-Jan-17.
 */
public enum PredeterminedShifts {

    MORNING(new TimeInterval(LocalTime.of(6,0),LocalTime.of(14,0))), // 6-14
    DAYTIME(new TimeInterval(LocalTime.of(6,0),LocalTime.of(14,0))), // 14-22
    NIGHT(new TimeInterval(LocalTime.of(6,0),LocalTime.of(14,0)));     // 22-6


    private TimeInterval interval;
    PredeterminedShifts(TimeInterval interval){
        this.interval = interval;
    }

    public TimeInterval getInterval() {
        return interval;
    }
    public Shift shiftForDate(LocalDate date){

        return new Shift(date, interval.getStart(), interval.getEnd());

    }
}
