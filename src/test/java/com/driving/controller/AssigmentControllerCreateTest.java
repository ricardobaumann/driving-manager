package com.driving.controller;

import com.driving.controller.mapper.AssigmentMapper;
import com.driving.datatransferobject.AssigmentDTO;
import com.driving.domainobject.AssigmentDO;
import com.driving.domainobject.CarDO;
import com.driving.domainobject.DriverDO;
import com.driving.exception.CarNotAvailableException;
import com.driving.exception.DriverNotOnlineException;
import com.driving.exception.EntityNotFoundException;
import com.driving.service.car.AssigmentService;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("When an assigment creation request is handled")
class AssigmentControllerCreateTest {

    private final AssigmentService assigmentService = mock(AssigmentService.class);
    private final AssigmentMapper assigmentMapper = mock(AssigmentMapper.class);

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(
                new AssigmentController(
                        assigmentMapper,
                        assigmentService
                )
        ).build();
    }

    @Nested
    @DisplayName("and it was successfully created")
    class SuccessfullyCreated {

        private ResultActions result;

        @BeforeEach()
        void setUp() throws Exception {

            AssigmentDO outputAssigmentDO = AssigmentDO.builder()
                    .id(1L)
                    .driverDO(DriverDO.builder().id(2L).build())
                    .carDO(CarDO.builder().id(3L).build())
                    .build();

            AssigmentDO inputAssigmentDO = AssigmentDO.builder()
                    .driverDO(DriverDO.builder().id(2L).build())
                    .carDO(CarDO.builder().id(3L).build())
                    .build();

            when(assigmentMapper.toAssigmentDO(any()))
                    .thenReturn(inputAssigmentDO);

            when(assigmentService.createAssigment(inputAssigmentDO))
                    .thenReturn(outputAssigmentDO);

            result = mockMvc.perform(post("/v1/assigment")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"carId\": 3, \"driverId\": 2}"));
        }

        @Test
        @DisplayName("it should return CREATED status code")
        void shouldReturnCreated() throws Exception {
            result.andExpect(status().isCreated());
        }

        @Test
        @DisplayName("it should map input request to service class")
        void shouldMapInput() {
            ArgumentCaptor<AssigmentDTO> argumentCaptor = ArgumentCaptor.forClass(AssigmentDTO.class);
            verify(assigmentMapper).toAssigmentDO(argumentCaptor.capture());
            assertThat(argumentCaptor.getValue())
                    .isEqualToComparingFieldByField(AssigmentDTO.builder()
                            .driverId(2L)
                            .carId(3L)
                            .build());
        }
    }

    @TestFactory
    Stream<DynamicTest> shouldReturnConflictOnExceptions() {

        List<Class<? extends RuntimeException>> exceptions = Arrays.asList(
                ConstraintViolationException.class,
                EntityNotFoundException.class,
                DriverNotOnlineException.class,
                CarNotAvailableException.class
        );

        return exceptions.stream().map(exceptionClass -> DynamicTest.dynamicTest(
                String.format("When an exception %s happen, a CONFLICT status code should be returned", exceptionClass.getSimpleName()),
                () -> {

                    doThrow(exceptionClass).when(assigmentService)
                            .createAssigment(any());

                    mockMvc.perform(post("/v1/assigment")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"carId\": 3, \"driverId\": 2}"))
                            .andExpect(status().isConflict());
                }
        ));


    }

}