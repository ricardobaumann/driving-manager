package com.driving.controller;

import com.driving.controller.mapper.DriverMapper;
import com.driving.datatransferobject.DriverDTO;
import com.driving.datatransferobject.ReportFilterDTO;
import com.driving.service.driver.DriverReportService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ReportController {

    private final DriverReportService driverReportService;

    public ReportController(DriverReportService driverReportService) {
        this.driverReportService = driverReportService;
    }

    @PostMapping("/v1/report/driver")
    public List<DriverDTO> generateReport(@RequestBody ReportFilterDTO reportFilterDTO) {
        return driverReportService.generateReport(reportFilterDTO)
                .stream()
                .map(DriverMapper::makeDriverDTO).collect(Collectors.toList());
    }

}
