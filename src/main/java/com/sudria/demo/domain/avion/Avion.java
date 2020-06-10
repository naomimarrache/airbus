package com.sudria.demo.domain.avion;

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
  private List<Long> achats;

}
