package com.sudria.demo.domain.avion;

import com.sudria.demo.infrastructure.AvionDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AvionService {

  private AvionDao avionDao;

  public AvionService(AvionDao avionDao) {
    this.avionDao = avionDao;
  }

  public List<Avion> getAvions() {
    return avionDao.findAvions();
  }

  @Cacheable("avions")
  public Avion getAvions(Long id) throws NotFoundException {
    log.info("********************** inside the AvionService ****************************");
    return avionDao.findAvions(id);
  }

  public Avion addAvion(Avion avion) throws NotFoundException {
    return avionDao.createAvions(avion);
  }

  public void deleteAvions(Long id) {
    avionDao.deleteAvions(id);
  }

  public void patchAvions(Avion avion) {
    avionDao.updateAvion(avion);
  }

  public Avion findAvion(Long id) throws NotFoundException {
    return avionDao.findAvions(id);
  }

  public Avion replaceAvion(Avion avion) {
    return avionDao.replaceAvion(avion);
  }
}
