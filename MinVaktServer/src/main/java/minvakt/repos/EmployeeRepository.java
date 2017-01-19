package minvakt.repos;

import minvakt.datamodel.Employee;
import minvakt.datamodel.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findByFirstName(String name);

    Employee findByEmail(String email);

    List<Employee> findByShiftAssignments_Shift(Shift shift);

    Optional<Employee> findByShiftAssignments_ShiftAndShiftAssignments_ResponsibleTrue(Shift shift);

    //void setPassword(User user, String password);

}