package ru.aston.task2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.aston.task2.dto.UserCreateRequest;
import ru.aston.task2.dto.UserDto;
import ru.aston.task2.dto.UserUpdateRequest;
import ru.aston.task2.mapper.UserMapper;
import ru.aston.task2.model.UserEntity;
import ru.aston.task2.service.UserService;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        List<UserDto> result = userService.getAllUsers().stream()
                .map(UserMapper::toDto)
                .toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        UserEntity entity = userService.getUserById(id);
        if (entity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(UserMapper.toDto(entity));
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserCreateRequest request) {
        UserEntity created = userService.createUser(UserMapper.fromCreateRequest(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UserUpdateRequest request) {
        UserEntity existing = userService.getUserById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        UserMapper.applyUpdate(existing, request);
        UserEntity updated = userService.updateUser(existing);
        return ResponseEntity.ok(UserMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
