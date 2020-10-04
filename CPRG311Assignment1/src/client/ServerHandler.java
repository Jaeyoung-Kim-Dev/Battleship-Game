/**
 * 
 */
package client;

import problemdomain.Battle;
import problemdomain.Message;

import java.io.*;
import java.net.*;

/**
 * @author nhamnett
 *
 */
public class ServerHandler implements Runnable {
	private MainWindow mainWindow;

	private Socket server;
	private ObjectInputStream ois;

	public ServerHandler(MainWindow mainWindow, Socket server, ObjectInputStream ois) {
		this.mainWindow = mainWindow;
		this.server = server;
		this.ois = ois;
	}

	@Override
	public void run() {
		while (!this.server.isClosed()) {
			try {
				Object receive = (Object) this.ois.readObject();
				if (receive instanceof Message) {
					receive = (Message) receive;
					this.mainWindow.addMessage(receive.toString());
				} else if (receive instanceof Battle) {
					Battle battle = (Battle) receive;
					this.mainWindow.addMessage(battle.toString());
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
