package org.example.repository;

import org.example.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

  public static final Logger LOG = LoggerFactory.getLogger(UserRepository.class);

  private final List<User> users = List.of(
      new User(1, "Artem", "Smirnov", "test@mail.ru"),
      new User(2, "A", "B", "test@mail.ru")
  );

  public User getUser(int id) {
    LOG.info("getUser()");
    return users.get(0);
  }

  public List<User> getUsers() {
    LOG.info("getUsers()");
    return users;
  }

  public void deleteUser(int id) {
    LOG.info("deleteUser()");
  }

  public User addUser(User user) {
    LOG.info("addUser()");
    return user;
  }

  public void updateUser(User user, int id) {LOG.info("updateUser()");}
}
