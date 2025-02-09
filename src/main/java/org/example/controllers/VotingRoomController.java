package org.example.controllers;

import org.example.models.VotingRoom;
import org.example.services.VotingRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/voting-room")
public class VotingRoomController {

  private final VotingRoomService votingRoomService;

  public VotingRoomController(VotingRoomService votingRoomService) {
    this.votingRoomService = votingRoomService;
  }

  @GetMapping
  public ResponseEntity<List<VotingRoom>> getVotingRoom() {
    return ResponseEntity
        .status(HttpStatus.FOUND)
        .body(votingRoomService.getAllVotingRooms());
  }

  @PostMapping("/{votingRoomId}")
  public void vote(@PathVariable("votingRoomId") long votingRoomId) {
    votingRoomService.updateInterestRate();
  }

  @PostMapping("/{votingRoomId}/{friendId}")
  public void vote(@PathVariable("votingRoomId") long votingRoomId, @PathVariable("friendId") long friendId) {
    votingRoomService.updateInterestRate();
  }

  @PutMapping("/{votingRoomId}")
  public void endVoting(@PathVariable long votingRoomId) {
    votingRoomService.endVoting();
  }
}
