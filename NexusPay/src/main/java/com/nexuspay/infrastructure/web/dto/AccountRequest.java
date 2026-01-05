package com.nexuspay.infrastructure.web.dto;

import java.util.UUID;

/**
 * Industrial-level DTO for account creation.
 * We use 'record' for immutability and clarity.
 */
public record AccountRequest(
        UUID userId,
        String currency) {
}