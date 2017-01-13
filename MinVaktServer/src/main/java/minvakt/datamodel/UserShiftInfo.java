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
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "shift_id")
    private Shift shift;

    @Column(name = "resopnsible")
    private boolean responsible;

    @Column
    private ShiftType shiftType;

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
}
