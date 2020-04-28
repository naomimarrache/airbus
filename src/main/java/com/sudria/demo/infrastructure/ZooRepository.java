package com.sudria.demo.infrastructure;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Repository;

@Repository
public class ZooRepository {

  public List<String> getAnnimals() {
    return Stream.of("cat", "dog", "bird").collect(Collectors.toList());
  }
}
