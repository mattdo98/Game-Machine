package com.peanutbutterdawg.gamerental;

import com.sun.org.apache.xpath.internal.operations.Bool;

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
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.*;

public class ProfileViewController implements Initializable {

  private static final String JDBC_DRIVER = "org.h2.Driver"; // path to h2 driver
  private static final String DB_URL = "jdbc:h2:./res/H2"; // Database url

  private ObservableList<Profile> profile;

  @FXML private AnchorPane profileView;

  @FXML private Label myName;

  @FXML private Label subRenew;

  @FXML private TextField userName;

  @FXML private TextField fullName;

  @FXML private TextField subEnd;

  @FXML private Pane subPane;

  @FXML private TextField cCardInfo1;

  @FXML private TextField cCardInfo2;

  @FXML private TextField cCardInfo3;

  @FXML private Button buySubButton;

  @FXML private Button subCancel;

  @FXML private Label subLabel;
  @FXML private Hyperlink helpLink;
  @FXML private TextField userPassWord;
  @FXML private Button showPassBtn;
  @FXML private Button hidePassBtn;
  @FXML private Label ccardError;

  @FXML private Button admin;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    initializeNameLabel();
    intitalizeProfile();
    initializeSubSelect();
    checkForAdmin();
  }

  // Get Admin Tab
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

      while (rs.next()) {
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

  // Initializes Profile Tab
  private void intitalizeProfile() {
    getUserName();
    getFullName();
    setUserPassWord();
    initializeSubTabs();
    hidePassBtn.setVisible(false);
  }

  private void initializeSubTabs() {
    buySubButton.setVisible(false);
    subPane.setVisible(false);
    cCardInfo1.setVisible(false);
    cCardInfo2.setVisible(false);
    cCardInfo3.setVisible(false);
    subLabel.setVisible(false);
    ccardError.setVisible(false);
  }

  // made method that shows a month from the current date
  private void displaySubEnd() {

    Date currentDate = new Date();
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    Calendar tempEnd = Calendar.getInstance();
    tempEnd.setTime(currentDate);
    tempEnd.add(Calendar.MONTH, 1);
    Date subEndDate = tempEnd.getTime();
    subEnd.setText(df.format(subEndDate));
  }

  // gets the full name
  private void getFullName() {

    profile = FXCollections.observableArrayList();
    String selectStmt1 = "SELECT FIRSTNAME, LASTNAME FROM USER WHERE ISACTIVEUSER = true";
    try {
      Class.forName(JDBC_DRIVER); // Database Driver
      Connection conn = DriverManager.getConnection(DB_URL); // Database Url
      // This prepared statement executes my SQL String Command.
      PreparedStatement stmt = conn.prepareStatement(selectStmt1);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        String firstNameGot = rs.getString("FIRSTNAME");
        String lastNameGot = rs.getString("LASTNAME");
        // Test to console
        System.out.println("\nName: " + firstNameGot + " " + lastNameGot);
        fullName.setText(firstNameGot + " " + lastNameGot);
      }

      stmt.execute();
      conn.close();
      stmt.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  // Get username from Database
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

  private void initializeSubSelect() {

    try {

      String sql = "SELECT SUBSCRIPTION FROM USER WHERE ISACTIVEUSER = true";
      Class.forName(JDBC_DRIVER);
      Connection conn = DriverManager.getConnection(DB_URL);

      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {
        boolean currentSub;

        if (rs.getBoolean("SUBSCRIPTION")) {
          // If current user has a subscription
          currentSub = true;
          subCancel.setVisible(true);
          displaySubEnd();
          System.out.println("Subscription verified");

        }
        // If current user does not have a subscription
        else {
          currentSub = false;
          System.out.println("No active Subscription. Please Press the button below to buy one");
          subLabel.setVisible(true);
          subLabel.setText("No active Subscription. Please Press the button below to buy one");
          buySubButton.setVisible(true);
          subCancel.setVisible(false);
        }
      }
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
      System.out.println("Load failed");
    }
  }

  @FXML
  void purchaseSub(ActionEvent event) {
    // Just sets visibility of Purchase tab
    subPane.setVisible(true);
    cCardInfo1.setVisible(true);
    cCardInfo2.setVisible(true);
    cCardInfo3.setVisible(true);
    buySubButton.setVisible(false);
    subLabel.setVisible(false);
  }
  // Sets the subscription
  @FXML
  void setSub(ActionEvent event) {
    try {
      String ccard1 = cCardInfo1.getText();
      String ccard2 = cCardInfo2.getText();
      String ccard3 = cCardInfo3.getText();

      if (ccard1.isEmpty() | ccard2.isEmpty() | ccard3.isEmpty()) {
        System.out.println("Please enter card information");
        ccardError.setVisible(true);
        return;
      }

      String sql = "UPDATE USER SET SUBSCRIPTION = TRUE WHERE ISACTIVEUSER = true ";
      Class.forName(JDBC_DRIVER);

      Connection conn = DriverManager.getConnection(DB_URL);
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.executeUpdate();

      System.out.println("Subcription Set");
      displaySubEnd();
      subCancel.setVisible(true);
      ;
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    initializeSubTabs();
    cCardInfo1.clear();
    cCardInfo2.clear();
    cCardInfo3.clear();
    ccardError.setVisible(false);
  }

  // On action to cancel sub
  @FXML
  void cancelSub(ActionEvent event) {
    try {
      String sql = "UPDATE USER SET SUBSCRIPTION = FALSE WHERE ISACTIVEUSER = true ";
      Class.forName(JDBC_DRIVER);

      Connection conn = DriverManager.getConnection(DB_URL);
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.executeUpdate();
      buySubButton.setVisible(true);
      subCancel.setVisible(false);

      System.out.println("Sub Canceled");

      subEnd.setText("");
      subLabel.setVisible(true);

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }
  // help popup
  @FXML
  void setHelpLink() {

    JFrame parent = new JFrame();
    JOptionPane.showMessageDialog(
        parent,
        "Having trouble with our program? Have suggestions on how to improve it?\n"
            + " Feel free to email us at support@pbdawg.com\n ");
  }
  // Gets user password and sets it to the textfield
  @FXML
  private void setUserPassWord() {
    try {
      String sql = "SELECT PASSWORD FROM USER WHERE ISACTIVEUSER = TRUE";

      Class.forName(JDBC_DRIVER); // Database Driver
      Connection conn = DriverManager.getConnection(DB_URL); // Database Url

      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {
        String password = rs.getString("PASSWORD");

        userPassWord.setText(password);
        userPassWord.setVisible(false);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  // Shows password information
  @FXML
  void showPassword() {
    userPassWord.setVisible(true);
    showPassBtn.setVisible(false);
    hidePassBtn.setVisible(true);
  }
  // Hides password information
  @FXML
  void hidePassword() {
    userPassWord.setVisible(false);
    hidePassBtn.setVisible(false);
    showPassBtn.setVisible(true);
  }

  @FXML
  private void checkForAdmin() {
    String getUser = "SELECT ISADMIN FROM USER WHERE ISACTIVEUSER";

    try {
      Class.forName(JDBC_DRIVER); // Database Driver
      Connection conn = DriverManager.getConnection(DB_URL); // Database Url

      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(getUser);

      while (rs.next()) {
        boolean adminbool = rs.getBoolean("ISADMIN");

        if (adminbool) {
          admin.setVisible(true);
        } else {
          admin.setVisible(false);
        }
      }
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }
}
