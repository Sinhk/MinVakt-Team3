package minvakt.repos;

import minvakt.datamodel.Shift;
import minvakt.datamodel.enums.PredeterminedIntervals;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface ShiftRepository extends CrudRepository<Shift, Integer> {

    //Page<UserEntity> findAll(Pageable pageable);

    Shift findBy(LocalDate date, PredeterminedIntervals interval);

}