package com.example.database2try;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;

import static com.example.database2try.HelloApplication.itemsArrayList;
import static com.example.database2try.HelloApplication.itemListArrayList;
public class MeltNewOrderOrItem {
    private static Connection con;


    @FXML
    private TextField MealNameTF;

    @FXML
    private TextField MealPriceTF;

    @FXML
    private TextField MenuNameTF;

    @FXML
    private Text MenuText;

    @FXML
    private TextField MenuTitle;

    @FXML
    private Text itemmTEXT;
    @FXML
    private TextField newMenuNameTF;
    @FXML
    private TextField newitemName;

    @FXML
    void AddMenuOnAction(MouseEvent event) throws IOException, SQLException, ClassNotFoundException {
        //check if menu already exist:
        int checkMenu=0;
        for(int i=0;i<itemListArrayList.size();i++)
        {
            if(itemListArrayList.get(i).getMenuTitle().equalsIgnoreCase(MenuNameTF.getText())) {
                checkMenu = 1;
                break;
            }
        }

        if(checkMenu==0) {//if menu doesn't exist:
            //updating database
            String SQL = "insert into ItemList(menuTitle) values('" + MenuNameTF.getText() + "');";
            connectDB();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(SQL);
            stmt.close();
            con.close();

            //clear the itemListArrayList
            itemListArrayList.clear();
            //refell it again

            connectDB();
            SQL = "select * from ItemList";
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                itemListArrayList.add(new ItemList(rs.getInt(1), rs.getString(2)));
            }
            rs.close();
            stmt.close();
            con.close();
            MenuText.setVisible(true);
            MenuText.setText("Menu has been added successfully");
        }

