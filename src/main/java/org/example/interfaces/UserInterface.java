package org.example.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.model.User;
import org.example.model.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RequestMapping("api/users")
@Tag(name = "User API", description = "Управление пользователями")
public interface UserInterface {

    @Operation(summary = "Получить пользователя")
    @ApiResponse(responseCode = "200", description = "Пользователь найден")
    @GetMapping("/{id}")
    ResponseEntity<User> getUser(@PathVariable("id") int id);

    @Operation(summary = "Получить всех пользователей")
    @ApiResponse(responseCode = "200", description = "Пользователи найдены")
    @GetMapping
    ResponseEntity<List<User>> getUsers() throws ExecutionException, InterruptedException;

    @Operation(summary = "Удалить пользователя")
    @ApiResponse(responseCode = "200", description = "Пользователь удален")
    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable("id") int id) throws JsonProcessingException;

    @Operation(summary = "Добавить пользователя")
    @ApiResponse(responseCode = "200", description = "Пользователь добавлен")
    @PutMapping()
    ResponseEntity<User> addUser(@RequestBody UserDTO user) throws JsonProcessingException;

    @Operation(summary = "Обновить пользователя")
    @ApiResponse(responseCode = "200", description = "Пользователь обновлен")
    @PatchMapping("/{id}")
    void updateUser(@RequestBody UserDTO user, @PathVariable("id") long id) throws JsonProcessingException;
}
