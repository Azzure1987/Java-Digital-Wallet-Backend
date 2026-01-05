package com.nexuspay.infrastructure.adapters.outgoing.jpa;

import com.nexuspay.core.domain.model.Transaction;
import com.nexuspay.core.ports.outgoing.TransactionRepositoryPort;
import com.nexuspay.infrastructure.adapters.outgoing.jpa.entity.TransactionEntity;
import com.nexuspay.infrastructure.adapters.outgoing.jpa.repository.JpaTransactionRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TransactionRepositoryAdapter implements TransactionRepositoryPort {

    private final JpaTransactionRepository jpaRepository;

    public TransactionRepositoryAdapter(JpaTransactionRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        TransactionEntity entity = TransactionEntity.fromDomain(transaction);
        TransactionEntity savedEntity = jpaRepository.save(entity);

        // We converted back to the domain to comply with the port contract
        return new Transaction(
                savedEntity.getId(),
                savedEntity.getAccountId(),
                savedEntity.getAmount(),
                savedEntity.getType(),
                savedEntity.getTimestamp());
    }

    @Override
    public List<Transaction> findByAccountId(UUID accountId) {
        return jpaRepository.findByAccountId(accountId).stream()
                .map(entity -> new Transaction(
                        entity.getId(),
                        entity.getAccountId(),
                        entity.getAmount(),
                        entity.getType(),
                        entity.getTimestamp()))
                .collect(Collectors.toList());
    }
}