package ru.andreev.exchange.dto.Rate;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class MonitoringRateDTO {

    private String fromCode;

    private String toCode;

    private Double price;

    private Double maxPriceForLastDay;

    private Double minPriceForLastDay;

    private Double difference;

    private OffsetDateTime date;
}
