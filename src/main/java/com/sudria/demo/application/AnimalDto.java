package com.sudria.demo.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnimalDto {

  private Long id;
  private String name;
  private int age;
  //  private Category category;
  private String category;

//  @Builder
//  private static class Category {
//
//    private String code;
//    private String label;
//
//  }
}
