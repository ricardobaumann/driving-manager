package com.driving.datatransferobject;

import com.driving.domainvalue.OnlineStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportFilterDTO {

    private String driverUsername;

    private OnlineStatus onlineStatus;

    private String licensePlate;

    private String rating;

    private Integer page = 0;

    private Integer pageLimit = 1000;

}
