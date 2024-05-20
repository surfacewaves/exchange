package ru.andreev.user.dto;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class PortfolioItemDTO {

    private UUID id;

    private String activeName;

    private Double activeAmount;

    private OffsetDateTime lastUpdatedDatetime;
}
