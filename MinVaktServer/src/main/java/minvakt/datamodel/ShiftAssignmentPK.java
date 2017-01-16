package minvakt.datamodel;

import java.io.Serializable;

/**
 * MinVakt-Team3
 * 15.01.2017
 */
public class ShiftAssignmentPK implements Serializable {
    private int shift;
    private int employee;

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public int getEmployee() {
        return employee;
    }

    public void setEmployee(int employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShiftAssignmentPK that = (ShiftAssignmentPK) o;

        if (shift != that.shift) return false;
        return employee == that.employee;
    }

    @Override
    public int hashCode() {
        int result = shift;
        result = 31 * result + employee;
        return result;
    }
}
