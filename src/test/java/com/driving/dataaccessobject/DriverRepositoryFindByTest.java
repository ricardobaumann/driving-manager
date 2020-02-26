package com.driving.dataaccessobject;

import com.driving.domainobject.AssigmentDO;
import com.driving.domainobject.CarDO;
import com.driving.domainobject.DriverDO;
import com.driving.domainvalue.AssigmentStatus;
import com.driving.domainvalue.EngineType;
import com.driving.domainvalue.GeoCoordinate;
import com.driving.domainvalue.OnlineStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DriverRepositoryFindByTest {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private AssigmentRepository assigmentRepository;
    private CarDO firstCar;
    private CarDO secondCar;
    private DriverDO onlineDriver;
    private DriverDO offlineDriver;

    @BeforeEach
    void setUp() {

        assigmentRepository.deleteAll();
        carRepository.deleteAll();
        driverRepository.deleteAll();

        firstCar = carRepository.save(CarDO.builder()
                .manufacturer("VW")
                .seatCount(2)
                .rating("A+")
                .licensePlate("ABC")
                .engineType(EngineType.ELECTRIC)
                .convertible(true)
                .build());

        secondCar = carRepository.save(CarDO.builder()
                .manufacturer("VW")
                .seatCount(3)
                .rating("B")
                .licensePlate("DEF")
                .engineType(EngineType.ELECTRIC)
                .convertible(true)
                .build());

        onlineDriver = driverRepository.save(DriverDO.builder()
                .password("ff")
                .username("first")
                .dateCoordinateUpdated(ZonedDateTime.now())
                .dateCreated(ZonedDateTime.now())
                .deleted(false)
                .coordinate(new GeoCoordinate(0.0, 0.0))
                .onlineStatus(OnlineStatus.ONLINE)
                .build());

        offlineDriver = driverRepository.save(DriverDO.builder()
                .password("ff")
                .username("second")
                .dateCoordinateUpdated(ZonedDateTime.now())
                .dateCreated(ZonedDateTime.now())
                .deleted(false)
                .coordinate(new GeoCoordinate(0.0, 0.0))
                .onlineStatus(OnlineStatus.OFFLINE)
                .build());

        assigmentRepository.save(AssigmentDO.builder()
                .carDO(firstCar)
                .driverDO(onlineDriver)
                .status(AssigmentStatus.ACTIVE)
                .build());

        assigmentRepository.save(AssigmentDO.builder()
                .carDO(secondCar)
                .driverDO(offlineDriver)
                .status(AssigmentStatus.ACTIVE)
                .build());

    }

    @Test
    void shouldFindByUsername() {
        assertThat(driverRepository.findBy(
                "first",
                null, null, null, PageRequest.of(0, 10)))
                .containsExactlyInAnyOrder(onlineDriver);
    }

    @Test
    void shouldFindByLicensePlate() {
        assertThat(driverRepository.findBy(
                null,
                "DEF", null, null, PageRequest.of(0, 10)))
                .containsExactlyInAnyOrder(offlineDriver);
    }

    @Test
    void shouldFindByOnlineStatus() {
        assertThat(driverRepository.findBy(
                null,
                null, OnlineStatus.ONLINE, null, PageRequest.of(0, 10)))
                .containsExactlyInAnyOrder(onlineDriver);
    }

    @Test
    void shouldFindByRating() {
        assertThat(driverRepository.findBy(
                null,
                null, null, "B", PageRequest.of(0, 10)))
                .containsExactlyInAnyOrder(offlineDriver);
    }
}