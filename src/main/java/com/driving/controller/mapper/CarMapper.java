package com.driving.controller.mapper;

import com.driving.datatransferobject.CarDTO;
import com.driving.domainobject.CarDO;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {
    public CarDO toCarDO(CarDTO carDTO) {
        return CarDO.builder()
                .convertible(carDTO.getConvertible())
                .engineType(carDTO.getEngineType())
                .licensePlate(carDTO.getLicensePlate())
                .rating(carDTO.getRating())
                .seatCount(carDTO.getSeatCount())
                .manufacturer(carDTO.getManufacturer())
                .build();
    }

    public CarDTO toCarDTO(CarDO carDO) {
        return CarDTO.builder()
                .convertible(carDO.getConvertible())
                .engineType(carDO.getEngineType())
                .licensePlate(carDO.getLicensePlate())
                .rating(carDO.getRating())
                .seatCount(carDO.getSeatCount())
                .manufacturer(carDO.getManufacturer())
                .id(carDO.getId())
                .build();
    }
}
