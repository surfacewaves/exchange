package ru.andreev.user.dto;

import lombok.Data;
import ru.andreev.user.entity.User;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class RequestDTO {

    private UUID id;

    private UserDTO createdUser;

    private String activeName;

    private Double price;

    private OffsetDateTime lastUpdatedDatetime;

    private List<UUID> boughtUserIds;

    private Double originalAmount;

    private Double currentAmount;

    private String targetCurrency;

    private DictionaryDTO status;
}
