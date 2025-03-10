package org.example.e2e;

import org.example.model.User;
import org.example.model.VotingRoom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringAppTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void e2eTest() {
        // creating user
        User mockUser = new User(1, "Artem", "Smirnov", "test@mail.ru");

        restTemplate.put("http://localhost:" + port + "/api/users", mockUser);

        ResponseEntity<String> responseUser =
                restTemplate.getForEntity("http://localhost:" + port + "/api/users/1", String.class);

        assertEquals(HttpStatus.OK, responseUser.getStatusCode());
        assertTrue(responseUser.getBody().contains("1"));
        assertTrue(responseUser.getBody().contains("Artem"));
        assertTrue(responseUser.getBody().contains("Smirnov"));
        assertTrue(responseUser.getBody().contains("test@mail.ru"));

        // creating voting room
        VotingRoom mockVotingRoom1 = new VotingRoom(1, 1, "test1", List.of(1, 2),
                List.of("Moscow, Paris, New York"), List.of(0, 0, 1), 0);
        VotingRoom mockVotingRoom2 = new VotingRoom(2, 1, "test2", List.of(1, 2),
                List.of("Moscow, Paris, New York"), List.of(1, 0, 1), 0);
        List<VotingRoom> mockList = List.of(mockVotingRoom1, mockVotingRoom2);


        restTemplate.patchForObject("http://localhost:" + port + "/api/voting-room", mockVotingRoom1, String.class);
        restTemplate.patchForObject("http://localhost:" + port + "/api/voting-room", mockVotingRoom2, String.class);

        ResponseEntity<String> responseVotingRooms =
                restTemplate.getForEntity("http://localhost:" + port + "/api/voting-room", String.class);

        assertEquals(HttpStatus.OK, responseVotingRooms.getStatusCode());
        assertTrue(responseVotingRooms.getBody().contains("test1"));
        assertTrue(responseVotingRooms.getBody().contains("test2"));
        assertTrue(responseVotingRooms.getBody().contains("Moscow"));
        assertTrue(responseVotingRooms.getBody().contains("Paris"));
        assertTrue(responseVotingRooms.getBody().contains("New York"));

        // vote
        assertEquals(
                HttpStatus.OK,
                restTemplate.postForEntity("http://localhost:" + port + "/api/voting-room/1", null, String.class)
                        .getStatusCode()
        );

        // append friend to room
        assertEquals(
                HttpStatus.OK,
                restTemplate.postForEntity("http://localhost:" + port + "/api/voting-room/1/1", null, String.class)
                        .getStatusCode()
        );

        // end voting
        assertEquals(
                HttpStatus.OK,
                restTemplate.exchange("http://localhost:" + port + "/api/voting-room/1", HttpMethod.PUT, null, String.class)
                        .getStatusCode()
        );

        // delete voting room
        assertEquals(
                HttpStatus.OK,
                restTemplate.exchange("http://localhost:" + port + "/api/voting-room/1", HttpMethod.DELETE, null, String.class)
                        .getStatusCode()
        );
    }
}
