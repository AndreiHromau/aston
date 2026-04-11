package ru.aston.task2.mapper;

import ru.aston.task2.dto.UserCreateRequest;
import ru.aston.task2.dto.UserDto;
import ru.aston.task2.dto.UserUpdateRequest;
import ru.aston.task2.model.UserEntity;

public final class UserMapper {

    private UserMapper() {
    }

    public static UserDto toDto(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return new UserDto(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getAge(),
                entity.getCreatedAt()
        );
    }

    public static UserEntity fromCreateRequest(UserCreateRequest request) {
        UserEntity entity = new UserEntity();
        entity.setName(request.getName());
        entity.setEmail(request.getEmail());
        entity.setAge(request.getAge());
        return entity;
    }

    public static void applyUpdate(UserEntity entity, UserUpdateRequest request) {
        if (request.getName() != null) {
            entity.setName(request.getName());
        }
        if (request.getEmail() != null) {
            entity.setEmail(request.getEmail());
        }
        if (request.getAge() != null) {
            entity.setAge(request.getAge());
        }
    }
}