package com.example.database2try;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.ResourceBundle;

import static com.example.database2try.HelloApplication.rulesArrayList;
import static com.example.database2try.HelloApplication.usersArrayList;
public class RulesController implements Initializable {
    private static Connection con;
    public static ObservableList<Rules> rules= FXCollections.observableArrayList(rulesArrayList);


    @FXML
    private TextField ruleIdT;
    @FXML
    private TextField ruleTitleT;

    @FXML
    private TableColumn<Rules,String> ruleTitle;

    @FXML
    private TableColumn<Rules,Integer> ruleId;

    @FXML
    private TableView<Rules> rulesTable;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        ruleId.setCellValueFactory(new PropertyValueFactory<Rules,Integer>("ruleId"));
        ruleTitle.setCellValueFactory(new PropertyValueFactory<Rules,String>("ruleTitle"));

        rulesTable.setItems(rules);
        rulesTable.setEditable(true);

        ruleId.setCellFactory(TextFieldTableCell.<Rules,Integer>forTableColumn(new IntegerStringConverter()));
        ruleTitle.setCellFactory(TextFieldTableCell.forTableColumn());// so you can double-click the column and choose it

        System.out.println("first "+rules.toString());

    }

    @FXML
    void DeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        int index=rulesTable.getSelectionModel().getSelectedIndex();
        int requiredId=rules.get(index).getRuleId();
        System.out.println("index="+index);
        rulesTable.getItems().remove(index);


        for(int i=0;i<usersArrayList.size();i++){
            if(usersArrayList.get(i).getUserRuleId()==requiredId){
                usersArrayList.get(i).setUserRuleId(0);
            }
        }

        //employees.remove(index);
        //System.out.println(list.toString());
        //   list.remove(index);
        System.out.println("after deletion");
        System.out.println("itemList "+rules.toString());

        //deleting from data base
        connectDB();
        System.out.println("connection initialized");
        String Sql="delete from Rules where ruleId="+requiredId+";";
        Statement stmt = con.createStatement();
        stmt.executeUpdate(Sql);
        stmt.close();

        con.close();
        System.out.println("connection closed");

    }

    @FXML
    void addOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        rules.add(new Rules((Integer.parseInt(ruleIdT.getText())),ruleTitleT.getText()));


        //updating database
        String SQL="insert into Rules values(" +Integer.parseInt(ruleIdT.getText())+",'"+ruleTitleT.getText()+"');";

        connectDB();
        System.out.println("Connection established");
        Statement stmt = con.createStatement();
        stmt.executeUpdate(SQL);
        stmt.close();
        con.close();
        System.out.println("Connection closed");



        ruleIdT.clear();
        ruleTitleT.clear();
        // System.out.println("after adding");
        //System.out.println(foodInfo.toString());

    }

    @FXML
    void ruleIdOnEdit(TableColumn.CellEditEvent<Rules,Integer>eIntegerCellEditEvent) throws SQLException, ClassNotFoundException{
        Rules e=rulesTable.getSelectionModel().getSelectedItem();//taking object
        int index=rulesTable.getSelectionModel().getSelectedIndex();//taking the index of the object
        int requiredId=rules.get(index).getRuleId();//taking the id of the employee

        // System.out.println(requiredId);
        e.setRuleId(eIntegerCellEditEvent.getNewValue());


        for(int i=0;i< usersArrayList.size();i++){
            if(usersArrayList.get(i).getUserRuleId()==requiredId){

                usersArrayList.get(i).setUserRuleId(e.getRuleId());
            }
        }


/////////////////////////////////updating the array list and the obser... one
        rules.set(index,e);
        //employees.set(index,e);
        System.out.println("after updating");
        System.out.println(rules.toString());
//////////////////////////////updating database
        connectDB();
        System.out.println("connection initialized");
        String Sql="update Rules" +
                " set ruleId= "+rules.get(index).getRuleId()+ " " +
                "where ruleId= "+requiredId+";";

        Statement stmt = con.createStatement();
        stmt.executeUpdate(Sql);
        stmt.close();
        con.close();
    }

    @FXML
    void ruleTitleOnEdit(TableColumn.CellEditEvent<Rules,String>eStringCellEditEvent) throws SQLException, ClassNotFoundException{
        Rules e=rulesTable.getSelectionModel().getSelectedItem();//taking object
        int index=rulesTable.getSelectionModel().getSelectedIndex();//taking the index of the object
        int requiredId=rules.get(index).getRuleId();//taking the id of the employee

        // System.out.println(requiredId);
        e.setRuleTitle(eStringCellEditEvent.getNewValue());

/////////////////////////////////updating the array list and the obser... one
        rules.set(index,e);
        //employees.set(index,e);
        System.out.println("after updating");
        System.out.println(rules.toString());
//////////////////////////////updating database
        connectDB();
        System.out.println("connection initialized");
        String Sql="update Rules" +
                " set ruleTitle= '"+rules.get(index).getRuleTitle()+ "' " +
                "where ruleId= "+requiredId+";";

        Statement stmt = con.createStatement();
        stmt.executeUpdate(Sql);
        stmt.close();
        con.close();
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