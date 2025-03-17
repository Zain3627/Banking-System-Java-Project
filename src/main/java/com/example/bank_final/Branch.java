package com.example.bank_final;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Branch {

    private static ArrayList<Branch> branches = new ArrayList<Branch>();

    private int id;  // read only property
    private String location;    // read only property
//    private ArrayList<Employee> employees = new ArrayList<Employee>();

    public int getId() {
        return this.id;
    }

    public String getLocation() {
        return this.location;
    }

    public Branch(int id, String location) {
        this.id = id;
        this.location = location;
        branches.add(this);
    }

    // Method overloadind
    public static ArrayList<Branch> getBranch() {
        Connection connection = Database.getInstance().getConnection(); // get the single instance of Database class
        String query = "SELECT * FROM branch";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                branches.add(new Branch(
                        rs.getInt("id"),
                        rs.getString("location")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return branches;
    }

    // Method overloadind
    public static Branch getBranch(int id) {
        branches = getBranch();   // to update the branches list before searching
        for (Branch branch : branches) {
            if (branch.getId() == id) {
                return branch;
            }
        }
        return null;
    }



    public String toString() {
        return this.id + "\t\t" + this.location;
    }

}
