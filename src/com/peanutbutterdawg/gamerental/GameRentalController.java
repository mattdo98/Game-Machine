package com.peanutbutterdawg.gamerental;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class GameRentalController implements Initializable {
  @FXML
  private AnchorPane EnterLogin;

  @FXML
  private AnchorPane EnterCreateAccount;

  @FXML
  private PasswordField CreatePassword;

  @FXML
  private TextField CreateUsername;

  @FXML
  private PasswordField Password;

  @FXML
  private TextField Username;

  @FXML
  private Label SuccessCreatedAccount;

  @FXML
  private TextFlow userName;

  @FXML
  private TextFlow userEmail;

  @FXML
  private TextFlow subEnd;


  @FXML
  private RadioButton AdminLogin;

  @FXML
  private Label myName;

  @FXML
  private AnchorPane getAdminTab;

  // Assigns the Created Username and Created Password the user entered to a String Variable
  @FXML public String createUsername;
  @FXML public String createPassword;

  // Assigns the Username and Password the user entered to a String Variable
  @FXML public String username;
  @FXML public String password;

  // initialize method
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // Text to show sub end date (*not currently working)
    subEnd = new TextFlow(new Text(displaySubEnd()));

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
    // Grab Text in Username and Password Fields
    createUsername = CreateUsername.getText();
    createPassword = CreatePassword.getText();

    // Print Username and Password in System for Debug
    System.out.println(createUsername + " " + createPassword);

    // Set These Menus Visibility
    EnterCreateAccount.setVisible(false);
    EnterLogin.setVisible(false);
    SuccessCreatedAccount.setVisible(true);

    // Clear Username and Password
    CreateUsername.setText("");
    CreatePassword.setText("");
  }

  // On Action for Login Button
  @FXML
  void Login(ActionEvent event) throws IOException {
    // Grab Text in Username and Password Fields
    username = Username.getText();
    password = Password.getText();

    // Loads HomeView
    Parent HomeViewParent = FXMLLoader.load(getClass().getResource("HomeView.fxml"));
    Scene HomeViewScene = new Scene(HomeViewParent);

    //This line gets the Stage information
    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

    window.setScene(HomeViewScene);
    window.show();

    // Print Username and Password in System for Debug
    System.out.println(username + " " + password);

    // Clear Username and Password
    Username.setText("");
    Password.setText("");
  }

  // On Action for LOGOUT Button
  @FXML
  void getLogout(ActionEvent event) throws IOException   {
    Parent LoginViewParent = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
    Scene LoginViewScene = new Scene(LoginViewParent);

    //This line gets the Stage information
    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

    window.setScene(LoginViewScene);
    window.show();
  }

  // On Action for HOME Button
  @FXML
  void getHome(ActionEvent event) throws IOException  {
    Parent HomeViewParent = FXMLLoader.load(getClass().getResource("HomeView.fxml"));
    Scene HomeViewScene = new Scene(HomeViewParent);

    //This line gets the Stage information
    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

    window.setScene(HomeViewScene);
    window.show();
  }

  // On Action for LIBRARY Button
  @FXML
  void getLibrary(ActionEvent event) throws IOException  {
    Parent LibraryViewParent = FXMLLoader.load(getClass().getResource("LibraryView.fxml"));
    Scene LibraryViewScene = new Scene(LibraryViewParent);

    //This line gets the Stage information
    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

    window.setScene(LibraryViewScene);
    window.show();
  }

  // On Action for PROFILE Button
  @FXML
  void getProfile(ActionEvent event) throws IOException  {
    Parent ProfileViewParent = FXMLLoader.load(getClass().getResource("ProfileView.fxml"));
    Scene ProfileViewScene = new Scene(ProfileViewParent);

    //This line gets the Stage information
    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    window.setScene(ProfileViewScene);
    window.show();

  }

  // On Action for ADMIN Button
  @FXML
  void getAdmin(ActionEvent event) throws IOException  {
    Parent AdminViewParent = FXMLLoader.load(getClass().getResource("AdminView.fxml"));
    Scene AdminViewScene = new Scene(AdminViewParent);

    //This line gets the Stage information
    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

    window.setScene(AdminViewScene);
    window.show();
  }
  //Shows subscription ending (*not currently functioning correctly)
  public String displaySubEnd(){
    Calendar dateEnd = Calendar.getInstance();
    dateEnd.add(Calendar.MONTH,1);
    return dateEnd.toString();
  }





}