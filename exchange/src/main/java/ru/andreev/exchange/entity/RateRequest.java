package ru.andreev.exchange.entity;

import jakarta.persistence.*;
import lombok.Data;
import ru.andreev.exchange.entity.base.BaseEntity;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "rate_request")
@Data
public class RateRequest extends BaseEntity {

    @Column(name = "created_date", nullable = false)
    private OffsetDateTime createdDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status", nullable = false)
    private Dictionary status;

    @Column(name = "currency_code", nullable = false)
    private String currencyCode;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type", nullable = false)
    private Dictionary type;

    @Column(name = "rate_date")
    private LocalDate rateDate;

    @OneToMany(mappedBy = "rateRequest")
    private List<Rate> rates;
}
