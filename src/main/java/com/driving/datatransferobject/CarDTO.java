package com.driving.datatransferobject;

import com.driving.domainvalue.EngineType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarDTO {
    private Long id;
    private String licensePlate;
    private Integer seatCount;
    private Boolean convertible;
    private String rating;
    private EngineType engineType;
    private String manufacturer;
}
