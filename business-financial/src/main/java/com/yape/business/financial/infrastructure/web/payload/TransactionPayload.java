package com.yape.business.financial.infrastructure.web.payload;

import java.math.BigDecimal;

public record TransactionPayload (
    String accountExternalIdDebit,
    String accountExternalIdCredit,
    Integer transferTypeId,
    BigDecimal value
) {}
