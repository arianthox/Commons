package com.arianthox.predictor.commons.persistence.elastic.repository;

import com.arianthox.predictor.commons.persistence.elastic.domain.BufferRawData;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@ConditionalOnProperty(value = "spring.data.elasticsearch.repositories.enabled",havingValue = "true")
public interface BufferRawPacketRepository extends ElasticsearchRepository<BufferRawData, String> {

    Optional<List<BufferRawData>> findAllByDeviceId(String deviceId);

}
