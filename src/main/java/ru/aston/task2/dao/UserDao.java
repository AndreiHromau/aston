package ru.aston.task2.dao;

import ru.aston.task2.model.UserEntity;

import java.util.List;
import java.util.Optional;

@Deprecated
public interface UserDao {

    boolean save(UserEntity user);

    Optional<UserEntity> findById(Long id);

    List<UserEntity> findAll();

    boolean update(UserEntity user);

    boolean deleteById(Long id);
}
