package com.freenow.controller;

import com.freenow.controller.mapper.CarMapper;
import com.freenow.datatransferobject.CarDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.service.car.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;

@RestController
@RequestMapping("/v1/cars")
public class CarController {
    private final CarMapper carMapper;
    private final CarService carService;

    public CarController(CarMapper carMapper,
                         CarService carService) {
        this.carMapper = carMapper;
        this.carService = carService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarDTO createCar(@RequestBody CarDTO carDTO) {
        CarDO carDO = carMapper.toCarDO(carDTO);
        return carMapper.toCarDTO(carService.save(carDO));
    }

    @ExceptionHandler({ConstraintViolationException.class, EntityNotFoundException.class})
    public void constraintViolationException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.CONFLICT.value());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getById(@PathVariable Long id) {
        return carService.getById(id)
                .map(carMapper::toCarDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public CarDTO updateCar(@PathVariable Long id, @RequestBody CarDTO carDTO) {
        CarDO carDO = carMapper.toCarDO(carDTO);
        carDO.setId(id);
        return carMapper.toCarDTO(carService.update(carDO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        carService.delete(id);
    }

}
