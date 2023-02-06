package com.example.database2try;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static com.example.database2try.HelloApplication.employees;

public class MeltEmployeesController implements Initializable {

    HBox hBoxForEmployees = new HBox();
    @FXML
    private VBox VBoxforEmployees;

    AtomicInteger countEmployees = new AtomicInteger();

    @FXML
    private Button deleteButton;

    @FXML
    private TextField deptTF;

    @FXML
    private TextField emailTF;

    @FXML
    private TextField empNameTF;

    @FXML
    private TextField phoneNumberTF;

    @FXML
    private Button updateButton;

    @FXML
    private Button addButton;
    private static Connection con;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hBoxForEmployees.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        hBoxForEmployees.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        hBoxForEmployees.setAlignment(Pos.CENTER);
        hBoxForEmployees.setSpacing(10);

        for(int i=0;i<employees.size();i++)
        {
            countEmployees.getAndIncrement();
            Button button = new Button(employees.get(i).getName()+"\n"+employees.get(i).getEmail()
                    +"\n"+employees.get(i).getDept()
                    +"\n"+employees.get(i).getPhoneNumber());
            // button.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
            //  button.setCursor(Cursor.HAND);
            // button.setFont(new Font(15));
            // button.setStyle("-fx-background-radius:15 15 15 15");
            // button.setPrefHeight(50);
            //button.setPrefWidth(100);
            button.setWrapText(true);
            AtomicInteger finalI= new AtomicInteger(i);
            button.setOnAction(actionEvent ->
            {
                empNameTF.setText(employees.get(finalI.get()).getName());
                emailTF.setText(employees.get(finalI.get()).getEmail());
                deptTF.setText(String.valueOf(employees.get(finalI.get()).getDept()));
                phoneNumberTF.setText(String.valueOf(employees.get(finalI.get()).getPhoneNumber()));
            });
            hBoxForEmployees.getChildren().add(button);
            if(countEmployees.get() == 5){
                VBoxforEmployees.getChildren().add(hBoxForEmployees);
                hBoxForEmployees=new HBox();
                hBoxForEmployees.setAlignment(Pos.CENTER);
                hBoxForEmployees.setSpacing(10);
                countEmployees.set(0);
            }
        }


        if (countEmployees.get() > 0)
        {
            VBoxforEmployees.getChildren().add(hBoxForEmployees);
            countEmployees.set(0);
        }
    }


    @FXML
    void backOnAction(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MeltView.fxml")));
        Scene scene=new Scene(root,1380,750);
        Stage primaryStage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
    public static void connectDB() throws ClassNotFoundException, SQLException {
        String URL = "127.0.0.1";
        String port = "3306";
        String dbName = "melt2";
        String dbURL = "jdbc:mysql://" + URL + ":" + port + "/" + dbName + "?verifyServerCertificate=false";
        Properties p = new Properties();
        String dbUsername = "root";
        p.setProperty("user", dbUsername);
        String dbPassword = "jj137157177jj";
        p.setProperty("password", dbPassword);
        p.setProperty("useSSL", "false");
        p.setProperty("autoReconnect", "true");
        Class.forName("com.mysql.jdbc.Driver");

        con = DriverManager.getConnection(dbURL, p);

    }

}

