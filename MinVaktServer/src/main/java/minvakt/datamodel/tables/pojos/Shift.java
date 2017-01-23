/*
 * This file is generated by jOOQ.
*/
package minvakt.datamodel.tables.pojos;


import javax.annotation.Generated;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


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
@Table(name = "shift", schema = "g_scrum03")
public class Shift implements Serializable {

    private static final long serialVersionUID = 247009417;

    private Integer       shiftId;
    private Integer       responsibleEmployeeId;
    private LocalDateTime fromTime;
    private LocalDateTime toTime;

    public Shift() {}

    public Shift(Shift value) {
        this.shiftId = value.shiftId;
        this.responsibleEmployeeId = value.responsibleEmployeeId;
        this.fromTime = value.fromTime;
        this.toTime = value.toTime;
    }

    public Shift(
        Integer       shiftId,
        Integer       responsibleEmployeeId,
        LocalDateTime fromTime,
        LocalDateTime toTime
    ) {
        this.shiftId = shiftId;
        this.responsibleEmployeeId = responsibleEmployeeId;
        this.fromTime = fromTime;
        this.toTime = toTime;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Shift (");

        sb.append(shiftId);
        sb.append(", ").append(responsibleEmployeeId);
        sb.append(", ").append(fromTime);
        sb.append(", ").append(toTime);

        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shift shift = (Shift) o;

        if (!shiftId.equals(shift.shiftId)) return false;
        if (!responsibleEmployeeId.equals(shift.responsibleEmployeeId)) return false;
        if (!fromTime.equals(shift.fromTime)) return false;
        if (!toTime.equals(shift.toTime)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = shiftId.hashCode();
        result = 31 * result + responsibleEmployeeId.hashCode();
        result = 31 * result + fromTime.hashCode();
        result = 31 * result + toTime.hashCode();
        return result;
    }
}
