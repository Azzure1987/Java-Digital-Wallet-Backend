package com.nexuspay.infrastructure.web.dto;

import java.util.UUID;

/**
 * DTO de nivel industrial para la creación de cuentas.
 * Usamos 'record' para inmutabilidad y claridad.
 */
public record AccountRequest(
        UUID userId,
        String currency) {
}