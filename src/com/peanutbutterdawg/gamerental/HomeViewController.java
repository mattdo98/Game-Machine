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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.plaf.nimbus.State;
import sun.dc.pr.PRError;

public class HomeViewController implements Initializable {

  private static final String JDBC_DRIVER = "org.h2.Driver"; // Path to my H2 Driver
  private static final String DB_URL = "jdbc:h2:./res/H2"; // Path to my DataBase URL

  private ObservableList<Games> games;

  @FXML private Label name;

  @FXML private AnchorPane getAdminTab;

  @FXML private AnchorPane getHome;

  @FXML private TextField searchBar;

  @FXML private TableView<Games> tableGamesTab;

  @FXML private TableColumn<?, ?> titleColumn2;

  @FXML private TableColumn<?, ?> genreColumn2;

  @FXML private TableColumn<?, ?> ratingColumn2;

  @FXML private ComboBox<Genre> getGenre;

  @FXML private TableColumn<?, ?> esrbColumn2;

  @FXML private Button setFilter;

  @FXML private Label addedGame;

  @FXML private ComboBox<ESRB> getESRB;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // this initializes the filter combo boxes with hardcoded data
    initializeFilterCBO();
    // this initializes the table view with hardcoded data
    initializeGamesTable();
    // this initializes the name label that is at the top of each page
    initializeNameLabel();
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
  void getSearch(ActionEvent event) {

    String searchBoxText = searchBar.getText();
    Genre genreText = getGenre.getValue();

    if (!searchBar.getText().isEmpty() && getGenre.getSelectionModel().isEmpty()) {
      String sql = "SELECT TITLE FROM VIDEOGAME WHERE TITLE = ?";

      System.out.println("test");

      try {
        Class.forName(JDBC_DRIVER); // Database Driver
        Connection conn = DriverManager.getConnection(DB_URL); // Database Url

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, searchBoxText);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
          String title = rs.getString("TITLE");

          System.out.println(title);

          setUpTableSearchTitle(title);
        }

      } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
      }

      // to search if the combobox genre was only filled
      //  currently not working
    } else if (searchBar.getText().isEmpty() && !getGenre.getSelectionModel().isEmpty()) {
      String sql = "SELECT GENRE FROM VIDEOGAME WHERE GENRE = ?";

      System.out.println("test");

      try {
        Class.forName(JDBC_DRIVER); // Database Driver
        Connection conn = DriverManager.getConnection(DB_URL); // Database Url

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, genreText.toString());

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
          int genre = rs.getInt("GENRE");

          System.out.println(genreText.getString());

          System.out.println(genre);

          // setUpTableSearchGenre(genre);
        }

      } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
      }
    }
  }

  private void initializeFilterCBO() {

    for (ESRB item : ESRB.values()) {
      getESRB.getItems().addAll(item);
    }

    for (Genre item : Genre.values()) {
      getGenre.getItems().addAll(item);
    }
  }

  @FXML
  void addGame(ActionEvent event) {
    ObservableList<Games> games = tableGamesTab.getSelectionModel().getSelectedItems();

    System.out.println(games.get(0).getTitle());

    String game1 = getGame1();
    String game2 = getGame2();
    String game3 = getGame3();

    if (game1 == null) {

      String sql = "UPDATE USERGAMES SET GAME1 = ?, GAMECOUNT = ? WHERE USERNAME = ?";

      try {
        Class.forName(JDBC_DRIVER); // Database Driver
        Connection conn = DriverManager.getConnection(DB_URL); // Database Url

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, games.get(0).getTitle());
        ps.setString(2, "1");
        ps.setString(3, name.getText());

        ps.executeUpdate();

        addedGame.setText("Game Added to Library!");

        ps.close();
        conn.close();
      } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
      }
    } else if (game2 == null) {

      String sql = "UPDATE USERGAMES SET GAME2 = ? WHERE USERNAME = ?";

      try {
        Class.forName(JDBC_DRIVER); // Database Driver
        Connection conn = DriverManager.getConnection(DB_URL); // Database Url

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, games.get(0).getTitle());
        ps.setString(2, name.getText());

        ps.executeUpdate();

        addedGame.setText("Game Added to Library!");

        ps.close();
        conn.close();
      } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
      }
    } else if (game3 == null) {
      String sql = "UPDATE USERGAMES SET GAME3 = ?, GAMECOUNT = ? WHERE USERNAME = ?";

      try {
        Class.forName(JDBC_DRIVER); // Database Driver
        Connection conn = DriverManager.getConnection(DB_URL); // Database Url

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, games.get(0).getTitle());
        ps.setString(2, "3");
        ps.setString(3, name.getText());

        ps.executeUpdate();

        addedGame.setText("Game Added to Library!");

        ps.close();
        conn.close();
      } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
      }
    } else {
      addedGame.setText("Out of rentals!");
    }
    initializeGamesTable();
  }

  private void initializeGamesTable() {
    // Initialize and populate the game page filter table with some stuff. I HAVE NO IDEA WHY NO
    // WORK

    games = FXCollections.observableArrayList();

    try {
      String fromDbSql = "SELECT TITLE, GENRE, RATING, ESRB FROM VIDEOGAME";

      Class.forName(JDBC_DRIVER); // Database Driver
      Connection conn = DriverManager.getConnection(DB_URL); // Database Url
      // This prepared statement executes my SQL String Command.
      PreparedStatement stmt = conn.prepareStatement(fromDbSql);

      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {

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
        tableGamesTab.setItems(games);
        games.add(new Games(title, realGenre, rating, realEsrb));
      }

      // Setup for the table view
      titleColumn2.setCellValueFactory(new PropertyValueFactory<>("title"));
      genreColumn2.setCellValueFactory(new PropertyValueFactory<>("genre"));
      ratingColumn2.setCellValueFactory(new PropertyValueFactory<>("rating"));
      esrbColumn2.setCellValueFactory(new PropertyValueFactory<>("esrb"));

      stmt.execute();
      conn.close();
      stmt.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
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

        name.setText(username);
      }

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  private String getGame1() {
    String sql = "SELECT GAME1 FROM USERGAMES WHERE USERNAME = ?";

    try {
      Class.forName(JDBC_DRIVER); // Database Driver
      Connection conn = DriverManager.getConnection(DB_URL); // Database Url

      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(1, name.getText());

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        return rs.getString("GAME1");
      }

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

      ps.setString(1, name.getText());

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        return rs.getString("GAME2");
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

      ps.setString(1, name.getText());

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        return rs.getString("GAME3");
      }

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }

    return null;
  }

  private void setUpTableSearchTitle(String title) {
    games = FXCollections.observableArrayList();

    try {
      String fromDbSql = "SELECT TITLE, GENRE, RATING, ESRB FROM VIDEOGAME WHERE TITLE = ?";

      Class.forName(JDBC_DRIVER); // Database Driver
      Connection conn = DriverManager.getConnection(DB_URL); // Database Url
      // This prepared statement executes my SQL String Command.
      PreparedStatement ps = conn.prepareStatement(fromDbSql);

      ps.setString(1, title);

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {

        String titleTb = rs.getString("TITLE");
        int esrbTb = rs.getInt("ESRB");
        int genreTb = rs.getInt("GENRE");
        int ratingTb = rs.getInt("RATING");

        ESRB realEsrb = null;

        if (esrbTb == 0) {
          realEsrb = ESRB.RP;
        }
        if (esrbTb == 1) {
          realEsrb = ESRB.C;
        }
        if (esrbTb == 2) {
          realEsrb = ESRB.E;
        }
        if (esrbTb == 3) {
          realEsrb = ESRB.T;
        }
        if (esrbTb == 4) {
          realEsrb = ESRB.M;
        }
        if (esrbTb == 5) {
          realEsrb = ESRB.A;
        }

        Genre realGenre = null;

        if (genreTb == 0) {
          realGenre = Genre.ACTION;
        }
        if (genreTb == 1) {
          realGenre = Genre.ADVENTURE;
        }
        if (genreTb == 2) {
          realGenre = Genre.RPG;
        }
        if (genreTb == 3) {
          realGenre = Genre.SPORTS;
        }
        if (genreTb == 4) {
          realGenre = Genre.MMO;
        }
        if (genreTb == 5) {
          realGenre = Genre.STRATEGY;
        }
        if (genreTb == 6) {
          realGenre = Genre.SIMULATION;
        }

        // testing in the console
        System.out.println(
            "\nTitle: " + titleTb + "\nGenre: " + realGenre + "\nRating: " + ratingTb);

        // setting the games to the table view
        tableGamesTab.setItems(games);
        games.add(new Games(titleTb, realGenre, ratingTb, realEsrb));
      }

      // Setup for the table view
      titleColumn2.setCellValueFactory(new PropertyValueFactory<>("title"));
      genreColumn2.setCellValueFactory(new PropertyValueFactory<>("genre"));
      ratingColumn2.setCellValueFactory(new PropertyValueFactory<>("rating"));
      esrbColumn2.setCellValueFactory(new PropertyValueFactory<>("esrb"));

      ps.execute();
      conn.close();
      ps.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  private void setUpTableSearchGenre(int genre) {
    games = FXCollections.observableArrayList();

    try {
      String fromDbSql = "SELECT TITLE, GENRE, RATING, ESRB FROM VIDEOGAME WHERE GENRE = ?";

      Class.forName(JDBC_DRIVER); // Database Driver
      Connection conn = DriverManager.getConnection(DB_URL); // Database Url
      // This prepared statement executes my SQL String Command.
      PreparedStatement ps = conn.prepareStatement(fromDbSql);

      ps.setString(1, Integer.toString(genre));

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {

        String titleTb = rs.getString("TITLE");
        int esrbTb = rs.getInt("ESRB");
        int genreTb = rs.getInt("GENRE");
        int ratingTb = rs.getInt("RATING");

        System.out.println();

        ESRB realEsrb = null;

        if (esrbTb == 0) {
          realEsrb = ESRB.RP;
        }
        if (esrbTb == 1) {
          realEsrb = ESRB.C;
        }
        if (esrbTb == 2) {
          realEsrb = ESRB.E;
        }
        if (esrbTb == 3) {
          realEsrb = ESRB.T;
        }
        if (esrbTb == 4) {
          realEsrb = ESRB.M;
        }
        if (esrbTb == 5) {
          realEsrb = ESRB.A;
        }

        Genre realGenre = null;

        if (genreTb == 0) {
          realGenre = Genre.ACTION;
        }
        if (genreTb == 1) {
          realGenre = Genre.ADVENTURE;
        }
        if (genreTb == 2) {
          realGenre = Genre.RPG;
        }
        if (genreTb == 3) {
          realGenre = Genre.SPORTS;
        }
        if (genreTb == 4) {
          realGenre = Genre.MMO;
        }
        if (genreTb == 5) {
          realGenre = Genre.STRATEGY;
        }
        if (genreTb == 6) {
          realGenre = Genre.SIMULATION;
        }

        // testing in the console
        System.out.println(
            "\nTitle: " + titleTb + "\nGenre: " + realGenre + "\nRating: " + ratingTb);

        // setting the games to the table view
        tableGamesTab.setItems(games);
        games.add(new Games(titleTb, realGenre, ratingTb, realEsrb));
      }

      // Setup for the table view
      titleColumn2.setCellValueFactory(new PropertyValueFactory<>("title"));
      genreColumn2.setCellValueFactory(new PropertyValueFactory<>("genre"));
      ratingColumn2.setCellValueFactory(new PropertyValueFactory<>("rating"));
      esrbColumn2.setCellValueFactory(new PropertyValueFactory<>("esrb"));

      ps.execute();
      conn.close();
      ps.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }
}
