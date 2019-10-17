package com.peanutbutterdawg.gamerental;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class GameRentalController {
  // FX ID for Task Bar AnchorPane
  @FXML
  private AnchorPane getTaskBar;

  @FXML
  private Label myName;

  @FXML
  private AnchorPane getAdminTab;

  @FXML
  private AnchorPane getHome;

  @FXML
  private ImageView imageView1;

  @FXML
  private ImageView imageView2;

  @FXML
  private ImageView imageView3;

  @FXML
  private TextField searchBar;

  @FXML
  private ChoiceBox<?> filterChoiceBox;

  @FXML
  private TableView<?> gamesTableView;

  @FXML
  private AnchorPane getLibrary;

  @FXML
  private AnchorPane getProfile;

  @FXML
  private AnchorPane getAdmin;

  @FXML
  private AnchorPane getLogin;

  @FXML
  private AnchorPane EnterLogin;

  @FXML
  private PasswordField getPassword;

  @FXML
  private TextField getUsername;

  @FXML
  private RadioButton AdminLogin;

  @FXML
  private AnchorPane EnterCreateAccount;

  @FXML
  private PasswordField CreatePassword;

  @FXML
  private TextField CreateUsername;

  @FXML
  private AnchorPane getLoginInfo;

  @FXML
  private Label SuccessCreatedAccount;

  // On Mouse Click Show Create Account
  @FXML
  void ClickCreateAccount(MouseEvent event) {
    EnterLogin.setVisible(false);
    EnterCreateAccount.setVisible(true);
    SuccessCreatedAccount.setVisible(false);
  }

  // On Mouse Click Show Login
  @FXML
  void ClickLogin(MouseEvent event) {
    EnterLogin.setVisible(true);
    EnterCreateAccount.setVisible(false);
    SuccessCreatedAccount.setVisible(false);
  }
  @FXML

  // On Mouse Click Logout
  void getLogout(MouseEvent event) {
    getLogin.setVisible(true);
    getHome.setVisible(false);
    getLibrary.setVisible(false);
    getProfile.setVisible(false);
    getTaskBar.setVisible(false);
    getAdminTab.setVisible(false);
    getAdmin.setVisible(false);
  }

  // On Mouse Click for Create Account Button
  @FXML
  void CreateLogin(MouseEvent event) {
    // Assigns the Username and Password the user entered to a String Variable
    String username = getUsername.getText();
    String password = getPassword.getText();

    // Print Username and Password in System for Debug
    System.out.println(username + " " + password);

    // Set These Menus Visibility
    EnterCreateAccount.setVisible(false);
    EnterLogin.setVisible(false);
    SuccessCreatedAccount.setVisible(true);
  }

  // On Mouse Click for Login Button
  @FXML
  void getLogin(MouseEvent event) {
    // Assigns the Username and Password the user entered to a String Variable
    String username = CreateUsername.getText();
    String password = CreatePassword.getText();

    // If the AdminLogin Radio Button is Selected, Show Admin Tab
    if (AdminLogin.isSelected()) {
      getAdminTab.setVisible(true);
    }

    // Print Username and Password in System for Debug
    System.out.println(username + " " + password);

    // On Login Set These Menus to Visible
    getLogin.setVisible(false);
    getHome.setVisible(true);
    getTaskBar.setVisible(true);
    myName.setText(username);
    SuccessCreatedAccount.setVisible(false);
  }

  // On Mouse Click for ADMIN Label
  @FXML
  void getAdmin(MouseEvent event) {
    // Set These Menus to Visible
    getHome.setVisible(false);
    getLibrary.setVisible(false);
    getProfile.setVisible(false);
    getAdmin.setVisible(true);
  }

  // On Mouse Click for HOME Label
  @FXML
  void getHome(MouseEvent event) {
    // Set These Menus to Visible
    getHome.setVisible(true);
    getLibrary.setVisible(false);
    getProfile.setVisible(false);
    getAdmin.setVisible(false);
  }

  // On Mouse Click for LIBRARY Label
  @FXML
  void getLibrary(MouseEvent event) {
    // Set These Menus to Visible
    getHome.setVisible(false);
    getLibrary.setVisible(true);
    getProfile.setVisible(false);
    getAdmin.setVisible(false);
  }

  // On Mouse Click for PROFILE Label
  @FXML
  void getProfile(MouseEvent event) {
    // Set These Menus to Visible
    getHome.setVisible(false);
    getLibrary.setVisible(false);
    getProfile.setVisible(true);
    getAdmin.setVisible(false);
  }
}