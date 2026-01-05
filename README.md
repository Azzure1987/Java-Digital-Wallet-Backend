# NexusPay - Industrial Grade Digital Wallet Backend

NexusPay is a high-performance, resilient digital wallet solution developed as an **Applied Data Science solution**. The project focuses on strict financial data integrity, transactional atomicity, and a decoupled architecture designed for scalability and maintainability.

## 🏗️ Architecture: Hexagonal (Ports & Adapters)

This project implements **Hexagonal Architecture** to ensure that the core business logic remains independent of external frameworks, databases, and UI components.

* **Domain Layer:** Contains the business entities (`Account`) and core logic. It is entirely agnostic of Spring Boot or JPA.
* **Application Layer (Use Cases):** Orchestrates the flow of data to and from the domain.
* **Infrastructure Layer:** * **Incoming Adapters:** REST Controllers using DTOs (Java Records) and Jakarta Validation.
    * **Outgoing Adapters:** Persistence implementation using Spring Data JPA and PostgreSQL.

## 🛡️ Financial Integrity & Transactional Safety

The system is designed with a "Safety-First" approach for fund management:

1.  **Transactional Atomicity:** All fund transfers are wrapped in `@Transactional` proxies. If any step (withdrawal, deposit, or auditing) fails, the entire operation is rolled back, ensuring no money is ever lost in transit.
2.  **Double-Layer Validation:**
    * **Entry Level:** Jakarta Validation (`@Valid`, `@Positive`) intercepts invalid requests at the Controller level.
    * **Domain Level:** The `Account` entity enforces business rules (e.g., preventing negative balances or invalid operations) even if bypassed by upper layers.
3.  **Precision:** Uses `BigDecimal` for all monetary calculations to avoid floating-point errors common in financial systems.

## 🧪 Quality Assurance

The project includes **Integration Tests (IT)** that simulate negative paths, specifically verifying that the database integrity remains intact and rollbacks are successfully triggered when encountering invalid target accounts or system errors.

## ❓ FAQ (Interview Questions)

**Q: Why use Hexagonal Architecture instead of traditional Layered Architecture?**
A: It allows for better testability and decouples the business logic from infrastructure. We can swap the database or move from REST to gRPC without touching the core banking logic.

**Q: How do you handle concurrency in account balance updates?**
A: While currently relying on Spring's transactional management, the architecture is prepared to implement Optimistic Locking (via JPA `@Version`) or Pessimistic Locking to handle high-concurrency environments.

---

📄 **License**
This project is distributed under the MIT license. Its purpose is strictly educational and research-based, developed as an Applied Data Science solution.

**Note for recruiters:**
This project demonstrates my ability to design and implement complex backend systems using professional standards. It highlights my mastery of transactional integrity, clean architecture, and the development of resilient financial software capable of handling real-world failure scenarios.

**Author:** JUAN S.  
**Contact:** https://github.com/johnyse99
