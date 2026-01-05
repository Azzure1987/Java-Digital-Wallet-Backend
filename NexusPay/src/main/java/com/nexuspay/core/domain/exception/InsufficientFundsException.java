package com.nexuspay.core.domain.exception;

// Excepción personalizada para lógica de negocio
public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
