package gui;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class MainWindow {

	public Parent base() {
		
		BorderPane root = new BorderPane();
		
		root.setCenter(createGame());
		root.setRight(connect());
		
		return root;
	}
	
	public Pane createGame() {
		
		BorderPane gameBaord = new BorderPane();
		
		Status status = new Status();
		
		gameBaord.setTop(status.statusArea());
		gameBaord.setCenter(gameMap());
				
		return gameBaord;
	}
	
	public Pane gameMap() {
		
		BorderPane gameBaord = new BorderPane();
		
		Map myMap = new Map();
		Map oppositeMap = new Map();
		
		gameBaord.setLeft(myMap.createMap());
		gameBaord.setRight(oppositeMap.createMap());
				
		return gameBaord;
	}
	
	public Pane connect() {
		
		BorderPane gameBaord = new BorderPane();
		
		Connection connection = new Connection();
		Chat chat = new Chat();
		
		gameBaord.setTop(connection.connectionArea());
		gameBaord.setCenter(chat.chatArea());
		
		return gameBaord;
	}
}
