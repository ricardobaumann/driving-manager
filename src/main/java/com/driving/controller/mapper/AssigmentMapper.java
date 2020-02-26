package com.driving.controller.mapper;

import com.driving.datatransferobject.AssigmentDTO;
import com.driving.domainobject.AssigmentDO;
import com.driving.service.car.CarService;
import com.driving.service.driver.DriverService;
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
