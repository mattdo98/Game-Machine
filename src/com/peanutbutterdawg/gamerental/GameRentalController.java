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
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class GameRentalController implements Initializable {

  public Label myName;
  public AnchorPane adminView;

  @FXML private AnchorPane EnterLogin;

  @FXML private AnchorPane EnterCreateAccount;

  @FXML private PasswordField CreatePassword;

  @FXML private TextField CreateUsername;

  @FXML private PasswordField Password;

  @FXML private TextField Username;

  @FXML private Label SuccessCreatedAccount;

  @FXML private TextFlow userName;

  @FXML private TextFlow userEmail;

  @FXML private TextFlow subEnd;

  @FXML private RadioButton AdminLogin;

  @FXML public Label name;

  @FXML private AnchorPane getAdminTab;

  @FXML private AnchorPane editGamePage;

  @FXML private AnchorPane createAdminAccount;

  @FXML private AnchorPane addRemoveGame;

  // This method causes the add game button on the admin tab to show add game text-fields and
  // buttons.
  @FXML
  void addGame() {
    editGamePage.setVisible(false);
    createAdminAccount.setVisible(false);
    addRemoveGame.setVisible(true);
  }

  // This method causes the edit game button on the admin tab to show edit game text-fields and
  // buttons.
  @FXML
  void editGame() {
    editGamePage.setVisible(true);
    createAdminAccount.setVisible(false);
    addRemoveGame.setVisible(false);
  }

  // This method causes the create admin button on the admin tab to show create admin text-fields
  // and buttons.
  @FXML
  void createAdmin() {
    editGamePage.setVisible(false);
    createAdminAccount.setVisible(true);
    addRemoveGame.setVisible(false);
  }

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
    Parent HomeViewParent = FXMLLoader.load(getClass().getResource("HomeView.fxml"));
    Scene HomeViewScene = new Scene(HomeViewParent);

    // This line gets the Stage information
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

    window.setScene(HomeViewScene);
    window.show();


    // Clear Username and Password
    Username.setText("");
    Password.setText("");
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

  // Shows subscription ending (*not currently functioning correctly)
  private String displaySubEnd() {
    Calendar dateEnd = Calendar.getInstance();
    dateEnd.add(Calendar.MONTH, 1);
    return dateEnd.toString();
  }
}