package com.example.bank_final;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.ArrayList;


public class showClientsController {

    @FXML
    private ListView<String> clientsList;

    public void setClientsList(ArrayList<Client> clients) {
        // Populate the ListView with client data
        for (Client client : clients) {
            clientsList.getItems().add(client.toString());
        }
    }

    public void handleCloseBt()
    {
        Stage stage = (Stage) clientsList.getScene().getWindow();
        stage.close();
    }

}
