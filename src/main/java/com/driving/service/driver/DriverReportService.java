package com.driving.service.driver;

import com.driving.dataaccessobject.DriverRepository;
import com.driving.datatransferobject.ReportFilterDTO;
import com.driving.domainobject.DriverDO;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverReportService {

    private final DriverRepository driverRepository;

    public DriverReportService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public List<DriverDO> generateReport(ReportFilterDTO reportFilterDTO) {
        return driverRepository.findBy(
                reportFilterDTO.getDriverUsername(),
                reportFilterDTO.getLicensePlate(),
                reportFilterDTO.getOnlineStatus(),
                reportFilterDTO.getRating(),
                PageRequest.of(
                        reportFilterDTO.getPage(),
                        reportFilterDTO.getPageLimit())
        );
    }
}
