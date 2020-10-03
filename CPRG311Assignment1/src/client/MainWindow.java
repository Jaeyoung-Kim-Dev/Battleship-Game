package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class MainWindow {

	private Chat chat;
	
	private ObjectOutputStream objectOutputStream;

	private ObjectInputStream objectInputStream;
	
	private Connection connection;
	
	public MainWindow() {
		this.connection = new Connection(); //objectOutputStream, objectInputStream
		this.chat = new Chat(objectOutputStream, objectInputStream);
		this.objectOutputStream = chat.getObjectOutputStream();
		
	}
	
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
		
		BorderPane connectionBaord = new BorderPane();
		
		connectionBaord.setTop(connection.connectionArea());
		connectionBaord.setCenter(chat.chatArea());
		
		return connectionBaord;
	}
}
