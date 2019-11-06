package com.peanutbutterdawg.gamerental;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.ResourceBundle;

import com.sun.org.apache.bcel.internal.generic.Select;
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
              isAdmin.isSelected(),
              false);
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
            isAdmin.isDisabled(),
            false);
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

  // On Action for Login Button
  @FXML
  void Login(ActionEvent event) throws IOException {
    if(checkLoginInformation(username.getText(), password.getText())){
      Parent HomeViewParent = FXMLLoader.load(getClass().getResource("HomeView.fxml"));
      Scene HomeViewScene = new Scene(HomeViewParent);

      // This line gets the Stage information
      Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

      window.setScene(HomeViewScene);
      window.show();
    }
    else{
      System.out.println("Entered incorrect username or password.");
    }
    // Clear Username and Password
    username.setText("");
    password.setText("");

    System.out.println("Test");
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

  private boolean checkLoginInformation(String enteredUser, String enteredPassword) {
    String checkFromDB = "SELECT USERNAME,PASSWORD FROM USER";

    try {
      Class.forName("org.h2.Driver");
      Connection conn = DriverManager.getConnection("jdbc:h2:./res/H2");
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(checkFromDB);

      while(rs.next()) {
        String username = rs.getString("USERNAME");
        String password = rs.getString("PASSWORD");
        if(username.equals(enteredUser) && password.equals(enteredPassword)){
          stmt.close();
          conn.close();
          return true;
        }
      }


      stmt.close();
      conn.close();
      return false;
    } catch (SQLException | ClassNotFoundException eb) {
      eb.printStackTrace();
    }

    return false;
  }

  // You can call this method to save to database
  private void insertToDatabase(
      String firstname,
      String lastname,
      String username,
      String password,
      Boolean subscription,
      Boolean isAdmin,
      Boolean isActive) {

    // had to add this so the program knows which user we are talking about
    // all users will be set false, until they login

    String insertToDB =
        "INSERT INTO USER(FIRSTNAME, LASTNAME, USERNAME, PASSWORD, SUBSCRIPTION, ISADMIN, ISACTIVEUSER) VALUES  "
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
            + "', '"
            + isActive
            + "')";
    // Here I am initializing my DataBase.
    try {
      Class.forName(JDBC_DRIVER); // Database Driver
      Connection conn = DriverManager.getConnection(DB_URL); // Database Url
      // This prepared statement executes my SQL String Command.
      PreparedStatement stmt = conn.prepareStatement(insertToDB);
      stmt.execute();
      conn.close();
      stmt.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }
}
