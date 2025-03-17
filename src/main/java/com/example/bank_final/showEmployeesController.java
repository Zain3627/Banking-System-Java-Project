package com.example.bank_final;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.ArrayList;


public class showEmployeesController {

    @FXML
    private ListView<String> employeesList;

    public void setClientsList(ArrayList<Employee> employees) {
        // Populate the ListView with client data
        for (Employee employee : employees) {
            employeesList.getItems().add(employee.toString());
        }
    }

    public void handleCloseBt()
    {
        Stage stage = (Stage) employeesList.getScene().getWindow();
        stage.close();
    }

}
