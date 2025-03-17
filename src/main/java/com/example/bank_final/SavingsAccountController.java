package com.example.bank_final;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;

public class SavingsAccountController {

    @FXML //Debit Card Screen
    private TextField tfAvailableBalanceDebit;
    @FXML
    private TextField tfMoneyDebit;
    @FXML
    private TextField tfDebitPIN;



    public void settfAvailableBalanceSaving(){
        Client client = (Client) Logincontroller.person;
        tfAvailableBalanceDebit.setText(String.valueOf(client.getSavingsAccount().getAccountBalance()));
    }

    //close button for saving card
    public void handleDCCloseBt(){
        Stage stage = (Stage) tfAvailableBalanceDebit.getScene().getWindow();
        stage.close();
    }

    public void handleDeposit(){
        Client client = (Client) Logincontroller.person;
        if (tfMoneyDebit.getText().isEmpty() || tfDebitPIN.getText().isEmpty() ||
                Float.parseFloat(tfMoneyDebit.getText()) <= 0)   {
            Logincontroller.showErrorAlert("Enter valid values");
            return;
        }
        else if (!(tfDebitPIN.getText().equals(client.getPin())))
        {
            Logincontroller.showErrorAlert("Invalid PIN");
            return;
        }
        else {
            Connection connection = Database.getInstance().getConnection(); // get the single instance of Database class
            try {
                client.getSavingsAccount().deposit(client,Float.parseFloat(tfMoneyDebit.getText()), connection);
                tfMoneyDebit.clear();
                tfDebitPIN.clear();
                tfAvailableBalanceDebit.setText(String.valueOf(client.getSavingsAccount().getAccountBalance()));
            }
            catch (Exception e){
                System.out.println(e.toString());
                Logincontroller.showErrorAlert("Operation Failed");
            }
        }

    }
    public void handleWithdraw(){
        Client client = (Client) Logincontroller.person;
        if (tfMoneyDebit.getText().isEmpty() || Float.parseFloat(tfMoneyDebit.getText()) <= 0 ||
                (SavingsAccount.interestRate*Float.parseFloat(tfMoneyDebit.getText())+Float.parseFloat(tfMoneyDebit.getText())) >= Float.parseFloat(tfAvailableBalanceDebit.getText()))    {
            Logincontroller.showErrorAlert("You don't have enough balance");
            return;
        }
        else if (!(tfDebitPIN.getText().equals(client.getPin())))
        {
            Logincontroller.showErrorAlert("Invalid PIN");
            return;
        }
        else {

            // create the confirmation alert
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Transaction Confirmation");
            confirmationAlert.setHeaderText("Are you sure you want to proceed?");
            confirmationAlert.setContentText("You are about to withdraw " + tfMoneyDebit.getText() +
                    " plus an additional interest fees equals " + 0.1f * Float.parseFloat(tfMoneyDebit.getText()) );
            // get response from user
            ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);
            if (result != ButtonType.OK) {
                return; // terminate the method
            }

            Connection connection = Database.getInstance().getConnection(); // get the single instance of Database class
            try {
                client.getSavingsAccount().withdraw(client,Float.parseFloat(tfMoneyDebit.getText()), connection);
                tfMoneyDebit.clear();
                tfDebitPIN.clear();
                tfAvailableBalanceDebit.setText(String.valueOf(client.getSavingsAccount().getAccountBalance()));
            }
            catch (Exception e){
                System.out.println(e.toString());
                Logincontroller.showErrorAlert("Operation Failed");
            }
        }
    }

    public void handleInterest()
    {
        Client client = (Client) Logincontroller.person;
        client.getSavingsAccount().setAccountBalance(Float.parseFloat(tfAvailableBalanceDebit.getText()));
        float interest = client.getSavingsAccount().calcMonth();
        Logincontroller.showInfoAlert("Interest for this month is: " + interest);
    }
}
