package com.arianthox.predictor.commons.adapter.util;

import akka.Done;
import akka.japi.function.Function;
import com.arianthox.predictor.commons.persistence.cache.domain.OffsetData;
import com.arianthox.predictor.commons.persistence.cache.repository.OffsetRepository;
import lombok.Setter;
import lombok.extern.java.Log;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.logging.Level;

@Log
@Component
@Scope("prototype")
public class OffsetStorage {

    @Setter
    private transient Function<ConsumerRecord<String, String>, CompletionStage<Done>> processor;

    @Setter
    private transient String consumerId;


    private final transient OffsetRepository currentOffset;

    public OffsetStorage(OffsetRepository currentOffset) {
        this.currentOffset = currentOffset;
    }


    public CompletionStage<Done> process(ConsumerRecord<String, String> record) {
        log.log(Level.FINE, "Receiving Message Offset: {0} Value: {2}", new Object[]{record.offset(),  record.value()});

        Optional.of(processor).ifPresent(processor -> {
            try {
                processor.apply(record);
                log.log(Level.FINEST, "Persisting Offset {0} for Topic {1}", new Object[]{record.offset(), record.topic()});
                currentOffset.save(OffsetData.builder()
                        .topic(record.topic())
                        .value(record.offset())
                        .consumerId(consumerId)
                        .build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return CompletableFuture.completedFuture(Done.getInstance());
    }

    public CompletionStage<Long> loadOffset(String consumerId,String topic) {
        this.consumerId = consumerId;
        Iterable<OffsetData> all = currentOffset.findAll();
        log.log(Level.INFO,"Offset List");
        all.forEach(offsetData -> {
            log.log(Level.INFO,"Offset Record [{0}]",offsetData);
        });
        return CompletableFuture.completedFuture(
                currentOffset.findById(OffsetData.builder().consumerId(consumerId).topic(topic).build().getId())
                        .orElseGet(() -> OffsetData.builder().value(0L).build())
                        .getValue());
    }

}
