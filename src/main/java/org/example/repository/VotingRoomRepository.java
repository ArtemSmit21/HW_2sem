package org.example.repository;

import org.example.model.VotingRoom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VotingRoomRepository {

  public static final Logger LOG = LoggerFactory.getLogger(VotingRoomRepository.class);

  private final List<VotingRoom> votingRooms = List.of(
      new VotingRoom(1, 1, "test1", List.of(1, 2),
          List.of("Moscow, Paris, New York"), List.of(0, 0, 1), 0),
      new VotingRoom(2, 1, "test2", List.of(1, 2),
          List.of("Moscow, Paris, New York"), List.of(1, 0, 1), 0)
  );

  public List<VotingRoom> getVotingRooms() {
    LOG.info("getVotingRooms()");
    return votingRooms;
  }

  public void updateInterestRate() {
    LOG.info("updateInterestRate()");
  }

  public void appendFriendToRoom() {
    LOG.info("appendFriendToRoom()");
  }

  public void endVoting() {
    LOG.info("endVoting()");
  }

  public void deleteVotingRoom() {LOG.info("deleteVotingRoom()");}

  public void createVotingRoom() {LOG.info("createVotingRoom()");}
}
