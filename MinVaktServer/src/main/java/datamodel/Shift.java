package datamodel;

import datamodel.enums.ShiftType;

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

    private ShiftType shiftType;

    private String comment;

    public Shift(LocalDate date, LocalTime start, LocalTime end) {
        this.date = date;
        this.start = start;
        this.end = end;
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
    public ShiftType getShiftType() { return shiftType; }

    public void setResponsible(boolean responsible) { this.responsible = responsible; }
    public void setShiftType(ShiftType shiftType) { this.shiftType = shiftType; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public String toString() {
        return date.toString()+": "+start.toString()+" -> "+end.toString();
    }

    public static void main(String[] args) {

        System.out.println(DayOfWeek.from(LocalDate.now()));

    }
}
