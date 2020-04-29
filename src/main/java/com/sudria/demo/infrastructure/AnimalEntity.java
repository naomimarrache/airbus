package com.sudria.demo.infrastructure;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AnimalEntity {

  @Id
  @GeneratedValue
  private Long id;
  @Column(name = "NAME", length = 50, nullable = false)
  private String name;
  @Column(name = "AGE", nullable = false)
  private int age;
  @Column(name = "CATEGORY", length = 50, nullable = false)
  private String category;
}
