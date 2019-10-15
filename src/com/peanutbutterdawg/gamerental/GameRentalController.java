package com.peanutbutterdawg.gamerental;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class GameRentalController {
  @FXML
  private TextField getUsername;
  @FXML
  private PasswordField getPassword;
  @FXML
  private AnchorPane getLogin;
  @FXML
  private AnchorPane getLoggedIn;
  @FXML
  private AnchorPane getHome;
  @FXML
  private AnchorPane getLibrary;
  @FXML
  private AnchorPane getProfile;
  /*
    Get Login, assigns the text in username and password fields to variables. Prints the username
    and password fields to test that we can see them. Changes the login screen to not be
    visible and then sets the loggedin screen as visible.
  */
  @FXML
  void getLogin(MouseEvent event) {
    String username = getUsername.getText();
    String password = getPassword.getText();

    System.out.println(username + " " + password);

    getLogin.setVisible(false);
    getLoggedIn.setVisible(true);
  }
  /*
    If user clicks on the home button, we set the home anchorpane to visible and set the rest to
    not be visible.
  */
  @FXML
  void getHome(MouseEvent event) {
    getHome.setVisible(true);
    getLibrary.setVisible(false);
    getProfile.setVisible(false);
  }
  /*
  If user clicks on the library button, we set the library anchorpane to visible and set the rest to
  not be visible.
*/
  @FXML
  void getLibrary(MouseEvent event) {
    getHome.setVisible(false);
    getLibrary.setVisible(true);
    getProfile.setVisible(false);
  }
  /*
  If user clicks on the profile button, we set the profile anchorpane to visible and set the rest to
  not be visible.
*/
  @FXML
  void getProfile(MouseEvent event) {
    getHome.setVisible(false);
    getLibrary.setVisible(false);
    getProfile.setVisible(true);
  }
}