package com.example.bank_final;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Logincontroller {

    @FXML
    private TextField tfUser;
    @FXML
    private TextField tfPass;

    @FXML
    private Button btLogOut;


    @FXML   //find client
    private TextField tfAccNo;
    @FXML   //find employee
    private TextField tfUserName;

    @FXML   //delete client
    private TextField tfAccNoDele;
    @FXML   //delete employee
    private TextField tfEmpNameDele;

    @FXML   //find client
    private ListView<String> oneClientList;
    @FXML   // find employee
    private ListView<String> oneEmployeeList;



    public static Person person;

    @FXML
    protected void handlebtLogin() {
        String username = tfUser.getText();
        String password = tfPass.getText();
        if (username.isEmpty() || password.isEmpty()) {
            showErrorAlert("Please enter valid values");
            return;
        }

        try {
            Connection connection = Database.getInstance().getConnection(); // get the single instance of Database class
            Person person1 = Admin.login(connection, username, password);
            Person person2 = Employee.login(connection, username, password);
            Person person3 = Client.login(connection, username, password);

            if (person1 instanceof Admin) {
                Stage stage = (Stage) tfUser.getScene().getWindow();
                Stage stage2 = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(BankingSystem.class.getResource("mainScreen.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
                stage2.setTitle("Admin Window");
                stage2.setScene(scene);
                stage2.show();
                stage.close();
                person = person1;

            }
            else if (person2 instanceof Employee) {
                Stage stage = (Stage) tfUser.getScene().getWindow();
                Stage stage2 = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(BankingSystem.class.getResource("mainScreen.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
                stage2.setTitle("Employee Window");
                stage2.setScene(scene);
                stage2.show();
                stage.close();
                person = person2;
            }
            else if (person3 instanceof Client)
            {
                Stage stage = (Stage) tfUser.getScene().getWindow();
                Stage stage2 = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(BankingSystem.class.getResource("mainScreen.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
                stage2.setTitle("Client Window");
                stage2.setScene(scene);
                stage2.show();
                stage.close();
                person = person3;
            }
            else
            {
                showErrorAlert("Invalid username or password");
            }
        }

        catch (Exception e) {
            System.out.println(e.toString());
            showErrorAlert("Failed to connect to the database");
        }

    }

    public static void showErrorAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Something went wrong!");
        alert.setContentText(message);
        alert.showAndWait();
    }
    public static void showInfoAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Successful operation");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void handlebtLogOut() throws IOException {
        Stage stage2 = (Stage) btLogOut.getScene().getWindow();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(BankingSystem.class.getResource("loginScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Banking System");
        stage.setScene(scene);
        stage.show();
        stage2.close();
        person = null;
    }

    public void handlebtShowClients () throws IOException {
        try {
            if (person instanceof Client)
            {
                showErrorAlert("You are not authorized to view this page");
                return;
            }
            else {
                Connection connection = Database.getInstance().getConnection(); // get the single instance of Database class
                Employee employee = (Employee) person;
                ArrayList<Client> clients = employee.showClients(connection);

                FXMLLoader fxmlLoader = new FXMLLoader(BankingSystem.class.getResource("showClientsScreen.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);

                showClientsController controller = fxmlLoader.getController();
                controller.setClientsList(clients);

                Stage stage = new Stage();
                stage.setTitle("All clients");
                stage.setScene(scene);
                stage.show();
            }

        }
        catch (Exception e) {
            System.out.println(e.toString());
            showErrorAlert("Failed to connect to the database");
            System.out.println(person instanceof Client);

        }

    }

    // find client screen
    public void handlebtFindClient () throws IOException {
        if (person instanceof Client)
        {
            showErrorAlert("You are not authorized to view this page");
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(BankingSystem.class.getResource("findClientsScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 400);

        Stage stage = new Stage();
        stage.setTitle("Find Client");
        stage.setScene(scene);
        stage.show();

    }

    // find employee screen
    public void handlebtFindEmployeet () throws IOException {
        if (person instanceof Admin)
        {
            FXMLLoader fxmlLoader = new FXMLLoader(BankingSystem.class.getResource("findEmployeeScreen.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 750, 400);

            Stage stage = new Stage();
            stage.setTitle("Find Employee");
            stage.setScene(scene);
            stage.show();
        }
        else {
            showErrorAlert("You are not authorized to view this page");
            return;
        }
    }
    //delete client screen
    public void handlebtDeleteClient() throws IOException {
        if (person instanceof Client)
        {
            showErrorAlert("You are not authorized to view this page");
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(BankingSystem.class.getResource("deleteClientsScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 400);

        Stage stage = new Stage();
        stage.setTitle("Delete Client");
        stage.setScene(scene);
        stage.show();
    }
    //delete Employee screen
    public void handlebtDeleteEmployee() throws IOException {
        if (person instanceof Admin)
        {
            FXMLLoader fxmlLoader = new FXMLLoader(BankingSystem.class.getResource("deleteEmployeeScreen.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 750, 400);

            Stage stage = new Stage();
            stage.setTitle("Delete Employee");
            stage.setScene(scene);
            stage.show();
        }
        else {
            showErrorAlert("You are not authorized to view this page");
            return;
        }
    }

    public void handlebtShowEmployees () throws IOException, SQLException {
        try {
            if (person instanceof Admin)
            {
                Connection connection = Database.getInstance().getConnection(); // get the single instance of Database class
                Admin admin = (Admin) person;
                ArrayList<Employee> employees = admin.showEmployees(connection);

                FXMLLoader fxmlLoader = new FXMLLoader(BankingSystem.class.getResource("showEmployeeScreen.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);

                showEmployeesController controller = fxmlLoader.getController();
                controller.setClientsList(employees);

                Stage stage = new Stage();
                stage.setTitle("All employees");
                stage.setScene(scene);
                stage.show();
            }
            else {
                showErrorAlert("You are not authorized to view this page");
            }}
        catch (Exception e) {
            System.out.println(e.toString());
            showErrorAlert("Failed to connect to the database");
        }
    }
    // find client
    public void handleFindBt()
    {
        if (tfAccNo.getText().isEmpty())
        {
            showErrorAlert("Please enter an account number.");
            return;
        }
        int accNo = Integer.parseInt(tfAccNo.getText());
        Employee employee = (Employee) person;
        Connection connection = Database.getInstance().getConnection(); // get the single instance of Database class

        try {
            Person personFound = employee.findClient(accNo,connection);

            if (personFound != null) {

                oneClientList.getItems().clear();
                oneClientList.getItems().add(personFound.toString());
            }
            else {
                showErrorAlert("Client not found");
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            showErrorAlert("Failed to connect to the database");
        }
    }

    // find employee
    public void handleFindEmpBt()
    {
        if (tfUserName.getText().isEmpty())
        {
            showErrorAlert("Please enter a username.");
            return;
        }
        String user = tfUserName.getText();
        Admin admin = (Admin) person;
        Connection connection = Database.getInstance().getConnection(); // get the single instance of Database class

        try {
            Person personFound = admin.findEmployee(user,connection);

            if (personFound != null) {

                oneEmployeeList.getItems().clear();
                oneEmployeeList.getItems().add(personFound.toString());
            }
            else {
                showErrorAlert("Employee not found");
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            showErrorAlert("Failed to connect to the database");
        }
    }

    //delete client button
    public void handleDeleteBt()
    {
        if (tfAccNoDele.getText().isEmpty())
        {
            showErrorAlert("Please enter an account number.");
            return;
        }
        // create the confirmation alert
        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Delete Confirmation");
        confirmationAlert.setHeaderText("Are you sure you want to delete this client?");
        confirmationAlert.setContentText("Account Number: " + tfAccNoDele.getText());

        // get response from user
        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);
        if (result != ButtonType.OK) {
            return; // terminate the method
        }

        Connection connection = Database.getInstance().getConnection(); // get the single instance of Database class

        Employee employee = (Employee) person;


        try {
            int deleted =employee.deleteClient(Integer.parseInt(tfAccNoDele.getText()), connection);

            if (deleted == 1) {
                showInfoAlert("Client deleted successfully");
                Stage stage = (Stage) tfAccNoDele.getScene().getWindow();
                stage.close();
            }
            else {
                showErrorAlert("Client not found");
            }
        }

        catch (Exception e) {
            System.out.println(e.getMessage());
            showErrorAlert("Failed to connect to the database");
        }
    }

    //delete employee button
    public void handleDeleteEmplBt()
    {
        if (tfEmpNameDele.getText().isEmpty())
        {
            showErrorAlert("Please enter a username.");
            return;
        }
        // create the confirmation alert
        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Delete Confirmation");
        confirmationAlert.setHeaderText("Are you sure you want to delete this Employee?");
        confirmationAlert.setContentText("Employee name: " + tfEmpNameDele.getText());

        // get response from user
        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);
        if (result != ButtonType.OK) {
            return; // terminate the method
        }

        Connection connection = Database.getInstance().getConnection(); // get the single instance of Database class

        Admin admin = (Admin) person;


        try {
            int deleted =admin.fireEmployee(tfEmpNameDele.getText(), connection);

            if (deleted == 1) {
                showInfoAlert("Employee deleted successfully");
                Stage stage = (Stage) tfEmpNameDele.getScene().getWindow();
                stage.close();
            }
            else {
                showErrorAlert("Employee not found");
            }
        }

        catch (Exception e) {
            System.out.println(e.getMessage());
            showErrorAlert("Failed to connect to the database");
        }
    }

    //close button for find client
    public void handleFCloseBt(){
        Stage stage = (Stage) oneClientList.getScene().getWindow();
        stage.close();
    }
    //close button for find employee
    public void handleFCloseEBt(){
        Stage stage = (Stage) oneEmployeeList.getScene().getWindow();
        stage.close();
    }
    // close button for delete client
    public void handleDCloseBt(){
        Stage stage = (Stage) tfAccNoDele.getScene().getWindow();
        stage.close();
    }
    // close button for delete employee
    public void handleDECloseBt(){
        Stage stage = (Stage) tfEmpNameDele.getScene().getWindow();
        stage.close();
    }
    // add employee button
    public void handlebtAddEmployee() throws IOException {
        if (person instanceof Admin) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(BankingSystem.class.getResource("addEmployeeScreen.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 500, 600);

                Stage stage = new Stage();
                stage.setTitle("Add Employee");
                stage.setScene(scene);
                stage.show();   }
            catch (Exception e) {
                System.out.println(e.toString());
                showErrorAlert("Failed to connect to the database");
            }
        }
        else {
            showErrorAlert("You are not authorized to view this page");
            return;
        }

    }
    // add client button
    public void handlebtAddClient() throws IOException {
        if (person instanceof Client) {
            showErrorAlert("You are not authorized to view this page");
            return;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(BankingSystem.class.getResource("addClientScreen.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 500, 600);

            Stage stage = new Stage();
            stage.setTitle("Add Client");
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            System.out.println(e.toString());
            showErrorAlert("Failed to connect to the database");
        }
    }

    //Debit card screen
    public void handleDebitbt () throws IOException {
        if (person instanceof Client)
        {
            FXMLLoader fxmlLoader = new FXMLLoader(BankingSystem.class.getResource("debitCardScreen.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 750, 400);

            DebitCardController debitCardController = fxmlLoader.getController();
            debitCardController.settfAvailableBalanceDebit();
            Stage stage = new Stage();
            stage.setTitle("Debit Card");
            stage.setScene(scene);
            stage.show();
        }
        else    {
            showErrorAlert("You are not authorized to view this page");
            return;
        }
    }


    //Savings account screen
    public void handleSavings () throws IOException {
        if (person instanceof Client)
        {
            FXMLLoader fxmlLoader = new FXMLLoader(BankingSystem.class.getResource("savingsAccountScreen.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 750, 400);

            SavingsAccountController savingsAccountController = fxmlLoader.getController();
            savingsAccountController.settfAvailableBalanceSaving();

            Stage stage = new Stage();
            stage.setTitle("Savings Account");
            stage.setScene(scene);
            stage.show();
        }
        else    {
            showErrorAlert("You are not authorized to view this page");
            return;
        }

    }

}