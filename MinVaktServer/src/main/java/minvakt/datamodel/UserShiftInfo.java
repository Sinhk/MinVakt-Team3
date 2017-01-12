package minvakt.datamodel;

import minvakt.datamodel.enums.ShiftType;

import javax.persistence.*;

/**
 * Created by magnu on 12.01.2017.
 */

@Entity
@Table(name = "Users_Shifts")
public class UserShiftInfo {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "shift_id")
    private Shift shift;

    @Column(name = "resopnsible", nullable = false)
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
