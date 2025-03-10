package org.example.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.example.interfaces.VotingRoomInterface;
import org.example.model.VotingRoom;
import org.example.model.VotingRoomDTO;
import org.example.service.VotingRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RateLimiter(name = "apiRateLimiter")
@CircuitBreaker(name = "apiCircuitBreaker")
@RestController
public class VotingRoomController implements VotingRoomInterface {

  private final VotingRoomService votingRoomService;

  @Override
  public ResponseEntity<List<VotingRoom>> getAllVotingRooms() {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(votingRoomService.getAllVotingRooms());
  }

  @Override
  public void appendFriendToRoom(long votingRoomId, long friendId) {
    votingRoomService.appendFriendToRoom(votingRoomId, friendId);
  }

  @Override
  public void deleteVotingRoom(long votingRoomId) {
    votingRoomService.deleteVotingRoom(votingRoomId);
  }

  @Override
  public void createVotingRoom(VotingRoomDTO votingRoom) {
    votingRoomService.createVotingRoom(votingRoom);
  }
}
