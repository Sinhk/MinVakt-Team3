package minvakt.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import minvakt.datamodel.tables.pojos.Employee;

import java.io.Serializable;

/**
 * Created by sindr on 23.01.2017.
 * in project: MinVakt-Team3
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssignedEmployee extends Employee implements Serializable{
    private boolean absent;
    private boolean assigned;
    private String commentForAbsence;

    public AssignedEmployee(Integer employeeId, Short categoryId, String firstName, String lastName, Integer phone, String email, Short positionPercentage, String passwd, Boolean enabled, Boolean changePwOnLogon, boolean absent, boolean assigned, String commentForAbsence) {
        super(employeeId, categoryId, firstName, lastName, phone, email, positionPercentage, passwd, enabled, changePwOnLogon);
        this.absent = absent;
        this.assigned = assigned;
        this.commentForAbsence = commentForAbsence;
    }

    public AssignedEmployee() {
    }

    public AssignedEmployee(boolean absent, boolean assigned, String commentForAbsence) {
        super();
        this.absent = absent;
        this.assigned = assigned;
        this.commentForAbsence = commentForAbsence;
    }

    public String getCommentForAbsence() {
        return commentForAbsence;
    }

    public void setCommentForAbsence(String commentForAbsence) {
        this.commentForAbsence = commentForAbsence;
    }

    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    public boolean isAbsent() {
        return absent;
    }

    public void setAbsent(boolean absent) {
        this.absent = absent;
    }
}
