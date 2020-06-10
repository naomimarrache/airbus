package com.sudria.demo.domain.achat;

import com.sudria.demo.infrastructure.AchatDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AchatService {

  private AchatDao achatDao;

  public AchatService(AchatDao achatDao) {
    this.achatDao = achatDao;
  }

  public Achat addAchats(Long avionId, Achat achat) {
    return achatDao.addAchats(avionId, achat);
  }

  public List<Achat> findAchatsByAvionId(Long avionId) {
    return achatDao.findAchatsByAvionId(avionId);
  }

}
