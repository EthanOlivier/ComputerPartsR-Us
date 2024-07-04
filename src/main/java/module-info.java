module com.example.computerpartsrus {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.computerpartsrus to javafx.fxml;
    exports com.example.computerpartsrus;
}