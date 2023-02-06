module com.example.database2try {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;
    requires java.prefs;


    opens com.example.database2try to javafx.fxml;
    exports com.example.database2try;
}