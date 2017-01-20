package minvakt.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

/**
 * MinVakt-Team3
 * 15.01.2017
 */
@Entity
@Table(name = "shift_assignment")
@IdClass(ShiftAssignmentPK.class)
public class ShiftAssignment implements Serializable{
    @Id
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "shift_id", nullable = false)
    private Shift shift;
    @Id
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private boolean absent;

    public ShiftAssignment() {
    }

    public ShiftAssignment(Shift shift, Employee employee) {
        this.shift = shift;
        this.employee = employee;
        absent = false;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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
                "absent=" + absent +
                ", shift=" + shift +
                ", employee=" + employee +
                '}';
    }

    public boolean isAbsent() {
        return absent;
    }

    public void setAbsent(boolean absent) {
        this.absent = absent;
    }
}
