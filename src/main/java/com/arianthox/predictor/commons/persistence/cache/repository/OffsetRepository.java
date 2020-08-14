package com.arianthox.predictor.commons.persistence.cache.repository;

import com.arianthox.predictor.commons.persistence.cache.domain.OffsetData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OffsetRepository extends CrudRepository<OffsetData, Long> {


}
