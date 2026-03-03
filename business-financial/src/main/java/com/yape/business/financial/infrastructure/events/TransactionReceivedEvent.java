package com.yape.business.financial.infrastructure.events;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionReceivedEvent (
    UUID id,
    String accountExternalIdDebit,
    String accountExternalIdCredit,
    Integer transferTypeId,
    BigDecimal value
) implements TransactionEvent {}