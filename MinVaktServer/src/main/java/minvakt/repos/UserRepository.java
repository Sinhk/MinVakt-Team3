package minvakt.repos;

import minvakt.datamodel.Shift;
import minvakt.datamodel.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByFirstName(String name);

    User findByEmail(String email);

    List<User> findByShiftAssignments_User(Shift shift);

    //void setPassword(User user, String password);

}