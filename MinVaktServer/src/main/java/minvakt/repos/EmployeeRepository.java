package minvakt.repos;

import minvakt.datamodel.Employee;
import minvakt.datamodel.Shift;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    Employee findByFirstName(String name);

    Employee findByEmail(String email);

    List<Employee> findByShiftAssignments_Shift(Shift shift);

    //void setPassword(User user, String password);

}