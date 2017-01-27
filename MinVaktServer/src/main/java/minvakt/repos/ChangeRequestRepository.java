package minvakt.repos;

import minvakt.datamodel.tables.pojos.ChangeRequest;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ChangeRequestRepository extends CrudRepository<ChangeRequest, Integer> {
}
