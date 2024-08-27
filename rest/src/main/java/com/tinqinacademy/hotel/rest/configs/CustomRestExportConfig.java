package com.tinqinacademy.hotel.rest.configs;

import com.tinqinacademy.hotel.api.RouteExports;
import com.tinqinacademy.restexportprocessor.main.RestExportConfig;
import org.springframework.context.annotation.Configuration;


@RestExportConfig(destination = RouteExports.CLIENT, clientName = "HotelClient")
@Configuration
public class CustomRestExportConfig {
}
