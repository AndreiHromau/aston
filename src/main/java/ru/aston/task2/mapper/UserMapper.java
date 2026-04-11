package ru.aston.task2.mapper;

import ru.aston.task2.dto.UserCreateRequest;
import ru.aston.task2.dto.UserResponse;
import ru.aston.task2.dto.UserUpdateRequest;
import ru.aston.task2.entity.UserEntity;

public final class UserMapper {

    private UserMapper() {
    }

    public static UserResponse toDto(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return new UserResponse(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getAge(),
                entity.getCreatedAt()
        );
    }

    public static UserEntity fromCreateRequest(UserCreateRequest request) {
        UserEntity entity = new UserEntity();
        entity.setName(request.name());
        entity.setEmail(request.email());
        entity.setAge(request.age());
        return entity;
    }

    public static void applyUpdate(UserEntity entity, UserUpdateRequest request) {
        if (request.name() != null) {
            entity.setName(request.name());
        }
        if (request.email() != null) {
            entity.setEmail(request.email());
        }
        if (request.age() != null) {
            entity.setAge(request.age());
        }
    }
}