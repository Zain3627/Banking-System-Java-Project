module com.example.bank_final {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.bank_final to javafx.fxml;
    exports com.example.bank_final;
}