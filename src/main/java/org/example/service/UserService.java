package org.example.service;

import lombok.AllArgsConstructor;
import org.example.exception.UserNotFoundException;
import org.example.model.User;
import org.example.model.UserDTO;
import org.example.repository.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;

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
  public User deleteUser(long id) {
    Optional<User> user = userRepository.findById(id);
    if (user.isPresent()) {
      userRepository.delete(user.get());
    }
    return user.get();
  }

  @Transactional
  @Cacheable(value = "add_user_cache", key = "#root.methodName")
  public User addUser(UserDTO user) {
    return userRepository.save(new User(user.getFirstName(), user.getLastName(), user.getEmail()));
  }

  @Transactional(rollbackFor = UserNotFoundException.class)
  @Cacheable(value = "update_user_cache", key = "#root.methodName")
  public void updateUser(UserDTO userDTO, long id) {
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
  }
}
