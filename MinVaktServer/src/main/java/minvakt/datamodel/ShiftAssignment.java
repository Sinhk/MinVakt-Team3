package minvakt.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * MinVakt-Team3
 * 15.01.2017
 */
@Entity
@Table(name = "shift_assignment")
@IdClass(ShiftAssignmentPK.class)
public class ShiftAssignment {
    @Id
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "shift_id", nullable = false)
    private Shift shift;
    @Id
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private User user;
    private boolean responsible;
    private boolean changeRequest;
    private boolean absent;

    public ShiftAssignment() {
    }

    public ShiftAssignment(Shift shift, User user) {
        this.shift = shift;
        this.user = user;
        this.responsible = false;
        this.changeRequest = false;
        this.absent = false;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isResponsible() {
        return responsible;
    }

    public void setResponsible(boolean responsible) {
        this.responsible = responsible;
    }

    public boolean isChangeRequest() {
        return changeRequest;
    }

    public void setChangeRequest(boolean changeRequest) {
        this.changeRequest = changeRequest;
    }

    public boolean isAbsent() {
        return absent;
    }

    public void setAbsent(boolean absent) {
        this.absent = absent;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }


    @Override
    public String toString() {
        return "ShiftAssignment{" +
                "responsible=" + responsible +
                ", changeRequest=" + changeRequest +
                ", absent=" + absent +
                ", shift=" + shift +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShiftAssignment that = (ShiftAssignment) o;

        if (responsible != that.responsible) return false;
        if (changeRequest != that.changeRequest) return false;
        if (absent != that.absent) return false;
        if (!shift.equals(that.shift)) return false;
        return user.equals(that.user);
    }

    @Override
    public int hashCode() {
        int result = shift.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + (responsible ? 1 : 0);
        result = 31 * result + (changeRequest ? 1 : 0);
        result = 31 * result + (absent ? 1 : 0);
        return result;
    }
}
