package org.example.unit;

import lombok.extern.slf4j.Slf4j;
import org.example.controller.UserController;
import org.example.model.User;
import org.example.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Slf4j
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    @DisplayName("check the correct operation of getUser()")
    public void test1() throws Exception {
        User mockUser = new User(1, "Artem", "Smirnov", "test@mail.ru");
        when(userService.getUser(1)).thenReturn(mockUser);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Artem"))
                .andExpect(jsonPath("$.lastName").value("Smirnov"))
                .andExpect(jsonPath("$.email").value("test@mail.ru"));
    }

    @Test
    @DisplayName("check the correct operation of getUsers()")
    public void test2() throws Exception {
        List<User> mockListUsers = new ArrayList<>();
        mockListUsers.add(new User(1, "Artem", "Smirnov", "test@mail.ru"));
        mockListUsers.add(new User(2, "A", "B", "test@mail.ru"));
        CompletableFuture<List<User>> completableFuture = CompletableFuture.completedFuture(mockListUsers);
        when(userService.getUsers()).thenReturn(completableFuture);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("Artem"))
                .andExpect(jsonPath("$[0].lastName").value("Smirnov"))
                .andExpect(jsonPath("$[0].email").value("test@mail.ru"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].firstName").value("A"))
                .andExpect(jsonPath("$[1].lastName").value("B"))
                .andExpect(jsonPath("$[1].email").value("test@mail.ru"));
    }

    @Test
    @DisplayName("check 400 http status on method deleteUser()")
    public void test3() throws Exception {
        mockMvc.perform(delete("/api/users/abcd"))
                .andExpect(status().isBadRequest());
    }
}
