package problemdomain;

import java.io.Serializable;

/**
 * Ask if a user want to play game again 
 * 
 * @author Jaeyoung Kim
 *
 */
public class ReGame implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String username;
	private String ip;
	private int port;
	private boolean stopGame;

	/**
	 * @param username A player's user name.
	 * @param ip Server's IP address.
	 * @param port Server's Port number.
	 * @param stopGame stop the game or not
	 */
	public ReGame(String username, String ip, int port, boolean stopGame) {
		super();
		this.username = username;
		this.ip = ip;
		this.port = port;
		this.stopGame = stopGame;
	}


	/**
	 * Gets the user name
	 * 
	 * @return user name
	 */
	public String getUsername() {
		return username;
	}


	/**
	 * Gets server's IP address.
	 * 
	 * @return IP address
	 */
	public String getIp() {
		return ip;
	}


	/**
	 * Gets server's port number.
	 * 
	 * @return Port number
	 */
	public int getPort() {
		return port;
	}


	/**
	 * Check if the user want to stop the game.
	 * 
	 * @return stop the game or not
	 */
	public boolean isStopGame() {
		return stopGame;
	}


	/**
	 * Print a user wants to play a new game or not.
	 */
	@Override
	public String toString() {
		if (stopGame)
			return username + " wants to exit the game.";
		else
			return username + " wants to play a new game.";
	}
}
