package com.example.database2try;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PipedReader;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;

import static com.example.database2try.HelloApplication.orderArrayList;

import static com.example.database2try.HelloApplication.usersArrayList;
import static com.example.database2try.MeltViewController.primaryStage;
import static com.example.database2try.OrderController.orders;


public class DeleteOrderController implements Initializable {

    @FXML
    private TextField userNameTF;

    @FXML
    private TableColumn<Order, String> address;

    @FXML
    private TableColumn<Order, Integer> orderId;

    @FXML
    private TableColumn<Order,String> orderType;

    @FXML
    private TableColumn<Order,String> paymentMethod;

    @FXML
    private TableColumn<Order, Integer> phoneNumber;

    @FXML
    private TextField phoneNumberTF;

    @FXML
    private TableColumn<Order,Double> totalPrice;

    @FXML
    private TableColumn<Order,Integer> userId;


    @FXML
    private TableView<Order> orderTable;
    private static Connection con;

    @FXML
    private HBox hbox;

    @FXML
    private TableColumn<Order,Date> order_date;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //
        orderId.setCellValueFactory(new PropertyValueFactory<Order,Integer>("orderId"));
        orderType.setCellValueFactory(new PropertyValueFactory<Order,String>("OrderType"));
        totalPrice.setCellValueFactory(new PropertyValueFactory<Order,Double>("totalPrice"));
        paymentMethod.setCellValueFactory(new PropertyValueFactory<Order, String>("paymentMethod"));
        address.setCellValueFactory(new PropertyValueFactory<Order,String>("address"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<Order,Integer>("phoneNumber"));
        order_date.setCellValueFactory(new PropertyValueFactory<Order,Date>("order_date"));
        userId.setCellValueFactory(new PropertyValueFactory<Order,Integer>("userId"));
        orderTable.setEditable(true);

        try {
            getOrders();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        orders= FXCollections.observableArrayList(orderArrayList);
        // System.out.println(orders);
        orderTable.setItems(orders);

        userId.setOnEditStart(EventHandler->{
            String tmpName="";
            System.out.println("iam unnnnnnnnnnnnnnnn");
            Order e=orderTable.getSelectionModel().getSelectedItem();//taking object
            int index=orderTable.getSelectionModel().getSelectedIndex();//taking the index of the object
            int requiredId=orders.get(index).getUserId();//taking the id of the employee
            for(int i=0;i<usersArrayList.size();i++)
            {

                if(usersArrayList.get(i).getUserId()==requiredId)
                {
                    tmpName=usersArrayList.get(i).getUserName();
                }
            }
            userNameTF.setText(tmpName);
        });
    }
    @FXML
    void userIdOnEDIT(ActionEvent event) {

    }

    @FXML
    void CloseOnAction(MouseEvent event) throws IOException {
        primaryStage.hide();
        Parent root=FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MeltView.fxml")));
        Scene scene=new Scene(root);
        primaryStage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    void DeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        int index=orderTable.getSelectionModel().getSelectedIndex();
        int requiredId=orders.get(index).getOrderId();
        orderTable.getItems().remove(index);
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
    void searchOnAction(MouseEvent event) {
        ObservableList<Order>tmp=orders;
        for(int i=0;i<orders.size();i++){
            if(orders.get(i).getPhoneNumber()==Integer.parseInt(phoneNumberTF.getText())){
                orderTable.getItems().add(0,orders.get(i));
                orderTable.getItems().remove(i+1);
            }
        }
        orders=tmp;


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
    private static void getOrders() throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub

        String SQL;
        orderArrayList=new ArrayList<>();

        connectDB();

        SQL = "select * from Orders";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);


        while ( rs.next() )  {
            orderArrayList.add(new Order(rs.getInt(1),rs.getString(2),
                    rs.getDouble(3),
                    rs.getString(4),rs.getString(5),rs.getInt(6)
                    ,rs.getDate(7),rs.getInt(8)));
            System.out.println(rs.getDate(7));
        }
        System.out.println(orderArrayList);
        rs.close();
        stmt.close();
        con.close();
    }


}