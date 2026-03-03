package com.yape.business.financial.infrastructure.web;

import com.yape.business.financial.infrastructure.events.TransactionEventBus;
import com.yape.business.financial.infrastructure.events.TransactionReceivedEvent;
import com.yape.business.financial.infrastructure.datastore.entity.EventEntity;
import com.yape.business.financial.infrastructure.web.payload.TransactionPayload;
import tools.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/write")
public class ServiceController {
    private final TransactionEventBus eventBus;
    private final ObjectMapper mapper;

    public ServiceController(TransactionEventBus eventBus, ObjectMapper mapper) {
        this.eventBus = eventBus;
        this.mapper = mapper;
    }

    @PostMapping()
    public Mono<TransactionReceivedEvent> create(@RequestBody TransactionPayload payload) {
        UUID txId = UUID.randomUUID();
        // Payload convert to EventEntity
        var event = new TransactionReceivedEvent(txId, payload.accountExternalIdDebit(), payload.accountExternalIdCredit(), payload.transferTypeId(), payload.value());
        eventBus.publish(event);
        return Mono.just(event);
    }
}
