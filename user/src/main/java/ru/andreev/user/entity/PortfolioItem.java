package ru.andreev.user.entity;

import jakarta.persistence.*;
import lombok.Data;
import ru.andreev.user.entity.base.BaseEntity;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "portfolio_item")
public class PortfolioItem extends BaseEntity {

    @Column(name = "active_name", nullable = false)
    private String activeName;

    @Column(name = "active_amount", nullable = false)
    private Double activeAmount;

    @Column(name = "last_updated_datetime", nullable = false)
    private OffsetDateTime lastUpdatedDatetime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;
}
