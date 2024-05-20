package ru.andreev.exchange.dto.Rate;

import lombok.Data;

import java.util.List;

@Data
public class LatestRatesDTO {

    private String dateOfRates;

    private List<MonitoringRateDTO> rates;
}
