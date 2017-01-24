package minvakt.repos;

import minvakt.datamodel.tables.pojos.Department;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by magnu on 24.01.2017.
 */
public interface DepartmentRepository extends JpaRepository<Department, Short> {

}
