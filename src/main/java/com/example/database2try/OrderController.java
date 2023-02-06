package com.example.database2try;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;
import static com.example.database2try.HelloApplication.orderArrayList;

public class OrderController implements Initializable {
    public static ObservableList<Order> orders= FXCollections.observableArrayList(orderArrayList);
    private static Connection con;
    @FXML
    private TextField addressT;


    @FXML
    private TextField orderIdT;
    @FXML
    private TextField phoneT;
    @FXML
    private TextField totalPriceT;
    @FXML
    private TextField userIdT;
    @FXML
    private ChoiceBox<String> choiceBox1;
    @FXML
    private ChoiceBox<String> choiceBox2;
    @FXML
    private TableColumn<Order,String> address;
    @FXML
    private TableColumn<Order,Integer > orderId;

    @FXML
    private TableColumn<Order,String> orderType;
    @FXML
    private TableView<Order> ordersTable;

    @FXML
    private TableColumn<Order,String> paymentMethod;


    @FXML
    private TableColumn<Order,Integer> phoneNumber;

    @FXML
    private TableColumn<Order, Double> totalPrice;

    @FXML
    private TableColumn<Order,Integer> userId;





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //System.out.println(employees.toString());
        orderId.setCellValueFactory(new PropertyValueFactory<Order,Integer>("orderId"));
        orderType.setCellValueFactory(new PropertyValueFactory<Order,String>("OrderType"));
        totalPrice.setCellValueFactory(new PropertyValueFactory<Order,Double>("totalPrice"));
        paymentMethod.setCellValueFactory(new PropertyValueFactory<Order, String>("paymentMethod"));
        address.setCellValueFactory(new PropertyValueFactory<Order,String>("address"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<Order,Integer>("phoneNumber"));
        userId.setCellValueFactory(new PropertyValueFactory<Order,Integer>("userId"));

        ordersTable.setItems(orders);

        orderId.setCellFactory(TextFieldTableCell.<Order,Integer>forTableColumn(new IntegerStringConverter()));
        orderType.setCellFactory(TextFieldTableCell.forTableColumn());// so you can double-click the column and choose it
        totalPrice.setCellFactory(TextFieldTableCell.<Order,Double>forTableColumn(new DoubleStringConverter()));
        paymentMethod.setCellFactory(TextFieldTableCell.forTableColumn());
        address.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneNumber.setCellFactory(TextFieldTableCell.<Order,Integer>forTableColumn(new IntegerStringConverter()));
        userId.setCellFactory(TextFieldTableCell.<Order,Integer>forTableColumn(new IntegerStringConverter()));

        ordersTable.setEditable(true);

        choiceBox1.getItems().add("In");
        choiceBox1.getItems().add("Out");

        choiceBox2.getItems().add("cash");
        choiceBox2.getItems().add("visa");

        System.out.println("first "+orders.toString());

    }


    @FXML
    void userIdOnEdit(TableColumn.CellEditEvent<Order,Integer>eIntegerCellEditEvent) throws SQLException, ClassNotFoundException{

        Order e=ordersTable.getSelectionModel().getSelectedItem();
        int index=ordersTable.getSelectionModel().getSelectedIndex();//taking the index of the object
        int requiredId=orders.get(index).getOrderId();//taking the id of the employee

        e.setUserId(eIntegerCellEditEvent.getNewValue());
/////////////////////////////////updating the array list and the obser... one
        orders.set(index,e);
//////////////////////////////updating database
        connectDB();
        System.out.println("connection initialized");
        String Sql="update Orders" +
                " set userId = "+orders.get(index).getUserId()+ " " +
                "where orderId = "+requiredId+";";
        Statement stmt = con.createStatement();
        stmt.executeUpdate(Sql);
        stmt.close();
        con.close();

    }


    @FXML
    void addressOnEdit(TableColumn.CellEditEvent<Order,String>eStringCellEditEvent) throws SQLException, ClassNotFoundException{
        Order e=ordersTable.getSelectionModel().getSelectedItem();
        int index=ordersTable.getSelectionModel().getSelectedIndex();//taking the index of the object
        int requiredId=orders.get(index).getOrderId();//taking the id of the employee

        e.setAddress(eStringCellEditEvent.getNewValue());

/////////////////////////////////updating the array list and the obser... one
        orders.set(index,e);
//////////////////////////////updating database
        connectDB();
        System.out.println("connection initialized");
        String Sql="update Orders" +
                " set address= '"+orders.get(index).getAddress()+ "' " +
                "where orderId = "+requiredId+";";
        Statement stmt = con.createStatement();
        stmt.executeUpdate(Sql);
        stmt.close();
        con.close();

    }

    @FXML
    void orderIdOnEdit(TableColumn.CellEditEvent<Order,Integer>eIntegerCellEditEvent)  throws SQLException, ClassNotFoundException{
        Order e=ordersTable.getSelectionModel().getSelectedItem();
        int index=ordersTable.getSelectionModel().getSelectedIndex();//taking the index of the object
        int requiredId=orders.get(index).getOrderId();//taking the id of the employee

        e.setOrderId(eIntegerCellEditEvent.getNewValue());
/////////////////////////////////updating the array list and the obser... one
        orders.set(index,e);
//////////////////////////////updating database
        connectDB();
        System.out.println("connection initialized");
        String Sql="update Orders" +
                " set orderId= "+orders.get(index).getOrderId()+ " " +
                "where orderId = "+requiredId+";";
        Statement stmt = con.createStatement();
        stmt.executeUpdate(Sql);
        stmt.close();
        con.close();

    }

