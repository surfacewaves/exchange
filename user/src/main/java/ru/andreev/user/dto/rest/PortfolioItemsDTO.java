package ru.andreev.user.dto.rest;

import lombok.Data;

import java.util.List;

@Data
public class PortfolioItemsDTO {

    private String baseCode;

    private List<ActiveDTO> actives;
}
