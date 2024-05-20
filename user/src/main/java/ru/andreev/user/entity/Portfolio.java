package ru.andreev.user.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.andreev.user.entity.base.BaseEntity;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "portfolio")
public class Portfolio extends BaseEntity {

    @Column(name = "base_currency", nullable = false)
    private String baseCurrency;

    @Column(name = "total", nullable = false)
    private Double total;

    @Column(name = "last_updated_datetime", nullable = false)
    private OffsetDateTime lastUpdatedDatetime;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<PortfolioItem> items;
}
