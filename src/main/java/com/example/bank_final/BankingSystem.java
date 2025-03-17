package com.example.bank_final;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;

import java.io.IOException;

public class BankingSystem extends Application {




    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BankingSystem.class.getResource("loginScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Banking System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {launch();}

}

