package org.example.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trip")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Trip", description = "Сущность путешествия")
public class Trip {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "Уникальный идетификатор", example = "111")
  private Long id;

  @NotNull
  @Column(name = "name")
  private String name;
}
