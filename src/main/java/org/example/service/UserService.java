package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exception.UserNotFoundException;
import org.example.model.OutboxRecord;
import org.example.model.User;
import org.example.model.UserAction;
import org.example.model.UserDTO;
import org.example.repository.OutboxRepository;
import org.example.repository.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final ObjectMapper objectMapper;
  private final OutboxRepository outboxRepository;

  @Cacheable(value = "get_user_cache", key = "#root.methodName")
  @Transactional(readOnly = true)
  public User getUser(long id) {
    Optional<User> user = userRepository.findById(id);
    if (user.isPresent()) {
      return user.get();
    } else {
      throw new UserNotFoundException(String.valueOf(id));
    }
  }

  @Transactional(readOnly = true)
  public List<User> getUsers() {
    return userRepository.findAll();
  }

  @Transactional
  @Cacheable(value = "delete_user_cache", key = "#root.methodName")
  public User deleteUser(long id) throws JsonProcessingException {
    Optional<User> user = userRepository.findById(id);
    if (user.isPresent()) {
      userRepository.delete(user.get());
    }

    UserAction userAction =
      new UserAction(
        id,
        Instant.now(),
        "DELETE",
        "none"
      );

    outboxRepository.save(
      OutboxRecord.builder()
        .data(objectMapper.writeValueAsString(userAction))
        .build()
    );
    return user.get();
  }

  @Transactional
  public User addUser(UserDTO user) throws JsonProcessingException {
    User tempUser = userRepository.save(new User(user.getFirstName(), user.getLastName(), user.getEmail()));

    UserAction userAction =
      new UserAction(
        tempUser.getId(),
        Instant.now(),
        "INSERT",
        "none"
      );

    outboxRepository.save(
      OutboxRecord.builder()
        .data(objectMapper.writeValueAsString(userAction))
        .build()
    );
    return tempUser;
  }

  @Transactional(rollbackFor = UserNotFoundException.class)
  @Cacheable(value = "update_user_cache", key = "#root.methodName")
  public void updateUser(UserDTO userDTO, long id) throws JsonProcessingException {
    User user;
    try {
      user = userRepository.findById(id).get();
    } catch (NoSuchElementException e) {
      throw new UserNotFoundException(String.valueOf(id));
    }
    user.setFirstName(userDTO.getFirstName());
    user.setLastName(userDTO.getLastName());
    user.setEmail(userDTO.getEmail());
    userRepository.save(user);

    UserAction userAction =
      new UserAction(
        id,
        Instant.now(),
        "UPDATE",
        "none"
      );

    outboxRepository.save(
      OutboxRecord.builder()
        .data(objectMapper.writeValueAsString(userAction))
        .build()
    );
  }
}
