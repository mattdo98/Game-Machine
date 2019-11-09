package com.peanutbutterdawg.gamerental;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class AdminViewController implements Initializable {

  private static final String JDBC_DRIVER = "org.h2.Driver"; // Path to my H2 Driver
  private static final String DB_URL = "jdbc:h2:./res/H2"; // Path to my DataBase URL

  private ObservableList<Games> games;

  @FXML private Label myName;

  @FXML private TableView<Games> tableAdminTab;

  @FXML private TableColumn<?, ?> titleColumn;

  @FXML private TableColumn<?, ?> genreColumn;

  @FXML private TableColumn<?, ?> ratingColumn;

  @FXML private ComboBox<Genre> getGenre;

  @FXML private ComboBox<Genre> getNewGenre;

  @FXML private ComboBox<Integer> getRating;

  @FXML private ComboBox<Integer> getNewRating;

  @FXML private TableColumn<?, ?> esrbColumn;

  @FXML private ComboBox<ESRB> getESRB;

  @FXML private ComboBox<ESRB> getNewESRB;

  @FXML private ComboBox<String> deleteGame;

  @FXML private ComboBox<String> selectGame;

  @FXML private TextField getTitle;

  // initialize method
  public void initialize(URL url, ResourceBundle rb) {
    initializeNameLabel();
    initializeGamesTable();
    initializeSelectGame();
    initializeDeleteGame();

    // Set Items for Genre ComboBx
    for (Genre item : Genre.values()) {
      getGenre.getItems().addAll(item);
      getNewGenre.getItems().addAll(item);
    }

    // Set Items for ESRB ComboBox
    for (ESRB item : ESRB.values()) {
      getESRB.getItems().addAll(item);
      getNewESRB.getItems().addAll(item);
    }

    // Set Items for Rating ComboBox
    getRating.getItems().add(1);
    getRating.getItems().add(2);
    getRating.getItems().add(3);
    getRating.getItems().add(4);
    getRating.getItems().add(5);
    getRating.getItems().add(6);
    getRating.getItems().add(7);
    getRating.getItems().add(8);
    getRating.getItems().add(9);
    getRating.getItems().add(10);

    // Set Items for NewRating ComboBox
    getNewRating.getItems().add(1);
    getNewRating.getItems().add(2);
    getNewRating.getItems().add(3);
    getNewRating.getItems().add(4);
    getNewRating.getItems().add(5);
    getNewRating.getItems().add(6);
    getNewRating.getItems().add(7);
    getNewRating.getItems().add(8);
    getNewRating.getItems().add(9);
    getNewRating.getItems().add(10);
  }

  // Get Admin Tab
  @FXML void getAdmin(ActionEvent event) throws IOException {
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

  // Get Home Tab
  @FXML void getHome(ActionEvent event) throws IOException {
    Parent HomeViewParent = FXMLLoader.load(getClass().getResource("HomeView.fxml"));
    Scene HomeViewScene = new Scene(HomeViewParent);

    // This line gets the Stage information
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

    window.setScene(HomeViewScene);
    window.show();
  }

  // Get Library
  @FXML void getLibrary(ActionEvent event) throws IOException {
    Parent LibraryViewParent = FXMLLoader.load(getClass().getResource("LibraryView.fxml"));
    Scene LibraryViewScene = new Scene(LibraryViewParent);

    // This line gets the Stage information
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

    window.setScene(LibraryViewScene);
    window.show();
  }

  // Get Logout
  @FXML void getLogout(ActionEvent event) throws IOException {
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

  // Get Profile Tab
  @FXML void getProfile(ActionEvent event) throws IOException {
    Parent ProfileViewParent = FXMLLoader.load(getClass().getResource("ProfileView.fxml"));
    Scene ProfileViewScene = new Scene(ProfileViewParent);

    // This line gets the Stage information
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

    window.setScene(ProfileViewScene);
    window.show();
  }

  // Add Game Button
  @FXML void addGame(MouseEvent event) {
    // Variables
    Genre genreText = getGenre.getValue();
    ESRB esrbText = getESRB.getValue();
    int ratingText = getRating.getValue();

    String sql = "INSERT INTO VIDEOGAME (TITLE, GENRE, ESRB, RATING) VALUES (?, ?, ?, ?)";

    try {

      Class.forName(JDBC_DRIVER); // Database Driver
      Connection conn = DriverManager.getConnection(DB_URL); // Database Url

      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(1, getTitle.getText());
      ps.setString(2, genreText.toString());
      ps.setString(3, esrbText.toString());
      ps.setString(4, Integer.toString(ratingText));

      ps.executeUpdate();

      initializeGamesTable();
      initializeSelectGame();
      initializeDeleteGame();

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  // Edit Game Button
  @FXML void editGame(MouseEvent event) {
    // Variables
    Genre genreText = getNewGenre.getValue();
    ESRB esrbText = getNewESRB.getValue();
    int ratingText = getNewRating.getValue();

    String sql = "UPDATE VIDEOGAME SET GENRE = ?, ESRB = ?, RATING = ? WHERE TITLE = ?";

    try {

      Class.forName(JDBC_DRIVER); // Database Driver
      Connection conn = DriverManager.getConnection(DB_URL); // Database Url

      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(4, selectGame.getValue());
      ps.setString(1, genreText.toString());
      ps.setString(2, esrbText.toString());
      ps.setString(3, Integer.toString(ratingText));

      ps.executeUpdate();

      initializeGamesTable();
      initializeSelectGame();
      initializeDeleteGame();

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  // Delete Game Button
  @FXML void deleteGame(MouseEvent event) {

    String sql = "DELETE FROM VIDEOGAME WHERE TITLE = ?";

    try {

      Class.forName(JDBC_DRIVER); // Database Driver
      Connection conn = DriverManager.getConnection(DB_URL); // Database Url

      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(1, deleteGame.getValue());

      ps.executeUpdate();

      initializeGamesTable();
      initializeSelectGame();
      initializeDeleteGame();

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  // Initialize Select Game
  private void initializeSelectGame() {

    selectGame.getItems().clear();

    try {
      String sql = "SELECT TITLE FROM VIDEOGAME";

      Class.forName(JDBC_DRIVER); // Database Driver
      Connection conn = DriverManager.getConnection(DB_URL); // Database Url

      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {
        String title = rs.getString("TITLE");

        selectGame.getItems().addAll(title);
      }

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  private void initializeDeleteGame() {

    deleteGame.getItems().clear();

    try {
      String sql = "SELECT TITLE FROM VIDEOGAME";

      Class.forName(JDBC_DRIVER); // Database Driver
      Connection conn = DriverManager.getConnection(DB_URL); // Database Url

      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {
        String title = rs.getString("TITLE");

        deleteGame.getItems().addAll(title);
      }

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  // Initialize Name Label
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

  // Initialize Games Table
  private void initializeGamesTable() {
    games = FXCollections.observableArrayList();

    try {
      String fromDbSql = "SELECT TITLE, GENRE, ESRB, RATING FROM VIDEOGAME";

      Class.forName(JDBC_DRIVER); // Database Driver
      Connection conn = DriverManager.getConnection(DB_URL); // Database Url
      // This prepared statement executes my SQL String Command.
      PreparedStatement stmt = conn.prepareStatement(fromDbSql);

      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {

        String title = rs.getString("TITLE");
        int genre = rs.getInt("GENRE");
        int rating = rs.getInt("RATING");
        int esrb = rs.getInt("ESRB");

        ESRB realEsrb = null;

        // If ESRB Value is Integer, Set ESRB realEsrb
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

        // If Genre Value is Integer, Set Genre realgenre
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
        System.out.println("\nTitle: " + title + "\nGenre: " + realGenre + "\nRating: " + rating + "\nESRB: " + realEsrb);

        // setting the games to the table view
        tableAdminTab.setItems(games);
        games.add(new Games(title, realGenre, rating, realEsrb));
      }

      // Setup for the table view
      titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
      genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
      ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
      esrbColumn.setCellValueFactory(new PropertyValueFactory<>("esrb"));

      stmt.execute();
      conn.close();
      stmt.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }
}
