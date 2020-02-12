package com.freenow.service.car;

import com.freenow.dataaccessobject.CarRepository;
import com.freenow.domainobject.CarDO;
import com.freenow.exception.EntityNotFoundException;
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
        return carRepository.save(carDO);
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
