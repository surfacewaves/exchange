package ru.andreev.user.entity;

import jakarta.persistence.*;
import lombok.Data;
import ru.andreev.user.entity.base.BaseEntity;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "request")
@Data
public class Request extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "created_user_id", nullable = false)
    private User createdUser;

    @Column(name = "active_name", nullable = false)
    private String activeName;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "last_updated_datetime", nullable = false)
    private OffsetDateTime lastUpdatedDatetime;

    @Column(name = "original_amount", nullable = false)
    private Double originalAmount;

    @Column(name = "current_amount", nullable = false)
    private Double currentAmount;

    @Column(name = "target_currency", nullable = false)
    private String targetCurrency;

    @ManyToOne
    @JoinColumn(name = "status", nullable = false)
    private Dictionary status;

    @ManyToMany
    @JoinTable(
            name = "user_request",
            joinColumns = @JoinColumn(name = "request_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> boughtUsers;
}
