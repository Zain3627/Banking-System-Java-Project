package com.example.bank_final;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Singleton class to manage the database connection
public class Database {

    // Static instance to hold the single instance of the class
    private static Database instance;

    // JDBC Connection object
    private Connection connection;

    // Database credentials
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/banking_system";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "";

    // Private constructor to prevent other instantiation outside the class
    private Database() {
        try {
            // Initialize the connection
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database.");
        }
    }

    // Public static method to get to the single instance
    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    // Public method to get the connection
    public Connection getConnection() {
        return connection;
    }
}
