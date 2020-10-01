package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import gui.MainWindow;

/**
 * Includes main method for Battleship game.
 * 
 * @author Jaeyoung Kim
 * @version 1.0, Sep 30, 2020
 */
public class AppDriver extends Application  {

	public static void main(String[] args) {		
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		MainWindow mainWindow= new MainWindow();
		
		Scene scene = new Scene(mainWindow.base(), 2000, 1000);

		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
