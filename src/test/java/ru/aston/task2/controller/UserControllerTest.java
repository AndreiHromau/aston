package ru.aston.task2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.aston.task2.dto.UserCreateRequest;
import ru.aston.task2.dto.UserUpdateRequest;
import ru.aston.task2.model.UserEntity;
import ru.aston.task2.service.UserService;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void getById_whenUserExists_returnsDto() throws Exception {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setName("Ivan");
        user.setEmail("ivan@mail.ru");
        user.setAge(25);
        user.setCreatedAt(LocalDateTime.of(2024, 1, 1, 12, 0));

        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Ivan"))
                .andExpect(jsonPath("$.email").value("ivan@mail.ru"))
                .andExpect(jsonPath("$.age").value(25))
                .andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    void getById_whenUserMissing_returns404() throws Exception {
        when(userService.getUserById(999L)).thenReturn(null);

        mockMvc.perform(get("/api/users/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_returns201AndDto() throws Exception {
        UserCreateRequest request = new UserCreateRequest("Ivan", "ivan@mail.ru", 25);

        UserEntity created = new UserEntity();
        created.setId(10L);
        created.setName("Ivan");
        created.setEmail("ivan@mail.ru");
        created.setAge(25);
        created.setCreatedAt(LocalDateTime.of(2024, 1, 1, 12, 0));

        when(userService.createUser(any(UserEntity.class))).thenReturn(created);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value("Ivan"))
                .andExpect(jsonPath("$.email").value("ivan@mail.ru"))
                .andExpect(jsonPath("$.age").value(25));
    }

    @Test
    void update_whenUserMissing_returns404() throws Exception {
        UserUpdateRequest request = new UserUpdateRequest("NewName", null, null);
        when(userService.getUserById(1L)).thenReturn(null);

        mockMvc.perform(put("/api/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void update_whenUserExists_returnsUpdatedDto() throws Exception {
        UserUpdateRequest request = new UserUpdateRequest("NewName", null, 30);

        UserEntity existing = new UserEntity();
        existing.setId(1L);
        existing.setName("Ivan");
        existing.setEmail("ivan@mail.ru");
        existing.setAge(25);
        existing.setCreatedAt(LocalDateTime.of(2024, 1, 1, 12, 0));

        when(userService.getUserById(1L)).thenReturn(existing);
        when(userService.updateUser(eq(existing))).thenAnswer(inv -> inv.getArgument(0));

        mockMvc.perform(put("/api/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("NewName"))
                .andExpect(jsonPath("$.email").value("ivan@mail.ru"))
                .andExpect(jsonPath("$.age").value(30));
    }

    @Test
    void delete_whenDeleted_returns204() throws Exception {
        when(userService.deleteUser(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/users/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_whenMissing_returns404() throws Exception {
        when(userService.deleteUser(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/users/{id}", 1L))
                .andExpect(status().isNotFound());
    }
}
