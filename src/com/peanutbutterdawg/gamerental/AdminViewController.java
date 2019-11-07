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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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

  @FXML private ComboBox<Genre> getGenre1;

  @FXML private ComboBox<String> getRating;

  @FXML private ComboBox<String> getRating1;

  @FXML private TableColumn<?, ?> esrbColumn;

  @FXML private ComboBox<ESRB> getESRB;

  @FXML private ComboBox<ESRB> getESRB1;

  // initialize method
  public void initialize(URL url, ResourceBundle rb) {
    initializeNameLabel();
    initializeGamesTable();

    // Set Items for Genre ComboBx
    for (Genre item : Genre.values()) {
      getGenre.getItems().addAll(item);
      getGenre1.getItems().addAll(item);
    }

    // Set Items for ESRB ComboBox
    for (ESRB item : ESRB.values()) {
      getESRB.getItems().addAll(item);
      getESRB1.getItems().addAll(item);
    }

    // Set Items for Rating ComboBox
    getRating.getItems().add("1");
    getRating.getItems().add("2");
    getRating.getItems().add("3");
    getRating.getItems().add("4");
    getRating.getItems().add("5");
    getRating.getItems().add("6");
    getRating.getItems().add("7");
    getRating.getItems().add("8");
    getRating.getItems().add("9");
    getRating.getItems().add("10");

    // Set Items for Rating1 ComboBox
    getRating1.getItems().add("1");
    getRating1.getItems().add("2");
    getRating1.getItems().add("3");
    getRating1.getItems().add("4");
    getRating1.getItems().add("5");
    getRating1.getItems().add("6");
    getRating1.getItems().add("7");
    getRating1.getItems().add("8");
    getRating1.getItems().add("9");
    getRating1.getItems().add("10");
  }

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

        if (esrb == 0) {
          realEsrb = ESRB.RP;
        }
        if (genre == 1) {
          realEsrb = ESRB.C;
        }
        if (genre == 2) {
          realEsrb = ESRB.E;
        }
        if (genre == 3) {
          realEsrb = ESRB.T;
        }
        if (genre == 4) {
          realEsrb = ESRB.M;
        }
        if (genre == 5) {
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
