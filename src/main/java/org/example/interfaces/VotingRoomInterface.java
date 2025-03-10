package org.example.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.model.VotingRoom;
import org.example.model.VotingRoomDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/voting-room")
@Tag(name = "Voting Room API", description = "Управление комнатами голосования")
public interface VotingRoomInterface {

    @Operation(summary = "Получить все комнаты голосований")
    @ApiResponse(responseCode = "200", description = "Комнаты голосований найдены")
    @GetMapping
    ResponseEntity<List<VotingRoom>> getAllVotingRooms();

    @Operation(summary = "Добавить друга в комнату голосования")
    @ApiResponse(responseCode = "200", description = "Человек добавлен в комнату")
    @PostMapping("/{votingRoomId}/{friendId}")
    void appendFriendToRoom(@PathVariable("votingRoomId") long votingRoomId, @PathVariable("friendId") long friendId);

    @Operation(summary = "Удалить комнату голосования")
    @ApiResponse(responseCode = "200", description = "Комната удалена")
    @DeleteMapping("/{votingRoomId}")
    void deleteVotingRoom(@PathVariable long votingRoomId);

    @Operation(summary = "Создать комнату голосования")
    @ApiResponse(responseCode = "201", description = "Комната создана")
    @PatchMapping
    void createVotingRoom(@RequestBody VotingRoomDTO votingRoom);
}
