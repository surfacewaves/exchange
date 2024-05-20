package ru.andreev.exchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.andreev.exchange.entity.Rate;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RateRepository extends JpaRepository<Rate, UUID>, JpaSpecificationExecutor<Rate> {

    @Query("SELECT r FROM Rate r WHERE r.baseCode='RUB' " +
            "AND r.conversionCode IN('USD', 'EUR', 'GBP', 'AUD', 'JPY', 'KRW', 'CNY')" +
            "AND r.date = (SELECT MAX(r1.date) FROM Rate r1 WHERE r1.baseCode = 'RUB')")
    List<Rate> getLatestPopularRates();

    @Query("SELECT r FROM Rate r WHERE r.baseCode = :currencyCode " +
            "AND r.date = (SELECT MAX(r1.date) FROM Rate r1 WHERE r1.baseCode = :currencyCode)")
    List<Rate> getLatestRatesByCurrencyCode(@Param("currencyCode") String currencyCode);

    List<Rate> getRatesByDateAndBaseCode(OffsetDateTime date, String baseCode);

    @Query("SELECT r FROM Rate r WHERE r.baseCode = :baseCode AND r.conversionCode = :conversionCode AND r.date = " +
            "(SELECT MAX(r1.date) FROM Rate r1 WHERE r1.baseCode = :baseCode AND r1.conversionCode = :conversionCode)")
    Optional<Rate> findLatestByBaseCodeAndConversionCode(
            @Param("baseCode") String baseCode, @Param("conversionCode") String conversionCode);
}
