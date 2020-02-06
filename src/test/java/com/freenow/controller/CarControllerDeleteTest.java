package com.freenow.controller;

import com.freenow.controller.mapper.CarMapper;
import com.freenow.service.driver.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("When a car delete request is handled")
class CarControllerDeleteTest {

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
    @DisplayName("it should return NO_CONTENT")
    void noContentOnDelete() throws Exception {
        //Given
        doNothing().when(carService).delete(1L);

        //When //Then
        mockMvc.perform(delete("/v1/cars/1"))
                .andExpect(status().isNoContent());
    }
}