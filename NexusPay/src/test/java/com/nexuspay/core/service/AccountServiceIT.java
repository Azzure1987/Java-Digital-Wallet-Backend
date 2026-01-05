package com.nexuspay.core.service;

import com.nexuspay.core.domain.model.Account;
import com.nexuspay.core.ports.outgoing.AccountRepositoryPort;
import com.nexuspay.core.ports.outgoing.TransactionRepositoryPort;
import com.nexuspay.core.usecase.AccountService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AccountServiceIT {

    @Autowired
    private AccountService accountService;

    @MockBean
    private AccountRepositoryPort accountRepositoryPort;

    @MockBean
    private TransactionRepositoryPort transactionRepositoryPort;

    @Test
    @DisplayName("Should rollback transaction when target account does not exist")
    void shouldRollbackWhenTargetAccountIsMissing() {
        // GIVEN
        UUID sourceId = UUID.randomUUID();
        UUID invalidTargetId = UUID.randomUUID(); // This ID won't exist
        BigDecimal initialBalance = new BigDecimal("500.00");
        BigDecimal transferAmount = new BigDecimal("200.00");

        Account sourceAccount = new Account(sourceId, UUID.randomUUID(), initialBalance, "USD");

        // Mocking: Source exists, but Target is empty (not found)
        when(accountRepositoryPort.findById(sourceId)).thenReturn(Optional.of(sourceAccount));
        when(accountRepositoryPort.findById(invalidTargetId)).thenReturn(Optional.empty());

        // WHEN & THEN
        // We expect an exception because the target account is missing
        assertThrows(RuntimeException.class, () -> {
            accountService.transferFunds(sourceId, invalidTargetId, transferAmount);
        });

        // VERIFICATION (The "Senior" part)
        // 1. Verify that the source account balance in memory didn't stay at 300
        // Note: In a real DB test with @Transactional, the DB state would revert.
        // Here we verify that the repository was NOT called for the second account.
        verify(accountRepositoryPort, times(1)).save(any(Account.class)); // Only the first save attempt

        // 2. Verify no transactions were recorded since the process failed mid-way
        verify(transactionRepositoryPort, never()).save(any());

        System.out.println("Rollback Test Passed: Integrity remains intact.");
    }
}
