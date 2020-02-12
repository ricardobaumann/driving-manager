package com.freenow.domainobject;

import com.freenow.domainvalue.AssigmentStatus;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Table(name = "assigment",
        indexes = {@Index(columnList = "car_id"),
                @Index(columnList = "driver_id"),
                @Index(columnList = "status")})
public class AssigmentDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "car_id")
    private CarDO carDO;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "driver_id")
    private DriverDO driverDO;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private AssigmentStatus status = AssigmentStatus.ACTIVE;

}
