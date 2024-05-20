package ru.andreev.exchange.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.andreev.exchange.entity.base.BaseEntity;

import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@Table(name = "rate")
public class Rate extends BaseEntity {

    @Column(name = "date", nullable = false)
    private OffsetDateTime date;

    @Column(name = "base_code", nullable = false)
    private String baseCode;

    @Column(name = "conversion_code", nullable = false)
    private String conversionCode;

    @Column(name = "conversion_rate", nullable = false)
    private Double conversionRate;

    @ManyToOne()
    @JoinColumn(name = "rate_request_id", nullable = false)
    private RateRequest rateRequest;

    @Column(name = "max_value_for_day")
    private Double maxValueForDay;

    @Column(name = "min_value_for_day")
    private Double minValueForDay;

    @Column(name = "difference_with_previous")
    private Double differenceWithPrevious;
}
