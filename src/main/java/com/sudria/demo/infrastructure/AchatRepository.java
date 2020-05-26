package com.sudria.demo.infrastructure;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchatRepository extends CrudRepository<AchatEntity, Long> {

}
