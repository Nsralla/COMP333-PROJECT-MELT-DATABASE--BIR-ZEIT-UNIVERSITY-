package com.example.database2try;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;
public class HelloApplication extends Application {
    public static int currentUserId=0;
    public static String currentUserName="";


    @FXML
    private Text loginT;
    @FXML
    private Text menuT;
    @FXML
    private Text ordersT;

    @FXML
    private Text rulesT;

    @FXML
    private Text usersT;
    LogIn tm;

    @FXML
    private TextField ruleSignT;

    @FXML
    private TextField nameSignT;

    @FXML
    private TextField passwordSignT;

    @FXML
    private Text ruleCheck;
    @FXML
    private Text passwordCheck;
    @FXML
    public static ArrayList<Employees> employees;
    public static ArrayList<ItemList> itemListArrayList;

    public static ArrayList<Items> itemsArrayList;
    public static ArrayList<Rules> rulesArrayList;
    public static ArrayList<Users> usersArrayList;
    public static ArrayList<LogIn> logInArrayList;
    public static ArrayList<Order> orderArrayList;
    private static Connection con;
    // private static int loginIdAutoIncreament=0;
    @FXML
    private Text loginDetection;

    @FXML
    private Hyperlink createNewAccount;

    @FXML
    private Button login;

    @FXML
    private TextField password;

    @FXML
    private TextField userName;

    @Override
    public void start(Stage primaryStage) throws IOException {


        Parent root=FXMLLoader.load(Objects.requireNonNull(getClass().getResource("NewLogInInterface.fxml")));
        Scene scene=new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();

    }
    @FXML
    void loginOnAction(ActionEvent event) throws ClassNotFoundException, IOException, SQLException {
        loginDetection.setDisable(true);
        loginDetection.setVisible(false);
        String tmpName=userName.getText();
        String tmpPassword=password.getText();
        //tmpUserId= Integer.parseInt(userIdT.getText());
        int tmpUserId=0;


        int flag=0;

        //searching at database
        for(int i=0;i<usersArrayList.size();i++){
            if(usersArrayList.get(i).getUserName().equals(tmpName)&&usersArrayList.get(i).getPassword().equals(tmpPassword)){
                flag=1;
               /* if(logInArrayList.size()!=0) {
                    loginIdAutoIncreament = logInArrayList.size();
                }*/
                currentUserId=usersArrayList.get(i).getUserId() ;
                currentUserName=usersArrayList.get(i).getUserName();
                // loginIdAutoIncreament++;



                Parent root=FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MeltView.fxml")));
                Scene scene=new Scene(root);
                Stage primaryStage=(Stage) ((Node) event.getSource()).getScene().getWindow();
                primaryStage.setScene(scene);
                primaryStage.setMaximized(true);
                primaryStage.show();


                for(int k=0;k<usersArrayList.size();k++)//get userId
                {
                    if(tmpName.equals(usersArrayList.get(k).getUserName()))
                        tmpUserId=usersArrayList.get(k).getUserId();
                }


                //sending log in data to database
                tm=new LogIn(new Date(),tmpUserId);//here we need use id
                logInArrayList.add(tm);
                //  System.out.println(logInArrayList.toString());


                String s = String.valueOf((tm.getLoginDate().getYear()+230000)+'-' + tm.getLoginDate().getMonth()+'-' + tm.getLoginDate().getDay()-16);
               int a=new Date().getYear()+Integer.parseInt(String.valueOf(230085));
               int b=new Date().getDay();
               int c=new Date().getMonth();
                String SQL="insert into Login(loginDate,userId) values("+a+'-'+c+'-'+b+","+tm.getUserId()+");";


                connectDB();
                PreparedStatement preparedStatement=con.prepareStatement(SQL);
                preparedStatement.execute();
                preparedStatement.close();
                con.close();
                logInArrayList.clear();
                getLogin();
                break;

            }
        }
        if(flag==0){
            loginDetection.setVisible(true);
            loginDetection.setText("user name or password is wrong");
        }

    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        connectDB();
        getData();
        getItemList();
        getItems();
        getRules();
        getUsers();
        getLogin();
        getOrders();
        launch(args);

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
    private static void getData() throws SQLException, ClassNotFoundException {//get d


        String SQL;
        employees = new ArrayList<>();

        connectDB();
        SQL = "select * from Employees";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);


        while ( rs.next() )  {
            //SimpleRecord rd = ;
            employees.add(new Employees(rs.getInt(1),rs.getString(2),
                    rs.getString(3),rs.getDouble(4), rs.getInt(5),rs.getInt(6)));
        }
        rs.close();
        stmt.close();
        con.close();
    }

