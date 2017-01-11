package minvakt.datamodel;

import minvakt.datamodel.enums.PredeterminedIntervals;
import minvakt.datamodel.enums.ShiftType;
import minvakt.util.TimeInterval;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Created by OlavH on 09-Jan-17.
 */
@Entity
public class Shift {

    @Id
    @GeneratedValue
    private int shiftId;

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @Column(nullable = false)
    private LocalDateTime endDateTime;

    private PredeterminedIntervals interval;

    private boolean responsible;

    private ShiftType shiftType = ShiftType.AVAILABLE;

    private String comment;

    public Shift(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Objects.requireNonNull(startDateTime); Objects.requireNonNull(endDateTime);

        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
    public Shift(LocalDateTime startDateTime, PredeterminedIntervals interval){
        Objects.requireNonNull(startDateTime); Objects.requireNonNull(interval);

        this.startDateTime = startDateTime;
        this.interval = interval;
    }
    public Shift(LocalDateTime date, PredeterminedIntervals interval, ShiftType type){
        this(date, interval);
        this.shiftType = type;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }
    public PredeterminedIntervals getPredeterminedInterval() {return interval;}
    public boolean isResponsible() {
        return responsible;
    }
    public ShiftType getShiftType() { return shiftType; }
    public TimeInterval getTimeInterval(){return new TimeInterval(startDateTime, endDateTime);}

    public void setResponsible(boolean responsible) { this.responsible = responsible; }
    public void setShiftType(ShiftType shiftType) { this.shiftType = shiftType; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public String toString() {
        return startDateTime.toString()+": "+ endDateTime.toString()+" -> "+endDateTime.toString();
    }

    public static void main(String[] args) {

        System.out.println(DayOfWeek.from(LocalDate.now()));

    }
}