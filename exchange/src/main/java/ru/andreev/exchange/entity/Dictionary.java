package ru.andreev.exchange.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import ru.andreev.exchange.entity.base.BaseEntity;

@Entity
@Table(name = "dictionary")
@Data
public class Dictionary extends BaseEntity {

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "key", nullable = false)
    private String key;

    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "description")
    private String description;
}
