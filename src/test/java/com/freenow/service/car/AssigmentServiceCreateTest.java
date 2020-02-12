package com.freenow.service.car;

import com.freenow.dataaccessobject.AssigmentRepository;
import com.freenow.domainobject.AssigmentDO;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.AssigmentStatus;
import com.freenow.domainvalue.OnlineStatus;
import com.freenow.exception.CarNotAvailableException;
import com.freenow.exception.DriverNotOnlineException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("When an assigment is created")
class AssigmentServiceCreateTest {

    private final AssigmentRepository assigmentRepository = mock(AssigmentRepository.class);

    private final AssigmentService assigmentService = new AssigmentService(assigmentRepository);

    private final CarDO carDO = CarDO.builder()
            .id(2L)
            .build();

    @Nested
    @DisplayName("and the driver is online")
    class DriverIsOnline {

        private final DriverDO driver = DriverDO.builder()
                .onlineStatus(OnlineStatus.ONLINE)
                .id(1L)
                .build();


        private final AssigmentDO assigmentDO = AssigmentDO.builder()
                .carDO(carDO)
                .driverDO(driver)
                .build();

        @Nested
        @DisplayName("and the car is available")
        class CarIsAvailable {

            private AssigmentDO result;

            @BeforeEach
            void setUp() {
                when(assigmentRepository.findByCarDOAndStatus(carDO, AssigmentStatus.ACTIVE))
                        .thenReturn(Optional.empty());


                when(assigmentRepository.save(assigmentDO))
                        .thenReturn(AssigmentDO.builder()
                                .id(3L)
                                .carDO(carDO)
                                .driverDO(driver)
                                .build());

                result = assigmentService.createAssigment(assigmentDO);
            }

            @Test
            void shouldCreateAssigment() {
                assertThat(result.getId()).isEqualTo(3L);
            }

        }

        @Nested
        @DisplayName("and the car is not available")
        class CarIsNotAvailable {

            @BeforeEach
            void setUp() {
                when(assigmentRepository.findByCarDOAndStatus(carDO, AssigmentStatus.ACTIVE))
                        .thenReturn(Optional.of(AssigmentDO.builder()
                                .id(4L)
                                .build()));

            }

            @Test
            @DisplayName("it should throw an exception")
            void shouldThrowException() {
                assertThatThrownBy(() ->
                        assigmentService.createAssigment(assigmentDO))
                        .isInstanceOf(CarNotAvailableException.class);
            }

        }

    }

    @Nested
    @DisplayName("and the driver is not online")
    class DriverIsNotOnline {

        private final DriverDO driver = DriverDO.builder()
                .onlineStatus(OnlineStatus.OFFLINE)
                .id(1L)
                .build();

        @Test
        @DisplayName("it should throw an exception")
        void shouldThrowException() {
            assertThatThrownBy(() ->
                    assigmentService.createAssigment(AssigmentDO.builder()
                            .driverDO(driver)
                            .carDO(carDO)
                            .build()))
                    .isInstanceOf(DriverNotOnlineException.class);
        }

    }


}