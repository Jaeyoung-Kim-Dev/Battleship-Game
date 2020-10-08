package server;

import java.io.*;
import java.net.*;
import java.util.*;

import javafx.application.Application;
import javafx.stage.Stage;
import problemdomain.*;

/**
 * Includes main method for Battleship game server.
 * 
 * @author Jaeyoung Kim
 * @version 1.0, October 7, 2020
 *
 */
public class ServerDriver extends Application  {

	public static void main(String[] args) {		
		launch(args);		
	}

	/**
	 * start method for JavaFX
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		ServerGUI serverGUI = new ServerGUI();
		serverGUI.display();
		
		ServerSocket listener = new ServerSocket(1234);

		serverGUI.addMessage("Listening on port 1234.");
		serverGUI.addMessage("Waiting for connection...");
		
		ArrayList<ClientConnection> connections = new ArrayList<>();
		
		while (listener.isBound()) {
			try {
				Socket client = listener.accept();

				serverGUI.addMessage("Client connected.");

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
					ClientHandler clientHandler = new ClientHandler(connection1, connection2, serverGUI);
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
