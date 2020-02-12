package com.freenow.service.car;

import com.freenow.dataaccessobject.CarRepository;
import com.freenow.domainobject.CarDO;
import com.freenow.exception.DuplicateLicensePlateException;
import com.freenow.exception.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;

@Service
@Validated
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public CarDO save(@Valid CarDO carDO) {
        try {
            return carRepository.save(carDO);
        } catch (DataIntegrityViolationException e) {
            //could be upgraded to handle different types of violations
            throw new DuplicateLicensePlateException(carDO.getLicensePlate());
        }
    }

    public Optional<CarDO> getById(Long id) {
        return carRepository.findById(id);
    }

    public CarDO update(@Valid CarDO carDO) {
        return Optional.ofNullable(carDO.getId())
                .filter(carRepository::existsById)
                .map(exists -> save(carDO))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Car id %d not found for update", carDO.getId())));
    }

    public void delete(Long id) {
        carRepository.deleteById(id);
    }
}
