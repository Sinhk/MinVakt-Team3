/*
 * This file is generated by jOOQ.
*/
package minvakt.datamodel.tables.pojos;


import java.io.Serializable;
import java.time.LocalDateTime;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * VIEW
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Table(name = "assigned_per_shift", schema = "g_scrum03")
public class AssignedPerShift implements Serializable {

    private static final long serialVersionUID = 477709812;

    private Integer       shiftId;
    private Integer       responsibleEmployeeId;
    private LocalDateTime fromTime;
    private LocalDateTime toTime;
    private Short         departmentId;
    private Short         requiredEmployees;
    private Long          numAssigned;

    public AssignedPerShift() {}

    public AssignedPerShift(AssignedPerShift value) {
        this.shiftId = value.shiftId;
        this.responsibleEmployeeId = value.responsibleEmployeeId;
        this.fromTime = value.fromTime;
        this.toTime = value.toTime;
        this.departmentId = value.departmentId;
        this.requiredEmployees = value.requiredEmployees;
        this.numAssigned = value.numAssigned;
    }

    public AssignedPerShift(
        Integer       shiftId,
        Integer       responsibleEmployeeId,
        LocalDateTime fromTime,
        LocalDateTime toTime,
        Short         departmentId,
        Short         requiredEmployees,
        Long          numAssigned
    ) {
        this.shiftId = shiftId;
        this.responsibleEmployeeId = responsibleEmployeeId;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.departmentId = departmentId;
        this.requiredEmployees = requiredEmployees;
        this.numAssigned = numAssigned;
    }

    @Column(name = "shift_id", nullable = false, precision = 10)
    public Integer getShiftId() {
        return this.shiftId;
    }

    public void setShiftId(Integer shiftId) {
        this.shiftId = shiftId;
    }

    @Column(name = "responsible_employee_id", nullable = false, precision = 10)
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

    @Column(name = "num_assigned", nullable = false, precision = 19)
    public Long getNumAssigned() {
        return this.numAssigned;
    }

    public void setNumAssigned(Long numAssigned) {
        this.numAssigned = numAssigned;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("AssignedPerShift (");

        sb.append(shiftId);
        sb.append(", ").append(responsibleEmployeeId);
        sb.append(", ").append(fromTime);
        sb.append(", ").append(toTime);
        sb.append(", ").append(departmentId);
        sb.append(", ").append(requiredEmployees);
        sb.append(", ").append(numAssigned);

        sb.append(")");
        return sb.toString();
    }
}
