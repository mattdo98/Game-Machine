package com.peanutbutterdawg.gamerental;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.plaf.nimbus.State;

public class GameRental extends Application {

  private static final String JDBC_DRIVER = "org.h2.Driver"; // Path to my H2 Driver
  private static final String DB_URL = "jdbc:h2:./res/H2"; // Path to my DataBase URL

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent LoginView = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
    primaryStage.setTitle("Video Game Rental System");
    // primaryStage.setScene(new Scene(LoginView, 800, 600));
    Scene scene = new Scene(LoginView, 800, 600);
    scene.getStylesheets().add(getClass().getResource("VGRS.css").toExternalForm());
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  @Override
  public void stop() {
    String sql = "UPDATE USER SET ISACTIVEUSER = FALSE WHERE ISACTIVEUSER = TRUE";

    try {
      Class.forName(JDBC_DRIVER); // Database Driver
      Connection conn = DriverManager.getConnection(DB_URL); // Database Url

      Statement stmt = conn.createStatement();

      stmt.executeUpdate(sql);

      stmt.close();
      conn.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
