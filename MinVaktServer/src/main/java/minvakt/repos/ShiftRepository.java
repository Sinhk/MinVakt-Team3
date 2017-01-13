package minvakt.repos;

import minvakt.datamodel.Shift;
import minvakt.datamodel.enums.PredeterminedIntervals;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface ShiftRepository extends CrudRepository<Shift, Integer> {

    Shift findBy(LocalDate date, PredeterminedIntervals interval);

}