package ru.andreev.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ru.andreev.user.dto.UserDTO;
import ru.andreev.user.entity.User;
import ru.andreev.user.mapper.UserMapper;
import ru.andreev.user.repository.UserRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserDTO registerClient(String info) {
        List<String> values = List.of(info.split("/"));

        User user = new User();
        user.setId(UUID.fromString(values.get(0)));
        user.setUsername(values.get(1));
        user.setFirstName(values.get(2));
        user.setLastName(values.get(3));
        user.setEmail(values.get(4));
        user.setRegisteredDatetime(OffsetDateTime.parse(values.get(5)));

        return userMapper.mapToUserDto(userRepository.save(user));
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() ->
                new NotFoundException(
                        String.format("User with ID = %s not found", id)
                ));
    }

    public UserDTO getUserDtoById(UUID userId) {
        return userMapper.mapToUserDto(getUserById(userId));
    }
}