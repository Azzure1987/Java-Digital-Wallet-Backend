package com.nexuspay.core.usecase;

import com.nexuspay.core.domain.model.Account;
import com.nexuspay.core.ports.outgoing.AccountRepositoryPort;
import com.nexuspay.core.ports.outgoing.TransactionRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    private AccountRepositoryPort repositoryMock;
    private TransactionRepositoryPort transactionRepositoryMock; // Nuevo Mock
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        // 1. Creamos ambos mocks
        repositoryMock = Mockito.mock(AccountRepositoryPort.class);
        transactionRepositoryMock = Mockito.mock(TransactionRepositoryPort.class);

        // 2. Inyectamos ambos mocks en el servicio (esto quita el error de compilación)
        accountService = new AccountService(repositoryMock, transactionRepositoryMock);
    }

    @Test
    void shouldDepositFundsSuccessfully() {
        // Arrange
        UUID accountId = UUID.randomUUID();
        Account existingAccount = new Account(accountId, UUID.randomUUID(), new BigDecimal("100.00"), "USD");

        when(repositoryMock.findById(accountId)).thenReturn(Optional.of(existingAccount));

        // Act
        accountService.depositFunds(accountId, new BigDecimal("50.00"));

        // Assert & Verify
        assertEquals(new BigDecimal("150.00"), existingAccount.getBalance());

        // Verificaciones de nivel Senior:
        verify(repositoryMock, times(1)).save(any(Account.class));

        // Verificamos que TAMBIÉN se registró la transacción en el historial
        verify(transactionRepositoryMock, times(1)).save(any());
    }
}