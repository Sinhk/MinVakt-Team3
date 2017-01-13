package minvakt.datamodel;

import minvakt.datamodel.enums.ShiftType;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by magnu on 12.01.2017.
 */

@Entity
@Table(name = "users_shifts")
//@IdClass(UserShiftInfoId.class)
@AssociationOverrides({
        @AssociationOverride(name = "pk.user",
                joinColumns = @JoinColumn(name = "user_id")),
        @AssociationOverride(name = "pk.shift",
                joinColumns = @JoinColumn(name = "shift_id")) })
public class UserShiftInfo implements Serializable{

    //@Id
    private UserShiftInfoId pk = new UserShiftInfoId();

   /* @Id
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    @Column(name = "user_id")
    private User user;

    @Id
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "shift_id")
    @Column(name = "shift_id")
    private Shift shift;*/

    //@Column(name = "responsible", nullable = false)
    private boolean responsible;

    //@Column
    private ShiftType shiftType;

    public UserShiftInfo() {}

    @EmbeddedId
    public UserShiftInfoId getPk() {
        return pk;
    }

    public void setPk(UserShiftInfoId pk) {
        this.pk = pk;
    }

    @Transient
    public User getUser() {
        return getPk().getUser();
    }

    public void setUser(User user) {
        this.getPk().setUser(user);
    }

    @Transient
    public Shift getShift() {
        return getPk().getShift();
    }

    public void setShift(Shift shift) {
        this.getPk().setShift(shift);
    }

    public UserShiftInfo(User user, Shift shift) {
        setUser(user);
        setShift(shift);

        responsible = false;
    }

    @Column(name = "responsible")
    public boolean getResponsible() {
        return responsible;
    }

    @Column(name = "shift_type")
    public ShiftType getShiftType() {
        return shiftType;
    }


    public void setResponsible(boolean resp) {
        responsible = resp;
    }

    public void setShiftType(ShiftType shift) {
        shiftType = shift;
    }
}
