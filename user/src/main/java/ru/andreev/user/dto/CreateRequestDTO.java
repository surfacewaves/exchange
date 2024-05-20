package ru.andreev.user.dto;

import lombok.Data;

@Data
public class CreateRequestDTO {

    private String activeName;

    private Double price;

    private Double originalAmount;

    private String targetCurrency;
}
