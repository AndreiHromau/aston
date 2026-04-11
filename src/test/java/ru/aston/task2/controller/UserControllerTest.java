package ru.aston.task2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.aston.task2.dto.UserCreateRequest;
import ru.aston.task2.dto.UserResponse;
import ru.aston.task2.dto.UserUpdateRequest;
import ru.aston.task2.exception.UserNotFoundException;
import ru.aston.task2.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserControllerImpl.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("Получение всех пользователей: возвращается список")
    void getAll_returnsListOfDtos() throws Exception {
        // given
        UserResponse user1 = userResponse(1L, "Ivan", "ivan@mail.ru", 25, LocalDateTime.of(2024, 1, 1, 12, 0));
        UserResponse user2 = userResponse(2L, "Petr", "petr@mail.ru", 30, LocalDateTime.of(2024, 1, 2, 12, 0));

        when(userService.getAllUsers()).thenReturn(List.of(user1, user2));

        // when
        mockMvc.perform(get("/api/users"))

                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Ivan"))
                .andExpect(jsonPath("$[0].email").value("ivan@mail.ru"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Petr"))
                .andExpect(jsonPath("$[1].email").value("petr@mail.ru"));
    }

    @Test
    @DisplayName("Получение пользователя по id: пользователь найден")
    void getById_whenUserExists_returnsDto() throws Exception {
        // given
        UserResponse user = userResponse(1L, "Ivan", "ivan@mail.ru", 25, LocalDateTime.of(2024, 1, 1, 12, 0));

        when(userService.getUserById(1L)).thenReturn(user);

        // when
        mockMvc.perform(get("/api/users/{id}", 1L))

                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Ivan"))
                .andExpect(jsonPath("$.email").value("ivan@mail.ru"))
                .andExpect(jsonPath("$.age").value(25))
                .andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    @DisplayName("Получение пользователя по id: пользователь не найден (404)")
    void getById_whenUserMissing_returns404() throws Exception {
        // given
        when(userService.getUserById(999L)).thenThrow(new UserNotFoundException(999L));

        // when
        mockMvc.perform(get("/api/users/{id}", 999L))

                // then
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Создание пользователя: возвращается 201 и созданный пользователь")
    void create_returns201AndDto() throws Exception {
        // given
        UserCreateRequest request = new UserCreateRequest("Ivan", "ivan@mail.ru", 25);

        UserResponse created = userResponse(10L, "Ivan", "ivan@mail.ru", 25, LocalDateTime.of(2024, 1, 1, 12, 0));

        when(userService.createUser(any(UserCreateRequest.class))).thenReturn(created);

        // when
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))

                // then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value("Ivan"))
                .andExpect(jsonPath("$.email").value("ivan@mail.ru"))
                .andExpect(jsonPath("$.age").value(25));
    }

    @Test
    @DisplayName("Обновление пользователя: пользователь не найден (404)")
    void update_whenUserMissing_returns404() throws Exception {
        // given
        UserUpdateRequest request = new UserUpdateRequest("NewName", "new@mail.ru", 30);
        when(userService.updateUser(eq(1L), any(UserUpdateRequest.class))).thenThrow(new UserNotFoundException(1L));

        // when
        mockMvc.perform(put("/api/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))

                // then
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Обновление пользователя: пользователь найден и обновлён")
    void update_whenUserExists_returnsUpdatedDto() throws Exception {
        // given
        UserUpdateRequest request = new UserUpdateRequest("NewName", "new@mail.ru", 30);

        UserResponse updated = userResponse(1L, "NewName", "ivan@mail.ru", 30, LocalDateTime.of(2024, 1, 1, 12, 0));

        when(userService.updateUser(eq(1L), any(UserUpdateRequest.class))).thenReturn(updated);

        // when
        mockMvc.perform(put("/api/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))

                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("NewName"))
                .andExpect(jsonPath("$.email").value("ivan@mail.ru"))
                .andExpect(jsonPath("$.age").value(30));
    }

    @Test
    @DisplayName("Удаление пользователя: успешно удалён (204)")
    void delete_whenDeleted_returns204() throws Exception {
        // given
        doNothing().when(userService).deleteUser(1L);

        // when
        mockMvc.perform(delete("/api/users/{id}", 1L))

                // then
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Удаление пользователя: пользователь не найден (404)")
    void delete_whenMissing_returns404() throws Exception {
        // given
        doThrow(new UserNotFoundException(1L)).when(userService).deleteUser(1L);

        // when
        mockMvc.perform(delete("/api/users/{id}", 1L))

                // then
                .andExpect(status().isNotFound());
    }

    private UserResponse userResponse(Long id, String name, String email, Integer age, LocalDateTime createdAt) {
        return new UserResponse(id, name, email, age, createdAt);
    }
}