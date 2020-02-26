package com.driving.controller;

import com.driving.datatransferobject.ReportFilterDTO;
import com.driving.domainobject.DriverDO;
import com.driving.domainvalue.OnlineStatus;
import com.driving.service.driver.DriverReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class ReportControllerTest {

    private final DriverReportService driverReportService = mock(DriverReportService.class);

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(
                new ReportController(driverReportService)
        ).build();
    }

    @Test
    void shouldGenerateReport() throws Exception {
        //Given
        when(driverReportService.generateReport(ReportFilterDTO.builder()
                .driverUsername("first")
                .onlineStatus(OnlineStatus.ONLINE)
                .page(0)
                .pageLimit(1000)
                .build()))
                .thenReturn(Collections.singletonList(DriverDO.builder()
                        .onlineStatus(OnlineStatus.ONLINE)
                        .username("first")
                        .build()));

        //When //Then
        mockMvc.perform(post("/v1/report/driver")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"onlineStatus\": \"ONLINE\", \"driverUsername\": \"first\"}"))
                .andExpect(jsonPath("$[0].username", is("first")));
    }
}