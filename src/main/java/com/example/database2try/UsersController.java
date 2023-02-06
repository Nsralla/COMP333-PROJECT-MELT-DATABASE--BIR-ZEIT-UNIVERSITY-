package com.example.database2try;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.IntegerStringConverter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.ResourceBundle;


import static com.example.database2try.HelloApplication.*;
import static com.example.database2try.RulesController.rules;


public class UsersController implements Initializable {
    public static ObservableList<Users> users= FXCollections.observableArrayList(usersArrayList);
    private static Connection con;

    @FXML
    private TableColumn<Users, Integer> userId;

    @FXML
    private TableColumn<Users, String> userName;

    @FXML
    private TableColumn<Users, String> password;

    @FXML
    private TableColumn<Users, Integer> userRuleId;

    @FXML
    private TableView<Users> usersTable;

    @FXML
    private TextField ruleT;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        //System.out.println("first "+users.toString());

        userId.setCellValueFactory(new PropertyValueFactory<Users,Integer>("userId"));
        userName.setCellValueFactory(new PropertyValueFactory<Users,String>("userName"));
        userRuleId.setCellValueFactory(new PropertyValueFactory<Users,Integer>("userRuleId"));
        password.setCellValueFactory(new PropertyValueFactory<Users,String>("password"));

        usersTable.setItems(users);

        userId.setCellFactory(TextFieldTableCell.<Users,Integer>forTableColumn(new IntegerStringConverter()));
        userName.setCellFactory(TextFieldTableCell.forTableColumn());// so you can double-click the column and choose it
        //  password.setCellFactory(TextFieldTableCell.forTableColumn());
        //userRuleId.setCellFactory(TextFieldTableCell.<Users,Integer>forTableColumn(new IntegerStringConverter()));
        usersTable.setEditable(true);


    }

    @FXML
    void ruleIdOnEdit(TableColumn.CellEditEvent<Users,Integer>eIntegerCellEditEvent)throws SQLException, ClassNotFoundException {
        Users e=usersTable.getSelectionModel().getSelectedItem();
        int index=usersTable.getSelectionModel().getSelectedIndex();//taking the index of the object
        int requiredId=users.get(index).getUserId();//taking the id of the employee

        e.setUserRuleId(eIntegerCellEditEvent.getNewValue());
/////////////////////////////////updating the array list and the obser... one
        users.set(index,e);
//////////////////////////////updating database
        connectDB();
        System.out.println("connection initialized");
        String Sql="update Users" +
                " set userRuleId = "+users.get(index).getUserRuleId()+ " " +
                "where userId = "+requiredId+";";
        Statement stmt = con.createStatement();
        stmt.executeUpdate(Sql);
        stmt.close();
        con.close();
    }

    @FXML
    void userIdOnEdit(TableColumn.CellEditEvent<Users,Integer>eIntegerCellEditEvent)throws SQLException, ClassNotFoundException {
        Users e=usersTable.getSelectionModel().getSelectedItem();
        int index=usersTable.getSelectionModel().getSelectedIndex();//taking the index of the object
        int requiredId=users.get(index).getUserId();//taking the id of the employee

        e.setUserId(eIntegerCellEditEvent.getNewValue());


        for(int i=0;i<employees.size();i++){
            if(employees.get(i).getUserId()==requiredId){
                employees.get(i).setUserId(e.getUserId());
            }
        }

        for(int i=0;i<orderArrayList.size();i++){
            if(orderArrayList.get(i).getUserId()==requiredId){
                orderArrayList.get(i).setUserId(e.getUserId());
            }
        }


/////////////////////////////////updating the array list and the obser... one
        users.set(index,e);
//////////////////////////////updating database
        connectDB();
        System.out.println("connection initialized");
        String Sql="update Users" +
                " set userId = "+users.get(index).getUserId()+ " " +
                "where userId = "+requiredId+";";
        Statement stmt = con.createStatement();
        stmt.executeUpdate(Sql);
        stmt.close();
        con.close();
    }

    @FXML
    void userNameOnEdit(TableColumn.CellEditEvent<Users,String>eStringCellEditEvent)throws SQLException, ClassNotFoundException {
        Users e=usersTable.getSelectionModel().getSelectedItem();
        int index=usersTable.getSelectionModel().getSelectedIndex();//taking the index of the object
        int requiredId=users.get(index).getUserId();//taking the id of the employee

        e.setUserName(eStringCellEditEvent.getNewValue());
/////////////////////////////////updating the array list and the obser... one
        users.set(index,e);
//////////////////////////////updating database
        connectDB();
        System.out.println("connection initialized");
        String Sql="update Users" +
                " set userName = '"+users.get(index).getUserName()+ "' " +
                "where userId = "+requiredId+";";
        Statement stmt = con.createStatement();
        stmt.executeUpdate(Sql);
        stmt.close();
        con.close();
    }
    @FXML
    void onDeleteClicked(MouseEvent event) throws SQLException, ClassNotFoundException {
        int index=usersTable.getSelectionModel().getSelectedIndex();
        int requiredId=users.get(index).getUserId();
        usersTable.getItems().remove(index);

        for(int i=0;i<employees.size();i++){
            if(employees.get(i).getUserId()==requiredId){
                employees.get(i).setUserId(0);
            }
        }


        //deleting from data base
        connectDB();
        System.out.println("connection initialized");
        String Sql="delete from Users where userId="+requiredId+";";
        Statement stmt = con.createStatement();
        stmt.executeUpdate(Sql);
        stmt.close();

        con.close();
        System.out.println("connection closed");

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

    @FXML
    void whenUserIsSelected(MouseEvent event) {
        int index=usersTable.getSelectionModel().getSelectedIndex();//taking the index of the object
        int tmpRuleId=users.get(index).getUserRuleId();
        ;//taking the Rule Id id for the selected item

        for(int i=0;i<rules.size();i++){//search for the rule id of the selected row
            if(rules.get(i).getRuleId()==tmpRuleId) {
                ruleT.setText(rules.get(i).getRuleTitle());
                break;
            }
        }




    }


}