package com.peanutbutterdawg.gamerental;

import java.io.*;
import java.net.URL;
import java.sql.*;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class LibraryViewController implements Initializable {

  private static final String JDBC_DRIVER = "org.h2.Driver"; // Path to my H2 Driver
  private static final String DB_URL = "jdbc:h2:./res/H2"; // Path to my DataBase URL

  @FXML private ImageView gameImgView1;

  @FXML private ImageView gameImgView2;

  @FXML private ImageView gameImgView3;

  @FXML private Label myName;

  @FXML private Label gameLimit;

  @FXML private TableView tableView;

  @FXML private TableColumn column1;

  @FXML private TableColumn column2;

  @FXML private TableColumn column3;

  @FXML private TableColumn column4;

  @FXML private ComboBox<String> removeGame;

  @FXML private Label removedGame;

  @FXML private Button admin;

  @FXML private Pane checkSub;

  // initialize method
  @FXML
  public void initialize(URL url, ResourceBundle rb) {

    initializeNameLabel();
    initializeGameLimit();
    initializeRemoveGame();
    checkForSubscription();

    initializeGameView();
    System.out.println("This is Library Tab");

    checkForAdmin();
  }

  private void initializeGameView(){
    getGame1();
    getGame2();
    getGame3();
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

  @FXML
  void playGame(MouseEvent event) {}

  @FXML
  void removeGame(MouseEvent event) {
    String game1 = getGame1();
    String game2 = getGame2();
    String game3 = getGame3();

    if (game1 == removeGame.getValue()) {

      String sql = "UPDATE USERGAMES SET GAME1 = ?, GAMECOUNT = ? WHERE USERNAME = ?";

      try {
        Class.forName(JDBC_DRIVER); // Database Driver
        Connection conn = DriverManager.getConnection(DB_URL); // Database Url

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, null);
        ps.setString(2, "0");
        ps.setString(3, myName.getText());

        ps.executeUpdate();

        removedGame.setText("Game Removed From Library!");
        removeGame.getItems().clear();
        initializeNameLabel();
        initializeGameLimit();
        initializeRemoveGame();
        initializeGameView();

        ps.close();
        conn.close();
      } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
      }
    } else if (game2 == removeGame.getValue()) {

      String sql = "UPDATE USERGAMES SET GAME2 = ?, GAMECOUNT = ? WHERE USERNAME = ?";

      try {
        Class.forName(JDBC_DRIVER); // Database Driver
        Connection conn = DriverManager.getConnection(DB_URL); // Database Url

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, null);
        ps.setString(2, "1");
        ps.setString(3, myName.getText());

        ps.executeUpdate();

        removedGame.setText("Game Removed From Library!");
        removeGame.getItems().clear();
        initializeNameLabel();
        initializeGameLimit();
        initializeRemoveGame();
        initializeGameView();

        ps.close();
        conn.close();
      } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
      }
    } else if (game3 == removeGame.getValue()) {
      String sql = "UPDATE USERGAMES SET GAME3 = ?, GAMECOUNT = ? WHERE USERNAME = ?";

      try {
        Class.forName(JDBC_DRIVER); // Database Driver
        Connection conn = DriverManager.getConnection(DB_URL); // Database Url

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, null);
        ps.setString(2, "2");
        ps.setString(3, myName.getText());

        ps.executeUpdate();

        removedGame.setText("Game Removed From Library!");
        removeGame.getItems().clear();
        initializeNameLabel();
        initializeGameLimit();
        initializeRemoveGame();
        initializeGameView();

        ps.close();
        conn.close();
      } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
      }
    }
    initializeGameLimit();
  }

  private String getGame1() {
    try {
      String sql = "SELECT GAME1 FROM USERGAMES WHERE USERNAME = ?";
      Class.forName(JDBC_DRIVER); // Database Driver
      Connection conn = DriverManager.getConnection(DB_URL); // Database Url

      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(1, myName.getText());

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        String title = rs.getString("GAME1");
        try{
          String sqlGame = "SELECT IMG FROM VIDEOGAME WHERE TITLE = ?";
          PreparedStatement psGame = conn.prepareStatement(sqlGame);

          psGame.setString(1, title);
          ResultSet rsGame = psGame.executeQuery();

          if (title != null) {
            while (rsGame.next()) {
              InputStream input1 = rsGame.getBinaryStream("IMG");
              Image img1 = new Image(input1);
              gameImgView1.setImage(img1);
              return title;
            }
          } else {
            gameImgView1.setImage(null);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  private String getGame2() {
    String sql = "SELECT GAME2 FROM USERGAMES WHERE USERNAME = ?";
    try {
      Class.forName(JDBC_DRIVER); // Database Driver
      Connection conn = DriverManager.getConnection(DB_URL); // Database Url

      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(1, myName.getText());

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        String title = rs.getString("GAME2");
        try{
          String sqlGame = "SELECT IMG FROM VIDEOGAME WHERE TITLE = ?";
          PreparedStatement psGame = conn.prepareStatement(sqlGame);

          psGame.setString(1, title);
          ResultSet rsGame = psGame.executeQuery();
          if (title != null) {
            while (rsGame.next()) {
              InputStream input2 = rsGame.getBinaryStream("IMG");
              Image img2 = new Image(input2);
              gameImgView2.setImage(img2);
              return title;
            }
          }else {
            gameImgView2.setImage(null);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  private String getGame3() {
    String sql = "SELECT GAME3 FROM USERGAMES WHERE USERNAME = ?";

    try {
      Class.forName(JDBC_DRIVER); // Database Driver
      Connection conn = DriverManager.getConnection(DB_URL); // Database Url

      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(1, myName.getText());

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        String title = rs.getString("GAME3");
        try{
          String sqlGame = "SELECT IMG FROM VIDEOGAME WHERE TITLE = ?";
          PreparedStatement psGame = conn.prepareStatement(sqlGame);

          psGame.setString(1, title);
          ResultSet rsGame = psGame.executeQuery();
          if (title != null) {
            while (rsGame.next()) {
              InputStream input3 = rsGame.getBinaryStream("IMG");
              Image img1 = new Image(input3);
              gameImgView3.setImage(img1);
              return title;
            }
          }else{
            gameImgView3.setImage(null);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }

    return null;
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

  private void initializeRemoveGame() {
    String game1;
    String game2;
    String game3;

    try {
      Class.forName(JDBC_DRIVER); // Database Driver
      Connection conn = DriverManager.getConnection(DB_URL); // Database Url
      Statement stmt = conn.createStatement();

      ResultSet rs = stmt.executeQuery("SELECT USERNAME FROM USER WHERE ISACTIVEUSER = TRUE");
      rs.next();
      String activeUser = rs.getString("USERNAME");

      PreparedStatement pstmt =
          conn.prepareStatement(
              "SELECT GAME1, GAME2, GAME3 " + "FROM USERGAMES WHERE USERNAME = ?");

      pstmt.setString(1, activeUser);
      rs = pstmt.executeQuery();
      rs.next();

      game1 = rs.getString("GAME1");
      game2 = rs.getString("GAME2");
      game3 = rs.getString("GAME3");

      String SQL =
          "SELECT TITLE FROM VIDEOGAME WHERE TITLE = '"
              + game1
              + "' OR TITLE = '"
              + game2
              + "' OR TITLE = '"
              + game3
              + "'";
      rs = stmt.executeQuery(SQL);

      removeGame.getItems().addAll(game1, game2, game3);

      conn.close();
      stmt.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  private void initializeGameLimit() {
    try {
      String sqlLimit = "SELECT GAMECOUNT FROM USERGAMES";

      Class.forName(JDBC_DRIVER); // Database Driver
      Connection conn = DriverManager.getConnection(DB_URL); // Database Url

      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sqlLimit);


      while (rs.next()) {
        String gameCount = rs.getString("GAMECOUNT");
        gameLimit.setText("Limit: " + gameCount + "/3");
      }
      conn.close();
      stmt.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
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
  @FXML
  private void checkForSubscription(){

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
          checkSub.setVisible(false);

        }
        // If current user does not have a subscription
        else {
          currentSub = false;
          checkSub.setVisible(true);

        }
      }



    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }
  }
