package ru.andreev.user.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import ru.andreev.user.dto.DictionaryDTO;
import ru.andreev.user.entity.Dictionary;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Component
@RequiredArgsConstructor
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = SPRING)
public abstract class DictionaryMapper {

    public abstract DictionaryDTO mapToDictionaryDTO(Dictionary dictionary);
}
