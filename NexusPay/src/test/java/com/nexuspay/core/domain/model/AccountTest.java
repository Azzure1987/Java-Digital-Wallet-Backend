package com.nexuspay.core.domain.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void shouldIncreaseBalanceWhenDepositIsMade() {
        // Arrange (Prepare)
        Account account = new Account(UUID.randomUUID(), UUID.randomUUID(), new BigDecimal("100.00"), "USD");

        // Act (Action)
        account.deposit(new BigDecimal("50.00"));

        // Assert (Verify)
        assertEquals(new BigDecimal("150.00"), account.getBalance());
    }

    @Test
    void shouldThrowExceptionWhenWithdrawMoreThanBalance() {
        Account account = new Account(UUID.randomUUID(), UUID.randomUUID(), new BigDecimal("100.00"), "USD");

        assertThrows(IllegalStateException.class, () -> {
            account.withdraw(new BigDecimal("150.00"));
        });
    }
}
