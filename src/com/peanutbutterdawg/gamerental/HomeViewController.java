package com.peanutbutterdawg.gamerental;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sun.java2d.loops.GeneralRenderer;

public class HomeViewController implements Initializable {

  private static final String JDBC_DRIVER = "org.h2.Driver"; // Path to my H2 Driver
  private static final String DB_URL = "jdbc:h2:./res/H2"; // Path to my DataBase URL

  private ObservableList<Games> games;

  @FXML private Label name;

  @FXML private AnchorPane getAdminTab;

  @FXML private AnchorPane getHome;

  @FXML private TableView<Games> tableGamesTab;

  @FXML private TableColumn<?, ?> titleColumn2;

  @FXML private TableColumn<?, ?> genreColumn2;

  @FXML private TableColumn<?, ?> ratingColumn2;

  @FXML private ComboBox<String> filter;

  @FXML private ComboBox<String> filter1;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // this initializes the filter combo boxes with hardcoded data
    initializeFilterCBO();
    // this initializes the table view with hardcoded data
    initializeGamesTable();
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

  private void initializeFilterCBO() {
    filter.getItems().add("Action");
    filter.getItems().add("Adventure");
    filter.getItems().add("RPG");
    filter.getItems().add("FPS");

    // Filter1 populate

    filter1.getItems().add(">2.5");
    filter1.getItems().add("2.5-7.5");
    filter1.getItems().add(">7.5");
  }

  private void initializeGamesTable() {
    // Initialize and populate the game page filter table with some stuff. I HAVE NO IDEA WHY NO
    // WORK

    games = FXCollections.observableArrayList();

    try {
      String fromDbSql = "SELECT TITLE, GENRE, RATING FROM VIDEOGAME";

      Class.forName(JDBC_DRIVER); // Database Driver
      Connection conn = DriverManager.getConnection(DB_URL); // Database Url
      // This prepared statement executes my SQL String Command.
      PreparedStatement stmt = conn.prepareStatement(fromDbSql);

      ResultSet rs = stmt.executeQuery();

      while(rs.next()) {

        String title = rs.getString("TITLE");
        int genre = rs.getInt("GENRE");
        int rating = rs.getInt("RATING");

        Genre realGenre = null;

        if(genre == 0) {
          realGenre = Genre.ACTION;
        }if(genre == 1) {
          realGenre = Genre.ADVENTURE;
        }if(genre == 2) {
          realGenre = Genre.RPG;
        }if(genre == 3) {
          realGenre = Genre.SPORTS;
        }if(genre == 4) {
          realGenre = Genre.MMO;
        }if(genre == 5) {
          realGenre = Genre.STRATEGY;
        }if(genre == 6) {
          realGenre = Genre.SIMULATION;
        }

        // testing in the console
        System.out.println("\nTitle: " + title + "\nGenre: " + realGenre + "\nRating: " + rating);

        // setting the games to the table view
        tableGamesTab.setItems(games);
        games.add(new Games(title, realGenre, rating));
      }

      // Setup for the table view
      titleColumn2.setCellValueFactory(new PropertyValueFactory<>("title"));
      genreColumn2.setCellValueFactory(new PropertyValueFactory<>("genre"));
      ratingColumn2.setCellValueFactory(new PropertyValueFactory<>("rating"));

      stmt.execute();
      conn.close();
      stmt.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }
}
