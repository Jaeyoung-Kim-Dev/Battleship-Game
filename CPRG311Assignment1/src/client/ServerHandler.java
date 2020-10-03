/**
 * 
 */
package client;

import problemdomain.Message;

import java.io.*;
import java.net.*;

/**
 * @author nhamnett
 *
 */
public class ServerHandler implements Runnable {
	private MainWindow mainWindow;
	
	private Chat chat;
	
	private Socket server;
	private ObjectInputStream ois;
	
	public ServerHandler(Chat chat, Socket server, ObjectInputStream ois) {
		this.chat = chat;
		this.server = server;
		this.ois = ois;
	}


	@Override
	public void run() {
		while (!this.server.isClosed()) {
			try {
				Message receive = (Message) this.ois.readObject();
				this.chat.addMessage(receive.toString());
				//this.mainWindow.addMessage("Sending a message from serverhandler");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
