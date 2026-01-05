package com.nexuspay.core.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record Transaction(
        UUID id,
        UUID accountId,
        BigDecimal amount,
        String type, // "DEPOSIT" or "WITHDRAWAL"
        LocalDateTime timestamp) {
}