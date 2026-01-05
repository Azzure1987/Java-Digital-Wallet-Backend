package com.nexuspay.infrastructure.config;

import com.nexuspay.core.ports.outgoing.AccountRepositoryPort;
import com.nexuspay.core.ports.outgoing.TransactionRepositoryPort;
import com.nexuspay.core.usecase.AccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Infrastructure configuration class.
 * This is where we tell Spring how to instantiate our Domain Services.
 */
@Configuration
@EnableTransactionManagement // Enables Spring's annotation-driven transaction management capability
public class BeanConfiguration {

    /**
     * Creates the AccountService bean.
     * Spring will automatically inject an implementation of AccountRepositoryPort
     * here.
     */
    @Bean
    @org.springframework.transaction.annotation.Transactional // <--- Esto es la clave
    public AccountService accountService(AccountRepositoryPort accountRepo,
            TransactionRepositoryPort transRepo) {
        return new AccountService(accountRepo, transRepo);
    }
}