package com.example.bank_final;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.Console;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class AddClientController {

    @FXML
    private TextField tfName;
    @FXML
    private TextField tfPass;
    @FXML
    private TextField tfPhone;
    @FXML
    private TextField tfPIN;
    @FXML
    private TextField tfSavings;
    @FXML
    private TextField tfDeposit;


    public static void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Something went wrong!");
        alert.setContentText(message);
        alert.showAndWait();
    }
    public static void showInfoAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Successful addition");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void handleAddClientBt()
    {
        String name = tfName.getText();
        String pass = tfPass.getText();
        String phone = tfPhone.getText();
        String pin = tfPIN.getText();
        String savings = tfSavings.getText();
        String deposit = tfDeposit.getText();



        if (name.isEmpty() || pass.isEmpty() || phone.isEmpty() || pin.isEmpty() || savings.isEmpty() || deposit.isEmpty()) {
            showErrorAlert("All fields are required");
            return;
        }
        try {
            Connection connection = Database.getInstance().getConnection(); // get the single instance of Database class

            Client client = new Client(pass,name,phone,pin,
                    Float.parseFloat(deposit),Float.parseFloat(savings));

            Employee employee = (Employee) Logincontroller.person;
            if (employee.addClient(client, connection))
            {
                Stage stage = (Stage) tfName.getScene().getWindow();
                stage.close();
            }

        } catch (Exception e) {
            showErrorAlert("Failed to add client.");
        }

    }

    public void handleCloseBt()
    {
        Stage stage = (Stage) tfName.getScene().getWindow();
        stage.close();
    }

}
