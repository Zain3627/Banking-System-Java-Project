package com.example.bank_final;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SavingsAccount implements IOperations {
    private float accountBalance;

    public float getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(float accountBalance) {
        this.accountBalance = accountBalance;
    }

    public static float interestRate = 0.1f;

    public SavingsAccount(float accountBalance) {
        this.accountBalance = accountBalance;
    }

    public float calcMonth() {
        return this.accountBalance * this.interestRate / 12.0F;
    }

    public void deposit(Client client, Float amount, Connection connection) throws SQLException {
        String query = "UPDATE Client SET savingsBalance = savingsBalance + ? WHERE accountnumber = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setFloat(1, amount);
        pstmt.setInt(2, Integer.parseInt(client.getUsername()));
        pstmt.executeUpdate();
        Logincontroller.showInfoAlert("Successful Transaction");
        client.getSavingsAccount().setAccountBalance(getAccountBalance() + amount);
    }

    public void withdraw(Client client, Float amount, Connection connection) throws SQLException {
        amount = amount + interestRate * amount;
        String query = "UPDATE Client SET savingsBalance = savingsBalance - ? WHERE accountnumber = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setFloat(1, amount);
        pstmt.setInt(2, Integer.parseInt(client.getUsername()));
        pstmt.executeUpdate();
        Logincontroller.showInfoAlert("Successful Transaction");
        client.getSavingsAccount().setAccountBalance(getAccountBalance() - amount);
    }

    public String toString() {
        return "Account Balance: " + this.accountBalance + ", Interest Rate: " + this.interestRate;
    }
}
