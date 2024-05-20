package ru.andreev.user.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import ru.andreev.user.dto.PortfolioDTO;
import ru.andreev.user.dto.rest.ActiveDTO;
import ru.andreev.user.dto.rest.PortfolioItemsDTO;
import ru.andreev.user.entity.Portfolio;
import ru.andreev.user.entity.PortfolioItem;

import java.util.ArrayList;
import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Component
@RequiredArgsConstructor
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = SPRING, uses = {PortfolioItemMapper.class, UserMapper.class})
public abstract class PortfolioMapper {

    public abstract PortfolioDTO mapToPortfolioDTO(Portfolio portfolio);

    @Mapping(target = "baseCode", source = "baseCurrency")
    @Mapping(target = "actives", source = "items", qualifiedByName = "portfolioItemToActiveDTO")
    public abstract PortfolioItemsDTO mapToPortfolioItemsDTO(Portfolio portfolio);

    @Named("portfolioItemToActiveDTO")
    public List<ActiveDTO> portfolioItemToActiveDTO(List<PortfolioItem> items) {
        if (items.isEmpty()) {
            return null;
        }

        List<ActiveDTO> actives = new ArrayList<>();
        items.forEach(i -> {
            ActiveDTO active = new ActiveDTO();
            active.setActiveCode(i.getActiveName());
            active.setActiveAmount(i.getActiveAmount());
            actives.add(active);
        });
        return actives;
    }
}
