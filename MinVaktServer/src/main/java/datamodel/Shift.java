package datamodel;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by OlavH on 09-Jan-17.
 */
public class Shift {

    private LocalDate date;
    private LocalTime start;
    private LocalTime end;

    private boolean responsible;

    public Shift(LocalDate date, LocalTime start, LocalTime end) {
        this.date = date;
        this.start = start;
        this.end = end;
    }
}
