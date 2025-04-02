package org.example.unit;

import org.example.model.Trip;
import org.example.model.User;
import org.example.model.VotingRoom;
import org.example.repository.TripRepository;
import org.example.repository.UserRepository;
import org.example.repository.VotingRoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class VotingRoomRepositoryTest {

  @Container
  @ServiceConnection
  private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:13");

  @Autowired
  private VotingRoomRepository votingRoomRepository;

  @Autowired
  private TripRepository tripRepository;

  @Autowired
  private UserRepository userRepository;

  @Test
  @Transactional(propagation = Propagation.REQUIRED)
  @DisplayName("this test check save method")
  public void test1() {
    Trip trip = new Trip();
    trip.setName("Trip 1");
    tripRepository.save(trip);

    User user1 = new User();
    user1.setFirstName("owner");
    user1.setLastName("owner");
    user1.setEmail("owner@owner");
    userRepository.save(user1);

    User user2 = new User();
    user2.setFirstName("participant");
    user2.setLastName("participant");
    user2.setEmail("participant@participant");
    userRepository.save(user2);

    VotingRoom votingRoom = new VotingRoom();
    votingRoom.setName("Voting Room 1");
    votingRoom.setOwner(user1);
    votingRoom.setParticipants(List.of(user1, user2));
    votingRoom.setTrips(List.of(trip));
    votingRoomRepository.save(votingRoom);

    assertEquals(votingRoom.getName(), votingRoomRepository.findAll().get(0).getName());
    assertEquals(votingRoom.getOwner(), votingRoomRepository.findAll().get(0).getOwner());
    assertEquals(votingRoom.getParticipants(), votingRoomRepository.findAll().get(0).getParticipants());
    assertEquals(votingRoom.getTrips(), votingRoomRepository.findAll().get(0).getTrips());
  }
}
