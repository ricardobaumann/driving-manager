package com.driving.service.driver;

import com.driving.domainobject.DriverDO;
import com.driving.domainvalue.OnlineStatus;
import com.driving.exception.ConstraintsViolationException;
import com.driving.exception.EntityNotFoundException;

import java.util.List;

public interface DriverService {

    DriverDO find(Long driverId) throws EntityNotFoundException;

    DriverDO create(DriverDO driverDO) throws ConstraintsViolationException;

    void delete(Long driverId) throws EntityNotFoundException;

    void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException;

    List<DriverDO> find(OnlineStatus onlineStatus);

}
