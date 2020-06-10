package com.sudria.demo.domain.achat;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Achat {

  private Long id;
  private int price;
  private int quantity;
  private String compagny;

}