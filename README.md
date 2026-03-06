# High-Concurrency Wallet Service 💳

A production-grade Peer-to-Peer (P2P) digital wallet API built to handle concurrent transactions safely. This service simulates a financial backend where users can transfer funds without the risk of race conditions or double-spending.

## 🚀 Key Features
* **P2P Fund Transfers:** Securely transfer money between digital wallets.
* **Concurrency Control:** Implemented **Optimistic Locking (`@Version`)** to prevent race conditions during simultaneous transfer requests.
* **ACID Compliance:** Strict transaction management using `@Transactional` to ensure database integrity if a failure occurs mid-transfer.
* **RESTful Design:** Clean and standardized API endpoints.

## 🛠️ Tech Stack
* **Language:** Java 17
* **Framework:** Spring Boot 3, Spring Web
* **Database:** MySQL
* **ORM/Data Access:** Spring Data JPA, Hibernate
* **Build Tool:** Maven

## 🧠 Core Architecture Highlights
Handling financial data requires strict concurrency management. This project demonstrates how to avoid the "lost update" anomaly by leveraging JPA's `@Version` annotation. If two threads attempt to update the same wallet balance simultaneously, one will fail gracefully with an `ObjectOptimisticLockingFailureException`, protecting the financial integrity of the system.

## ⚙️ How to Run Locally
1. Clone the repository: `git clone https://github.com/hemanth-kumar-achar/high-concurrency-wallet.git`
2. Configure your MySQL database credentials in `src/main/resources/application.properties`.
3. Build the project: `mvn clean install`
4. Run the application: `mvn spring-boot:run`

## 📡 API Endpoints (Example)
* `POST /api/v1/wallets/transfer` - Initiates a fund transfer between two wallets.
* `GET /api/v1/wallets/{id}/balance` - Retrieves the current balance.
