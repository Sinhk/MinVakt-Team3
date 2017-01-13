package minvakt.datamodel;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by sindr on 13.01.2017.
 * in project: MinVakt-Team3
 */
@Embeddable
public class UserShiftInfoId implements Serializable {
    private User user;
    private Shift shift;

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
        int result = user.hashCode();
        result = 31 * result + shift.hashCode();
        return result;
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    @ManyToOne
    public Shift getShift() {
        return shift;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }
}
