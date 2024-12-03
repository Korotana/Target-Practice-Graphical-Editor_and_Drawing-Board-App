module com.example.ass3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ass3 to javafx.fxml;
    exports com.example.ass3;
}