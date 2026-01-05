package com.nexuspay.infrastructure.adapters.outgoing.jpa;

import com.nexuspay.core.domain.model.Account;
import com.nexuspay.core.ports.outgoing.AccountRepositoryPort;
import com.nexuspay.infrastructure.adapters.outgoing.AccountEntity;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
public class PostgresAccountAdapter implements AccountRepositoryPort {

    private final JpaAccountRepository jpaRepository;

    public PostgresAccountAdapter(JpaAccountRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Account> findById(UUID id) {
        // We search in the database and convert to a domain model
        return jpaRepository.findById(id).map(AccountEntity::toDomain);
    }

    @Override
    @Transactional
    public Account save(Account account) {
        // We convert the domain to a JPA entity and save
        AccountEntity entity = AccountEntity.fromDomain(account);
        AccountEntity savedEntity = jpaRepository.save(entity);
        return savedEntity.toDomain();
    }
}