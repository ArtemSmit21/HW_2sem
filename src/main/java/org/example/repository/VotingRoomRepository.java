package org.example.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.model.VotingRoom;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class VotingRoomRepository {

  private final List<VotingRoom> votingRooms = List.of(
      new VotingRoom(1, 1, "test1", List.of(1, 2),
          List.of("Moscow, Paris, New York"), List.of(0, 0, 1), 0),
      new VotingRoom(2, 1, "test2", List.of(1, 2),
          List.of("Moscow, Paris, New York"), List.of(1, 0, 1), 0)
  );

  public List<VotingRoom> getVotingRooms() {
    log.info("getVotingRooms()");
    return votingRooms;
  }

  public void updateInterestRate() {
    log.info("updateInterestRate()");
  }

  public void appendFriendToRoom() {
    log.info("appendFriendToRoom()");
  }

  public void endVoting() {
    log.info("endVoting()");
  }

  public void deleteVotingRoom() {log.info("deleteVotingRoom()");}

  public void createVotingRoom() {log.info("createVotingRoom()");}
}
