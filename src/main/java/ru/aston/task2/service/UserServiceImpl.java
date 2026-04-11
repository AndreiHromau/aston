package ru.aston.task2.service;

import org.springframework.stereotype.Service;
import ru.aston.task2.model.UserEntity;
import ru.aston.task2.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public UserEntity updateUser(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public boolean deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }
}