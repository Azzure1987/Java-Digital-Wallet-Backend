package com.nexuspay.core.ports.outgoing;

import com.nexuspay.core.domain.model.Transaction;
import java.util.List;
import java.util.UUID;

public interface TransactionRepositoryPort {
    Transaction save(Transaction transaction);

    List<Transaction> findByAccountId(UUID accountId);
}