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

  @FXML private ImageView gameImgView1;

  @FXML private ImageView gameImgView2;

  @FXML private ImageView gameImgView3;

  @FXML private Label myName;

  @FXML private Label gameLimit;

  @FXML private ComboBox<String> removeGame;

  @FXML private Button admin;

  @FXML private Pane checkSub;

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

  private ResultSet getResultSet(String sql) throws ClassNotFoundException, SQLException {
    Class.forName("org.h2.Driver");
    Connection conn = DriverManager.getConnection("jdbc:h2:./res/H2");
    Statement stmt = conn.createStatement();
    return stmt.executeQuery(sql);
  }

  private PreparedStatement getPreparedStatement(String sql) throws ClassNotFoundException, SQLException {
    Class.forName("org.h2.Driver");
    Connection conn = DriverManager.getConnection("jdbc:h2:./res/H2");
    return conn.prepareStatement(sql);
  }

  @FXML
  private void setScene(String parent, ActionEvent event) throws IOException {
    Parent viewParent = FXMLLoader.load(getClass().getResource(parent));
    Scene scene = new Scene(viewParent);

    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

    window.setScene(scene);
    window.show();
  }

  @FXML
  void getAdmin(ActionEvent event) throws IOException {
    setScene("AdminView.fxml", event);
  }

  @FXML
  void getHome(ActionEvent event) throws IOException {
    setScene("HomeView.fxml", event);
  }

  @FXML
  void getLibrary(ActionEvent event) throws IOException {
    setScene("LibraryView.fxml", event);
  }

  @FXML
  void getProfile(ActionEvent event) throws IOException {
    setScene("ProfileView.fxml", event);
  }

  @FXML
  void getLogout(ActionEvent event) throws IOException {
    setScene("LoginView.fxml", event);

    String getUserName = "SELECT USERNAME FROM USER WHERE ISACTIVEUSER = TRUE";
    String setActiveToFalse = "UPDATE USER SET ISACTIVEUSER = FALSE WHERE USERNAME = ?";

    try {
      ResultSet rs = getResultSet(getUserName);

      PreparedStatement ps = getPreparedStatement(setActiveToFalse);

      while (rs.next()) {
        String username = rs.getString("USERNAME");

        ps.setString(1, username);
        ps.executeUpdate();
      }
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }



  @FXML
  void playGame(MouseEvent event) {}

  @FXML
  void removeGame(MouseEvent event) {
    String game1 = getGame1();
    String game2 = getGame2();
    String game3 = getGame3();

    if (game1.equals(removeGame.getValue())) {

      try {
        PreparedStatement ps = getPreparedStatement("UPDATE USERGAMES SET GAME1 = ?, GAMECOUNT = ? WHERE USERNAME = ?");

        ps.setString(1, null);
        ps.setString(2, "0");
        ps.setString(3, myName.getText());

        ps.executeUpdate();

        removeGame.getItems().clear();
        initializeNameLabel();
        initializeGameLimit();
        initializeRemoveGame();
        initializeGameView();

      } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
      }
    } else if (game2.equals(removeGame.getValue())) {

      try {
        PreparedStatement ps = getPreparedStatement("UPDATE USERGAMES SET GAME2 = ?, GAMECOUNT = ? WHERE USERNAME = ?");

        ps.setString(1, null);
        ps.setString(2, "1");
        ps.setString(3, myName.getText());

        ps.executeUpdate();

        removeGame.getItems().clear();
        initializeNameLabel();
        initializeGameLimit();
        initializeRemoveGame();
        initializeGameView();
      } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
      }
    } else if (game3.equals(removeGame.getValue())) {

      try {
        PreparedStatement ps = getPreparedStatement("UPDATE USERGAMES SET GAME3 = ?, GAMECOUNT = ? WHERE USERNAME = ?");

        ps.setString(1, null);
        ps.setString(2, "2");
        ps.setString(3, myName.getText());

        ps.executeUpdate();

        removeGame.getItems().clear();
        initializeNameLabel();
        initializeGameLimit();
        initializeRemoveGame();
        initializeGameView();
      } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
      }
    }
    initializeGameLimit();
  }

  private String getGame1() {
    try {

      PreparedStatement ps = getPreparedStatement("SELECT GAME1 FROM USERGAMES WHERE USERNAME = ?");

      ps.setString(1, myName.getText());

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        String title = rs.getString("GAME1");
        try{
          PreparedStatement psGame = getPreparedStatement("SELECT IMG FROM VIDEOGAME WHERE TITLE = ?");

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
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  private String getGame2() {
    try {
      PreparedStatement ps = getPreparedStatement("SELECT GAME2 FROM USERGAMES WHERE USERNAME = ?");

      ps.setString(1, myName.getText());

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        String title = rs.getString("GAME2");
        try{
          PreparedStatement psGame = getPreparedStatement("SELECT IMG FROM VIDEOGAME WHERE TITLE = ?");

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
    try {
      PreparedStatement ps = getPreparedStatement("SELECT GAME3 FROM USERGAMES WHERE USERNAME = ?");

      ps.setString(1, myName.getText());

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        String title = rs.getString("GAME3");
        try{
          PreparedStatement psGame = getPreparedStatement("SELECT IMG FROM VIDEOGAME WHERE TITLE = ?");

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
      ResultSet rs = getResultSet("SELECT USERNAME FROM USER WHERE ISACTIVEUSER = TRUE");

      while (rs.next()) {
        String username = rs.getString("USERNAME");
        myName.setText(username);
      }
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  private void initializeRemoveGame() {
    try {
      ResultSet rs = getResultSet("SELECT USERNAME FROM USER WHERE ISACTIVEUSER = TRUE");
      rs.next();
      String activeUser = rs.getString("USERNAME");

      PreparedStatement pstmt = getPreparedStatement("SELECT GAME1, GAME2, GAME3 " + "FROM USERGAMES WHERE " +
          "USERNAME = ?");

      pstmt.setString(1, activeUser);
      rs = pstmt.executeQuery();
      rs.next();

      String game1 = rs.getString("GAME1");
      String game2 = rs.getString("GAME2");
      String game3 = rs.getString("GAME3");

      removeGame.getItems().addAll(game1, game2, game3);
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  //Maximum game limit is 3
  private void initializeGameLimit() {
    try {
      ResultSet rs = getResultSet("SELECT GAMECOUNT FROM USERGAMES");
      while (rs.next()) {
        String gameCount = rs.getString("GAMECOUNT");
        gameLimit.setText("Limit: " + gameCount + "/3");
      }
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void checkForAdmin() {
    try {
      ResultSet rs = getResultSet("SELECT ISADMIN FROM USER WHERE ISACTIVEUSER");

      while (rs.next()) {
        boolean adminBool = rs.getBoolean("ISADMIN");

        if (adminBool) {
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
      ResultSet rs = getResultSet("SELECT SUBSCRIPTION FROM USER WHERE ISACTIVEUSER = true");
      while (rs.next()) {
        if (rs.getBoolean("SUBSCRIPTION")) {
          // If current user has a subscription
          checkSub.setVisible(false);
        }
        // If current user does not have a subscription
        else {
          checkSub.setVisible(true);
        }
      }
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }
}
