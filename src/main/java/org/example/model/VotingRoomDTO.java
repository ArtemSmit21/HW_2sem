package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class VotingRoomDTO {
  private Long ownerId;
  private String name;
  private List<Long> participantsId;
  private List<Long> tripsId;
}
