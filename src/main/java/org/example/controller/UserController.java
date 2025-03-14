package org.example.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.example.interfaces.UserInterface;
import org.example.model.User;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@AllArgsConstructor
@CircuitBreaker(name = "apiCircuitBreaker")
@RestController
public class UserController implements UserInterface {

  private final UserService userService;

  @Override
  public ResponseEntity<User> getUser(int id) {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.getUser(id));
  }

  @Override
  public ResponseEntity<List<User>> getUsers() throws ExecutionException, InterruptedException {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(userService.getUsers().get());
  }

  @Override
  public void deleteUser(int id) {
    userService.deleteUser(id);
  }

  @Override
  public ResponseEntity<User> addUser(User user) {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.addUser(user));
  }

  @Override
  public void updateUser(User user, int id) {
    userService.updateUser(user, id);
  }
}
