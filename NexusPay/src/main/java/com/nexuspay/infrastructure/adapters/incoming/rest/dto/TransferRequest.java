package com.nexuspay.infrastructure.adapters.incoming.rest.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequest(
                @NotNull(message = "Target account ID is required") UUID targetAccountId,

                @NotNull(message = "Amount is required") @Positive(message = "Transfer amount must be positive") BigDecimal amount) {
}