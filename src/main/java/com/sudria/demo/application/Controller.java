package com.sudria.demo.application;

import com.sudria.demo.domain.AnimalService;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

  private AnimalService animalService;

  public Controller(AnimalService animalService) {
    this.animalService = animalService;
  }

  @GetMapping("/animals")
  public List<String> animals(){
    return animalService.getAnimals();
  }

}
