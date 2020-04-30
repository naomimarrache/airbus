package com.sudria.demo.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Builder
public class AnimalSpecie {

private String family;
private String geographicDistribution;
private Integer individualsNumber;
private Boolean Endangered;
}
