package com.peanutbutterdawg.gamerental;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class LibraryViewController implements Initializable {

  private static final String JDBC_DRIVER = "org.h2.Driver"; // Path to my H2 Driver
  private static final String DB_URL = "jdbc:h2:./res/H2"; // Path to my DataBase URL

  @FXML
  private AnchorPane getLibrary;

  @FXML
  private Label myName;

  // initialize method
  @FXML
  public void initialize(URL url, ResourceBundle rb) {

    initializeNameLabel();

    System.out.println("This is Library Tab");
  }

  @FXML
  void getAdmin(ActionEvent event) throws IOException {
    String getUser = "SELECT ISADMIN FROM USER WHERE ISACTIVEUSER = TRUE";

    try {
      Class.forName(JDBC_DRIVER); // Database Driver
      Connection conn = DriverManager.getConnection(DB_URL); // Database Url

      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(getUser);

      while (rs.next()) {
        boolean admin = rs.getBoolean("ISADMIN");

        if (admin == true) {
          System.out.println("User is Admin");

          Parent AdminViewParent = FXMLLoader.load(getClass().getResource("AdminView.fxml"));
          Scene AdminViewScene = new Scene(AdminViewParent);

          // This line gets the Stage information
          Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

          window.setScene(AdminViewScene);
          window.show();

        } else {
          System.out.println("User is Not Admin");
          JFrame parent = new JFrame();
          JOptionPane.showMessageDialog(parent, "User is Not Admin");
        }
      }

      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
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
