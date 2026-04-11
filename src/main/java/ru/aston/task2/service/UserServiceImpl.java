package ru.aston.task2.service;

import org.springframework.stereotype.Service;
import ru.aston.task2.dto.UserCreateRequest;
import ru.aston.task2.dto.UserResponse;
import ru.aston.task2.dto.UserUpdateRequest;
import ru.aston.task2.exception.UserNotFoundException;
import ru.aston.task2.mapper.UserMapper;
import ru.aston.task2.model.UserEntity;
import ru.aston.task2.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse createUser(UserCreateRequest request) {
        UserEntity created = userRepository.save(UserMapper.fromCreateRequest(request));
        return UserMapper.toDto(created);
    }

    @Override
    public UserResponse getUserById(Long id) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return UserMapper.toDto(entity);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }

    @Override
    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        UserMapper.applyUpdate(entity, request);
        UserEntity updated = userRepository.save(entity);
        return UserMapper.toDto(updated);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}