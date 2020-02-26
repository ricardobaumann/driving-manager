package com.driving.dataaccessobject;

import com.driving.domainobject.CarDO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends CrudRepository<CarDO, Long> {
}
