package org.example.service;

import lombok.AllArgsConstructor;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;

  public User getUser(int id) {return userRepository.getUser(id);}

  public List<User> getUsers() {
    return userRepository.getUsers();
  }

  public void deleteUser(int id) {
    userRepository.deleteUser(id);
  }

  public User addUser(User user) {return userRepository.addUser(user);}

  public void updateUser(User user, int id) {userRepository.updateUser(user, id);}
}
