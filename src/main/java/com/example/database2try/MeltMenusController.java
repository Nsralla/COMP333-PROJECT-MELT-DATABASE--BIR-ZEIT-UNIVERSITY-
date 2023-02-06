package com.example.database2try;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;

import static com.example.database2try.HelloApplication.itemListArrayList;
import static com.example.database2try.HelloApplication.itemsArrayList;
import static com.example.database2try.MeltViewController.primaryStage;

public class MeltMenusController implements Initializable {

    @FXML
    private VBox myVBox;

    private static Connection con;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(!myVBox.getChildren().isEmpty())
            myVBox.getChildren().clear();

        myVBox.setAlignment(Pos.TOP_LEFT);

        Button back=new Button("Back");
        Button back2=new Button("Back");
        back2.setPrefHeight(50);
        back2.setPrefWidth(50);

        for(int i=0;i<itemListArrayList.size();i++){

            Button b1=new Button(itemListArrayList.get(i).getMenuTitle());
            b1.setPrefWidth(160);
            b1.setPrefHeight(65);

            myVBox.getChildren().add(b1);
            int finalI = i;

            b1.setOnAction(actionEvent ->
            {
                GridPane gridPane=new GridPane();
                gridPane.setPadding(new Insets(10));
                gridPane.setHgap(10);
                gridPane.setVgap(10);
                ArrayList<Button> buttons=new ArrayList<>();
                for(int j=0;j<itemsArrayList.size();j++)
                {
                    if(itemsArrayList.get(j).getMenuId()==itemListArrayList.get(finalI).getMenuId())
                    {
                        Button b2 = new Button(itemsArrayList.get(j).getItemTitle()
                                +"\n"+itemsArrayList.get(j).getItemPrice()+"₪");
                        b2.setPrefWidth(240);
                        b2.setPrefHeight(80);
                        buttons.add(b2);


                        int finalJ = j;
                        int finalJ1 = j;
                        b2.setOnAction(event ->
                        {//to remove button
                            //to remove from the array list also from (buttons):
                            String itemName=b2.getText();
                            int tmpItemId=0;
                            for(int k=0;k<itemsArrayList.size();k++)
                            {
                                if(itemsArrayList.get(k).getItemTitle().trim().equalsIgnoreCase(itemsArrayList.get(finalJ1).getItemTitle()))
                                {
                                    tmpItemId=itemsArrayList.get(k).getItemId();
                                    System.out.println(itemsArrayList.get(k).getItemTitle());
                                    itemsArrayList.remove(k);
                                    break;
                                }
                            }
                            Pane parent = (Pane) b2.getParent();//remove from scene
                            parent.getChildren().remove(b2);
                            //we have to remove it from database:
                            try {
                                connectDB();
                            } catch (ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            //System.out.println(tmpItemId);
                            String Sql="delete from Items where itemId="+tmpItemId+";";
                            Statement stmt = null;
                            try {
                                stmt = con.createStatement();
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            try {
                                stmt.executeUpdate(Sql);
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

                        });
                    }
                }
                // System.out.println(buttons.toString());
                int row=0;
                int kk=0;
                for(int k=0;k<buttons.size();k++)
                {
                    gridPane.add(buttons.get(k),kk,row);
                    if((k+1)%5==0)
                    {
                        row++;
                        kk=0;
                    }
                    kk++;
                }
                gridPane.add(back2,0,kk);
                buttons.clear();
                Scene scene=new Scene(gridPane,700,330);
                primaryStage.setScene(scene);
                primaryStage.show();
            });

        }
        myVBox.getChildren().add(back);
        back.setOnAction(actionEvent ->{
            Parent root= null;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MeltView.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene=new Scene(root);
            primaryStage.setScene(scene);
        });
        back2.setOnAction(actionEvent ->{
            Parent root= null;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MeltMenus.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene=new Scene(root);
            primaryStage.setScene(scene);
        });
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