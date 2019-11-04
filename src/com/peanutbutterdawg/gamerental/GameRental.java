package com.peanutbutterdawg.gamerental;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameRental extends Application {

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

  public static void main(String[] args) {
    launch(args);
  }
}
