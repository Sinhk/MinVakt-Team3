package datamodel;

import java.time.DayOfWeek;
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
    public Shift(){

    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public boolean isResponsible() {
        return responsible;
    }


    public String toString() {
        return date.toString()+": "+start.toString()+" -> "+end.toString();
    }

    public static void main(String[] args) {

        System.out.println(DayOfWeek.from(LocalDate.now()));

    }
}
