package com.sudria.demo;

import com.sudria.demo.infrastructure.AnimalEntity;
import com.sudria.demo.infrastructure.ZooRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

  @Autowired
  private ZooRepository zooRepository;

  public DemoApplication(ZooRepository zooRepository) {
    this.zooRepository = zooRepository;
  }

  public static void main(String[] args) {

    SpringApplication.run(DemoApplication.class, args);
    System.out.println("Hello SUDRIA !");
  }

  @Override
  public void run(String... args) {

    log.info("Data initilisation...");
    this.zooRepository.save(
        AnimalEntity
            .builder()
            .id(1L)
            .name("Garfield")
            .age(5)
            .category("FELINE")
            .build());

    this.zooRepository.save(
        AnimalEntity
            .builder()
            .id(2L)
            .name("Nemo")
            .age(1)
            .category("FISCH")
            .build());
  }

}
