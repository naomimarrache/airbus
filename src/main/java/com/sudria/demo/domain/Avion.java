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
  private List<Food> foods;

  @Getter
  @ToString
  @EqualsAndHashCode
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Food {

    private Long id;
    private int frequency;
    private int quantity;
    private String category;

  }

}
