package ru.andreev.currencyapiadapter.dto;

import lombok.Data;

@Data
public class RateRequestJmsDTO {

    private String currencyCode;

    private String type;

    private String rateDate;
}