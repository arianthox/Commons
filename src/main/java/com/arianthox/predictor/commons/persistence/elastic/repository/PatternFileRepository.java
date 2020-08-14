package com.arianthox.predictor.commons.persistence.elastic.repository;

import com.arianthox.predictor.commons.persistence.elastic.domain.PatternFileData;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@ConditionalOnProperty(value = "spring.data.elasticsearch.repositories.enabled",havingValue = "true")
public interface PatternFileRepository extends ElasticsearchRepository<PatternFileData, String> {

    Optional<List<PatternFileData>> findByType(String type);

    Optional<PatternFileData> findByName(String name);

    void deleteByName(String name);


}
