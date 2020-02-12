package com.freenow.dataaccessobject;

import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.OnlineStatus;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface DriverRepository extends CrudRepository<DriverDO, Long> {

    List<DriverDO> findByOnlineStatus(OnlineStatus onlineStatus);

    @Query("select driver from DriverDO driver " +
            "   left join AssigmentDO assigment " +
            "     on driver = assigment.driverDO " +
            "      and assigment.status = com.freenow.domainvalue.AssigmentStatus.ACTIVE " +
            "   left join assigment.carDO car " +
            "where (driver.username = ?1 or ?1 is null) " +
            "and (car.licensePlate = ?2 or ?2 is null)" +
            "and (driver.onlineStatus = ?3 or ?3 is null) " +
            "and (car.rating = ?4 or ?4 is null)")
    List<DriverDO> findBy(String driverUsername,
                          String licensePlate,
                          OnlineStatus onlineStatus,
                          String rating,
                          PageRequest pageRequest);
}
