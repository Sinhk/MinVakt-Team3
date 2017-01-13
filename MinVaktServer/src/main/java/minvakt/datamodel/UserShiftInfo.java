package minvakt.datamodel;

import minvakt.datamodel.enums.ShiftType;

import javax.persistence.*;

/**
 * Created by magnu on 12.01.2017.
 */

@Entity
@Table(name = "users_shifts")
@IdClass(UserShiftInfoId.class)
public class UserShiftInfo {

    @Id
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "shift_id")
    private Shift shift;

    @Column(name = "resopnsible", nullable = false)
    private boolean responsible;

    @Column
    private ShiftType shiftType;

    public UserShiftInfo() {
    }

    public UserShiftInfo(User user, Shift shift) {
        this.user = user;
        this.shift = shift;
        responsible = false;
    }

    public boolean getResponsible() {
        return responsible;
    }

    public ShiftType getShiftType() {
        return shiftType;
    }


    public void setResponsible(boolean resp) {
        responsible = resp;
    }

    public void setShiftType(ShiftType shift) {
        shiftType = shift;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public boolean isResponsible() {
        return responsible;
    }
}
