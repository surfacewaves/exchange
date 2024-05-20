package ru.andreev.exchange.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import ru.andreev.exchange.dto.RateRequest.RateRequestJmsDTO;
import ru.andreev.exchange.entity.Dictionary;
import ru.andreev.exchange.entity.RateRequest;

import java.time.LocalDate;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Component
@RequiredArgsConstructor
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = SPRING)
public abstract class RateRequestMapper {

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "createdDate", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "currencyCode", source = "currencyCode")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "rateDate", source = "date")
    public abstract RateRequest createRequest(String currencyCode, LocalDate date,
                                              Dictionary status, Dictionary type);

    @Mapping(target = "type", expression = "java(rateRequest.getType().getKey())")
    @Mapping(target = "rateDate", source = "rateDate", qualifiedByName = "localDateToString")
    public abstract RateRequestJmsDTO mapToRateRequestJmsDTO(RateRequest rateRequest);

    @Named("localDateToString")
    public String localDateToString(LocalDate date) {
        if (date != null) {
            return date.toString();
        } else {
            return null;
        }
    }
}
