package com.peanutbutterdawg.gamerental;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class ProfileViewController {

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
  private void intitalizeProfile(){



  }

//Easy access to database
private void connectToDB(String selectStmt){
    try {

        Class.forName(JDBC_DRIVER); // Database Driver
        Connection conn = DriverManager.getConnection(DB_URL); // Database Url
        // This prepared statement executes my SQL String Command.
        PreparedStatement stmt = conn.prepareStatement(selectStmt);
        stmt.execute();
        conn.close();
        stmt.close();
    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
    }
}





}
