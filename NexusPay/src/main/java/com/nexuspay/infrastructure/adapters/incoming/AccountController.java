package com.nexuspay.infrastructure.adapters.incoming;

import com.nexuspay.core.domain.model.Account;
import com.nexuspay.core.usecase.AccountService;
import com.nexuspay.infrastructure.adapters.incoming.rest.dto.TransferRequest;
import com.nexuspay.infrastructure.web.dto.AccountRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid; // <--- Import required
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@Tag(name = "Account Management", description = "Endpoints for managing digital wallet accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "Create a new account")
    @PostMapping
    public ResponseEntity<Account> createAccount(@Valid @RequestBody AccountRequest request) { // <--- Added @Valid
        Account newAccount = new Account(
                UUID.randomUUID(),
                request.userId(),
                BigDecimal.ZERO,
                request.currency());

        Account savedAccount = accountService.saveAccount(newAccount);
        return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
    }

    @Operation(summary = "Deposit funds", description = "Adds a positive amount of money to an existing account balance")
    @PostMapping("/{id}/deposit")
    public ResponseEntity<Void> deposit(@PathVariable UUID id, @RequestParam BigDecimal amount) {
        // Note: @Valid is not used directly in simple @RequestParam,
        // the Service's business logic will still protect this field.
        accountService.depositFunds(id, amount);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get account details by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable UUID id) {
        Account account = accountService.getAccountDetails(id);
        return ResponseEntity.ok(account);
    }

    @Operation(summary = "Transfer funds to another account")
    @PostMapping("/{id}/transfer")
    public ResponseEntity<Void> transfer(
            @PathVariable UUID id,
            @Valid @RequestBody TransferRequest request) { // <--- Agregado @Valid
        accountService.transferFunds(id, request.targetAccountId(), request.amount());
        return ResponseEntity.ok().build();
    }
}