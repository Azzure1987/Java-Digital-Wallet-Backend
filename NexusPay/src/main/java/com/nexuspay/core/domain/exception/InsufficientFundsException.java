package com.nexuspay.core.domain.exception;

// Custom exception for business logic
public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
