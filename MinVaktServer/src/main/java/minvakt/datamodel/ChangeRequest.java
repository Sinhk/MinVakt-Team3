package minvakt.datamodel;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by magnu on 18.01.2017.
 */

@Entity
@Table(name = "change_request")
public class ChangeRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private int requestId;

    @ManyToOne
    @JoinColumn(name = "shift_id", nullable = false)
    private Shift shift;

    @ManyToOne
    @JoinColumn(name = "old_employee_id", nullable = false)
    private Employee oldEmployee;

    @ManyToOne
    @JoinColumn(name = "new_employee_id", nullable = false)
    private Employee newEmployee;


    public ChangeRequest() {
    }

    public ChangeRequest(Shift shift, Employee oldEmp, Employee newEmp) {
        this.shift = shift;
        oldEmployee = oldEmp;
        newEmployee = newEmp;
    }


    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public Employee getOldEmployee() {
        return oldEmployee;
    }

    public void setOldEmployee(Employee oldEmployee) {
        this.oldEmployee = oldEmployee;
    }

    public Employee getNewEmployee() {
        return newEmployee;
    }

    public void setNewEmployee(Employee newEmployee) {
        this.newEmployee = newEmployee;
    }


    @Override
    public String toString() {
        return "ChangeRequest{" +
                "shift=" + shift +
                ", old employee=" + oldEmployee +
                ", new employee=" + newEmployee +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChangeRequest that = (ChangeRequest) o;

        if (requestId != that.requestId) return false;
        if (shift != that.shift) return false;
        return shift.equals(that.shift);
    }

    @Override
    public int hashCode() {
        int result = shift.hashCode();
        result = 31 * result + oldEmployee.hashCode();
        result = 31 * result + newEmployee.hashCode();
        return result;
    }

}
