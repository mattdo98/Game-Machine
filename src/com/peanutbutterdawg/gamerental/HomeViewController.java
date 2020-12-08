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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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

  @FXML private Button resetTableButton;

  @FXML private Button admin;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    checkForAdmin();
    // this initializes the filter combo boxes with hardcoded data
    initializeFilterCBO();
    // this initializes the table view with hardcoded data
    initializeGamesTable();
    // this initializes the name label that is at the top of each page
    initializeNameLabel();
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
  void getSearch(ActionEvent event) {
    String searchBoxText = searchBar.getText();
    Genre genreText = getGenre.getValue();
    ESRB esrbText = getESRB.getValue();

    if (!searchBar.getText().isEmpty()
        && getGenre.getSelectionModel().isEmpty()
        && getESRB.getSelectionModel().isEmpty()) {

      try {
        PreparedStatement ps = getPreparedStatement("SELECT TITLE FROM VIDEOGAME WHERE TITLE = ?");
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
    } else if (searchBar.getText().isEmpty()
        && !getGenre.getSelectionModel().isEmpty()
        && getESRB.getSelectionModel().isEmpty()) {
      try {
        PreparedStatement ps = getPreparedStatement("SELECT GENRE FROM VIDEOGAME WHERE GENRE = ?");
        ps.setString(1, genreText.toString());
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
          int genre = rs.getInt("GENRE");

          System.out.println(genreText.getString());
          System.out.println(genre);

          setUpTableSearchGenre(genre);
        }
      } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
      }
    } else if (searchBar.getText().isEmpty()
        && getGenre.getSelectionModel().isEmpty()
        && !getESRB.getSelectionModel().isEmpty()) {

      try {
        PreparedStatement ps = getPreparedStatement("SELECT ESRB FROM VIDEOGAME WHERE ESRB = ?");

        ps.setString(1, esrbText.toString());

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
          int esrb = rs.getInt("ESRB");

          System.out.println(esrbText.getString());
          setUpTableSearchESRB(esrb);
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
      try {
        PreparedStatement ps = getPreparedStatement("UPDATE USERGAMES SET GAME1 = ?, GAMECOUNT = ? WHERE USERNAME = ?");

        ps.setString(1, games.get(0).getTitle());
        ps.setString(2, "1");
        ps.setString(3, name.getText());
        ps.executeUpdate();

        addedGame.setText("Game Added to Library!");
      } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
      }
    } else if (game2 == null) {

      try {
        PreparedStatement ps = getPreparedStatement("UPDATE USERGAMES SET GAME2 = ?, GAMECOUNT = ? WHERE USERNAME = ?");

        ps.setString(1, games.get(0).getTitle());
        ps.setString(2, "2");
        ps.setString(3, name.getText());
        ps.executeUpdate();

        addedGame.setText("Game Added to Library!");
      } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
      }
    } else if (game3 == null) {
      try {
        PreparedStatement ps = getPreparedStatement("UPDATE USERGAMES SET GAME3 = ?, GAMECOUNT = ? WHERE USERNAME = ?");

        ps.setString(1, games.get(0).getTitle());
        ps.setString(2, "3");
        ps.setString(3, name.getText());
        ps.executeUpdate();

        addedGame.setText("Game Added to Library!");
      } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
      }
    } else {
      addedGame.setText("Out of rentals!");
    }
    initializeGamesTable();
  }

  @FXML
  void resetTableView(ActionEvent event) {
    initializeGamesTable();
  }

  private void sortTable(ResultSet rs) throws SQLException {
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
  }

  private void initializeGamesTable() {
    games = FXCollections.observableArrayList();

    try {
      PreparedStatement stmt = getPreparedStatement("SELECT TITLE, GENRE, RATING, ESRB FROM VIDEOGAME");
      ResultSet rs = stmt.executeQuery();

      sortTable(rs);

      stmt.execute();
      stmt.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

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

  private String getGame1() {
    try {
      PreparedStatement ps = getPreparedStatement("SELECT GAME1 FROM USERGAMES WHERE USERNAME = ?");
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
    try {
      PreparedStatement ps = getPreparedStatement("SELECT GAME2 FROM USERGAMES WHERE USERNAME = ?");
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
    try {
      PreparedStatement ps = getPreparedStatement("SELECT GAME3 FROM USERGAMES WHERE USERNAME = ?");
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
      PreparedStatement ps = getPreparedStatement("SELECT TITLE, GENRE, RATING, ESRB FROM VIDEOGAME WHERE TITLE = ?");
      ps.setString(1, title);
      ResultSet rs = ps.executeQuery();

      sortTable(rs);

      ps.execute();
      ps.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  private void setUpTableSearchGenre(int genre) {
    games = FXCollections.observableArrayList();

    try {
      PreparedStatement ps = getPreparedStatement("SELECT TITLE, GENRE, RATING, ESRB FROM " +
          "VIDEOGAME WHERE GENRE =" + " ?");

      ps.setInt(1, genre);
      ResultSet rs = ps.executeQuery();

      sortTable(rs);

      ps.execute();
      ps.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  private void setUpTableSearchESRB(int esrb) {
    games = FXCollections.observableArrayList();

    try {

      PreparedStatement ps = getPreparedStatement("SELECT TITLE, GENRE, RATING, ESRB FROM " +
          "VIDEOGAME WHERE ESRB = ?");

      ps.setInt(1, esrb);
      ResultSet rs = ps.executeQuery();

      sortTable(rs);

      ps.execute();
      ps.close();
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
}
