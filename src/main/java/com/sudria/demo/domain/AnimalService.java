package com.sudria.demo.domain;

import com.sudria.demo.application.AnimalDto;
import com.sudria.demo.infrastructure.AnimalDao;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AnimalService {

  private AnimalDao animalDao;

  public AnimalService(AnimalDao animalDao) {
    this.animalDao = animalDao;
  }

  public List<AnimalDto> getAnimals() {
    return animalDao.findAnimals();
  }

  public void addAnimal(AnimalDto animalDto) {
    animalDao.createAnimal(animalDto);
  }

  public void deleteAnimals(Long id) {
    animalDao.deleteAnimals(id);
  }
}
