package com.nexuspay.core.usecase;

import com.nexuspay.core.domain.model.Account;
import com.nexuspay.core.domain.model.Transaction;
import com.nexuspay.core.ports.outgoing.AccountRepositoryPort;
import com.nexuspay.core.ports.outgoing.TransactionRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class AccountService {

    private final AccountRepositoryPort repositoryPort;
    private final TransactionRepositoryPort transactionRepository;

    // Constructor fixed: Now initializes both final variables
    public AccountService(AccountRepositoryPort repositoryPort, TransactionRepositoryPort transactionRepository) {
        this.repositoryPort = repositoryPort;
        this.transactionRepository = transactionRepository;
    }

    public Account saveAccount(Account account) {
        return repositoryPort.save(account);
    }

    public void depositFunds(UUID accountId, BigDecimal amount) {
        validateAmount(amount);
        Account account = getAccountOrThrow(accountId);

        account.deposit(amount);
        repositoryPort.save(account);

        // Registro de auditoría
        registerTransaction(accountId, amount, "DEPOSIT");
    }

    public void withdrawFunds(UUID accountId, BigDecimal amount) {
        validateAmount(amount);
        Account account = getAccountOrThrow(accountId);

        account.withdraw(amount);
        repositoryPort.save(account);

        // Registro de auditoría
        registerTransaction(accountId, amount, "WITHDRAWAL");
    }

    public List<Transaction> getTransactionHistory(UUID accountId) {
        getAccountOrThrow(accountId); // Verify that the account exists
        return transactionRepository.findByAccountId(accountId);
    }

    private void registerTransaction(UUID accountId, BigDecimal amount, String type) {
        Transaction transaction = new Transaction(
                UUID.randomUUID(),
                accountId,
                amount,
                type,
                LocalDateTime.now());
        transactionRepository.save(transaction);
    }

    private Account getAccountOrThrow(UUID accountId) {
        return repositoryPort.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with ID: " + accountId));
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("The amount must be greater than zero");
        }
    }

    // In AccountService.java
    public Account getAccountDetails(UUID accountId) {
        return getAccountOrThrow(accountId);
    }

    // In AccountService.java
    /**
     * Executes a fund transfer between two accounts.
     * Ensures atomicity: if any step fails, the entire operation is rolled back.
     */
    @Transactional(rollbackFor = Exception.class)
    public void transferFunds(UUID sourceId, UUID targetId, BigDecimal amount) {
        try {
            validateAmount(amount);

            // Step 1: Withdraw from source account
            Account source = getAccountOrThrow(sourceId);
            source.withdraw(amount);
            repositoryPort.save(source);

            // Step 2: Deposit into target account
            // If targetId is a userId instead of an accountId, this will throw an exception
            Account target = getAccountOrThrow(targetId);
            target.deposit(amount);
            repositoryPort.save(target);

            // Step 3: Record audit transactions for both accounts
            registerTransaction(sourceId, amount.negate(), "TRANSFER_OUT");
            registerTransaction(targetId, amount, "TRANSFER_IN");

        } catch (Exception e) {
            // Senior Tip: Always log the context of the failure
            System.err.println("Transaction aborted: " + e.getMessage());
            throw e; // Critical: rethrow to ensure the @Transactional proxy sees the error
        }
    }
}