package minvakt.repos;

import minvakt.datamodel.tables.pojos.Shift;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

//import minvakt.datamodel.enums.PredeterminedIntervals;

public interface ShiftRepository extends CrudRepository<Shift, Integer> {

    //List<Shift> findByShiftAssignments_Employee(Employee employee);
    @Query("SELECT s from Shift s LEFT JOIN ShiftAssignment a on s.shiftId = a.shiftId where a.employeeId = ?1")
    List<Shift> findByShiftAssignments_Employee_id(int employee);

    // TODO: 16-Jan-17  
    //List<Shift> findShiftsInRange(User user, LocalDate start, LocalDate end);
}