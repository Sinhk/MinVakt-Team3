package minvakt.repos;

import minvakt.datamodel.tables.pojos.ShiftAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShiftAssignmentRepository extends JpaRepository<ShiftAssignment, Integer> {

    Optional<ShiftAssignment> findByShiftIdAndEmployeeId(int shift_id, int user_id);

    List<ShiftAssignment> findByShiftId(int shift_id);

    List<ShiftAssignment> findByAssignedTrue();

    List<ShiftAssignment> findByAssignedFalse();

    List<ShiftAssignment> findByAvailableTrue();

    List<ShiftAssignment> findByAvailableFalse();
}