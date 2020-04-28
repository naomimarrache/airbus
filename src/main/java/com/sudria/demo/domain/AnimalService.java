package com.sudria.demo.domain;

import com.sudria.demo.infrastructure.ZooRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AnimalService {

  private ZooRepository zooRepository;

  public AnimalService(ZooRepository zooRepository) {
    this.zooRepository = zooRepository;
  }

  public List<String> getAnimals() {
    return zooRepository.getAnnimals();
  }

}
