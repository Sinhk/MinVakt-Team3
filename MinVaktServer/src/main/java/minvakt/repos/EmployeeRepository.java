package minvakt.repos;

import minvakt.datamodel.tables.pojos.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Override
    @Query("SELECT e FROM Employee e WHERE enabled = true ")
    List<Employee> findAll();

    Employee findByFirstName(String name);

    Employee findByEmail(String email);

    @Query("SELECT e FROM Employee e LEFT JOIN ShiftAssignment s ON e.employeeId = s.employeeId WHERE s.shiftId = ?1")
    List<Employee> findByShiftAssignments_Shift(int shift);

    @Query("SELECT e FROM Employee e LEFT JOIN Shift s ON e.employeeId = s.responsibleEmployeeId WHERE s.shiftId = ?1")
    Employee findResponsibleForShift(int shift_id);

    Optional<Employee> findByShiftAssignments_ShiftAndShiftAssignments_ResponsibleTrue(Shift shift);

    //void setPassword(User user, String password);

}