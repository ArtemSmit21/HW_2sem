package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class VotingRoom {
  private Integer id;
  private Integer ownerId;
  private String name;
  private List<Integer> participantsId;
  private List<String> variantsNames;
  private List<Integer> variantsInterestRate;
  private Integer state;
}
