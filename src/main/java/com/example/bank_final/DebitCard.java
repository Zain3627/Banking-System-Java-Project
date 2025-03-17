package com.example.bank_final;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DebitCard implements IOperations {


    private float accountBalance;
    public float getAccountBalance() {
        return accountBalance;
    }
    public void setAccountBalance(float accountBalance) {
        this.accountBalance = accountBalance;
    }

    public DebitCard(float accountBalance) {
        this.accountBalance = accountBalance;
    }

    public void deposit(Client client, Float amount, Connection connection) throws SQLException {
        String query = "UPDATE Client SET debitBalance = debitBalance + ? WHERE accountnumber = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setFloat(1, amount);
        pstmt.setInt(2, Integer.parseInt(client.getUsername()));
        pstmt.executeUpdate();
        Logincontroller.showInfoAlert("Successful Transaction");
        client.getDebitCard().setAccountBalance(getAccountBalance() + amount);
    }

    public void withdraw(Client client, Float amount, Connection connection) throws SQLException {
        String query = "UPDATE Client SET debitBalance = debitBalance - ? WHERE accountnumber = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setFloat(1, amount);
        pstmt.setInt(2, Integer.parseInt(client.getUsername()));
        pstmt.executeUpdate();
        Logincontroller.showInfoAlert("Successful Transaction");
        client.getDebitCard().setAccountBalance(getAccountBalance() - amount);

    }

    public String toString() {
        return "Account Balance: " + this.accountBalance;
    }
}
