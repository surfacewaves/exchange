package ru.andreev.user.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user_")
@Data
public class User {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "registered_datetime", nullable = false)
    private OffsetDateTime registeredDatetime;

    @ManyToMany(mappedBy = "boughtUsers")
    private List<Request> requests;

    @OneToOne(mappedBy = "user")
    private Portfolio portfolio;
}
