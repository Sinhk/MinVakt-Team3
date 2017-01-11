package minvakt.repos;

import minvakt.datamodel.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    //Page<UserEntity> findAll(Pageable pageable);

    User findByFirstName(String name);

    User findByEmail(String email);
}