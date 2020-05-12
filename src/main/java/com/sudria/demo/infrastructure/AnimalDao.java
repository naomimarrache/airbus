package com.sudria.demo.infrastructure;

import com.sudria.demo.domain.Animal;
import com.sudria.demo.domain.Animal.Food;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AnimalDao {

  private ZooRepository zooRepository;
  private FoodRepository foodRepository;

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

    AnimalEntity animalEntity = zooRepository.save(buildEntity(animal));

    animal
        .getFoods()
        .stream()
        .forEach(food ->
            foodRepository.save(FoodEntity.builder()
            .category(food.getCategory())
            .frequency(food.getFrequency())
            .quantity(food.getQuantity())
                .animalEntity(animalEntity)
            .build()));
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
        .foodEntities(
            animal
                .getFoods()
                .stream()
                .map(food -> FoodEntity.builder()
                    .category(food.getCategory())
                    .frequency(food.getFrequency())
                    .quantity(food.getQuantity())
                    .build())
                .collect(Collectors.toList()))
        .build();
  }

  private Animal buildAnimal(AnimalEntity animalEntity) {
    return Animal.builder()
        .id(animalEntity.getId())
        .name(animalEntity.getName())
        .age(animalEntity.getAge())
        .category(animalEntity.getCategory())
        .foods(
            animalEntity
                .getFoodEntities()
                .stream()
                .map(foodEntity -> Food.builder()
                    .id(foodEntity.getId())
                    .category(foodEntity.getCategory())
                    .frequency(foodEntity.getFrequency())
                    .quantity(foodEntity.getQuantity())
                    .build())
                .collect(Collectors.toList())
                )
        .build();
  }


}
