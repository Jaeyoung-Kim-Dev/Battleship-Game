package server;

import java.io.*;
import java.net.*;
import java.util.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import problemdomain.*;

public class ServerDriver extends Application  {

	public static void main(String[] args) {		
		launch(args);		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		ServerGUI serverGUI= new ServerGUI();
		
		Scene scene = new Scene(serverGUI.base());
		//scene.getStylesheets().add(ServerGUI.class.getResource("ClientDriver.css").toExternalForm());
		primaryStage.setTitle("Battleship Game Server");
		primaryStage.setScene(scene);
		primaryStage.show();
				
		ServerSocket listener = new ServerSocket(1234);

		System.out.println("Listening on port 1234.");
		System.out.println("Waiting for connection...");

		ArrayList<ClientConnection> connections = new ArrayList<>();
		
		while (listener.isBound()) {
			try {
				Socket client = listener.accept();

				System.out.println("Client connected.");

				InputStream inputStream = client.getInputStream();
				ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

				OutputStream outputStream = client.getOutputStream();
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

				ClientConnection connection = new ClientConnection(client, objectInputStream, objectOutputStream);

				connections.add(connection);

				if (connections.size() % 2 == 0) {
					ClientConnection connection1 = connections.get(0);
					ClientConnection connection2 = connections.get(1);

					// Spin up a thread to handle connections
					ClientHandler clientHandler = new ClientHandler(connection1, connection2);
					Thread thread = new Thread(clientHandler);
					
					thread.start();
					
					// Remove connections array list
					connections.remove(connection1);
					connections.remove(connection2);
				}

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		listener.close();
		
	}

}
