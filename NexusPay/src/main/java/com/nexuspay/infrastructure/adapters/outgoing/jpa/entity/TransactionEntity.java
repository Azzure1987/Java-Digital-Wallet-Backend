package com.nexuspay.infrastructure.adapters.outgoing.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID accountId;

    @Column(nullable = false, precision = 38, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private String type; // DEPOSIT o WITHDRAWAL

    @Column(nullable = false)
    private LocalDateTime timestamp;

    // Método de ayuda para mapear desde el Dominio a JPA (Nivel Senior)
    public static TransactionEntity fromDomain(com.nexuspay.core.domain.model.Transaction domain) {
        return new TransactionEntity(
                domain.id(),
                domain.accountId(),
                domain.amount(),
                domain.type(),
                domain.timestamp());
    }
}
