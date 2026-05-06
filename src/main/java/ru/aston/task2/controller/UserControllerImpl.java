package ru.aston.task2.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.aston.task2.hateoas.UserRepresentationAssembler;
import ru.aston.task2.dto.UserCreateRequest;
import ru.aston.task2.dto.UserResponse;
import ru.aston.task2.dto.UserUpdateRequest;
import ru.aston.task2.service.UserService;

@RestController
public class UserControllerImpl implements UserController {

    private final UserService userService;
    private final UserRepresentationAssembler assembler;

    public UserControllerImpl(UserService userService, UserRepresentationAssembler assembler) {
        this.userService = userService;
        this.assembler = assembler;
    }

    @Override
    public ResponseEntity<CollectionModel<EntityModel<UserResponse>>> getAll() {
        return ResponseEntity.ok(assembler.toCollectionModel(userService.getAllUsers()));
    }

    @Override
    public ResponseEntity<EntityModel<UserResponse>> getById(Long id) {
        return ResponseEntity.ok(assembler.toModel(userService.getUserById(id)));
    }

    @Override
    public ResponseEntity<EntityModel<UserResponse>> create(UserCreateRequest request) {
        UserResponse created = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(created));
    }

    @Override
    public ResponseEntity<EntityModel<UserResponse>> update(Long id, UserUpdateRequest request) {
        return ResponseEntity.ok(assembler.toModel(userService.updateUser(id, request)));
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}