    @FXML
    void orderTypeOnEdit(TableColumn.CellEditEvent<Order,String>eStringCellEditEvent) throws SQLException, ClassNotFoundException {
        Order e=ordersTable.getSelectionModel().getSelectedItem();
        int index=ordersTable.getSelectionModel().getSelectedIndex();//taking the index of the object
        int requiredId=orders.get(index).getOrderId();//taking the id of the employee


        e.setOrderType(eStringCellEditEvent.getNewValue());
/////////////////////////////////updating the array list and the obser... one
        orders.set(index,e);
//////////////////////////////updating database
        connectDB();
        System.out.println("connection initialized");
        String Sql="update Orders" +
                " set orderType= '"+orders.get(index).getOrderType()+ "' " +
                "where orderId = "+requiredId+";";
        Statement stmt = con.createStatement();
        stmt.executeUpdate(Sql);
        stmt.close();
        con.close();
    }

    @FXML
    void paymentMethodOnEdit(TableColumn.CellEditEvent<Order,String>eStringCellEditEvent) throws SQLException, ClassNotFoundException {
        Order e=ordersTable.getSelectionModel().getSelectedItem();
        int index=ordersTable.getSelectionModel().getSelectedIndex();//taking the index of the object
        int requiredId=orders.get(index).getOrderId();//taking the id of the employee

        e.setPaymentMethod(eStringCellEditEvent.getNewValue());
/////////////////////////////////updating the array list and the obser... one
        orders.set(index,e);

//////////////////////////////updating database
        connectDB();
        System.out.println("connection initialized");
        String Sql="update Orders" +
                " set paymentMethod= '"+orders.get(index).getPaymentMethod()+ "' " +
                "where orderId = "+requiredId+";";
        Statement stmt = con.createStatement();
        stmt.executeUpdate(Sql);
        stmt.close();
        con.close();
    }

    @FXML
    void phoneNumberOnEdit(TableColumn.CellEditEvent<Order,Integer>eIntegerCellEditEvent) throws SQLException, ClassNotFoundException {
        Order e=ordersTable.getSelectionModel().getSelectedItem();
        int index=ordersTable.getSelectionModel().getSelectedIndex();//taking the index of the object
        int requiredId=orders.get(index).getOrderId();//taking the id of the employee

        // System.out.println(requiredId);
        e.setPhoneNumber(eIntegerCellEditEvent.getNewValue());
/////////////////////////////////updating the array list and the obser... one
        orders.set(index,e);
//////////////////////////////updating database
        connectDB();
        System.out.println("connection initialized");
        String Sql="update Orders" +
                " set phoneNumber= "+orders.get(index).getPhoneNumber()+ " " +
                "where orderId = "+requiredId+";";
        Statement stmt = con.createStatement();
        stmt.executeUpdate(Sql);
        stmt.close();
        con.close();
    }


    @FXML
    void totalOnEdit(TableColumn.CellEditEvent<Order,Double>eDoubleCellEditEvent) throws SQLException, ClassNotFoundException{
        Order e=ordersTable.getSelectionModel().getSelectedItem();
        int index=ordersTable.getSelectionModel().getSelectedIndex();//taking the index of the object
        int requiredId=orders.get(index).getOrderId();//taking the id of the employee

        e.setTotalPrice(eDoubleCellEditEvent.getNewValue());

/////////////////////////////////updating the array list and the obser... one
        orders.set(index,e);
//////////////////////////////updating database
        connectDB();
        System.out.println("connection initialized");
        String Sql="update Orders" +
                " set totalPrice= "+orders.get(index).getTotalPrice()+ " " +
                "where orderId = "+requiredId+";";
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

    @FXML
    void whenDeleteClicked(MouseEvent event) throws SQLException, ClassNotFoundException {
        int index=ordersTable.getSelectionModel().getSelectedIndex();
        int requiredId=orders.get(index).getOrderId();
        ordersTable.getItems().remove(index);

        //deleting from data base
        connectDB();
        System.out.println("connection initialized");
        String Sql="delete from Orders where orderId="+requiredId+";";
        Statement stmt = con.createStatement();
        stmt.executeUpdate(Sql);
        stmt.close();

        con.close();
        System.out.println("connection closed");
    }

    @FXML
    void addOnClick(MouseEvent event) throws SQLException, ClassNotFoundException {
        java.util.Date javaDate = new java.util.Date();
        java.sql.Date mySQLDate = new java.sql.Date(javaDate.getTime());
        orders.add(new Order(Integer.parseInt(orderIdT.getText()),choiceBox1.getValue(),Double.parseDouble(totalPriceT.getText()),
                (choiceBox2.getValue()),addressT.getText(),
                Integer.parseInt(phoneT.getText()),mySQLDate,Integer.parseInt(userIdT.getText())));


        //updating database
        String SQL="insert into Orders values(" +Integer.parseInt(orderIdT.getText())+",'"+choiceBox1.getValue()+"',"+totalPriceT.getText()+",'"
                +choiceBox2.getValue()+"','"+addressT.getText()+"',"
                +Integer.parseInt(phoneT.getText())+","+
                ","+
                Integer.parseInt(userIdT.getText())+");";

        connectDB();
        System.out.println("Connection established");
        Statement stmt = con.createStatement();
        stmt.executeUpdate(SQL);
        stmt.close();
        con.close();
        System.out.println("Connection closed");


        orderIdT.clear();
        phoneT.clear();
        userIdT.clear();
        addressT.clear();
        totalPriceT.clear();

    }


}