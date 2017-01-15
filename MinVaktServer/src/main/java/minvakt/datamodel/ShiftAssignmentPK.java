package minvakt.datamodel;

import java.io.Serializable;

/**
 * MinVakt-Team3
 * 15.01.2017
 */
public class ShiftAssignmentPK implements Serializable {
    private int shift;
    private int user;

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShiftAssignmentPK that = (ShiftAssignmentPK) o;

        if (shift != that.shift) return false;
        return user == that.user;
    }

    @Override
    public int hashCode() {
        int result = shift;
        result = 31 * result + user;
        return result;
    }
}
