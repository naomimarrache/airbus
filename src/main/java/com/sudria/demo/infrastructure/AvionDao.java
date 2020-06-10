package com.sudria.demo.infrastructure;

import com.sudria.demo.domain.avion.Avion;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AvionDao {

  private AvionRepository avionRepository;
  private AchatRepository achatRepository;

  public AvionDao(AvionRepository avionRepository, AchatRepository achatRepository) {
    this.avionRepository = avionRepository;
    this.achatRepository = achatRepository;
  }

  public List<Avion> findAvions() {
    return StreamSupport.stream(avionRepository.findAll().spliterator(), false)
            .map(avionEntitie -> buildAvion(avionEntitie, achatRepository.findByAvionEntity(avionEntitie)))
            .collect(Collectors.toList());
  }

  public Avion findAvions(Long id) throws NotFoundException {
    AvionEntity avionEntity = avionRepository.findById(id).orElseThrow(NotFoundException::new);
    return buildAvion(avionEntity, achatRepository.findByAvionEntity(avionEntity));
  }

  public Avion createAvions(Avion avion) throws NotFoundException {
    AvionEntity avionEntity = avionRepository.save(buildAvionEntity(avion));
    return buildAvion(
            avionRepository.findById(avionEntity.getId()).orElseThrow(NotFoundException::new),
            achatRepository.findByAvionEntity(avionEntity));
  }


  public void deleteAvions(Long id) {
    avionRepository.delete(avionRepository.findById(id).get());
  }

  public Avion updateAvion(Avion avion) {
    return buildAvion(avionRepository.save(buildAvionEntity(avion)),
            achatRepository.findByAvionEntityId(avion.getId()));
  }


  public Avion replaceAvion(Avion avion) {
    AvionEntity avionEntity = avionRepository.save(buildAvionEntity(avion));
    return buildAvion(avionEntity, achatRepository.findByAvionEntity(avionEntity));
  }

  private AvionEntity buildAvionEntity(Avion avion) {
    return AvionEntity
            .builder()
            .version(avion.getVersion())
            .longueur(avion.getLongueur())
            .famille(avion.getFamille())
            .build();
  }

  private Avion buildAvion(AvionEntity avionEntity, List<AchatEntity> achatEntities) {
    return Avion.builder()
            .id(avionEntity.getId())
            .version(avionEntity.getVersion())
            .longueur(avionEntity.getLongueur())
            .famille(avionEntity.getFamille())
            .achats(achatEntities
                    .stream()
                    .map(achatEntity -> achatEntity.getId())
                    .collect(Collectors.toList()))
            .build();
  }
}
