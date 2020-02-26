package com.driving.controller;

import com.driving.controller.mapper.CarMapper;
import com.driving.domainobject.CarDO;
import com.driving.domainvalue.EngineType;
import com.driving.service.car.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("When a car create request is handled")
class CarControllerCreateTest {

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

    @Nested
    @DisplayName("and the car was successfully created")
    class WhenSuccessfullyCreated {
        private ResultActions result;
        private final ArgumentCaptor<CarDO> carDOArgumentCaptor = ArgumentCaptor.forClass(CarDO.class);

        private final CarDO expectedInputCarDO = CarDO.builder()
                .convertible(true)
                .engineType(EngineType.ELECTRIC)
                .licensePlate("ABC")
                .rating("A+")
                .seatCount(2)
                .manufacturer("me")
                .build();

        private final CarDO expectedOutputCarDO = CarDO.builder()
                .convertible(true)
                .engineType(EngineType.ELECTRIC)
                .licensePlate("ABC")
                .rating("A+")
                .seatCount(2)
                .manufacturer("me")
                .id(1L)
                .build();

        @BeforeEach
        void setUp() throws Exception {

            when(carService.save(expectedInputCarDO))
                    .thenReturn(expectedOutputCarDO);

            result = mockMvc.perform(post("/v1/cars")
                    .content("{ \"licensePlate\": \"ABC\", \"seatCount\": 2, \"convertible\": true, \"rating\": \"A+\", \"engineType\": \"ELECTRIC\", \"manufacturer\": \"me\"  }")
                    .contentType(MediaType.APPLICATION_JSON));

        }

        @Test
        @DisplayName("it should map input request")
        void inputIsMapped() {
            verify(carService).save(carDOArgumentCaptor.capture());
            assertThat(carDOArgumentCaptor.getValue())
                    .isEqualToComparingFieldByField(expectedInputCarDO);
        }

        @Test
        @DisplayName("it should return CREATED status code")
        void returnCreated() throws Exception {
            result.andExpect(status().isCreated());
        }

        @Test
        @DisplayName("it should return the generated car id")
        void idReturned() throws Exception {
            result.andExpect(jsonPath("$.id", is(1)));
        }

    }

    @Nested
    @DisplayName("and the service validation fails")
    class WhenValidationFails {
        private ResultActions result;

        @BeforeEach
        void setUp() throws Exception {
            CarDO expectedInputCarDO = CarDO.builder()
                    .convertible(true)
                    .engineType(EngineType.ELECTRIC)
                    .licensePlate("ABC")
                    .rating("A+")
                    .seatCount(2)
                    .build();

            when(carService.save(expectedInputCarDO))
                    .thenThrow(ConstraintViolationException.class);

            result = mockMvc.perform(post("/v1/cars")
                    .content("{ \"licensePlate\": \"ABC\", \"seatCount\": 2, \"convertible\": true, \"rating\": \"A+\", \"engineType\": \"ELECTRIC\"  }")
                    .contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        @DisplayName("it should return CONFLICT status code")
        void returnConflict() throws Exception {
            result.andExpect(status().isConflict());
        }
    }

}