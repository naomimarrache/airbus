package com.sudria.demo.application;

import com.sudria.demo.domain.AnimalService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {


  private AnimalService animalService;

  public Controller(AnimalService animalService) {
    this.animalService = animalService;
  }

  @GetMapping("/animals")
  public ResponseEntity<List<AnimalDto>> animals() {
    return new ResponseEntity<>(animalService.getAnimals(), HttpStatus.OK);
  }

  @RequestMapping(value = "/animals", method = RequestMethod.POST)
  public ResponseEntity<AnimalDto> animals(@RequestBody AnimalDto animalDto) {
    animalService.addAnimal(animalDto);
    return new ResponseEntity<AnimalDto>(animalDto, HttpStatus.CREATED);
  }


  @RequestMapping(value = "/animals/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<AnimalDto> deleteAnimals(@PathVariable Long id) {
    animalService.deleteAnimals(id);
    return new ResponseEntity<AnimalDto>(HttpStatus.OK);
  }

}
