package com.sudria.demo;

import com.sudria.demo.domain.Avion.Achat;
import com.sudria.demo.infrastructure.AchatEntity;
import com.sudria.demo.infrastructure.AchatRepository;
import com.sudria.demo.infrastructure.AvionEntity;
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
  private AchatRepository achatRepository;

  public DemoApplication(ZooRepository zooRepository, AchatRepository achatRepository) {
    this.zooRepository = zooRepository;
    this.achatRepository = achatRepository;
  }

  public static void main(String[] args) {

    SpringApplication.run(DemoApplication.class, args);
    System.out.println("Hello SUDRIA !");
  }

  @Override
  public void run(String... args) {

    log.info("Data initilisation...");
    saveAvion(1L, "A300B1", 50, "A300", Arrays.asList(Achat.builder().price(15000000).compagny("AirFrance").build()));
    saveAvion(2L, "A310-200", 47, "A310", Arrays.asList(Achat.builder().price(7600000).compagny("EasyJet").build()));
  }

  @Transactional
  private void saveAvion(long id, String version, int longueur, String famille, List<Achat> achats) {


    AvionEntity avionEntity = this.zooRepository.save(
        AvionEntity
            .builder()
            .id(id)
            .version(version)
            .longueur(longueur)
            .famille(famille)
            .build());

    achats.stream()
        .forEach(achat ->
            achatRepository.save(
                AchatEntity
                    .builder()
                    .compagny(achat.getCompagny())
                    .price(achat.getPrice())
                    .quantity(achat.getQuantity())
                    .avionEntity(avionEntity)
                    .build()
            ));
  }

}
