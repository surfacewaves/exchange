package ru.andreev.user.dto;

import lombok.Data;
import ru.andreev.user.entity.User;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class PortfolioDTO {

    private UUID id;

    private String baseCurrency;

    private Double total;

    private OffsetDateTime lastUpdatedDatetime;

    private UserDTO user;

    private List<PortfolioItemDTO> items;
}
