package org.example.repositories;

import org.example.models.User;
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

  public List<User> getUsers() {
    LOG.info("getUser()");
    return users;
  }
}
