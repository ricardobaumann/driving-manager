package com.freenow.controller;

import com.freenow.controller.mapper.CarMapper;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.EngineType;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.service.driver.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("When a car update request is handled")
class CarControllerUpdateTest {

    private final CarService carService = mock(CarService.class);

    private MockMvc mockMvc;

    private final CarDO expectedInputCarDO = CarDO.builder()
            .convertible(true)
            .engineType(EngineType.ELECTRIC)
            .licensePlate("ABC")
            .rating("A+")
            .seatCount(2)
            .id(1L)
            .build();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(
                new CarController(
                        new CarMapper(),
                        carService
                )).build();
    }

    @Test
    @DisplayName("should return ok when successfully persisted")
    void successfullyPersisted() throws Exception {
        //Given
        when(carService.update(expectedInputCarDO))
                .thenReturn(expectedInputCarDO);

        //When //Then
        mockMvc.perform(put("/v1/cars/1")
                .content("{ \"licensePlate\": \"ABC\", \"seatCount\": 2, \"convertible\": true, \"rating\": \"A+\", \"engineType\": \"ELECTRIC\"  }")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("should return conflict on entity not found")
    void failedToBePersisted() throws Exception {
        //Given
        when(carService.update(expectedInputCarDO))
                .thenThrow(EntityNotFoundException.class);

        //When //Then
        mockMvc.perform(put("/v1/cars/1")
                .content("{ \"licensePlate\": \"ABC\", \"seatCount\": 2, \"convertible\": true, \"rating\": \"A+\", \"engineType\": \"ELECTRIC\"  }")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }


}