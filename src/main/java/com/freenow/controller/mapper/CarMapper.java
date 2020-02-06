package com.freenow.controller.mapper;

import com.freenow.datatransferobject.CarDTO;
import com.freenow.domainobject.CarDO;
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
