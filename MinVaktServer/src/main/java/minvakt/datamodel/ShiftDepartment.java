package minvakt.datamodel;

import javax.persistence.*;

/**
 * MinVakt-Team3
 * 15.01.2017
 */
@Entity
@Table(name = "shift_department", schema = "g_scrum03", catalog = "")
@IdClass(ShiftDepartmentPK.class)
public class ShiftDepartment {
    @Id
    @Column(name = "shift_id", nullable = false)
    private Integer shiftId;

    @Id
    @Column(name = "department_id", nullable = false)
    private Integer departmentId;

    public Integer getShiftId() {
        return shiftId;
    }

    public void setShiftId(Integer shiftId) {
        this.shiftId = shiftId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShiftDepartment that = (ShiftDepartment) o;

        if (shiftId != null ? !shiftId.equals(that.shiftId) : that.shiftId != null) return false;
        if (departmentId != null ? !departmentId.equals(that.departmentId) : that.departmentId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = shiftId != null ? shiftId.hashCode() : 0;
        result = 31 * result + (departmentId != null ? departmentId.hashCode() : 0);
        return result;
    }
}
