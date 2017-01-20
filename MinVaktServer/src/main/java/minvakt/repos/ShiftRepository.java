package minvakt.repos;

import minvakt.datamodel.tables.pojos.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

//import minvakt.datamodel.enums.PredeterminedIntervals;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Integer> {

    //List<Shift> findByShiftAssignments_Employee(Employee employee);
    @Query("SELECT s from Shift s LEFT JOIN ShiftAssignment a on s.shiftId = a.shiftId where a.employeeId = ?1")
    List<Shift> findByShiftAssignments_Employee_id(int employee);

    @Query("SELECT s FROM Shift s ")
    List<Shift> findAvailable();

    //@Query("select s from Shift s join ShiftAssignment a on s.shiftId = a.shift where a.employee = ?1 and (s.startDateTime between ?2 and ?3)")
    //List<Shift> findBetweenDates(Employee employee, LocalDateTime start, LocalDateTime end);
    //boolean exsistsByShiftAssignmentStatusAndShiftId(ShiftStatus status,int shift_id);

    // TODO: 16-Jan-17
    //List<Shift> findShiftsInRange(User user, LocalDate start, LocalDate end);
}