package com.sudria.demo.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Builder
public class Supervisor {

  private Long id;
  private String firstname;
  private String lastname;
  private int age;
  private String phone;
  private String email;

}
