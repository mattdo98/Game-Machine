package com.peanutbutterdawg.gamerental;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.ResourceBundle;

import com.sun.org.apache.bcel.internal.generic.Select;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class LoginViewController implements Initializable {

  // Database stuff: No touch >:-(
  private static final String JDBC_DRIVER = "org.h2.Driver"; // Path to my H2 Driver
  private static final String DB_URL = "jdbc:h2:./res/H2"; // Path to my DataBase URL

  @FXML public AnchorPane getLogin;

  @FXML private AnchorPane EnterLogin;

  @FXML private AnchorPane EnterCreateAccount;

  @FXML private Label SuccessCreatedAccount;

  @FXML private TextFlow userName;

  @FXML private TextFlow userEmail;

  @FXML private TextFlow subEnd;

  @FXML private RadioButton AdminLogin;

  // Hi this is Robbie. These are the fields for LOGIN:
  //////////////////////////////////////////////////////////////

  @FXML private PasswordField password;

  @FXML private TextField username;

  @FXML private Button login;

  // Hi this is Robbie. These are the fields and methods for CREATE ACCOUNT:
  //////////////////////////////////////////////////////////////
  @FXML private TextField firstName;

  @FXML private TextField lastName;

  @FXML private TextField createUsername;

  @FXML private PasswordField createPassword;

  @FXML private RadioButton isAdmin;

  @FXML private TextField adminPassword;

  @FXML private Button createAccount;

  // Hi Robbie here. Every time the createAccount button is pressed, this code is executed. :)
  @FXML
  private void createAccountButton() {
    if (!checkUsernameMultiples(createUsername.getText())) {
      if (isAdmin.isSelected()) {
        String passwordToCreateAdminAccount = "Ravioli"; // <----- Password to create admin account.
        if (adminPassword.getText().equals(passwordToCreateAdminAccount)) {
          insertToDatabase(
              firstName.getText(),
              lastName.getText(),
              createUsername.getText(),
              createPassword.getText(),
              false, // <--- When an account is first created, SUB is always false.
              isAdmin.isSelected());
          System.out.println("Admin account successfully created.");
        } else {
          System.out.println("You entered an incorrect admin password.");
        }
      } else {
        insertToDatabase(
            firstName.getText(),
            lastName.getText(),
            createUsername.getText(),
            createPassword.getText(),
            false, // <--- When an account is first created, SUB is always false.
            isAdmin.isDisabled());
        System.out.println("Account successfully created.");
      }
    } else {
      System.out.println("That username is already taken. Please try again.");
    }
  }

  /////////////////////////////////////////////////////////////

  // initialize method
  @FXML
  public void initialize(URL url, ResourceBundle rb) {

    System.out.println("is this working, guess not.");
    // End of game page table initialize
  }

  // On Mouse Click Show Create Account Items on LoginView
  @FXML
  void ClickCreateAccount(MouseEvent event) {
    EnterLogin.setVisible(false);
    EnterCreateAccount.setVisible(true);
    SuccessCreatedAccount.setVisible(false);
  }

  // On Mouse Click Show Login Items on LoginView
  @FXML
  void ClickLogin(MouseEvent event) {
    EnterLogin.setVisible(true);
    EnterCreateAccount.setVisible(false);
    SuccessCreatedAccount.setVisible(false);
  }

  // On Action for Create Account Button
  @FXML
  void CreateLogin(ActionEvent event) {
    // Set These Menus Visibility
    EnterCreateAccount.setVisible(false);
    EnterLogin.setVisible(false);
    SuccessCreatedAccount.setVisible(true);

    // Clear Username and Password
    createUsername.setText("");
    createPassword.setText("");
  }

  // On Action for Login Button
  @FXML
  void Login(ActionEvent event) throws IOException {
    Parent HomeViewParent = FXMLLoader.load(getClass().getResource("HomeView.fxml"));
    Scene HomeViewScene = new Scene(HomeViewParent);

    // This line gets the Stage information
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

    window.setScene(HomeViewScene);
    window.show();

    // Clear Username and Password
    username.setText("");
    password.setText("");
  }

  // On Action for LOGOUT Button
  @FXML
  void getLogout(ActionEvent event) throws IOException {
    Parent LoginViewParent = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
    Scene LoginViewScene = new Scene(LoginViewParent);

    // This line gets the Stage information
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

    window.setScene(LoginViewScene);
    window.show();
  }

  // On Action for HOME Button
  @FXML
  void getHome(ActionEvent event) throws IOException {
    Parent HomeViewParent = FXMLLoader.load(getClass().getResource("HomeView.fxml"));
    Scene HomeViewScene = new Scene(HomeViewParent);

    // This line gets the Stage information
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

    window.setScene(HomeViewScene);
    window.show();
  }

  // On Action for LIBRARY Button
  @FXML
  void getLibrary(ActionEvent event) throws IOException {
    Parent LibraryViewParent = FXMLLoader.load(getClass().getResource("LibraryView.fxml"));
    Scene LibraryViewScene = new Scene(LibraryViewParent);

    // This line gets the Stage information
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

    window.setScene(LibraryViewScene);
    window.show();
  }

  // On Action for PROFILE Button
  @FXML
  void getProfile(ActionEvent event) throws IOException {
    Parent ProfileViewParent = FXMLLoader.load(getClass().getResource("ProfileView.fxml"));
    Scene ProfileViewScene = new Scene(ProfileViewParent);

    // This line gets the Stage information
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

    window.setScene(ProfileViewScene);
    window.show();
  }

  // On Action for ADMIN Button
  @FXML
  void getAdmin(ActionEvent event) throws IOException {
    Parent AdminViewParent = FXMLLoader.load(getClass().getResource("AdminView.fxml"));
    Scene AdminViewScene = new Scene(AdminViewParent);

    // This line gets the Stage information
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

    window.setScene(AdminViewScene);
    window.show();
  }
  // Matt here, will delete when implemented in the right controller
  /* Shows subscription ending (*not currently functioning correctly)
    private String displaySubEnd() {
      Calendar dateEnd = Calendar.getInstance();
      dateEnd.add(Calendar.MONTH, 1);
      return dateEnd.toString();
    }
  */

  // You can call this method to check is the database contains a username that is taken.
  //  This works do not edit.
  private Boolean checkUsernameMultiples(String personsUser) {
    String checkFromDB = "SELECT USERNAME FROM USER";
    try {
      Class.forName(JDBC_DRIVER); // Database Driver
      Connection conn = DriverManager.getConnection(DB_URL); // Database Url
      PreparedStatement stmt = conn.prepareStatement(checkFromDB);
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        String username = rs.getString("USERNAME");
        if (username.equals(personsUser)) {
          return true;
        }
      }
      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  // You can call this method to save to database
  //  This works do not edit.
  private void insertToDatabase(
      String firstname,
      String lastname,
      String username,
      String password,
      Boolean subscription,
      Boolean isAdmin) {

    String insertToDB =
        "INSERT INTO USER(FIRSTNAME, LASTNAME, USERNAME, PASSWORD, SUBSCRIPTION, ISADMIN) VALUES  "
            + "('"
            + firstname
            + "', '"
            + lastname
            + "', '"
            + username
            + "', '"
            + password
            + "', '"
            + subscription
            + "', '"
            + isAdmin
            + "')";
    try {
      Class.forName(JDBC_DRIVER); // Database Driver
      Connection conn = DriverManager.getConnection(DB_URL); // Database Url
      PreparedStatement stmt = conn.prepareStatement(insertToDB);
      stmt.execute();
      conn.close();
      stmt.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }
}
