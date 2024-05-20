package ru.andreev.exchange.dto.Portfolio;

import lombok.Data;

import java.util.List;

@Data
public class PortfolioItemsDTO {

    private String baseCode;

    private List<ActiveDTO> actives;
}
