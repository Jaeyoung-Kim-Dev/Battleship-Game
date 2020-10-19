/**
 * 
 */
package server;

import java.io.IOException;
import java.net.SocketException;

import problemdomain.*;


/**
 * Start InputOutputHandler threads for both users.
 * 
 * @author Jaeyoung Kim
 *
 */
public class ClientHandler implements Runnable {
	private ClientConnection connection1;
	private ClientConnection connection2;
	private ServerGUI serverGUI;

	/**
	 * User-defined constructor for ClientHandler.
	 * 
	 * @param connection1 connection for the first user.
	 * @param connection2 connection for the second user.
	 * @param serverGUI GUI for server
	 */
	public ClientHandler(ClientConnection connection1, ClientConnection connection2, ServerGUI serverGUI) {
		this.connection1 = connection1;
		this.connection2 = connection2;
		this.serverGUI = serverGUI;
	}

	/**
	 * run method for thread
	 */
	@Override
	public void run() {

		serverGUI.addMessage("Waiting for messages...");

		InputOutputHandler ioHandler1 = new InputOutputHandler(this.connection1, this.connection2, this.serverGUI);
		Thread thread1 = new Thread(ioHandler1);

		thread1.start();

		InputOutputHandler ioHandler2 = new InputOutputHandler(this.connection2, this.connection1, this.serverGUI);
		Thread thread2 = new Thread(ioHandler2);

		thread2.start();

		// randomly pick which user goes first.
		boolean goFirst = (((int) (Math.random() * 100)) % 2 == 0) ? true : false;

		StartGame firstPlayer = new StartGame(true, goFirst);
		StartGame secondPlayer = new StartGame(true, !goFirst);
		try {
			connection1.getOos().writeObject(firstPlayer);
			connection2.getOos().writeObject(secondPlayer);
		} catch (IOException e1) {
			e1.printStackTrace();			
		}

		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();			
		} 
	}

}
