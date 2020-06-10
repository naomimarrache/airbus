package com.sudria.demo.infrastructure;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AchatRepository extends CrudRepository<AchatEntity, Long> {
    List<AchatEntity> findByAvionEntityId(Long avionEntityId);
    List<AchatEntity> findByAvionEntity(AvionEntity avionEntity);

}
