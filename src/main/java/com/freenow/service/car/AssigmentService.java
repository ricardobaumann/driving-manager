package com.freenow.service.car;

import com.freenow.dataaccessobject.AssigmentRepository;
import com.freenow.domainobject.AssigmentDO;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.AssigmentStatus;
import com.freenow.exception.AssigmentNotFoundException;
import com.freenow.exception.CarNotAvailableException;
import com.freenow.exception.DriverNotOnlineException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Service
@Validated
public class AssigmentService {

    private final AssigmentRepository assigmentRepository;

    public AssigmentService(AssigmentRepository assigmentRepository) {
        this.assigmentRepository = assigmentRepository;
    }

    @Transactional
    public AssigmentDO createAssigment(@Valid AssigmentDO assigmentDO) {
        validateDriver(assigmentDO.getDriverDO());
        validateCar(assigmentDO.getCarDO());
        assigmentDO.setStatus(AssigmentStatus.ACTIVE);
        return assigmentRepository.save(assigmentDO);
    }

    private void validateCar(CarDO carDO) {
        Optional.ofNullable(carDO)
                .map(car -> assigmentRepository.findByCarDOAndStatus(car, AssigmentStatus.ACTIVE))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .ifPresent(previousAssigment -> {
                    throw new CarNotAvailableException(
                            String.format("Car id %d still active on assigment id %d",
                                    carDO.getId(),
                                    previousAssigment.getId())
                    );
                });
    }

    private void validateDriver(DriverDO driverDO) {
        Optional.ofNullable(driverDO)
                .filter(DriverDO::isOnLine)
                .orElseThrow(DriverNotOnlineException::new);
    }

    @Transactional
    public void finishAssigment(Long id) {
        AssigmentDO assigment = assigmentRepository.findByIdAndStatus(id, AssigmentStatus.ACTIVE)
                .orElseThrow(() -> new AssigmentNotFoundException(id));
        assigment.setStatus(AssigmentStatus.FINISHED);
        assigmentRepository.save(assigment);
    }
}
