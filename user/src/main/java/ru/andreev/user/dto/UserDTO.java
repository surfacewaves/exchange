package ru.andreev.user.dto;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class UserDTO {

    private UUID id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private OffsetDateTime registeredDatetime;
}
