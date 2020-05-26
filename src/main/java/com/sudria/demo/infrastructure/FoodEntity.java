package com.sudria.demo.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FoodEntity {

  @Id
  @GeneratedValue
  private Long id;
  @Column(name = "FREQUENCY", length = 50, nullable = false)
  private int frequency;
  @Column(name = "QUANTITY", nullable = false)
  private int quantity;
  @Column(name = "CATEGORY", length = 50, nullable = false)
  private String category;


  @ManyToOne
  private AvionEntity avionEntity;
}
