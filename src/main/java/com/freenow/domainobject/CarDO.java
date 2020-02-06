package com.freenow.domainobject;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Table(name = "car",
        uniqueConstraints = @UniqueConstraint(name = "uc_car_license_plate", columnNames = {"licensePlate"}))
public class CarDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String licensePlate;

    @NotNull
    private Integer seatCount = 2;

    @NotNull
    private Boolean convertible = Boolean.FALSE;

    @NotNull
    private String rating;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EngineType engineType;

    @NotNull
    private String manufacturer;

    @NotNull
    private ZonedDateTime updatedAt;
}
