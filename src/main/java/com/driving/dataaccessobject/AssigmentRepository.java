package com.driving.dataaccessobject;

import com.driving.domainobject.AssigmentDO;
import com.driving.domainobject.CarDO;
import com.driving.domainvalue.AssigmentStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssigmentRepository extends CrudRepository<AssigmentDO, Long> {

    Optional<AssigmentDO> findByCarDOAndStatus(CarDO carDO, AssigmentStatus assigmentStatus);

    Optional<AssigmentDO> findByIdAndStatus(Long id, AssigmentStatus active);
}
