package org.example.unit;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class UserRepositoryTest {

  @Container
  @ServiceConnection
  private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:13");

  @Autowired
  private UserRepository userRepository;

  @Test
  @DisplayName("This test check save method")
  public void test1() {
    User user = new User();
    user.setFirstName("test");
    user.setLastName("test");
    user.setEmail("test@mail.ru");
    userRepository.save(user);
    Optional<User> userOptional = userRepository.findById(user.getId());
    assertTrue(userOptional.isPresent());
    assertEquals(user.getId(), userOptional.get().getId());
    assertEquals("test", userOptional.get().getFirstName());
    assertEquals("test", userOptional.get().getLastName());
    assertEquals("test@mail.ru", userOptional.get().getEmail());
  }

  @Test
  @DisplayName("This test check delete method")
  public void test2() {
    User user = new User();
    user.setFirstName("test1");
    user.setLastName("test1");
    user.setEmail("test1@mail.ru");
    userRepository.save(user);
    userRepository.deleteById(user.getId());
    Optional<User> userOptional = userRepository.findById(user.getId());
    assertTrue(userOptional.isEmpty());
  }
}
