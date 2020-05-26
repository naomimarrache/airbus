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
public class AchatEntity {

  @Id
  @GeneratedValue
  private Long id;
  @Column(name = "PRICE", length = 50, nullable = false)
  private int price;
  @Column(name = "QUANTITY", nullable = false)
  private int quantity;
  @Column(name = "COMPAGNY", length = 50, nullable = false)
  private String compagny;


  @ManyToOne
  private AvionEntity avionEntity;
}
