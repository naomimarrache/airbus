package com.sudria.demo.infrastructure;

import com.sudria.demo.domain.Avion;
import com.sudria.demo.domain.Avion.Achat;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AvionDao {

  private AirbusRepository airbusRepository;
  private AchatRepository achatRepository;

  public AvionDao(AirbusRepository airbusRepository) {
    this.airbusRepository = airbusRepository;
  }

  public List<Avion> findAvions() {
    return StreamSupport.stream(airbusRepository.findAll().spliterator(), false)
        .map(avionEntitie -> buildAvion(avionEntitie))
        .collect(Collectors.toList());
  }

  public Avion findAvions(Long id) throws NotFoundException {
    return buildAvion(airbusRepository.findById(id).orElseThrow(NotFoundException::new));
  }

  public Avion createAvions(Avion avion) {
    return buildAvion(airbusRepository.save(buildEntity(avion)));
  }

  public void deleteAvions(Long id) {
    airbusRepository.delete(airbusRepository.findById(id).get());
  }

  public void updateAvion(Avion avion) {

    AvionEntity avionEntity = airbusRepository.save(buildEntity(avion));

    avion
        .getAchats()
        .stream()
        .forEach(achat ->
            achatRepository.save(AchatEntity.builder()
            .compagny(achat.getCompagny())
            .price(achat.getPrice())
            .quantity(achat.getQuantity())
                .avionEntity(avionEntity)
            .build()));
  }

  public Avion replaceAvion(Avion avion) {
    return buildAvion(airbusRepository.save(buildEntity(avion)));
  }

  private AvionEntity buildEntity(Avion avion) {
    return AvionEntity
        .builder()
        .id(avion.getId())
        .version(avion.getVersion())
        .longueur(avion.getLongueur())
        .famille(avion.getFamille())
        .achatEntities(
            avion
                .getAchats()
                .stream()
                .map(achat -> AchatEntity.builder()
                    .compagny(achat.getCompagny())
                    .price(achat.getPrice())
                    .quantity(achat.getQuantity())
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
        .achats(
            avionEntity
                .getAchatEntities()
                .stream()
                .map(achatEntity -> Achat.builder()
                    .id(achatEntity.getId())
                    .compagny(achatEntity.getCompagny())
                    .price(achatEntity.getPrice())
                    .quantity(achatEntity.getQuantity())
                    .build())
                .collect(Collectors.toList())
                )
        .build();
  }


}
