package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.interfaces.VotingRoomInterface;
import org.example.model.VotingRoom;
import org.example.service.VotingRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class VotingRoomController implements VotingRoomInterface {

  private final VotingRoomService votingRoomService;

  @Override
  public ResponseEntity<List<VotingRoom>> getVotingRoom() {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(votingRoomService.getAllVotingRooms());
  }

  @Override
  public void vote(long votingRoomId) {
    votingRoomService.updateInterestRate();
  }

  @Override
  public void appendFriendToRoom(long votingRoomId, long friendId) {
    votingRoomService.appendFriendToRoom();
  }

  @Override
  public void endVoting(long votingRoomId) {
    votingRoomService.endVoting();
  }

  @Override
  public void deleteVotingRoom(long votingRoomId) {
    votingRoomService.deleteVotingRoom();
  }

  @Override
  public void createVotingRoom(VotingRoom votingRoom) {
    votingRoomService.createVotingRoom(votingRoom);
  }
}
