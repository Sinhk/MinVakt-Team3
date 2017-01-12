package minvakt.repos;

import minvakt.datamodel.Shift;
import minvakt.datamodel.User;
import org.springframework.data.repository.CrudRepository;

public interface ShiftRepository extends CrudRepository<User, Integer> {

    //Page<UserEntity> findAll(Pageable pageable);

    Shift findBy(String name);

    Shift findByEmail(String email);
}