package com.example.bank_final;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Client extends Person {
    private String clientName;
    private String phoneNumber;
    private String pin;

    public SavingsAccount getSavingsAccount() {
        return savingsAccount;
    }

    public DebitCard getDebitCard() {
        return debitCard;
    }

    private DebitCard debitCard;
    private SavingsAccount savingsAccount;

    public Client(String accountNumber, String password, String clientName, String phoneNumber,
                  String pin, float debitBalance, float savingsBalance) {
        super(accountNumber, password);
        this.clientName = clientName;
        this.phoneNumber = phoneNumber;
        this.pin = pin;
        this.savingsAccount = new SavingsAccount(debitBalance);
        this.debitCard = new DebitCard(savingsBalance);
    }

    // constructor without account number for passing the person to addClient method
    public Client(String password, String clientName, String phoneNumber,
                  String pin, float debitBalance, float savingsBalance) {
        super(password);
        this.clientName = clientName;
        this.phoneNumber = phoneNumber;
        this.pin = pin;
        this.savingsAccount = new SavingsAccount(debitBalance);
        this.debitCard = new DebitCard(savingsBalance);
    }


    public String getClientName() {
        return this.clientName;
    }


    public String getPhoneNumber() {
        return this.phoneNumber;
    }


    public String getPin() {
        return this.pin;
    }


    public static Person login(Connection connection, String username, String password) throws Exception {
        // SQL query to check if the user exists with the given username and password
        String query = "SELECT * FROM " + "Client" + " WHERE accountnumber = ? AND password = ?";

        // Prepare the SQL statement
        PreparedStatement pstmt = connection.prepareStatement(query);   // Create a prepared statement
        pstmt.setString(1, username);  // Set the username parameter
        pstmt.setString(2, password);  // Set the password parameter

        // Execute the query
        ResultSet rs = pstmt.executeQuery();    // Execute the query and get the result set

        if (rs.next()) {
            Person person = new Client(
                    rs.getString("accountnumber"),
                    rs.getString("password"),
                    rs.getString("clientName"),
                    rs.getString("phoneNumber"),
                    rs.getString("PIN"),
                    rs.getInt("savingsBalance"),
                    rs.getInt("debitBalance")// Create a new Client object
            );
            return person;
        }
        return null;
    }

    public String toString() {
        return this.clientName + "\t\t\t\t" +
                this.getUsername() + "\t\t\t"  +
                this.phoneNumber + "\t\t" +
                this.debitCard.getAccountBalance() + "\t\t\t" +
                this.savingsAccount.getAccountBalance();
    }
}

