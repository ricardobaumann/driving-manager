package com.driving.controller;

import com.driving.controller.mapper.CarMapper;
import com.driving.domainobject.CarDO;
import com.driving.domainvalue.EngineType;
import com.driving.service.car.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("When a car is requested by id")
class CarControllerGetByIdTest {

    private final CarService carService = mock(CarService.class);

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(
                new CarController(
                        new CarMapper(),
                        carService
                )).build();
    }

    @Test
    @DisplayName("it should return the car when present")
    void shouldReturnCarByIdWhenPresent() throws Exception {
        //Given
        Long id = 1L;
        CarDO carDo = CarDO.builder()
                .id(id)
                .convertible(true)
                .engineType(EngineType.ELECTRIC)
                .licensePlate("ABC")
                .rating("B-")
                .seatCount(3)
                .build();

        when(carService.getById(id))
                .thenReturn(Optional.of(carDo));

        //When //Then
        mockMvc.perform(get("/v1/cars/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.convertible", is(true)))
                .andExpect(jsonPath("$.engineType", is("ELECTRIC")))
                .andExpect(jsonPath("$.licensePlate", is("ABC")))
                .andExpect(jsonPath("$.rating", is("B-")))
                .andExpect(jsonPath("$.seatCount", is(3)));
    }

    @Test
    @DisplayName("it should return not found when car is absent")
    void shouldReturnNotFoundWhenAbsent() throws Exception {
        //Given
        when(carService.getById(1L))
                .thenReturn(Optional.empty());

        //When //Then
        mockMvc.perform(get("/v1/cars/1"))
                .andExpect(status().isNotFound());
    }
}