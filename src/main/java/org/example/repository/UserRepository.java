package org.example.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class UserRepository {

  private final List<User> users = List.of(
      new User(1, "Artem", "Smirnov", "test@mail.ru"),
      new User(2, "A", "B", "test@mail.ru")
  );

  public User getUser(int id) {
    log.info("getUser()");
    return users.get(0);
  }

  public List<User> getUsers() {
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
