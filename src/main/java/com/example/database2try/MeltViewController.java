package com.example.database2try;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;

import static com.example.database2try.HelloApplication.*;

public class MeltViewController implements Initializable {
    public static Stage primaryStage;
    @FXML
    private Button newItem_MenuButton;

    @FXML
    private Button Cash;

    @FXML
    private Button Day;

    @FXML
    private Button Employee;
    @FXML
    private Button outOrdersButton;
    private static Connection con;
    @FXML
    private Button Menu;

    @FXML
    private Button Month;

    @FXML
    private Button Total;

    @FXML
    private Button Year;
    @FXML
    private Button employeesButton;
    @FXML
    private Text userNameTF;

    @FXML
    void CashOnAction(ActionEvent event) throws IOException {

        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MeltOrder.fxml")));
        Scene scene=new Scene(root);
        primaryStage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
    @FXML
    void outOrdersOnAction(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("DeleteOrder.fxml")));
        Scene scene=new Scene(root);
        primaryStage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    @FXML
    void menuOnAction(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MeltMenus.fxml")));
        Scene scene=new Scene(root);
        primaryStage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    @FXML
    void NewOrderOrItemOnAction(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MeltNewOrderOrItem.fxml")));
        Scene scene=new Scene(root,1380,750);
        primaryStage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    @FXML
    void EmployeesOnAction(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MeltEmployees.fxml")));
        Scene scene=new Scene(root,1380,750);
        primaryStage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
    @FXML
    void editEmpOnAction(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addDeleteEmployee.fxml")));
        Scene scene=new Scene(root,1380,750);
        primaryStage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
    @FXML
    void logOutOnAction(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("NewLogInInterface.fxml")));
        Scene scene=new Scene(root,1380,750);
        primaryStage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    @FXML
    void dailyOnAction(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MeltFinancialReportDaily.fxml")));
        Scene scene=new Scene(root);
        Stage primaryStage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
    @FXML
    void monthlyOnAction(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MeltFinancialReportMonthlyController.fxml")));
        Scene scene=new Scene(root);
        Stage primaryStage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
    @FXML
    void yearlyOnAction(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MeltFinancialReportYearly.fxml")));
        Scene scene=new Scene(root);
        Stage primaryStage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
    @FXML
    void totalOnAction(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MeltFinancialReportTotal.fxml")));
        Scene scene=new Scene(root);
        Stage primaryStage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userNameTF.setVisible(true);
        userNameTF.setText(currentUserName);

        ArrayList<Users> tmp=new ArrayList<>();
        int currentRuleId=0;

        String SQL;

        try {
            connectDB();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        SQL = "select * from users";

        Statement stmt = null;
        try {
            stmt = con.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        while (true)  {
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                tmp.add(new Users(rs.getInt(1),rs.getString(2),
                        rs.getString(3),rs.getInt(4)));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int userCurrentRuleId=0;
        for(int i=0;i<tmp.size();i++)
        {
            if(tmp.get(i).getUserId()==currentUserId)
            {
                userCurrentRuleId=tmp.get(i).getUserRuleId();
            }

        }
        System.out.println(userCurrentRuleId);
        if(userCurrentRuleId==2)//if the user is employee
        {
            Menu.setDisable(true);
            employeesButton.setDisable(true);
            newItem_MenuButton.setDisable(true);
            Employee.setDisable(true);
        }


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
        con = DriverManager.getConnection (dbURL, p);
    }

}