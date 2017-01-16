package minvakt.repos;

import minvakt.datamodel.Shift;
import minvakt.datamodel.User;
import minvakt.datamodel.enums.PredeterminedIntervals;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface ShiftRepository extends CrudRepository<Shift, Integer> {

    Shift findBy(LocalDate date, PredeterminedIntervals interval);

    List<Shift> findByShiftAssignments_User(User user);
}