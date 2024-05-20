package ru.andreev.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.andreev.user.dto.UserDTO;
import ru.andreev.user.service.UserService;

import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerClient(@RequestBody String info) {
        return new ResponseEntity<>(userService.registerClient(info), OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") UUID userId) {
        return new ResponseEntity<>(userService.getUserDtoById(userId), OK);
    }
}
