package com.freenow.controller.mapper;

import com.freenow.datatransferobject.AssigmentDTO;
import com.freenow.domainobject.AssigmentDO;
import com.freenow.service.car.CarService;
import com.freenow.service.driver.DriverService;
import org.springframework.stereotype.Component;

@Component
public class AssigmentMapper {

    private final CarService carService;
    private final DriverService driverService;

    public AssigmentMapper(CarService carService, DriverService driverService) {
        this.carService = carService;
        this.driverService = driverService;
    }

    public AssigmentDO toAssigmentDO(AssigmentDTO assigmentDTO) {
        return AssigmentDO.builder()
                .carDO(carService.getById(assigmentDTO.getCarId()).orElse(null))
                .driverDO(driverService.find(assigmentDTO.getDriverId()))
                .build();
    }

    public AssigmentDTO toAssigmentDTO(AssigmentDO assigment) {
        return AssigmentDTO.builder()
                .carId(assigment.getCarDO().getId())
                .driverId(assigment.getDriverDO().getId())
                .id(assigment.getId())
                .build();
    }
}
