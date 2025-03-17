package com.example.bank_final;

import java.sql.Connection;
import java.sql.SQLException;

public interface IOperations {
    public float getAccountBalance();

    void deposit(Client client, Float amount, Connection connection)throws SQLException;

    void withdraw(Client client, Float amount, Connection connection)throws SQLException;
}
