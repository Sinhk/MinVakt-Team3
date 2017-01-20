package minvakt.repos;

import minvakt.datamodel.tables.pojos.Employee;
import minvakt.datamodel.tables.pojos.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findByFirstName(String name);

    Employee findByEmail(String email);

    @Query("SELECT e FROM Employee e LEFT JOIN ShiftAssignment s ON e.employeeId = s.employeeId WHERE s.shiftId = ?1")
    List<Employee> findByShiftAssignments_Shift(Shift shift);

    @Query("SELECT e FROM Employee e LEFT JOIN Shift s ON e.employeeId = s.responsibleEmployeeId WHERE s.shiftId = ?1")
    Employee findResponsibleForShift(int shift_id);

    //void setPassword(User user, String password);

}