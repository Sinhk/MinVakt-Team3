package minvakt.repos;

import minvakt.datamodel.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

    //Page<UserEntity> findAll(Pageable pageable);

    UserEntity findByFirstName(String name);

}