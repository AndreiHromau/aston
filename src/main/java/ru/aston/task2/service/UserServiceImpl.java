package ru.aston.task2.service;

import ru.aston.task2.dao.UserDao;
import ru.aston.task2.model.UserEntity;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserEntity createUser(UserEntity user) {
        userDao.save(user);
        return user;
    }

    @Override
    public UserEntity getUserById(Long id) {
        return userDao.findById(id).orElse(null);
    }

    @Override
    public UserEntity updateUser(UserEntity user) {
        userDao.update(user);
        return user;
    }

    @Override
    public boolean deleteUser(Long id) {
        return userDao.deleteById(id);
    }
}