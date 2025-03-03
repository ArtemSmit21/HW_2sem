package org.example.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Repository
@Slf4j
public class UserRepository {

  private final RestTemplate restTemplate = new RestTemplate();
  private final WebClient webClient = WebClient.create();

  private final List<User> users = List.of(
      new User(1, "Artem", "Smirnov", "test@mail.ru"),
      new User(2, "A", "B", "test@mail.ru")
  );

  public User getUser(int id) {
    String response = restTemplate.getForObject("http://localhost:8080/api/voting-room", String.class);
    log.info("getUser()");
    return users.get(0);
  }

  public List<User> getUsers() {
    String response = webClient.get().uri("http://localhost:8080/api/voting-room").retrieve().bodyToMono(String.class).block();
    log.info("getUsers()");
    return users;
  }

  public void deleteUser(int id) {
    log.info("deleteUser()");
  }

  public User addUser(User user) {
    log.info("addUser()");
    return user;
  }

  public void updateUser(User user, int id) {log.info("updateUser()");}
}
