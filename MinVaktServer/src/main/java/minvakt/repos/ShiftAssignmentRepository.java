package minvakt.repos;

import minvakt.datamodel.ShiftAssignment;
import minvakt.datamodel.ShiftAssignmentPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftAssignmentRepository extends JpaRepository<ShiftAssignment, ShiftAssignmentPK> {

}