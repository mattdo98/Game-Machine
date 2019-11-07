package com.peanutbutterdawg.gamerental;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ProfileViewController implements Initializable {

    private static final String JDBC_DRIVER = "org.h2.Driver"; // path to h2 driver
    private static final String DB_URL = "jdbc:h2:./res/H2"; // Database url

    private ObservableList<Profile> profile;

    @FXML
    private AnchorPane profileView;

    @FXML
    private Label myName;

    @FXML
    private TextField userName;

    @FXML
    private TextField userEmail;

    @FXML
    private TextField subEnd;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeNameLabel();
        displaySubEnd();
        intitalizeProfile();
    }

    @FXML
    void getAdmin(ActionEvent event) throws IOException {
        Parent AdminViewParent = FXMLLoader.load(getClass().getResource("AdminView.fxml"));
        Scene AdminViewScene = new Scene(AdminViewParent);

        // This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(AdminViewScene);
        window.show();
    }

    @FXML
    void getHome(ActionEvent event) throws IOException {
        Parent HomeViewParent = FXMLLoader.load(getClass().getResource("HomeView.fxml"));
        Scene HomeViewScene = new Scene(HomeViewParent);

        // This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(HomeViewScene);
        window.show();
    }

    @FXML
    void getLibrary(ActionEvent event) throws IOException {
        Parent LibraryViewParent = FXMLLoader.load(getClass().getResource("LibraryView.fxml"));
        Scene LibraryViewScene = new Scene(LibraryViewParent);

        // This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(LibraryViewScene);
        window.show();
    }

    @FXML
    void getLogout(ActionEvent event) throws IOException {
        Parent LoginViewParent = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        Scene LoginViewScene = new Scene(LoginViewParent);

        // This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(LoginViewScene);
        window.show();

        String getUserName = "SELECT USERNAME FROM USER WHERE ISACTIVEUSER = TRUE";
        String setActiveToFalse = "UPDATE USER SET ISACTIVEUSER = FALSE WHERE USERNAME = ?";

        try {
            Class.forName(JDBC_DRIVER); // Database Driver
            Connection conn = DriverManager.getConnection(DB_URL); // Database Url

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getUserName);

            PreparedStatement ps = conn.prepareStatement(setActiveToFalse);

            while(rs.next()) {
                String username = rs.getString("USERNAME");

                ps.setString(1, username);
                ps.executeUpdate();
            }

            stmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void getProfile(ActionEvent event) throws IOException {
        Parent ProfileViewParent = FXMLLoader.load(getClass().getResource("ProfileView.fxml"));
        Scene ProfileViewScene = new Scene(ProfileViewParent);

        // This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(ProfileViewScene);
        window.show();
    }

    // Initialize start
    @FXML
    public void initialize() {
        // Text to show sub end date
        displaySubEnd();
        intitalizeProfile();


    }

    //Matt here, made method that shows a month from the current date
    private void displaySubEnd() {
        Date currentDate = new Date();
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(currentDate);
        tempEnd.add(Calendar.MONTH, 1);
        Date subEndDate = tempEnd.getTime();
        subEnd.setText(df.format(subEndDate));
    }

    //Gets the Users Name from database and sets it to the userName TextField
    private void intitalizeProfile() {
        getUserName();


    }

    //Get
    private void getUserName() {
      profile = FXCollections.observableArrayList();
        String selectStmt = "SELECT USERNAME FROM USER WHERE ISACTIVEUSER = true";

        try {
            Class.forName(JDBC_DRIVER); // Database Driver
            Connection conn = DriverManager.getConnection(DB_URL); // Database Url
            // This prepared statement executes my SQL String Command.
            PreparedStatement stmt = conn.prepareStatement(selectStmt);
            ResultSet rs = stmt.executeQuery();



            while (rs.next()) {
              String usernameGot = rs.getString("USERNAME");
                System.out.println("\nUsername: " + usernameGot);
              userName.setText(usernameGot);
            }


            stmt.execute();
            conn.close();
            stmt.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializeNameLabel() {

        try {

            String sql = "SELECT USERNAME FROM USER WHERE ISACTIVEUSER = TRUE";

            Class.forName(JDBC_DRIVER); // Database Driver
            Connection conn = DriverManager.getConnection(DB_URL); // Database Url

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String username = rs.getString("USERNAME");

                myName.setText(username);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
