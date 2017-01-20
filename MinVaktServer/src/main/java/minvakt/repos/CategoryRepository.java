package minvakt.repos;

import minvakt.datamodel.tables.pojos.EmployeeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * MinVakt-Team3
 * 15.01.2017
 */
public interface CategoryRepository extends JpaRepository<EmployeeCategory, Integer> {

}
