package minvakt.datamodel;

import javax.persistence.*;

/**
 * Created by magnu on 12.01.2017.
 */

@Entity
@Table(name = "users_shifts")
@IdClass(UserShiftInfoId.class)
public class UserShiftInfo {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    //@JoinTable(name = "users")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "shift_id")
    //@JoinTable(name = "shifts")
    private Shift shift;
    @Id
    private UserShiftInfoId id = new UserShiftInfoId();

    @Column(name = "responsible")
    private boolean responsible;

    @Column
    private int shiftType;

    public UserShiftInfo() {
    }

    public UserShiftInfoId getId() {
        return id;
    }
    public void setId(UserShiftInfoId id) {
        this.id = id;
    }

    public boolean getResponsible() {
        return responsible;
    }

    public int getShiftType() {
        return shiftType;
    }


    public void setResponsible(boolean resp) {
        responsible = resp;
    }

    public void setShiftType(int shift) {
        shiftType = shift;
    }

    public int getUser() {
        return id.getUser();
    }

    public void setUser(int user) {
        this.id.setUser(user);
    }

    public int getShift() {
        return id.getShift();
    }

    public void setShift(int shift) {
        this.id.setUser(shift);
    }

    public boolean isResponsible() {
        return responsible;
    }
}
