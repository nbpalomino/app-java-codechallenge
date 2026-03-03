package com.yape.business.financial.infrastructure.events.listener;

import com.yape.business.financial.infrastructure.datastore.entity.EventEntity;
import com.yape.business.financial.infrastructure.events.TransactionReceivedEvent;
import com.yape.shared.domain.TransactionType;
import com.yape.business.financial.infrastructure.service.EventStoreService;
import com.yape.business.financial.infrastructure.events.TransactionCreatedEvent;
import com.yape.business.financial.infrastructure.events.TransactionEventBus;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

@Slf4j
@Component
@AllArgsConstructor
public class TransactionEventListener {
    private final EventStoreService service;
    private final TransactionEventBus eventBus;
    private final ObjectMapper mapper;

    @PostConstruct
    public void init() {
        eventBus.asFlux()
            .ofType(TransactionReceivedEvent.class)
            .flatMap(event -> {
                log.info("[EVENT={}]: {}", event.eventName(), event);
                return service.storeEvent(event.id(), event.eventName(), mapper.writeValueAsString(event));
            })
            .doOnNext(this::send)
            .subscribe();
    }

    private void send(EventEntity entity) {

        eventBus.publish(new TransactionCreatedEvent(entity.getTransactionId(), TransactionType.PENDING, BigDecimal.valueOf(1200)));
    }
}
