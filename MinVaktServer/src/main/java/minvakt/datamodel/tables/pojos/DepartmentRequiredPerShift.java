/*
 * This file is generated by jOOQ.
*/
package minvakt.datamodel.tables.pojos;


import javax.annotation.Generated;
import javax.persistence.*;
import java.io.Serializable;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Entity
@Table(name = "department_required_per_shift", schema = "g_scrum03")
public class DepartmentRequiredPerShift implements Serializable {

    private static final long serialVersionUID = 1083879400;

    private Integer id;
    private Short   departmentId;
    private Integer shiftId;
    private Short   amount;

    public DepartmentRequiredPerShift() {}

    public DepartmentRequiredPerShift(DepartmentRequiredPerShift value) {
        this.id = value.id;
        this.departmentId = value.departmentId;
        this.shiftId = value.shiftId;
        this.amount = value.amount;
    }

    public DepartmentRequiredPerShift(
        Integer id,
        Short   departmentId,
        Integer shiftId,
        Short   amount
    ) {
        this.id = id;
        this.departmentId = departmentId;
        this.shiftId = shiftId;
        this.amount = amount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, precision = 10)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "department_id", nullable = false, precision = 5)
    public Short getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(Short departmentId) {
        this.departmentId = departmentId;
    }

    @Column(name = "shift_id", nullable = false, precision = 10)
    public Integer getShiftId() {
        return this.shiftId;
    }

    public void setShiftId(Integer shiftId) {
        this.shiftId = shiftId;
    }

    @Column(name = "amount", nullable = false, precision = 5)
    public Short getAmount() {
        return this.amount;
    }

    public void setAmount(Short amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("DepartmentRequiredPerShift (");

        sb.append(id);
        sb.append(", ").append(departmentId);
        sb.append(", ").append(shiftId);
        sb.append(", ").append(amount);

        sb.append(")");
        return sb.toString();
    }
}