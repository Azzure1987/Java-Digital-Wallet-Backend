package com.nexuspay.infrastructure.adapters.outgoing.jpa.repository;

import com.nexuspay.infrastructure.adapters.outgoing.jpa.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaTransactionRepository extends JpaRepository<TransactionEntity, UUID> {
    // Spring Data genera la consulta SQL automáticamente por el nombre del método
    List<TransactionEntity> findByAccountId(UUID accountId);
}