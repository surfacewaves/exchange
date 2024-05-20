package ru.andreev.event;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class InfoDTO {

    private UUID id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private OffsetDateTime registeredDatetime;
}
