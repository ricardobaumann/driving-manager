package com.freenow.domainobject;

import com.freenow.domainvalue.EngineType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
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

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
