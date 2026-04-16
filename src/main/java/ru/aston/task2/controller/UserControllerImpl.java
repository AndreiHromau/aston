package ru.aston.task2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.aston.task2.dto.UserCreateRequest;
import ru.aston.task2.dto.UserResponse;
import ru.aston.task2.dto.UserUpdateRequest;
import ru.aston.task2.service.UserService;

import java.util.List;

@RestController
public class UserControllerImpl implements UserController {

    private final UserService userService;

    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Override
    public ResponseEntity<UserResponse> getById(Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Override
    public ResponseEntity<UserResponse> create(UserCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
    }

    @Override
    public ResponseEntity<UserResponse> update(Long id, UserUpdateRequest request) {
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}