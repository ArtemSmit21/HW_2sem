package org.example.services;

import org.example.models.VotingRoom;
import org.example.repositories.VotingRoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VotingRoomService {

  private final VotingRoomRepository votingRoomRepository;

  public VotingRoomService(VotingRoomRepository votingRoomRepository) {
    this.votingRoomRepository = votingRoomRepository;
  }

  public List<VotingRoom> getAllVotingRooms() {
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
}
