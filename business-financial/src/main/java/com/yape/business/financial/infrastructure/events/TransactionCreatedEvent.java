package com.yape.business.financial.infrastructure.events;

import com.yape.shared.domain.TransactionType;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionCreatedEvent(
    UUID id,
    TransactionType status,
    BigDecimal value
) implements TransactionEvent {}