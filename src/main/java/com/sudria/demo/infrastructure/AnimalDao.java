package com.sudria.demo.infrastructure;

import com.sudria.demo.domain.Animal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AnimalDao {

  private ZooRepository zooRepository;

  public AnimalDao(ZooRepository zooRepository) {
    this.zooRepository = zooRepository;
  }

  public List<Animal> findAnimals() {
    return StreamSupport.stream(zooRepository.findAll().spliterator(), false)
        .map(animalEntitie -> buildAnimal(animalEntitie))
        .collect(Collectors.toList());
  }

  public Animal findAnimals(Long id) throws NotFoundException {
    return buildAnimal(zooRepository.findById(id).orElseThrow(NotFoundException::new));
  }

  public Animal createAnimals(Animal animal) {
    return buildAnimal(zooRepository.save(buildEntity(animal)));
  }

  public void deleteAnimals(Long id) {
    zooRepository.delete(zooRepository.findById(id).get());
  }

  public void updateAnimal(Animal animal) {
    zooRepository.save(buildEntity(animal));
  }

  public Animal replaceAnimal(Animal animal) {
    return buildAnimal(zooRepository.save(buildEntity(animal)));
  }

  private AnimalEntity buildEntity(Animal animal) {
    return AnimalEntity
        .builder()
        .id(animal.getId())
        .name(animal.getName())
        .age(animal.getAge())
        .category(animal.getCategory())
        .build();
  }

  private Animal buildAnimal(AnimalEntity animalEntity) {
    return Animal.builder()
        .id(animalEntity.getId())
        .name(animalEntity.getName())
        .age(animalEntity.getAge())
        .category(animalEntity.getCategory())
        .build();
  }


}
