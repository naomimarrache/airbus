package com.sudria.demo.infrastructure;

import com.sudria.demo.domain.Avion;
import com.sudria.demo.domain.Avion.Food;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AvionDao {

  private ZooRepository zooRepository;
  private FoodRepository foodRepository;

  public AvionDao(ZooRepository zooRepository) {
    this.zooRepository = zooRepository;
  }

  public List<Avion> findAvions() {
    return StreamSupport.stream(zooRepository.findAll().spliterator(), false)
        .map(avionEntitie -> buildAvion(avionEntitie))
        .collect(Collectors.toList());
  }

  public Avion findAvions(Long id) throws NotFoundException {
    return buildAvion(zooRepository.findById(id).orElseThrow(NotFoundException::new));
  }

  public Avion createAvions(Avion avion) {
    return buildAvion(zooRepository.save(buildEntity(avion)));
  }

  public void deleteAvions(Long id) {
    zooRepository.delete(zooRepository.findById(id).get());
  }

  public void updateAvion(Avion avion) {

    AvionEntity avionEntity = zooRepository.save(buildEntity(avion));

    avion
        .getFoods()
        .stream()
        .forEach(food ->
            foodRepository.save(FoodEntity.builder()
            .category(food.getCategory())
            .frequency(food.getFrequency())
            .quantity(food.getQuantity())
                .avionEntity(avionEntity)
            .build()));
  }

  public Avion replaceAvion(Avion avion) {
    return buildAvion(zooRepository.save(buildEntity(avion)));
  }

  private AvionEntity buildEntity(Avion avion) {
    return AvionEntity
        .builder()
        .id(avion.getId())
        .version(avion.getVersion())
        .longueur(avion.getLongueur())
        .famille(avion.getFamille())
        .foodEntities(
            avion
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

  private Avion buildAvion(AvionEntity avionEntity) {
    return Avion.builder()
        .id(avionEntity.getId())
        .version(avionEntity.getVersion())
        .longueur(avionEntity.getLongueur())
        .famille(avionEntity.getFamille())
        .foods(
            avionEntity
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