    private static void getItemList() throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub

        String SQL;
        itemListArrayList=new ArrayList<>();

        connectDB();
        SQL = "select * from ItemList";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        while ( rs.next() )  {
            itemListArrayList.add(new ItemList(rs.getInt(1),rs.getString(2)));
        }
        rs.close();
        stmt.close();
        con.close();

    }

    private static void getItems() throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub

        String SQL;
        itemsArrayList=new ArrayList<>();

        connectDB();
        SQL = "select * from Items";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);


        while ( rs.next() )  {
            itemsArrayList.add(new Items(rs.getInt(1),rs.getString(2),
                    rs.getDouble(3),rs.getInt(4)));
        }
        rs.close();
        stmt.close();

        con.close();
        // System.out.println("Connection closed" + employees.size());

    }



    private static void getRules() throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub

        String SQL;
        rulesArrayList=new ArrayList<>();

        connectDB();


        SQL = "select * from Rules";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);


        while ( rs.next() )  {
            rulesArrayList.add(new Rules(rs.getInt(1),rs.getString(2)));
        }
        // System.out.println(rulesArrayList.toString());
        rs.close();
        stmt.close();

        con.close();


    }

    @FXML
    void createAccount(MouseEvent event) throws Exception{
        Parent root=FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SignInFile.fxml")));
        Scene scene=new Scene(root);
        Stage primaryStage=new Stage();
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    @FXML
    void createNewAccountOnAction(ActionEvent event) throws IOException {
        Parent root=FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SignInFile.fxml")));
        Scene scene=new Scene(root);
        Stage primaryStage=new Stage();
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    private static void getUsers() throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub

        String SQL;
        usersArrayList=new ArrayList<>();

        connectDB();

        SQL = "select * from Users";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);


        while ( rs.next() )  {
            usersArrayList.add(new Users(rs.getInt(1),rs.getString(2),
                    rs.getString(3),rs.getInt(4)));
        }
        rs.close();
        stmt.close();
        con.close();
    }

    @FXML
    void sign_createAccount(ActionEvent event) throws SQLException, ClassNotFoundException {
        Users tmp=new Users(nameSignT.getText(),passwordSignT.getText(),Integer.parseInt(ruleSignT.getText()));
        ruleCheck.setDisable(true);

        //check the password:
        int check=0;

        for(int i=0;i<usersArrayList.size();i++){
            if((tmp.getPassword().equals(usersArrayList.get(i).getPassword()))){
                check=1;
                break;

            }
        }

        //make sure that rule id exist:
        int flag=0;
        for(int i=0;i<rulesArrayList.size();i++){
            if(tmp.getUserRuleId()==rulesArrayList.get(i).getRuleId()){
                flag=1;
                if(check==0){
                    //   check=1;
                    usersArrayList.add(tmp);//you added with out id, so re read from data base
                    //you have to add the user to the database

                    //updating database
                    String SQL = "insert into Users(userName,userPassword,userRuleId) values('" + nameSignT.getText() + "','" + passwordSignT.getText() + "',"
                            + Integer.parseInt(ruleSignT.getText()) + ");";

                    connectDB();
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(SQL);
                    stmt.close();
                    con.close();
                    nameSignT.clear();
                    passwordSignT.clear();
                    ruleSignT.clear();
                    usersArrayList.clear();
                    getUsers();
                    break;
                }
            }
        }


        if(flag==0) {
            ruleCheck.setVisible(true);
            ruleCheck.setText("Rule with this id doesn't exist");
            ruleSignT.clear();
        }

        if(check==1) {
            passwordCheck.setVisible(true);
            passwordCheck.setText("choose another password");
        }

    }
    private static void getLogin() throws SQLException, ClassNotFoundException {
        // TODO Auto-generated method stub

        String SQL;
        logInArrayList=new ArrayList<>();

        connectDB();

        SQL = "select * from Login";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);


        while ( rs.next() )  {
            logInArrayList.add(new LogIn(rs.getInt(1),rs.getDate(2),
                    rs.getInt(3)));
        }
        rs.close();
        stmt.close();

        con.close();

    }//read login data from database









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
                    rs.getDouble(3),rs.getString(4),rs.getString(5),
                    rs.getInt(6),rs.getDate(7),rs.getInt(8)));
        }
        rs.close();
        stmt.close();
        con.close();
    }//read login data from database


}