package com.freenow.dataaccessobject;

import com.freenow.domainobject.AssigmentDO;
import com.freenow.domainobject.CarDO;
import com.freenow.domainvalue.AssigmentStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssigmentRepository extends CrudRepository<AssigmentDO, Long> {

    Optional<AssigmentDO> findByCarDOAndStatus(CarDO carDO, AssigmentStatus assigmentStatus);

    Optional<AssigmentDO> findByIdAndStatus(Long id, AssigmentStatus active);
}
