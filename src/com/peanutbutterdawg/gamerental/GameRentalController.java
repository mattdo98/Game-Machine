package com.peanutbutterdawg.gamerental;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
  // FX ID for Password Field under Create Account
  @FXML
  private PasswordField CreatePassword;
  // FX ID for Username Field under Create Account
  @FXML
  private TextField CreateUsername;

  // On Mouse Click Show Create Account
  @FXML
  void ClickCreateAccount(MouseEvent event) {
    EnterLogin.setVisible(false);
    EnterCreateAccount.setVisible(true);
  }

  // On Mouse Click Show Login
  @FXML
  void ClickLogin(MouseEvent event) {
    EnterLogin.setVisible(true);
    EnterCreateAccount.setVisible(false);
  }
  @FXML

  // On Mouse Click Logout
  void getLogout(MouseEvent event) {
    getLogin.setVisible(true);
    getHome.setVisible(false);
    getLibrary.setVisible(false);
    getProfile.setVisible(false);
    getTaskBar.setVisible(false);
  }

  // On Mouse Click for Create Account Button
  @FXML
  void CreateLogin(MouseEvent event) {

  }

  // On Mouse Click for Login Button
  @FXML
  void getLogin(MouseEvent event) {
    String username = getUsername.getText();
    String password = getPassword.getText();

    System.out.println(username + " " + password);

    getLogin.setVisible(false);
    getHome.setVisible(true);
    getTaskBar.setVisible(true);
    myName.setText(username);
  }

  // On Mouse Click for HOME Label
  @FXML
  void getHome(MouseEvent event) {
    getHome.setVisible(true);
    getLibrary.setVisible(false);
    getProfile.setVisible(false);
  }

  // On Mouse Click for LIBRARY Label
  @FXML
  void getLibrary(MouseEvent event) {
    getHome.setVisible(false);
    getLibrary.setVisible(true);
    getProfile.setVisible(false);
  }

  // On Mouse Click for PROFILE Label
  @FXML
  void getProfile(MouseEvent event) {
    getHome.setVisible(false);
    getLibrary.setVisible(false);
    getProfile.setVisible(true);
  }
}