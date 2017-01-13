package minvakt.repos;

import minvakt.datamodel.UserShiftInfo;
import minvakt.datamodel.UserShiftInfoId;
import org.springframework.data.repository.CrudRepository;

public interface UserShiftRepository extends CrudRepository<UserShiftInfo, UserShiftInfoId> {

}