package org.example.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "voting_room")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Voting Room", description = "Сущность комнаты голосования")
public class VotingRoom {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "Уникальный идентификатор", example = "111")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_id")
  private User owner;

  @NotNull
  @Column(name = "name")
  private String name;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
    name = "voting_room_user",
    joinColumns = @JoinColumn(name = "voting_room_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id")
  )
  private List<User> participants = new ArrayList<>();

  @OneToMany
  @JoinColumn(name = "voting_room_id")
  private List<Trip> trips = new ArrayList<>();
}
