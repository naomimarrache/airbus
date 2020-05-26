package com.sudria.demo.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Avion {

  private Long id;
  private String version;
  private int longueur;
  private String famille;
  private List<Achat> achats;

  @Getter
  @ToString
  @EqualsAndHashCode
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Achat {

    private Long id;
    private int price;
    private int quantity;
    private String compagny;

  }

}
