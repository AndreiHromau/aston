package ru.aston.task2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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

/**
 * REST API для управления пользователями.
 *
 * <p>Принимает и возвращает только DTO — entity никогда не покидает сервисный слой.</p>
 */
@Tag(name = "Users", description = "CRUD пользователей")
@RequestMapping("/api/users")
public interface UserController {

    /**
     * Создать пользователя.
     *
     * @param request данные для создания пользователя
     * @return созданный пользователь, обёрнутый в HATEOAS-модель (ссылки в поле _links)
     */
    @Operation(summary = "Создать пользователя")
    @ApiResponse(responseCode = "201", description = "Пользователь создан",
            content = @Content(schema = @Schema(implementation = UserResponse.class)))
    @ApiResponse(responseCode = "400", description = "Ошибка валидации: некорректные поля запроса",
            content = @Content(examples = @ExampleObject(
                    name = "validationError",
                    value = "{\"timestamp\":\"2026-04-25T12:00:00\",\"status\":400,\"error\":\"Bad Request\",\"path\":\"/api/users\"}"
            )))
    @PostMapping
    ResponseEntity<EntityModel<UserResponse>> create(@Valid @RequestBody UserCreateRequest request);

    /**
     * Получить пользователя по идентификатору.
     *
     * @param id идентификатор пользователя
     * @return пользователь, обёрнутый в HATEOAS-модель (ссылки в поле _links), либо ответ 404 если пользователь не найден
     */
    @Operation(summary = "Получить пользователя по id")
    @ApiResponse(responseCode = "200", description = "Пользователь найден",
            content = @Content(schema = @Schema(implementation = UserResponse.class)))
    @ApiResponse(responseCode = "404", description = "Пользователь не найден",
            content = @Content(examples = @ExampleObject(
                    name = "notFound",
                    value = "{\"timestamp\":\"2026-04-25T12:00:00\",\"status\":404,\"error\":\"Not Found\",\"message\":\"Пользователь с id=1 не найден\",\"path\":\"/api/users/1\"}"
            )))
    @GetMapping("/{id}")
    ResponseEntity<EntityModel<UserResponse>> getById(
            @Parameter(description = "ID пользователя", example = "1")
            @PathVariable("id") Long id
    );

    /**
     * Получить список всех пользователей.
     *
     * @return коллекция пользователей в формате HAL (элементы в _embedded, ссылки в _links)
     */
    @Operation(summary = "Получить список всех пользователей")
    @ApiResponse(responseCode = "200", description = "Список пользователей",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponse.class))))
    @GetMapping
    ResponseEntity<CollectionModel<EntityModel<UserResponse>>> getAll();

    /**
     * Обновить пользователя.
     *
     * @param id      идентификатор пользователя
     * @param request данные для обновления пользователя
     * @return обновлённый пользователь, обёрнутый в HATEOAS-модель (ссылки в поле _links),
     * либо ответ 404 если пользователь не найден
     */
    @Operation(summary = "Обновить пользователя")
    @ApiResponse(responseCode = "200", description = "Пользователь обновлён",
            content = @Content(schema = @Schema(implementation = UserResponse.class)))
    @ApiResponse(responseCode = "400", description = "Ошибка валидации: некорректные поля запроса",
            content = @Content(examples = @ExampleObject(
                    name = "validationError",
                    value = "{\"timestamp\":\"2026-04-25T12:00:00\",\"status\":400,\"error\":\"Bad Request\",\"path\":\"/api/users/1\"}"
            )))
    @ApiResponse(responseCode = "404", description = "Пользователь не найден",
            content = @Content(examples = @ExampleObject(
                    name = "notFound",
                    value = "{\"timestamp\":\"2026-04-25T12:00:00\",\"status\":404,\"error\":\"Not Found\",\"message\":\"Пользователь с id=1 не найден\",\"path\":\"/api/users/1\"}"
            )))
    @PutMapping("/{id}")
    ResponseEntity<EntityModel<UserResponse>> update(
            @Parameter(description = "ID пользователя", example = "1")
            @PathVariable("id") Long id,
            @Valid @RequestBody UserUpdateRequest request
    );

    /**
     * Удалить пользователя по идентификатору.
     *
     * @param id идентификатор пользователя
     * @return ответ 204 если пользователь удалён, либо ответ 404 если пользователь не найден
     */
    @Operation(summary = "Удалить пользователя")
    @ApiResponse(responseCode = "204", description = "Пользователь удалён", content = @Content)
    @ApiResponse(responseCode = "404", description = "Пользователь не найден",
            content = @Content(examples = @ExampleObject(
                    name = "notFound",
                    value = "{\"timestamp\":\"2026-04-25T12:00:00\",\"status\":404,\"error\":\"Not Found\",\"message\":\"Пользователь с id=1 не найден\",\"path\":\"/api/users/1\"}"
            )))
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
            @Parameter(description = "ID пользователя", example = "1")
            @PathVariable("id") Long id
    );
}