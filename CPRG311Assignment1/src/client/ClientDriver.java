package client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Includes main method for Battleship game.
 * 
 * @author Jaeyoung Kim
 * @version 1.0, Sep 30, 2020
 */
public class ClientDriver extends Application  {

	public static void main(String[] args) {		
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		MainWindow mainWindow= new MainWindow();
		
		Scene scene = new Scene(mainWindow.base());
		scene.getStylesheets().add(MainWindow.class.getResource("ClientDriver.css").toExternalForm());
		primaryStage.setTitle("Battleship Game");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
