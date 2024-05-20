package ru.andreev.user.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import ru.andreev.user.dto.UserDTO;
import ru.andreev.user.entity.User;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Component
@RequiredArgsConstructor
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = SPRING)
public abstract class UserMapper {

    public abstract UserDTO mapToUserDto(User user);

    public abstract User mapToUser(UserDTO dto);
}
