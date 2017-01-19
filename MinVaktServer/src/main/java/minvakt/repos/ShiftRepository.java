package minvakt.repos;

import minvakt.datamodel.Employee;
import minvakt.datamodel.Shift;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Integer> {


    //@Query(value = "SELECT s FROM Shift s LEFT JOIN FETCH ShiftAssignment a on s.shiftId = a.shift")

    @EntityGraph(value = "Shift.assignments", type = EntityGraph.EntityGraphType.LOAD)
    List<Shift> findByStartDateTimeBetween(LocalDateTime start, LocalDateTime end);

    List<Shift> findByShiftAssignments_Employee(Employee employee);

    @Query("select s from Shift s join ShiftAssignment a on s.shiftId = a.shift where a.employee = ?1 and (s.startDateTime between ?2 and ?3)")
    List<Shift> findBetweenDates(Employee employee, LocalDateTime start, LocalDateTime end);
    //boolean exsistsByShiftAssignmentStatusAndShiftId(ShiftStatus status,int shift_id);

    // TODO: 16-Jan-17  
    //List<Shift> findShiftsInRange(User user, LocalDate start, LocalDate end);
}