package com.example.bank_final;

import java.sql.Connection;

public abstract class Person {

    private String username;
    private String password;

    public Person(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Person(String password) {
        this.password = password;
    }

    public Person() {
    }

    public String getUsername() {
        return this.username;
    }


    public String getPassword() {
        return this.password;
    }


    public String toString() {
        return this.username;
    }
}

