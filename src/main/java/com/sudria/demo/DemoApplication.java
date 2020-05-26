package com.sudria.demo;

import com.sudria.demo.domain.Avion.Food;
import com.sudria.demo.infrastructure.AvionEntity;
import com.sudria.demo.infrastructure.FoodEntity;
import com.sudria.demo.infrastructure.FoodRepository;
import com.sudria.demo.infrastructure.ZooRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Slf4j
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

  //  @Autowired
  private ZooRepository zooRepository;
  //  @Autowired
  private FoodRepository foodRepository;

  public DemoApplication(ZooRepository zooRepository, FoodRepository foodRepository) {
    this.zooRepository = zooRepository;
    this.foodRepository = foodRepository;
  }

  public static void main(String[] args) {

    SpringApplication.run(DemoApplication.class, args);
    System.out.println("Hello SUDRIA !");
  }

  @Override
  public void run(String... args) {

    log.info("Data initilisation...");
    saveAvion(1L, "A300B1", 50, "A300", Arrays.asList(Food.builder().frequency(2).category("meat").build()));
    saveAvion(2L, "A310-200", 47, "A310", Arrays.asList(Food.builder().frequency(1).category("algue").build()));
  }

  @Transactional
  private void saveAvion(long id, String version, int longueur, String famille, List<Food> foods) {


    AvionEntity avionEntity = this.zooRepository.save(
        AvionEntity
            .builder()
            .id(id)
            .version(version)
            .longueur(longueur)
            .famille(famille)
            .build());

    foods.stream()
        .forEach(food ->
            foodRepository.save(
                FoodEntity
                    .builder()
                    .category(food.getCategory())
                    .frequency(food.getFrequency())
                    .quantity(food.getQuantity())
                    .avionEntity(avionEntity)
                    .build()
            ));
  }

}
