package com.nexuspay.infrastructure.adapters.outgoing;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
public class AccountEntity {
    @Id
    // This ensures that Hibernate generates the UUID if one isn't provided,
    // but allows us to use the domain's UUID if it already exists.
    private UUID id;

    @Column(nullable = false, unique = true) // A user should not have two identical accounts in this model.
    private UUID userId;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    @Column(nullable = false, length = 3)
    private String currency;

    // Helper method to map from Domain to Entity
    public static AccountEntity fromDomain(com.nexuspay.core.domain.model.Account domain) {
        AccountEntity entity = new AccountEntity();
        entity.setId(domain.getId());
        entity.setUserId(domain.getUserId());
        entity.setBalance(domain.getBalance());
        entity.setCurrency(domain.getCurrency());
        return entity;
    }

    // Helper method to map from Entity to Domain
    public com.nexuspay.core.domain.model.Account toDomain() {
        return new com.nexuspay.core.domain.model.Account(
                this.id, this.userId, this.balance, this.currency);
    }

}