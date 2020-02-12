package com.freenow.datatransferobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssigmentDTO {
    private Long id;

    @NotNull
    private Long carId;

    @NotNull
    private Long driverId;
}
