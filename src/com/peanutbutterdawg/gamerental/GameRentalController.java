package com.peanutbutterdawg.gamerental;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class GameRentalController {
  // FX ID for Task Bar AnchorPane
  @FXML
  private AnchorPane getTaskBar;
  // FX ID for the myName label on top right
  @FXML
  private Label myName;
  // FX ID for the HOME AnchorPane
  @FXML
  private AnchorPane getHome;
  // FX ID for the LIBRARY AnchorPane
  @FXML
  private AnchorPane getLibrary;
  // FX ID for the PROFILE AnchorPane
  @FXML
  private AnchorPane getProfile;
  // FX ID for the ADMIN Tab AnchorPane
  @FXML
  private AnchorPane getAdminTab;
  // FX ID for the ADMIN AnchorPane
  @FXML
  private AnchorPane getAdmin;
  // FX ID for the LOGIN AnchorPane
  @FXML
  private AnchorPane getLogin;
  // FX ID for the EnterLogin AnchorPane
  @FXML
  private AnchorPane EnterLogin;
  // FX ID for the EnterCreateAccount AnchorPane
  @FXML
  private AnchorPane EnterCreateAccount;
  // FX ID for Password Field under Login
  @FXML
  private PasswordField getPassword;
  // FX ID for Username Field under Login
  @FXML
  private TextField getUsername;
  // FX ID for Admin Radio Button
  @FXML
  private RadioButton AdminLogin;
  // FX ID for Password Field under Create Account
  @FXML
  private PasswordField CreatePassword;
  // FX ID for Username Field under Create Account
  @FXML
  private TextField CreateUsername;
  // FX ID for SuccessCreatedAccount Label on Create Account Screen
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