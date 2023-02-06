package com.example.database2try;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;

public class MeltFinancialReportMonthlyController implements Initializable {

    public static ArrayList<ItemsQuantity> itemsQuantitiesArrayList;
    private static Connection con;

    public static int totalPricef=0;

    @FXML
    private Button back;

    @FXML
    private PieChart pieChart;

    @FXML
    private Label totalPrice;

    @FXML
    void backOnAction(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MeltView.fxml")));
        Scene scene=new Scene(root);
        Stage primaryStage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        try {
            getItemsQuantity();
            getTotalPrice();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (int i = 0; i < itemsQuantitiesArrayList.size(); i++) {


            pieChartData.add(new PieChart.Data(itemsQuantitiesArrayList.get(i).getItemTitle(),
                    itemsQuantitiesArrayList.get(i).getQuantity()));
        }

        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " \namount: ", data.pieValueProperty()
                        )
                )
        );

        pieChart.getData().addAll(pieChartData);
        totalPrice.setText(String.valueOf(totalPricef)+" ₪");
    }

    private static void getItemsQuantity() throws SQLException, ClassNotFoundException {

        itemsQuantitiesArrayList=new ArrayList<>();

        String SQL = "SELECT items.itemTitle, SUM(orders_items.quantity) as quantity FROM orders_items JOIN orders ON orders_items.orderId = orders.orderId JOIN items ON orders_items.itemId = items.itemId WHERE MONTH(orders.order_date) = MONTH(NOW()) AND YEAR(orders.order_date) = YEAR(NOW()) GROUP BY items.itemTitle;";

       connectDB();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        while ( rs.next() )  {
            itemsQuantitiesArrayList.add(new ItemsQuantity(rs.getString(1),rs.getInt(2)));
        }

        System.out.println(itemsQuantitiesArrayList.toString());
        rs.close();
        stmt.close();
        con.close();
    }

    private static void getTotalPrice() throws SQLException, ClassNotFoundException {

        String SQL = " SELECT SUM(orders.totalPrice) as total_price FROM orders WHERE MONTH(orders.order_date) = MONTH(NOW()) AND YEAR(orders.order_date) = YEAR(NOW());";

       connectDB();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        while ( rs.next() )  {
            totalPricef=rs.getInt(1);
        }

        rs.close();
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
        con = DriverManager.getConnection (dbURL, p);
    }

}