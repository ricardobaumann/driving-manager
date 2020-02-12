package com.freenow.service.car;

import com.freenow.dataaccessobject.CarRepository;
import com.freenow.domainobject.CarDO;
import com.freenow.exception.DuplicateLicensePlateException;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

class CarServiceSaveTest {

    private final CarRepository carRepository = mock(CarRepository.class);

    private final CarService carService = new CarService(carRepository);

    @Test
    void shouldMapIntegrityException() {
        //Given
        doThrow(new DataIntegrityViolationException("test"))
                .when(carRepository).save(any());

        //When //Then
        assertThatThrownBy(() -> carService.save(CarDO.builder().build()))
                .isInstanceOf(DuplicateLicensePlateException.class);
    }
}