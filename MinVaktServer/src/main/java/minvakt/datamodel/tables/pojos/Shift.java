/*
 * This file is generated by jOOQ.
*/
package minvakt.datamodel.tables.pojos;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.*;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "shift", schema = "g_scrum03")
public class Shift implements Serializable {

    private static final long serialVersionUID = 1633364147;

    private Integer       shiftId;
    private Integer       responsibleEmployeeId;
    private Employee responsible;
    private LocalDateTime fromTime;
    private LocalDateTime toTime;
    private Short         departmentId;
    private Short         requiredEmployees;
    private List<MissingPerShiftCategory> missing;

    public Shift() {}

    public Shift(Shift value) {
        this.shiftId = value.shiftId;
        this.responsibleEmployeeId = value.responsibleEmployeeId;
        this.fromTime = value.fromTime;
        this.toTime = value.toTime;
        this.departmentId = value.departmentId;
        this.requiredEmployees = value.requiredEmployees;
    }

    public Shift(
        Integer       shiftId,
        Integer       responsibleEmployeeId,
        LocalDateTime fromTime,
        LocalDateTime toTime,
        Short         departmentId,
        Short         requiredEmployees
    ) {
        this.shiftId = shiftId;
        this.responsibleEmployeeId = responsibleEmployeeId;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.departmentId = departmentId;
        this.requiredEmployees = requiredEmployees;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shift_id", unique = true, nullable = false, precision = 10)
    public Integer getShiftId() {
        return this.shiftId;
    }

    public void setShiftId(Integer shiftId) {
        this.shiftId = shiftId;
    }

    @Column(name = "responsible_employee_id", precision = 10)
    public Integer getResponsibleEmployeeId() {
        return this.responsibleEmployeeId;
    }

    public void setResponsibleEmployeeId(Integer responsibleEmployeeId) {
        this.responsibleEmployeeId = responsibleEmployeeId;
    }

    @Column(name = "from_time", nullable = false)
    public LocalDateTime getFromTime() {
        return this.fromTime;
    }

    public void setFromTime(LocalDateTime fromTime) {
        this.fromTime = fromTime;
    }

    @Column(name = "to_time", nullable = false)
    public LocalDateTime getToTime() {
        return this.toTime;
    }

    public void setToTime(LocalDateTime toTime) {
        this.toTime = toTime;
    }

    @Column(name = "department_id", nullable = false, precision = 5)
    public Short getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(Short departmentId) {
        this.departmentId = departmentId;
    }

    @Column(name = "required_employees", nullable = false, precision = 5)
    public Short getRequiredEmployees() {
        return this.requiredEmployees;
    }

    public void setRequiredEmployees(Short requiredEmployees) {
        this.requiredEmployees = requiredEmployees;
    }

    @ManyToOne
    @JoinColumn(insertable=false, updatable=false, name = "responsible_employee_id")
    public Employee getResponsible() {
        return responsible;
    }

    public void setResponsible(Employee responsible) {
        this.responsible = responsible;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Shift (");

        sb.append(shiftId);
        sb.append(", ").append(responsibleEmployeeId);
        sb.append(", ").append(fromTime);
        sb.append(", ").append(toTime);
        sb.append(", ").append(departmentId);
        sb.append(", ").append(requiredEmployees);

        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shift shift = (Shift) o;

        if (!shiftId.equals(shift.shiftId)) return false;
        if (!fromTime.equals(shift.fromTime)) return false;
        if (!toTime.equals(shift.toTime)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = shiftId.hashCode();
        result = 31 * result + fromTime.hashCode();
        result = 31 * result + toTime.hashCode();
        return result;
    }



    @Transient
    public List<MissingPerShiftCategory> getMissing() {
        return missing;
    }

    public void setMissing(List<MissingPerShiftCategory> missing) {
        this.missing = missing;
    }
}
