package minvakt.datamodel;

import minvakt.datamodel.tables.pojos.Shift;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sindr on 23.01.2017.
 * in project: MinVakt-Team3
 */
public class ShiftDetailed extends Shift {
    private List<AssignedEmployee> employees = new ArrayList<>();

    public ShiftDetailed() {
    }

    public ShiftDetailed(Integer shiftId, Integer responsibleEmployeeId, LocalDateTime fromTime, LocalDateTime toTime, Short departmentId, Short requiredEmployees) {
        super(shiftId, responsibleEmployeeId, fromTime, toTime, departmentId, requiredEmployees);
    }

    public void addEmployee(AssignedEmployee employee) {
        employees.add(employee);
    }

    public List<AssignedEmployee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<AssignedEmployee> employees) {
        this.employees = employees;
    }
}
