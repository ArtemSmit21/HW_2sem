package org.example.service;

import lombok.AllArgsConstructor;
import org.example.exception.CustomException;
import org.example.model.VotingRoom;
import org.example.repository.VotingRoomRepository;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class VotingRoomService {

  private final VotingRoomRepository votingRoomRepository;

  // гарантия At Least Once доставки
  @Retryable(value = CustomException.class, maxAttempts = 5, backoff = @Backoff(delay = 10))
  public List<VotingRoom> getAllVotingRooms() {
    if (Math.random() > 0.5) {
      throw new CustomException("Exception!");
    }
    return votingRoomRepository.getVotingRooms();
  }

  public void updateInterestRate() {
    votingRoomRepository.updateInterestRate();
  }

  public void appendFriendToRoom() {
    votingRoomRepository.appendFriendToRoom();
  }

  public void endVoting() {
    votingRoomRepository.endVoting();
  }

  public void deleteVotingRoom() {votingRoomRepository.deleteVotingRoom();}

  public void createVotingRoom(VotingRoom votingRoom) {votingRoomRepository.createVotingRoom();}
}
