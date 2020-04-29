package com.sudria.demo.domain;

import com.sudria.demo.application.AnimalDto;
import com.sudria.demo.infrastructure.AnimalDao;
import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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

  public void patchAnimals(AnimalDto animalDto) {
    animalDao.updateAnimal( animalDto);
  }

  public AnimalDto findAnimal(Long id) throws NotFoundException {
   return  animalDao.findAnimal(id);
  }
}
