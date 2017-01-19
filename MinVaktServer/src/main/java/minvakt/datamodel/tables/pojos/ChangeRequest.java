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
@Table(name = "change_request", schema = "g_scrum03")
public class ChangeRequest implements Serializable {

    private static final long serialVersionUID = 1166282095;

    private Integer requestId;
    private Integer shiftId;
    private Integer oldEmployeeId;
    private Integer newEmployeeId;

    public ChangeRequest() {
    }

    public ChangeRequest(ChangeRequest value) {
        this.requestId = value.requestId;
        this.shiftId = value.shiftId;
        this.oldEmployeeId = value.oldEmployeeId;
        this.newEmployeeId = value.newEmployeeId;
    }

    public ChangeRequest(
            Integer requestId,
            Integer shiftId,
            Integer oldEmployeeId,
            Integer newEmployeeId
    ) {
        this.requestId = requestId;
        this.shiftId = shiftId;
        this.oldEmployeeId = oldEmployeeId;
        this.newEmployeeId = newEmployeeId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id", unique = true, nullable = false, precision = 10)
    public Integer getRequestId() {
        return this.requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    @Column(name = "shift_id", nullable = false, precision = 10)
    public Integer getShiftId() {
        return this.shiftId;
    }

    public void setShiftId(Integer shiftId) {
        this.shiftId = shiftId;
    }

    @Column(name = "old_employee_id", nullable = false, precision = 10)
    public Integer getOldEmployeeId() {
        return this.oldEmployeeId;
    }

    public void setOldEmployeeId(Integer oldEmployeeId) {
        this.oldEmployeeId = oldEmployeeId;
    }

    @Column(name = "new_employee_id", nullable = false, precision = 10)
    public Integer getNewEmployeeId() {
        return this.newEmployeeId;
    }

    public void setNewEmployeeId(Integer newEmployeeId) {
        this.newEmployeeId = newEmployeeId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ChangeRequest (");

        sb.append(requestId);
        sb.append(", ").append(shiftId);
        sb.append(", ").append(oldEmployeeId);
        sb.append(", ").append(newEmployeeId);

        sb.append(")");
        return sb.toString();
    }
}
