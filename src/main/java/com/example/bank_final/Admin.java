package com.example.bank_final;

import java.sql.*;
import java.util.ArrayList;

public class Admin extends Employee {
    public Admin(String username, String password, float salary, int branchId) {
        super(username, password,salary,branchId);
    }

    public ArrayList<Employee> showEmployees(Connection connection) throws SQLException {
        ArrayList<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM Admin";  // SQL query to retrieve all admins

        PreparedStatement pstmt = connection.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery(query);  // Execute the query
        while (rs.next()) {
            // Add the client to the list
            employees.add(new Admin(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getInt("salary"),
                    rs.getInt("branchid") ));
        }

        query = "SELECT * FROM Employee";  // SQL query to retrieve all employees
        pstmt = connection.prepareStatement(query);
        rs = pstmt.executeQuery(query);  // Execute the query
        while (rs.next()) {
            // Add the client to the list
            employees.add(new Employee(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getInt("salary"),
                    rs.getInt("branchid") ));
        }
        return employees;
    }

    public boolean addEmployee(Employee employee, Connection connection) throws SQLException {
        String query = "INSERT INTO employee (username, password, salary, branchid) " +
                "VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);  // this makes generated keys available for retreival

        pstmt.setString(1, employee.getUsername());
        pstmt.setString(2, employee.getPassword());
        pstmt.setFloat(3, employee.getSalary());
        pstmt.setInt(4, employee.getBranch().getId());

        int rowsAffected = pstmt.executeUpdate();   // a variable to store the number of changed rows to check the insertion has been completed or not

        if (rowsAffected > 0) {

            AddEmployeeController.showInfoAlert("Employee added successfully ");
            return true;
        } else {
            AddEmployeeController.showErrorAlert("Failed to add Employee.");
            return  false;
        }
    }

    public Person findEmployee(String user, Connection connection) throws SQLException {

        String query = "SELECT * FROM employee WHERE username = '" + user+"'";

        PreparedStatement pstmt = connection.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            Person personFound = new Employee(
                    user,
                    rs.getString("password"),
                    rs.getFloat("salary"),
                    rs.getInt("branchid"));
            return personFound;
        }
        else {
            query = "SELECT * FROM admin WHERE username = '" + user+"'";
            pstmt = connection.prepareStatement(query);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Person personFound = new Employee(
                        user,
                        rs.getString("password"),
                        rs.getFloat("salary"),
                        rs.getInt("branchid"));
                return personFound; }
            else    { return null;    }
        }

    }

    public int fireEmployee(String user, Connection connection) throws SQLException {
        String query = "DELETE FROM employee WHERE username = '" + user + "'";

        PreparedStatement pstmt = connection.prepareStatement(query);
        int rs = pstmt.executeUpdate();
        return rs;
    }

    public static Person login(Connection connection, String username, String password) throws Exception {
        // SQL query to check if the user exists with the given username and password
        String query = "SELECT * FROM " + "Admin" + " WHERE username = ? AND password = ?";

        // Prepare the SQL statement
        PreparedStatement pstmt = connection.prepareStatement(query);   // Create a prepared statement
        pstmt.setString(1, username);  // Set the username parameter
        pstmt.setString(2, password);  // Set the password parameter

        // Execute the query
        ResultSet rs = pstmt.executeQuery();    // Execute the query and get the result set

        if (rs.next()) {
            Person person = new Admin(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getInt("salary"),
                    rs.getInt("branchId")   // Create a new Admin object
            );
            return person;
        }
        return null;
    }


    public String toString() {
        return super.toString();
    }
}