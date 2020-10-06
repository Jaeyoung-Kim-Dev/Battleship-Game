package server;

import java.io.*;
import java.net.*;
import java.util.*;

import problemdomain.*;

public class ServerDriver {

	public static void main(String[] args) throws IOException {
		ServerSocket listener = new ServerSocket(1234);

		System.out.println("Waiting for connection port 1234...");

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
