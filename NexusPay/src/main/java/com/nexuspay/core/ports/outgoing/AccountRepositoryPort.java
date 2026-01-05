package com.nexuspay.core.ports.outgoing;

import com.nexuspay.core.domain.model.Account;
import java.util.Optional;
import java.util.UUID;

/**
 * Outgoing Port.
 * This interface defines what the core needs from the persistence layer.
 * It follows the Dependency Inversion Principle.
 */
public interface AccountRepositoryPort {

    /**
     * Retrieves an account from the storage.
     * 
     * @param id The unique identifier of the account.
     * @return An Optional containing the account if found.
     */
    Optional<Account> findById(UUID id);

    /**
     * Saves or updates an account in the storage.
     * 
     * @param account The domain account to persist.
     * @return The saved account.
     */
    Account save(Account account);
}
