package com.sudria.demo.application;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(description = "All details about the Animal")
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnimalDto  implements Serializable {

  @Setter
  private Long id;
  private String name;
  private int age;
  private String category;
}
