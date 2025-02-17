package org.example.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Schema(name = "Voting Room", description = "Сущность комнаты голосования")
public class VotingRoom {
  @Schema(description = "Уникальный идентификатор", example = "111")
  private Integer id;
  private Integer ownerId;
  private String name;
  private List<Integer> participantsId;
  private List<String> variantsNames;
  private List<Integer> variantsInterestRate;
  private Integer state;
}
