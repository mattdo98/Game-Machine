package com.peanutbutterdawg.gamerental;

import java.io.*;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AdminViewController implements Initializable {

  private static final String JDBC_DRIVER = "org.h2.Driver"; // Path to my H2 Driver
  private static final String DB_URL = "jdbc:h2:./res/H2"; // Path to my DataBase URL
  public Button addPhotoBtn2;

  private ObservableList<Games> games;

  @FXML
  public Button addPhotoBtn;

  @FXML private Label name;

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

  @FXML private Button admin;

  private String fileInputPath = "";

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

  // Add Game Button
  @FXML
  void addGame(MouseEvent event) {
    Genre genreText = getGenre.getValue();
    ESRB esrbText = getESRB.getValue();
    int ratingText = getRating.getValue();

    try {
      InputStream input = new FileInputStream(new File(fileInputPath));

      PreparedStatement ps = getPreparedStatement("INSERT INTO VIDEOGAME (TITLE, GENRE, ESRB, RATING, IMG) " +
          "VALUES " + "(?, ?, ?, ?, ?)");

      ps.setString(1, getTitle.getText());
      ps.setString(2, genreText.toString());
      ps.setString(3, esrbText.toString());
      ps.setString(4, Integer.toString(ratingText));
      ps.setBlob(5, input);

      ps.executeUpdate();

      initializeGamesTable();
      initializeSelectGame();
      initializeDeleteGame();

    } catch (ClassNotFoundException | SQLException | FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  @FXML
  void addPhotoBtn(ActionEvent event){
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter(".IMAGES", "*.png", "*.jpg", "*.jpeg")
    );
    File tempFile = fileChooser.showOpenDialog(null);
    if (tempFile != null){
      fileInputPath = tempFile.getAbsolutePath();
    }else{
      System.out.println("No Data");
    }
  }

  @FXML
  void editGame(MouseEvent event) {
    Genre genreText = getNewGenre.getValue();
    ESRB esrbText = getNewESRB.getValue();
    int ratingText = getNewRating.getValue();

    String sql = "UPDATE VIDEOGAME SET GENRE = ?, ESRB = ?, RATING = ?, IMG = ? WHERE TITLE = ?";

    try {
      InputStream input = new FileInputStream(new File(fileInputPath));

      PreparedStatement ps = getPreparedStatement("UPDATE VIDEOGAME SET GENRE = ?, ESRB = ?, " +
          "RATING = ?, IMG = ? WHERE TITLE = ?");

      ps.setString(5, selectGame.getValue());
      ps.setString(1, genreText.toString());
      ps.setString(2, esrbText.toString());
      ps.setString(3, Integer.toString(ratingText));
      ps.setBlob(4, input);


      ps.executeUpdate();

      initializeGamesTable();
      initializeSelectGame();
      initializeDeleteGame();

    } catch (ClassNotFoundException | SQLException | FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  // Delete Game Button
  @FXML
  void deleteGame(MouseEvent event) {
    try {
      PreparedStatement ps = getPreparedStatement("DELETE FROM VIDEOGAME WHERE TITLE = ?");

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
      ResultSet rs = getResultSet("SELECT TITLE FROM VIDEOGAME");

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
      ResultSet rs = getResultSet("SELECT TITLE FROM VIDEOGAME");

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
      ResultSet rs = getResultSet("SELECT USERNAME FROM USER WHERE ISACTIVEUSER = TRUE");

      while (rs.next()) {
        String username = rs.getString("USERNAME");

        name.setText(username);
      }

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  // Initialize Games Table
  private void initializeGamesTable() {
    games = FXCollections.observableArrayList();

    try {
      PreparedStatement stmt = getPreparedStatement("SELECT TITLE, GENRE, ESRB, RATING FROM VIDEOGAME");
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
        System.out.println(
            "\nTitle: "
                + title
                + "\nGenre: "
                + realGenre
                + "\nRating: "
                + rating
                + "\nESRB: "
                + realEsrb);

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
      stmt.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }
}
