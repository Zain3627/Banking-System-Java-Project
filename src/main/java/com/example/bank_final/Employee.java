package com.example.bank_final;

import java.sql.*;
import java.util.ArrayList;

public class Employee extends Person {
    private float salary;

    public Branch getBranch() {
        return branch;
    }

    private Branch branch;

    public Employee(String username, String password, float salary, int branch_id) {
        super(username, password);
        this.salary = salary;
        branch = Branch.getBranch(branch_id);
    }

    public float getSalary() {
        return this.salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }


    public boolean addClient(Client client, Connection connection) throws SQLException {
        String query = "INSERT INTO Client (clientName, password, phoneNumber, PIN, savingsBalance, debitBalance) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);  // this makes generated keys available for retreival

        pstmt.setString(1, client.getClientName());
        pstmt.setString(2, client.getPassword());
        pstmt.setString(3, client.getPhoneNumber());
        pstmt.setString(4, client.getPin());
        pstmt.setFloat(5, client.getSavingsAccount().getAccountBalance());
        pstmt.setFloat(6, client.getDebitCard().getAccountBalance());

        int rowsAffected = pstmt.executeUpdate();   // a variable to store the number of changed rows to check the insertion has been completed or not

        if (rowsAffected > 0) {
            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();  // to enter the new line to read
            int accNo = rs.getInt(1);   // get the value in the column number one from the generated keys
            AddClientController.showInfoAlert("Client added successfully with account number "+accNo);
            return true;
        } else {
            AddClientController.showErrorAlert("Failed to add client.");
            return  false;
        }
    }

    public Person findClient(int accNo, Connection connection) throws SQLException {

        String query = "SELECT * FROM Client WHERE accountnumber = " + accNo;


        PreparedStatement pstmt = connection.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            Person personFound = new Client(
                    rs.getString("accountnumber"),
                    rs.getString("password"),
                    rs.getString("clientName"),
                    rs.getString("phoneNumber"),
                    rs.getString("PIN"),
                    rs.getInt("savingsBalance"),
                    rs.getInt("debitBalance"));
            return personFound;
        }

        else return null;

    }

    public int deleteClient(int accNo, Connection connection) throws SQLException {
        String query = "DELETE FROM Client WHERE accountnumber = " + accNo;

        PreparedStatement pstmt = connection.prepareStatement(query);
        int rs = pstmt.executeUpdate();
        return rs;
    }

    public static Person login(Connection connection, String username, String password) throws Exception {
        // SQL query to check if the user exists with the given username and password
        String query = "SELECT * FROM " + "Employee" + " WHERE username = ? AND password = ?";

        // Prepare the SQL statement
        PreparedStatement pstmt = connection.prepareStatement(query);   // Create a prepared statement
        pstmt.setString(1, username);  // Set the username parameter
        pstmt.setString(2, password);  // Set the password parameter

        // Execute the query
        ResultSet rs = pstmt.executeQuery();    // Execute the query and get the result set

        if (rs.next()) {
            Person person = new Employee(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getInt("salary"),
                    rs.getInt("branchId")   // Create a new Employee object
            );
            return person;
        }
        return null;
    }

    public ArrayList<Client> showClients(Connection connection) throws SQLException {
        ArrayList<Client> clients = new ArrayList<>();
        String query = "SELECT * FROM Client";  // SQL query to retrieve all clients

        PreparedStatement pstmt = connection.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery(query);  // Execute the query
        while (rs.next()) {
            // Add the client to the list
            clients.add(new Client(
                    rs.getString("accountnumber"),
                    rs.getString("password"),
                    rs.getString("clientName"),
                    rs.getString("phoneNumber"),
                    rs.getString("PIN"),
                    rs.getInt("savingsBalance"),
                    rs.getInt("debitBalance") ));
        }
        return clients;
    }



    public String toString() {
        return super.toString() + "\t\t\t"
                + this.salary + "\t\t" +
                branch.toString();
    }
}
