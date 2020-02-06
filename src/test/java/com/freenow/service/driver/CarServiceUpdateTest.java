package com.freenow.service.driver;

import com.freenow.dataaccessobject.CarRepository;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.EngineType;
import com.freenow.exception.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@DisplayName("When a car is updated")
class CarServiceUpdateTest {

    private final CarRepository carRepository = mock(CarRepository.class);

    private final CarService carService = new CarService(carRepository);

    private final Long id = 1L;
    private final CarDO carDo = CarDO.builder()
            .seatCount(3)
            .rating("A")
            .licensePlate("ABC")
            .engineType(EngineType.ELECTRIC)
            .convertible(false)
            .id(id)
            .build();

    @Test
    @DisplayName("it should update car if present")
    void shouldUpdateCarIfPresent() {
        //Given
        when(carRepository.existsById(id))
                .thenReturn(true);

        when(carRepository.save(carDo))
                .thenReturn(carDo);

        //When
        CarDO result = carService.update(carDo);

        //Then
        assertThat(result).isEqualToComparingFieldByField(carDo);
        verify(carRepository).save(carDo);
    }

    @Test
    @DisplayName("it should throw exception when absent")
    void shouldThrowExceptionWhenAbsent() {
        //Given
        when(carRepository.existsById(id))
                .thenReturn(false);

        //When //Then
        assertThatThrownBy(() -> carService.update(carDo))
                .isInstanceOf(EntityNotFoundException.class);
    }
}