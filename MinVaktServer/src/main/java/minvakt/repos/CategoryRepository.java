package minvakt.repos;

import minvakt.datamodel.EmployeeCategory;
import org.springframework.data.repository.CrudRepository;

/**
 * MinVakt-Team3
 * 15.01.2017
 */
public interface CategoryRepository extends CrudRepository<EmployeeCategory, Integer> {

}
