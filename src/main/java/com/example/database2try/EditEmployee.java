package com.example.database2try;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;

import static com.example.database2try.HelloApplication.currentUserId;
import static com.example.database2try.HelloApplication.employees;

public class EditEmployee {

    @FXML
    private Button addButton;
    private static Connection con;

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
    private Text checkUpdates;

    @FXML
    private TextField newNameTF;

    @FXML
    void addOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        Employees tmpEmp=new Employees(empNameTF.getText(),emailTF.getText(),Double.parseDouble(deptTF.getText()),Integer.parseInt(phoneNumberTF.getText()),1);
        //add to database

        //updating database
        String SQL = "insert into employees(employeeName,email,dept,phoneNumber,userId) values('"
                +empNameTF.getText()+ "','"
                + emailTF.getText() + "',"
                + Double.parseDouble(deptTF.getText())
                + "," + Integer.parseInt(phoneNumberTF.getText()) + "," +currentUserId+");";

        connectDB();
        System.out.println("Connection established");
        Statement stmt = con.createStatement();
        stmt.executeUpdate(SQL);
        stmt.close();
        con.close();
        System.out.println("Connection closed");

        empNameTF.clear();
        phoneNumberTF.clear();
        deptTF.clear();
        emailTF.clear();
        employees.clear();
        checkUpdates.setText("Employee has been added successfully");

        getData();//insert the employee to the array list


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

    @FXML
    void deleteOnAction(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        //remove it first from the array list:
        //search for the employee:
        int tmpEmpId=0;
        int exist=0;
        for(int k=0;k<employees.size();k++)
        {
            if(employees.get(k).getName().equalsIgnoreCase(empNameTF.getText()))
            {
                tmpEmpId=employees.get(k).getId();
                employees.remove(k);
                System.out.println(tmpEmpId);
                exist=1;
                break;
            }
        }
        //remove from database
        connectDB();
        String Sql="delete from Employees where employeeId="+tmpEmpId+";";
        Statement stmt= con.createStatement();
        stmt.executeUpdate(Sql);
        stmt.close();
        con.close();
        checkUpdates.setVisible(true);
        if(exist==1) {
            checkUpdates.setText("employee has been deleted");
        }
        else
            checkUpdates.setText("Employee not found");
    }

    @FXML
    void onKeyPressedOnName(KeyEvent event) {
        for(int i=0;i<employees.size();i++)
        {
            if(employees.get(i).getName().equalsIgnoreCase(empNameTF.getText()))
            {
                emailTF.setText(employees.get(i).getEmail());
                deptTF.setText(String.valueOf(employees.get(i).getDept()));
                phoneNumberTF.setText(String.valueOf(employees.get(i).getPhoneNumber()));
            }
        }
    }

    @FXML
    void updateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        //update email:
        int tmpEmpId=0;
        int index=0;
        int exist=0;
        for(int i=0;i<employees.size();i++)
        {
            if(employees.get(i).getName().equalsIgnoreCase(empNameTF.getText()))
            {
                exist=1;
                tmpEmpId=employees.get(i).getId();
                employees.get(i).setEmail(emailTF.getText());
                employees.get(i).setDept(Double.parseDouble(deptTF.getText()));
                employees.get(i).setPhoneNumber(Integer.parseInt(phoneNumberTF.getText()));

                index=i;
                break;
            }
        }
        if(exist==1) {
            checkUpdates.setVisible(true);
            checkUpdates.setText("Employee has been updated successfully");
            //update database
            connectDB();
            System.out.println("connection initialized");
            String Sql = "update Employees" +
                    " set email = '" + employees.get(index).getEmail() + "' where employeeId = " + tmpEmpId + ";";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(Sql);
            stmt.close();
            con.close();
            ////////////////////////////////////
            //update dept
            connectDB();
            System.out.println("connection initialized");
            Sql = "update Employees" +
                    " set dept = " + employees.get(index).getDept() + " " +
                    "where employeeId = " + tmpEmpId + ";";
            stmt = con.createStatement();
            stmt.executeUpdate(Sql);
            stmt.close();
            con.close();
            //////////////////////////////////
            //update phone Number
            connectDB();
            System.out.println("connection initialized");
            Sql = "update Employees" +
                    " set phoneNumber = " + employees.get(index).getPhoneNumber() + " " +
                    "where employeeId = " + tmpEmpId + ";";
            stmt = con.createStatement();
            stmt.executeUpdate(Sql);
            stmt.close();
            con.close();
            ///////////////////////////////
            if (!newNameTF.getText().isEmpty()) {
                //update name
                connectDB();
                System.out.println("connection initialized");
                Sql = "update Employees" +
                        " set employeeName = '" + newNameTF.getText() + "' where employeeId = " + tmpEmpId + ";";
                stmt = con.createStatement();
                stmt.executeUpdate(Sql);
                stmt.close();
                con.close();

                empNameTF.clear();
                phoneNumberTF.clear();
                deptTF.clear();
                emailTF.clear();
                employees.clear();
            }
        }
        else {
            checkUpdates.setVisible(true);
            checkUpdates.setText("Employee not found");
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

        con = DriverManager.getConnection(dbURL, p);

    }
    private  void getData() throws SQLException, ClassNotFoundException {//get d


        String SQL;
        connectDB();
        SQL = "select * from Employees";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);


        while ( rs.next() )  {
            employees.add(new Employees(rs.getInt(1),rs.getString(2),
                    rs.getString(3),rs.getDouble(4), rs.getInt(5),rs.getInt(6)));
        }
        rs.close();
        stmt.close();
        con.close();
    }


    @FXML
    void toUpdateNameOnAction(MouseEvent event) {
        newNameTF.setVisible(true);
    }

}