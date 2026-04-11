package ru.aston.task2.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.aston.task2.dto.UserCreateRequest;
import ru.aston.task2.dto.UserResponse;
import ru.aston.task2.dto.UserUpdateRequest;

import java.util.List;

@RequestMapping("/api/users")
public interface UserController {

    /**
     * Получить список всех пользователей.
     *
     * @return список пользователей
     */
    @GetMapping
    ResponseEntity<List<UserResponse>> getAll();

    /**
     * Получить пользователя по идентификатору.
     *
     * @param id идентификатор пользователя
     * @return пользователь, либо ответ 404 если пользователь не найден
     */
    @GetMapping("/{id}")
    ResponseEntity<UserResponse> getById(@PathVariable("id") Long id);

    /**
     * Создать пользователя.
     *
     * @param request данные для создания пользователя
     * @return созданный пользователь
     */
    @PostMapping
    ResponseEntity<UserResponse> create(@Valid @RequestBody UserCreateRequest request);

    /**
     * Обновить пользователя.
     *
     * @param id идентификатор пользователя
     * @param request данные для обновления пользователя
     * @return обновлённый пользователь, либо ответ 404 если пользователь не найден
     */
    @PutMapping("/{id}")
    ResponseEntity<UserResponse> update(@PathVariable("id") Long id, @Valid @RequestBody UserUpdateRequest request);

    /**
     * Удалить пользователя по идентификатору.
     *
     * @param id идентификатор пользователя
     * @return ответ 204 если пользователь удалён, либо ответ 404 если пользователь не найден
     */
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable("id") Long id);
}