package minvakt.datamodel;

import java.io.Serializable;

/**
 * Created by sindr on 13.01.2017.
 * in project: MinVakt-Team3
 */
public class UserShiftInfoId implements Serializable {
    private int user;
    private int shift;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserShiftInfoId that = (UserShiftInfoId) o;

        if (user != that.user) return false;
        return shift == that.shift;
    }

    @Override
    public int hashCode() {
        int result = user;
        result = 31 * result + shift;
        return result;
    }

    public int getUser() {
        return user;
    }

    public int getShift() {
        return shift;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }
}