        else
        {
            MenuText.setVisible(true);
            MenuText.setText("Menu already Exists");
        }

    }

    @FXML
    void CloseOnAction(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MeltView.fxml")));
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    @FXML
    void addItemOnAction(MouseEvent event) throws SQLException, ClassNotFoundException {
        //we have to make sure that the item doesn't already exist
        String tmpItemTitle=MealNameTF.getText();
        int checkItemExist=0;
        for(int i=0;i<itemsArrayList.size();i++)
        {
            if(tmpItemTitle.equalsIgnoreCase(itemsArrayList.get(i).getItemTitle()))
            {
                checkItemExist=1;
                break;
            }
        }

        int tmpMenuId = 0;
        if(checkItemExist==0)
        {//if item doesn't exist, add it

            //find menuId
            for (int i = 0; i < itemListArrayList.size(); i++) {
                if (itemListArrayList.get(i).getMenuTitle().equalsIgnoreCase(MenuTitle.getText())) {
                    tmpMenuId = itemListArrayList.get(i).getMenuId();

                    break;
                }
            }

            if (tmpMenuId != 0) {

                String SQL = "insert into items(itemTitle,itemPrice,menuId) values('" + MealNameTF.getText() + "'," + Double.parseDouble(MealPriceTF.getText())  +
                        "," + tmpMenuId + ");";
                connectDB();
                Statement stmt = con.createStatement();
                stmt.executeUpdate(SQL);
                stmt.close();
                con.close();

                itemsArrayList.clear();//remove all items before reading from data base, to avoid dupplicating
                //update array list by reading, since i don't know the id of this item
                connectDB();
                SQL = "select * from Items";
                stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(SQL);


                while (rs.next()) {
                    itemsArrayList.add(new Items(rs.getInt(1), rs.getString(2),
                            rs.getDouble(3),rs.getInt(4)));
                }
                rs.close();
                stmt.close();
                con.close();
                itemmTEXT.setVisible(true);
                itemmTEXT.setText("item has been added successfully");
            }//if this condition is true, no menu with such id//add to database
        }


        if(checkItemExist==1)
        {
            itemmTEXT.setVisible(true);
            itemmTEXT.setText("Item already exist");
        }
        else if (tmpMenuId == 0)
        {
            itemmTEXT.setVisible(true);
            itemmTEXT.setText("Menu Not Found");
        }

    }

    @FXML
    void searchItemOnAction(MouseEvent event) {
        String tmpItemName = MealNameTF.getText();
        int tmpMenuId = 0;
        //System.out.println(itemsArrayList);
        for (int i = 0; i < itemsArrayList.size(); i++) {
            if (itemsArrayList.get(i).getItemTitle().equals(tmpItemName)) {
                MealPriceTF.setText(String.valueOf(itemsArrayList.get(i).getItemPrice()));
                tmpMenuId = itemsArrayList.get(i).getMenuId();
                break;
            }
        }

        if (tmpMenuId != 0) {//if the menu has been founded

            //search for the menu
            for (int j = 0; j < itemListArrayList.size(); j++) {
                if (tmpMenuId == itemListArrayList.get(j).getMenuId()) {
                    MenuTitle.setText(itemListArrayList.get(j).getMenuTitle());
                    itemmTEXT.setText(" ");
                    break;
                }
            }
        }
        if(tmpMenuId==0) {
            itemmTEXT.setVisible(true);
            itemmTEXT.setText("Meal Not Founded");
        }

    }



    @FXML
    void updateItemOnAction(MouseEvent event) throws SQLException, ClassNotFoundException {
        //update price
        //MealPriceTF
        //MealNameTF
        //search for items, find it's id to update it in database
        int tmpItemId=0;
        int index=0;
        int check=0;
        System.out.println(MealPriceTF.getText());
        if(!MealPriceTF.getText().isEmpty()) {

            for (int i = 0; i < itemsArrayList.size(); i++) {
                if (MealNameTF.getText().equalsIgnoreCase(itemsArrayList.get(i).getItemTitle())) {
                    tmpItemId = itemsArrayList.get(i).getItemId();
                    index = i;
                    check = 1;
                    break;
                }
            }
            //update the array list
            itemsArrayList.get(index).setItemPrice(Double.parseDouble(MealPriceTF.getText()));

            //update the database
            connectDB();
            System.out.println("connection initialized");
            String Sql = "update Items" +
                    " set itemPrice= " + itemsArrayList.get(index).getItemPrice() + " " +
                    "where itemId= " + tmpItemId + ";";

            Statement stmt = con.createStatement();
            stmt.executeUpdate(Sql);
            stmt.close();
            con.close();


            itemmTEXT.setVisible(true);
            if (check == 1) {
                itemmTEXT.setText("Item has been updated successfully");
            } else {
                itemmTEXT.setText("Item has not been updated");
            }
        }
        ///////////////////////////////////
        //update item name, only if newMenuNameItem!=NULL
        //but first check if the item name has changed or not
        ///update menu name in itemList
        //search for menu in itemListArray
        int check2=0;
        if(!newitemName.getText().isEmpty()) {
            for (int i = 0; i < itemsArrayList.size(); i++) {
                if (MealNameTF.getText().equalsIgnoreCase(itemsArrayList.get(i).getItemTitle())) {
                    itemsArrayList.get(i).setItemTitle(newitemName.getText());
                    index = i;
                    tmpItemId = itemsArrayList.get(i).getItemId();
                    break;
                }
            }
            check2=1;
            //now update database
            connectDB();
            System.out.println("connection initialized");
            String Sql = "update Items" +
                    " set itemTitle= '" + itemsArrayList.get(index).getItemTitle() + "'" +
                    "where itemId= " + tmpItemId + ";";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(Sql);
            stmt.close();
            con.close();
        }
        newitemName.clear();
        MealPriceTF.clear();
        MealNameTF.clear();
        MenuTitle.clear();
        itemmTEXT.setVisible(true);
        if (check2 == 1) {
            itemmTEXT.setText("Item has been updated successfully");
        } else {
            itemmTEXT.setText("Item has not been updated");
        }

    }
    @FXML
    void IWantToupdateItemNameOnAction(MouseEvent event) {
        newitemName.setVisible(true);
    }


    @FXML
    void updateMenuOnAction(MouseEvent event) throws SQLException, ClassNotFoundException {
        int exist=0;
        int tmpMenuId=0;
        for(int i=0;i<itemListArrayList.size();i++)
        {
            if(itemListArrayList.get(i).getMenuTitle().equalsIgnoreCase(MenuNameTF.getText()))
            {
                exist=1;
                itemListArrayList.get(i).setMenuTitle(newMenuNameTF.getText());
                tmpMenuId=itemListArrayList.get(i).getMenuId();
            }
        }

        if(exist==1)
        {
            connectDB();
            String Sql="update itemList" +
                    " set menuTitle='"+newMenuNameTF.getText()+"'"+
                    "where menuId="+tmpMenuId+";";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(Sql);
            stmt.close();
            con.close();
            MenuText.setVisible(true);
            MenuText.setText("Menu has been updated Successfully");
        }

    }
    @FXML
    void iWantToUpdateOnAction(MouseEvent event) {//to make TF Visible
        newMenuNameTF.setVisible(true);
    }


    @FXML
    void deleteOnAction(MouseEvent event) {
        // MenuNameTF
        int tmpMenuId=0;
        int check=0;
        for(int i=0;i<itemListArrayList.size();i++)
        {
            if(MenuNameTF.getText().equalsIgnoreCase(itemListArrayList.get(i).getMenuTitle()))
            {
                itemListArrayList.remove(i);
                check=1;
                break;
            }
        }

        if(check==0)
        {
            MenuText.setVisible(true);
            MenuText.setText("Menu Doesn't Exist");
        }
        else {
            MenuText.setVisible(true);
            MenuText.setText("Menu has been deleted");
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

}