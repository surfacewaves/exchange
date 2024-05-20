package ru.andreev.exchange.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import ru.andreev.exchange.dto.Rate.LatestRatesDTO;
import ru.andreev.exchange.dto.Rate.MonitoringRateDTO;
import ru.andreev.exchange.entity.Rate;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Component
@RequiredArgsConstructor
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = SPRING)
public abstract class RateMapper {

    @Mapping(target = "fromCode", source = "baseCode")
    @Mapping(target = "toCode", source = "conversionCode")
    @Mapping(target = "price", source = "conversionRate", qualifiedByName = "roundDouble")
    @Mapping(target = "maxPriceForLastDay", source = "maxValueForDay", qualifiedByName = "roundDouble")
    @Mapping(target = "minPriceForLastDay", source = "minValueForDay", qualifiedByName = "roundDouble")
    @Mapping(target = "difference", source = "differenceWithPrevious", qualifiedByName = "roundDouble")
    public abstract MonitoringRateDTO mapToPopularRateDTO(Rate rate);

    public abstract List<MonitoringRateDTO> mapListToPopularRateDTO(List<Rate> rates);

    @Mapping(target = "dateOfRates", source = "dateOfRates", qualifiedByName = "formatDate")
    public abstract LatestRatesDTO mapToLatestRatesDTO(OffsetDateTime dateOfRates, List<MonitoringRateDTO> rates);

    // TODO: перенести округление на фронт
    @Named("roundDouble")
    public Double roundDouble(Double d) {
        if (d == null) {
            return null;
        }

        return Math.round(d * 10000.0) / 10000.0;
    }

    @Named("formatDate")
    public String formatDate(OffsetDateTime date) {
        OffsetDateTime targetDateTime = date.withOffsetSameInstant(ZoneOffset.ofHours(3));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        return targetDateTime.format(formatter);
    }
}
