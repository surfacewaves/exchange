package ru.andreev.user.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import ru.andreev.user.dto.PortfolioItemDTO;
import ru.andreev.user.dto.PortfolioItemRequestDTO;
import ru.andreev.user.entity.Portfolio;
import ru.andreev.user.entity.PortfolioItem;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Component
@RequiredArgsConstructor
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = SPRING)
public abstract class PortfolioItemMapper {

    public abstract PortfolioItemDTO mapToPortfolioItemDTO(PortfolioItem item);

    @Mapping(target = "activeName", source = "request.activeName")
    @Mapping(target = "activeAmount", source = "request.activeAmount")
    @Mapping(target = "portfolio", source = "portfolio")
    @Mapping(target = "lastUpdatedDatetime", expression = "java(java.time.OffsetDateTime.now())")
    public abstract PortfolioItem createPortfolioItemFromRequest(PortfolioItemRequestDTO request, Portfolio portfolio);
}
