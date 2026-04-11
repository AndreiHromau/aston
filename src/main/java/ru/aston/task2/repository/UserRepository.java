package ru.aston.task2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aston.task2.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}