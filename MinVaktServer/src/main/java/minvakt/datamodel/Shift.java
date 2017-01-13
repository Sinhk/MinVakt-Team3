package minvakt.datamodel;

import minvakt.datamodel.enums.PredeterminedIntervals;
import minvakt.datamodel.enums.ShiftType;
import minvakt.util.TimeInterval;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Shift {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private int shiftId;

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @Column(nullable = false)
    private LocalDateTime endDateTime;

    private ShiftType shiftType = ShiftType.AVAILABLE;

    private String comment;

    public Shift() {
    }

    public Shift(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Objects.requireNonNull(startDateTime); Objects.requireNonNull(endDateTime);

        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public Shift(LocalDate startDate, PredeterminedIntervals interval) {
        Objects.requireNonNull(startDate);
        Objects.requireNonNull(interval);

        LocalDateTime startDateTime = LocalDateTime.of(startDate, interval.getInterval().getTimeStart());
        LocalDateTime endDateTime = startDateTime.plus(interval.getInterval().getDuration());

        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public Shift(LocalDate date, PredeterminedIntervals intervals, ShiftType type) {
        this(date, intervals);
        this.shiftType = type;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }
    public ShiftType getShiftType() { return shiftType; }
    public TimeInterval getTimeInterval(){return new TimeInterval(startDateTime, endDateTime);}

    public void setShiftType(ShiftType shiftType) { this.shiftType = shiftType; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public String toString() {
        return startDateTime.toString()+": "+ endDateTime.toString()+" -> "+endDateTime.toString();
    }


    @OneToMany(mappedBy = "shift", targetEntity = UserShiftInfo.class, fetch = FetchType.EAGER)
    private Set<User> users = new HashSet<>();

    public Set<User> getUsers() {
        return users;
    }

    public boolean changeShiftFromUserToUser(User from, User to){

        boolean remove = users.remove(from);
        boolean add = users.add(to);

        return remove && add;
    }

    public int getShiftId() {
        return shiftId;
    }

    /*public User getResponsibleForShift(){

        Collection<User> users = getUsers();

    }*/

}