package com.example.bank_final;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;


public class AddEmployeeController {

    @FXML
    private TextField tfName;
    @FXML
    private TextField tfPass;
    @FXML
    private TextField tfSalary;
    @FXML
    private TextField tfBranchId;



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

    public void handleAddEmployeeBt()
    {
        String name = tfName.getText();
        String pass = tfPass.getText();
        String salary = tfSalary.getText();
        String branchId = tfBranchId.getText();


        if (name.isEmpty() || pass.isEmpty() || salary.isEmpty() || branchId.isEmpty()) {
            showErrorAlert("All fields are required");
            return;
        }

        try {
            Connection connection = Database.getInstance().getConnection(); // get the single instance of Database class

            Employee employee = new Employee(name,pass,
                    Float.parseFloat(salary),Integer.parseInt(branchId));

            Admin admin = (Admin) Logincontroller.person;
            if (admin.addEmployee(employee, connection))
            {
                Stage stage = (Stage) tfName.getScene().getWindow();
                stage.close();
            }

        } catch (Exception e) {
            showErrorAlert("Failed to add employee.");
            System.out.println(e.toString());

        }

    }

    public void handleCloseBt()
    {
        Stage stage = (Stage) tfName.getScene().getWindow();
        stage.close();
    }

}
