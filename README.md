# Banking System

This project is a **Banking System** developed in Java, designed based on the provided UML diagram and connected to a MySQL database. The system supports various functionalities for administrators, employees, and clients, including account management, transactions, and branch operations.

---

## Features

### 1. Admin Features
- Add and manage employees.
- View and remove employees.
- Find specific employees.
- Login functionality.

### 2. Employee Features
- Add and delete clients.
- Manage client accounts (deposits and withdrawals).
- View client details.
- Login functionality.

### 3. Client Features
- Manage savings and debit accounts.
- Deposit and withdraw money.
- View account balances.
- Login functionality.

### 4. Database Integration
- The system uses a MySQL database for storing and managing data for branches, clients, employees, and transactions.

---

## Class Design

The project follows an object-oriented design with the following key components:

### 1. **Admin**
- Manages employees and their details.
- Extends the abstract `Person` class.

### 2. **Employee**
- Handles client operations like account creation and deletion.
- Extends the abstract `Person` class.

### 3. **Client**
- Represents a bank customer with `DebitCard` and `SavingsAccount` objects.

### 4. **SavingsAccount & DebitCard**
- Implements the `IOperations` interface for deposit, withdrawal, and balance management.

### 5. **Branch**
- Represents a bank branch with unique ID and location.

### 6. **Database**
- Singleton class for managing database connections.

---

## Prerequisites

### Software Requirements
- **Java**: JDK 11 or above
- **MySQL**: 8.0 or above
- **IDE**: IntelliJ IDEA, Eclipse, or any preferred IDE

### Database Setup
1. Import the provided `banking_system.sql` file into your MySQL database.
2. Update the database credentials in the `Database` class:
   ```java
   DATABASE_USER = "your_username";
   DATABASE_PASSWORD = "your_password";
   DATABASE_URL = "jdbc:mysql://localhost:3306/your_database";
   ```

---

## How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/your_username/banking-system.git
   ```

2. Import the project into your preferred IDE.

3. Configure the database connection in the `Database` class.

4. Run the main Java file to start the application.

---

## UML Diagram

The design follows the provided UML diagram, which outlines the relationships and functionalities between classes such as `Admin`, `Employee`, `Client`, `SavingsAccount`, and `Branch`.

---

## License

This project is licensed under the [MIT License](LICENSE).

---

## Contributions

Feel free to fork the repository and submit pull requests for improvements or additional features.

---

## Contact

For questions or issues, contact [your_email@example.com].

