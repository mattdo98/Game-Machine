package com.peanutbutterdawg.gamerental;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.h2.jdbc.JdbcSQLNonTransientException;

public class LibraryViewController implements Initializable {

  private static final String JDBC_DRIVER = "org.h2.Driver"; // Path to my H2 Driver
  private static final String DB_URL = "jdbc:h2:./res/H2"; // Path to my DataBase URL
  private ObservableList<Games> games;
  @FXML
  private ImageView imageView1;
  @FXML
  private ImageView imageView2;
  @FXML
  private ImageView imageView3;
  @FXML
  private AnchorPane getLibrary;

  @FXML
  private Label myName;

  @FXML
  private Label gameLimit;
  @FXML
  private TableView tableView;
  @FXML
  private TableColumn column1;
  @FXML
  private TableColumn column2;
  @FXML
  private TableColumn column3;
  @FXML
  private TableColumn column4;







  // initialize method
  @FXML
  public void initialize(URL url, ResourceBundle rb) {

    initializeNameLabel();
    initializeGameLimit();
    /*try {
      initializeImageView();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }*/
    try {
      initializeGameTable();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    System.out.println("This is Library Tab");
  }

  private void initializeGameTable() throws SQLException, ClassNotFoundException {
    String game1;
    String game2;
    String game3;
    games = FXCollections.observableArrayList();
    Class.forName(JDBC_DRIVER); // Database Driver
    Connection conn = DriverManager.getConnection(DB_URL); // Database Url
    Statement stmt = conn.createStatement();

    ResultSet rs = stmt.executeQuery("SELECT USERNAME FROM USER WHERE ISACTIVEUSER = TRUE");
    rs.next();
    String activeUser = rs.getString("USERNAME");

    PreparedStatement pstmt = conn.prepareStatement("SELECT GAME1, GAME2, GAME3 "
        + "FROM USERGAMES WHERE USERNAME = ?");

    pstmt.setString(1, activeUser);
    rs = pstmt.executeQuery();
    rs.next();

    game1 = rs.getString("GAME1");
    game2 = rs.getString("GAME2");
    game3 = rs.getString("GAME3");
    String SQL = "SELECT TITLE, GENRE, RATING, ESRB FROM VIDEOGAME WHERE TITLE = '"
        + game1 + "' OR TITLE = '" + game2 + "' OR TITLE = '" + game3 + "'";
    rs = stmt.executeQuery(SQL);
    while(rs.next()){
      String title = rs.getString("TITLE");
      int esrb = rs.getInt("ESRB");
      int genre = rs.getInt("GENRE");
      int rating = rs.getInt("RATING");

      ESRB realEsrb = null;

      if (esrb == 0) {
        realEsrb = ESRB.RP;
      }
      if (esrb == 1) {
        realEsrb = ESRB.C;
      }
      if (esrb == 2) {
        realEsrb = ESRB.E;
      }
      if (esrb == 3) {
        realEsrb = ESRB.T;
      }
      if (esrb == 4) {
        realEsrb = ESRB.M;
      }
      if (esrb == 5) {
        realEsrb = ESRB.A;
      }

      Genre realGenre = null;

      if (genre == 0) {
        realGenre = Genre.ACTION;
      }
      if (genre == 1) {
        realGenre = Genre.ADVENTURE;
      }
      if (genre == 2) {
        realGenre = Genre.RPG;
      }
      if (genre == 3) {
        realGenre = Genre.SPORTS;
      }
      if (genre == 4) {
        realGenre = Genre.MMO;
      }
      if (genre == 5) {
        realGenre = Genre.STRATEGY;
      }
      if (genre == 6) {
        realGenre = Genre.SIMULATION;
      }

      // testing in the console
      System.out.println("\nTitle: " + title + "\nGenre: " + realGenre + "\nRating: " + rating);

      // setting the games to the table view
      tableView.setItems(games);
      games.add(new Games(title, realGenre, rating, realEsrb));
    }

    // Setup for the table view
    column1.setCellValueFactory(new PropertyValueFactory<>("title"));
    column2.setCellValueFactory(new PropertyValueFactory<>("genre"));
    column3.setCellValueFactory(new PropertyValueFactory<>("rating"));
    column4.setCellValueFactory(new PropertyValueFactory<>("esrb"));


    conn.close();
    stmt.close();
    }



  @FXML
  void getAdmin(ActionEvent event) throws IOException {
    String getUser = "SELECT ISADMIN FROM USER WHERE ISACTIVEUSER = TRUE";

    try {
      Class.forName(JDBC_DRIVER); // Database Driver
      Connection conn = DriverManager.getConnection(DB_URL); // Database Url

      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(getUser);

      while (rs.next()) {
        boolean admin = rs.getBoolean("ISADMIN");

        if (admin == true) {
          System.out.println("User is Admin");

          Parent AdminViewParent = FXMLLoader.load(getClass().getResource("AdminView.fxml"));
          Scene AdminViewScene = new Scene(AdminViewParent);

          // This line gets the Stage information
          Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

          window.setScene(AdminViewScene);
          window.show();

        } else {
          System.out.println("User is Not Admin");
          JFrame parent = new JFrame();
          JOptionPane.showMessageDialog(parent, "User is Not Admin");
        }
      }

      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
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

      while(rs.next()) {
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

  private void initializeGameLimit(){
    try {
      String sqlLimit = "SELECT GAMECOUNT FROM USERGAMES";

      Class.forName(JDBC_DRIVER); // Database Driver
      Connection conn = DriverManager.getConnection(DB_URL); // Database Url

      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sqlLimit);
      while(rs.next()) {
        String gameCount = rs.getString("GAMECOUNT");
        gameLimit.setText("Limit: " + gameCount + "/3");
      }
      conn.close();
      stmt.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }

  }
  /*private void initializeImageView() throws ClassNotFoundException, SQLException {
    boolean game1missing;
    boolean game2missing;
    boolean game3missing;
    String game1;
    String game2;
    String game3;
    Class.forName(JDBC_DRIVER); // Database Driver
    Connection conn = DriverManager.getConnection(DB_URL); // Database Url
    Statement stmt = conn.createStatement();

    ResultSet rs = stmt.executeQuery("SELECT USERNAME FROM USER WHERE ISACTIVEUSER = TRUE");
    rs.next();
    String activeUser = rs.getString("USERNAME");

    PreparedStatement pstmt = conn.prepareStatement("SELECT GAME1, GAME2, GAME3 "
        + "FROM USERGAMES WHERE USERNAME = ?");

    pstmt.setString(1, activeUser);
    rs = pstmt.executeQuery();
    rs.next();

    game1 = rs.getString("GAME1");
    game2 = rs.getString("GAME2");
    game3 = rs.getString("GAME3");



    pstmt = conn.prepareStatement("SELECT IMG FROM VIDEOGAME WHERE TITLE = ?");
    pstmt.setString(1,game1);
    rs = pstmt.executeQuery();
    rs.next();
    Blob blob1 = rs.getBlob("IMG");
    InputStream in1 = blob1.getBinaryStream();
    Image img1 = new Image(in1);
    imageView1 = new ImageView(img1);

    rs = pstmt.executeQuery();
    pstmt.setString(1,game2);
    rs.next();
    blob1 = rs.getBlob("IMG");
    in1 = blob1.getBinaryStream();
    img1 = new Image(in1);
    imageView2 = new ImageView(img1);


    pstmt.setString(1,game3);
    rs = pstmt.executeQuery();
    rs.next();
    blob1 = rs.getBlob("IMG");
    in1 = blob1.getBinaryStream();
    img1 = new Image(in1);
    imageView3 = new ImageView(img1);

    }*/






}
