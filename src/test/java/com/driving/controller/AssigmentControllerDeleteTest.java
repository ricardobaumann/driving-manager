package com.driving.controller;

import com.driving.exception.AssigmentNotFoundException;
import com.driving.service.car.AssigmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("When an assigment delete request is handled")
class AssigmentControllerDeleteTest {

    private final AssigmentService assigmentService = mock(AssigmentService.class);

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(
                new AssigmentController(null, assigmentService)
        ).build();
    }

    @Test
    @DisplayName("it should return no content if successful")
    void shouldReturnNoContentIfSuccessful() throws Exception {
        //Given
        doNothing().when(assigmentService).finishAssigment(1L);

        //When //then
        mockMvc.perform(delete("/v1/assigment/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("it should return not found when absent")
    void shouldReturnNotFoundIfAbsent() throws Exception {
        //Given
        doThrow(new AssigmentNotFoundException(1L))
                .when(assigmentService).finishAssigment(1L);

        //When //then
        mockMvc.perform(delete("/v1/assigment/1"))
                .andExpect(status().isNotFound());
    }
}