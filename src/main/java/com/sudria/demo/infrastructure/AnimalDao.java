package com.sudria.demo.infrastructure;

import com.sudria.demo.application.AnimalDto;
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

  public List<AnimalDto> findAnimals() {
    return StreamSupport.stream(zooRepository.findAll().spliterator(), false)
        .map(animalEntitie -> buildDto(animalEntitie))
        .collect(Collectors.toList());
  }

  public AnimalDto findAnimals(Long id) throws NotFoundException {
    return buildDto(zooRepository.findById(id).orElseThrow(NotFoundException::new));
  }

  public AnimalDto createAnimals(AnimalDto animalDto) {
    return buildDto(zooRepository.save(buildEntity(animalDto)));
  }

  public void deleteAnimals(Long id) {
    zooRepository.delete(zooRepository.findById(id).get());
  }

  public void updateAnimal(AnimalDto animalDto) {
    zooRepository.save(buildEntity(animalDto));
  }

  public AnimalDto replaceAnimal(AnimalDto animalDto) {
    return buildDto(zooRepository.save(buildEntity(animalDto)));
  }

  private AnimalEntity buildEntity(AnimalDto animalDto) {
    return AnimalEntity
        .builder()
        .id(animalDto.getId())
        .name(animalDto.getName())
        .age(animalDto.getAge())
        .category(animalDto.getCategory())
        .build();
  }

  private AnimalDto buildDto(AnimalEntity animalEntity) {
    return AnimalDto.builder()
        .id(animalEntity.getId())
        .name(animalEntity.getName())
        .age(animalEntity.getAge())
        .category(animalEntity.getCategory())
        .build();
  }


}
