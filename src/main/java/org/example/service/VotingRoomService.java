package org.example.service;

import lombok.AllArgsConstructor;
import org.example.exception.TripNotFoundException;
import org.example.exception.VotingRoomNotFoundException;
import org.example.exception.UserNotFoundException;
import org.example.model.User;
import org.example.model.Trip;
import org.example.model.VotingRoom;
import org.example.model.VotingRoomDTO;
import org.example.repository.TripRepository;
import org.example.repository.UserRepository;
import org.example.repository.VotingRoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class VotingRoomService {

  private final VotingRoomRepository votingRoomRepository;
  private final UserRepository userRepository;
  private final TripRepository tripRepository;

  @Transactional(readOnly = true)
  public List<VotingRoom> getAllVotingRooms() {
    return votingRoomRepository.findAll();
  }

  @Transactional(rollbackFor = {VotingRoomNotFoundException.class})
  public void appendFriendToRoom(Long id, Long friendId) {
    VotingRoom votingRoom = votingRoomRepository.findById(id).orElseThrow(VotingRoomNotFoundException::new);
    User user = userRepository.findById(friendId).orElseThrow(VotingRoomNotFoundException::new);

    List<User> participants = votingRoom.getParticipants();
    participants.add(user);
    votingRoom.setParticipants(participants);
    votingRoomRepository.save(votingRoom);
  }

  @Transactional(rollbackFor = VotingRoomNotFoundException.class)
  public void deleteVotingRoom(Long id) {
    VotingRoom votingRoom = votingRoomRepository.findById(id).orElseThrow(VotingRoomNotFoundException::new);
    votingRoomRepository.delete(votingRoom);
  }

  @Transactional(rollbackFor = {UserNotFoundException.class, TripNotFoundException.class, VotingRoomNotFoundException.class})
  public void createVotingRoom(VotingRoomDTO votingRoomDTO) {
    List<User> participants = new ArrayList<>();
    List<Trip> trips = new ArrayList<>();

    for (Long userId : votingRoomDTO.getParticipantsId()) {
      User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
      participants.add(user);
    }

    for (Long tripId : votingRoomDTO.getTripsId()) {
      Trip trip = tripRepository.findById(tripId).orElseThrow(TripNotFoundException::new);
      trips.add(trip);
    }

    User owner = userRepository.findById(votingRoomDTO.getOwnerId()).orElseThrow(UserNotFoundException::new);

    VotingRoom votingRoom = new VotingRoom();
    votingRoom.setName(votingRoomDTO.getName());
    votingRoom.setParticipants(participants);
    votingRoom.setTrips(trips);
    votingRoom.setOwner(owner);
    votingRoomRepository.save(votingRoom);
  }
}
