package minvakt.datamodel.enums;

import java.time.LocalTime;

/**
 * Created by OlavH on 10-Jan-17.
 */
public enum PredeterminedIntervals {

    MORNING(LocalTime.of(7, 30), LocalTime.of(15, 30)),
    DAYTIME(LocalTime.of(15, 0), LocalTime.of(22, 30)),
    NIGHT(LocalTime.of(22, 0), LocalTime.of(8, 0));


    private final LocalTime startTime;
    private final LocalTime endTime;

    PredeterminedIntervals(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
