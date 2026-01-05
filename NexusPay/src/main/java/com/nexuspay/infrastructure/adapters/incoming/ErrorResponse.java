package com.nexuspay.infrastructure.adapters.incoming;

import java.time.LocalDateTime;

/**
 * Standardized error response for the NexusPay API.
 */
public record ErrorResponse(
                LocalDateTime timestamp,
                int status,
                String error,
                String message,
                String path) {
}