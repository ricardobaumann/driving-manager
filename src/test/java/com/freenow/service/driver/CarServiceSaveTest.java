package com.freenow.service.driver;

import com.freenow.dataaccessobject.CarRepository;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.EngineType;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CarServiceSaveTest {

    private final CarRepository carRepository = mock(CarRepository.class);

    private final CarService carService = new CarService(carRepository);

    private final ArgumentCaptor<CarDO> carDOArgumentCaptor = ArgumentCaptor.forClass(CarDO.class);

    @Test
    void shouldSetUpdatedAtOnSave() {
        //Given
        CarDO inputCarDo = CarDO.builder()
                .seatCount(3)
                .rating("A")
                .licensePlate("ABC")
                .engineType(EngineType.ELECTRIC)
                .convertible(false)
                .build();

        CarDO outputCarDo = CarDO.builder()
                .seatCount(3)
                .rating("A")
                .licensePlate("ABC")
                .engineType(EngineType.ELECTRIC)
                .convertible(false)
                .id(1L)
                .build();

        when(carRepository.save(any()))
                .thenReturn(outputCarDo);

        //When
        carService.save(inputCarDo);

        //Then
        verify(carRepository).save(carDOArgumentCaptor.capture());
        assertThat(carDOArgumentCaptor.getValue().getUpdatedAt()).isNotNull();
    }
}