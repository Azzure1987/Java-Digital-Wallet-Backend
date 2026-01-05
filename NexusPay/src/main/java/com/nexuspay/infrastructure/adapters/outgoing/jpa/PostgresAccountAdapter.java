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
        // Buscamos en DB y convertimos a modelo de dominio
        return jpaRepository.findById(id).map(AccountEntity::toDomain);
    }

    @Override
    @Transactional
    public Account save(Account account) {
        // Convertimos el dominio a entidad JPA y guardamos
        AccountEntity entity = AccountEntity.fromDomain(account);
        AccountEntity savedEntity = jpaRepository.save(entity);
        return savedEntity.toDomain();
    }
}