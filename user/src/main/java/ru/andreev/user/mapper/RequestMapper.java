package ru.andreev.user.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import ru.andreev.user.dto.CreateRequestDTO;
import ru.andreev.user.dto.RequestDTO;
import ru.andreev.user.entity.Dictionary;
import ru.andreev.user.entity.Request;
import ru.andreev.user.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Component
@RequiredArgsConstructor
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = SPRING, uses = {DictionaryMapper.class, UserMapper.class})
public abstract class RequestMapper {

    @Mapping(target = "boughtUserIds", source = "boughtUsers", qualifiedByName = "getBoughtUserIds")
    @Mapping(target = "status", source = "status")
    public abstract RequestDTO mapToRequestDTO(Request request);

    public abstract List<RequestDTO> mapToListRequestDTO(List<Request> requests);

    @Named("getBoughtUserIds")
    public List<UUID> getBoughtUserIds(List<User> users) {
        List<UUID> result = new ArrayList<>();

        if (users != null) {
            users.stream()
                    .map(User::getId)
                    .forEach(result::add);
        }

        return result;
    }

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "createdUser", source = "user")
    @Mapping(target = "lastUpdatedDatetime", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "currentAmount", source = "newRequest.originalAmount")
    @Mapping(target = "status", source = "dictionary")
    public abstract Request createRequest(CreateRequestDTO newRequest, User user, Dictionary dictionary);
}
