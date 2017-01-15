package minvakt.datamodel;

import minvakt.datamodel.enums.PredeterminedIntervals;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class Shift {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private int shiftId;

    @Column(nullable = false, name = "from_time")
    private LocalDateTime startDateTime;

    @Column(nullable = false, name = "to_time")
    private LocalDateTime endDateTime;

    private String comments;

    @OneToMany(mappedBy = "shift", orphanRemoval = true, cascade = {javax.persistence.CascadeType.PERSIST, javax.persistence.CascadeType.MERGE})
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<ShiftAssignment> shiftAssignments;

    public Shift() {
    }

    public Shift(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Objects.requireNonNull(startDateTime);
        Objects.requireNonNull(endDateTime);

        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public Shift(LocalDate startDate, PredeterminedIntervals interval) {
        Objects.requireNonNull(startDate);
        Objects.requireNonNull(interval);

        LocalDateTime startDateTime = LocalDateTime.of(startDate, interval.getStartTime());
        LocalDateTime endDateTime;
        if (interval.getStartTime().isAfter(interval.getEndTime())) {
            endDateTime = LocalDateTime.of(startDate.plusDays(1), interval.getEndTime());
        } else {
            endDateTime = LocalDateTime.of(startDate, (interval.getEndTime()));
        }
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String toString() {
        return startDateTime.toString() + " -> " + endDateTime.toString();
    }

    public int getShiftId() {
        return shiftId;
    }

    public List<ShiftAssignment> getShiftAssignments() {
        return shiftAssignments;
    }

    public void setShiftAssignments(List<ShiftAssignment> shiftAssignments) {
        this.shiftAssignments = shiftAssignments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shift shift = (Shift) o;

        if (shiftId != shift.shiftId) return false;
        if (!startDateTime.equals(shift.startDateTime)) return false;
        return endDateTime.equals(shift.endDateTime);
    }

    @Override
    public int hashCode() {
        int result = shiftId;
        result = 31 * result + startDateTime.hashCode();
        result = 31 * result + endDateTime.hashCode();
        return result;
    }

    /*public User getResponsibleForShift(){

        Collection<User> users = getUsers();

    }*/


}