package com.sudria.demo.infrastructure;



import com.sudria.demo.domain.achat.Achat;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AchatDao {

    private AvionRepository avionRepository;
    private AchatRepository achatRepository;

    public AchatDao(AvionRepository avionRepository, AchatRepository achatRepository) {
        this.avionRepository = avionRepository;
        this.achatRepository = achatRepository;
    }

    public Achat addAchats(Long avionId, Achat achat) {
        AchatEntity achatEntity = buildAchatEntity(achat, avionRepository.findById(avionId).orElse(null));
        return buildAchat(achatRepository.save(achatEntity));
    }

    public List<Achat> findAchatsByAvionId() {
        return StreamSupport.stream(achatRepository.findAll().spliterator(), false)
                .map(achatEntity -> buildAchat(achatEntity))
                .collect(Collectors.toList());
    }

    public Achat findAchatById(Long avionId) {
        return buildAchat(achatRepository.findById(avionId).orElse(null));
    }

    public List<Achat> findAchatsByAvionId(Long avionId) {
        return achatRepository.findByAvionEntityId(avionId)
                .stream()
                .map(achatEntity -> buildAchat(achatEntity))
                .collect(Collectors.toList());
    }

    private AchatEntity buildAchatEntity(Achat achat, AvionEntity avionEntity) {
        return AchatEntity.builder()
                .compagny(achat.getCompagny())
                .price(achat.getPrice())
                .quantity(achat.getQuantity())
                .avionEntity(avionEntity)
                .build();
    }

    private Achat buildAchat(AchatEntity achatEntity) {
        return Achat.builder()
                .id(achatEntity.getId())
                .price(achatEntity.getPrice())
                .compagny(achatEntity.getCompagny())
                .quantity(achatEntity.getQuantity())
                .build();
    }
}
