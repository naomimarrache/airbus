package com.sudria.demo.infrastructure;

import com.sudria.demo.domain.Animal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Service;

@Service
public class AnimalsDao {

  private AnimalRepository animalRepository;

  public AnimalsDao(AnimalRepository animalRepository) {
    this.animalRepository = animalRepository;
  }

  public List<Animal> getAnimals(){
     return StreamSupport.stream(animalRepository.findAll().spliterator(), false)
        .map(animalEntitie -> buildDto(animalEntitie))
        .collect(Collectors.toList());

  }

  private Animal buildDto(AnimalEntity animalEntitie) {
    return Animal.builder()
        .identifiant(animalEntitie.getId())
        .name(animalEntitie.getName())
        .build();
  }

}
