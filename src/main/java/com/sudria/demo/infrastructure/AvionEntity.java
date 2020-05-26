package com.sudria.demo.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AvionEntity {

  @Id
  @GeneratedValue
  private Long id;
  @Column(name = "VERSION", length = 50, nullable = false)
  private String version;
  @Column(name = "LONGUEUR", nullable = false)
  private int longueur;
  @Column(name = "FAMILLE", length = 50, nullable = false)
  private String famille;

  @OneToMany(mappedBy = "avionEntity", cascade = CascadeType.ALL, orphanRemoval = true , fetch = FetchType.EAGER)
  private List<FoodEntity> foodEntities;

}