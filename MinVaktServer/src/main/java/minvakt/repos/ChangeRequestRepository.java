package minvakt.repos;

import minvakt.datamodel.ChangeRequest;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by magnu on 18.01.2017.
 */
public interface ChangeRequestRepository extends CrudRepository<ChangeRequest, Integer> {


}
