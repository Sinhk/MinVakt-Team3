package minvakt.repos;

import minvakt.datamodel.Employee;
import minvakt.datamodel.Shift;
import minvakt.datamodel.enums.PredeterminedIntervals;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface ShiftRepository extends CrudRepository<Shift, Integer> {

    Shift findBy(LocalDate date, PredeterminedIntervals interval);

    List<Shift> findByShiftAssignments_Employee(Employee employee);

    // TODO: 16-Jan-17  
    //List<Shift> findShiftsInRange(User user, LocalDate start, LocalDate end);
}