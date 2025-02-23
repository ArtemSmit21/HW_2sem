package org.example.service;

import lombok.AllArgsConstructor;
import org.example.model.VotingRoom;
import org.example.repository.VotingRoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class VotingRoomService {

  private final VotingRoomRepository votingRoomRepository;

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

  public void deleteVotingRoom() {votingRoomRepository.deleteVotingRoom();}

  public void createVotingRoom(VotingRoom votingRoom) {votingRoomRepository.createVotingRoom();}
}
