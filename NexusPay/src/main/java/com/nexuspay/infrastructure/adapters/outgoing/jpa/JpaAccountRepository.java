package com.nexuspay.infrastructure.adapters.outgoing.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexuspay.infrastructure.adapters.outgoing.AccountEntity;

import java.util.UUID;

public interface JpaAccountRepository extends JpaRepository<AccountEntity, UUID> {
    // Spring Data JPA generará el código automáticamente
}