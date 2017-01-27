package minvakt.util;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

/**
 * Created by OlavH on 10-Jan-17.
 */
public class WeekDateInterval implements Serializable{

    private LocalDate start;
    private LocalDate end;

    private WeekDateInterval(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public static WeekDateInterval of(LocalDate date){

        LocalDate monday = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        return new WeekDateInterval(monday, monday.plusDays(6));
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public static void main(String[] args) {

        LocalDate jan1 = LocalDate.of(2017, 1, 1);

        WeekDateInterval of = of(jan1);

        System.out.println(of.start +" - "+of.end);

    }
}
