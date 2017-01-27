package minvakt.repos;

import minvakt.datamodel.tables.Employee;
import minvakt.datamodel.tables.pojos.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

//import minvakt.datamodel.enums.PredeterminedIntervals;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Integer> {

    //List<Shift> findByShiftAssignments_Employee(Employee employee);
    @Query("SELECT s from Shift s LEFT JOIN ShiftAssignment a on s.shiftId = a.shiftId where a.employeeId = ?1 and a.assigned=true")
    List<Shift> findAssignedByShiftEmployeeId(int employee);

    @Query("SELECT s from Shift s LEFT JOIN ShiftAssignment a on s.shiftId = a.shiftId where a.employeeId = ?1")
    List<Shift> findByShiftEmployeeId(int employee);

    @Query("select s from Shift s where (s.fromTime between ?1 and ?2)")
    List<Shift> findBetweenDates( LocalDateTime start, LocalDateTime end);
}