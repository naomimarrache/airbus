package com.sudria.demo.infrastructure;

import com.sudria.demo.application.AnimalDto;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Service;

@Service
public class AnimalDao {

  private ZooRepository zooRepository;

  public AnimalDao(ZooRepository zooRepository) {
    this.zooRepository = zooRepository;
  }

  public List<AnimalDto> findAnimals() {
    return StreamSupport.stream(zooRepository.findAll().spliterator(), false)
        .map(animalEntitie -> AnimalDto
            .builder()
            .id(animalEntitie.getId())
            .name(animalEntitie.getName())
            .age(animalEntitie.getAge())
            .category(animalEntitie.getCategory())
            .build()
        )
        .collect(Collectors.toList())
        ;
  }

  public void createAnimal(AnimalDto animalDto) {
    zooRepository
        .save(
            AnimalEntity
                .builder()
                .id(animalDto.getId())
                .name(animalDto.getName())
                .age(animalDto.getAge())
                .category(animalDto.getCategory())
                .build());
  }

  public void deleteAnimals(Long id) {
    zooRepository.delete(zooRepository.findById(id).get());
  }
}
