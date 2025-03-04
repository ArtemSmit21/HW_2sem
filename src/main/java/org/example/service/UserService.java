package org.example.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final Set<Integer> processedIds = ConcurrentHashMap.newKeySet();

  @Cacheable(value = "get_user_cache", key = "#root.methodName")
  public User getUser(int id) {return userRepository.getUser(id);}

  @Async
  public CompletableFuture<List<User>> getUsers() {
    return CompletableFuture.completedFuture(userRepository.getUsers());
  }

  // гарантия Exactly Once
  @Cacheable(value = "delete_user_cache", key = "#root.methodName")
  public void deleteUser(int id) {
    if (!processedIds.add(id)) {
      System.out.println("Already processed id: " + id);
    }
    userRepository.deleteUser(id);
    System.out.println("Processed id: " + id);
  }

  @Cacheable(value = "add_user_cache", key = "#root.methodName")
  public User addUser(User user) {return userRepository.addUser(user);}

  @Cacheable(value = "update_user_cache", key = "#root.methodName")
  public void updateUser(User user, int id) {userRepository.updateUser(user, id);}
}
