package com.yape.business.financial.infrastructure.datastore.repository;

import com.yape.business.financial.infrastructure.datastore.entity.EventEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface EventStoreRepository extends ReactiveCrudRepository<EventEntity, Long> {
    Flux<EventEntity> findByTransactionId(UUID transactionId);
}
