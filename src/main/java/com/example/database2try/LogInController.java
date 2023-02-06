package com.example.database2try;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import static com.example.database2try.HelloApplication.logInArrayList;
import static com.example.database2try.UsersController.users;


public class LogInController implements Initializable {
    public static ObservableList<LogIn> logIns= FXCollections.observableArrayList(logInArrayList);

    @FXML
    private TextField userNameT;

    @FXML
    private TableColumn<LogIn, Date> loginDate;

    @FXML
    private TableColumn<LogIn, Integer> loginId;

    @FXML
    private TableView<LogIn> loginTable;

    @FXML
    private TableColumn<LogIn, Integer> userId;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        loginId.setCellValueFactory(new PropertyValueFactory<LogIn,Integer>("loginId"));
        loginDate.setCellValueFactory(new PropertyValueFactory<LogIn,Date>("loginDate"));
        userId.setCellValueFactory(new PropertyValueFactory<LogIn,Integer>("userId"));
        loginTable.setItems(logIns);

    }
    @FXML
    void whenRowIsSelected(MouseEvent event) {
        int index=loginTable.getSelectionModel().getSelectedIndex();//taking the index of the object
        int tmpUserId=logIns.get(index).getUserId();//taking the user id for the selected item

        for(int i=0;i<users.size();i++){//search for the user id of the selected row
            if(users.get(i).getUserId()==tmpUserId) {
                userNameT.setText(users.get(i).getUserName());
                break;
            }
        }


    }

}