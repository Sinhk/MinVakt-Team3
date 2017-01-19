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
@SuppressWarnings({"all", "unchecked", "rawtypes"})
@Entity
@Table(name = "shift_overtime", schema = "g_scrum03")
public class ShiftOvertime implements Serializable {

    private static final long serialVersionUID = -1338587941;

    private Integer id;
    private Integer shiftAssignmentId;
    private Integer minutes;

    public ShiftOvertime() {
    }

    public ShiftOvertime(ShiftOvertime value) {
        this.id = value.id;
        this.shiftAssignmentId = value.shiftAssignmentId;
        this.minutes = value.minutes;
    }

    public ShiftOvertime(
            Integer id,
            Integer shiftAssignmentId,
            Integer minutes
    ) {
        this.id = id;
        this.shiftAssignmentId = shiftAssignmentId;
        this.minutes = minutes;
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

    @Column(name = "shift_assignment_id", nullable = false, precision = 10)
    public Integer getShiftAssignmentId() {
        return this.shiftAssignmentId;
    }

    public void setShiftAssignmentId(Integer shiftAssignmentId) {
        this.shiftAssignmentId = shiftAssignmentId;
    }

    @Column(name = "minutes", nullable = false, precision = 7)
    public Integer getMinutes() {
        return this.minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ShiftOvertime (");

        sb.append(id);
        sb.append(", ").append(shiftAssignmentId);
        sb.append(", ").append(minutes);

        sb.append(")");
        return sb.toString();
    }
}
