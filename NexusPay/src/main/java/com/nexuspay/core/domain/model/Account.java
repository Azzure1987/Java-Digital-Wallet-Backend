package com.nexuspay.core.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

import com.nexuspay.core.domain.exception.InsufficientFundsException;

/**
 * Account is a Domain Entity.
 * It follows business rules regardless of the database or UI.
 */
public class Account {
    private final UUID id;
    private final UUID userId;
    private BigDecimal balance;
    private final String currency;

    public Account(UUID id, UUID userId, BigDecimal balance, String currency) {
        this.id = id;
        this.userId = userId;
        this.balance = balance;
        this.currency = currency;
    }

    // Business Logic: Only positive deposits allowed
    public void deposit(BigDecimal amount) {
        // Industrial standard: fail fast and be explicit
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero");
        }
        this.balance = this.balance.add(amount);
    }

    // Business Logic: Withdrawal with safety check
    public void withdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero");
        }
        if (this.balance.compareTo(amount) < 0) {
            throw new InsufficientFundsException("Not enough balance for this operation");
        }
        this.balance = this.balance.subtract(amount);
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getCurrency() {
        return currency;
    }
}
