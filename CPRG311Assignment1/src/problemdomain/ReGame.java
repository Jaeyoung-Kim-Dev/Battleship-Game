package problemdomain;

import java.io.Serializable;

public class ReGame implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;

	private String ip;

	private int port;
	
	private boolean stopGame;

	public ReGame(String username, String ip, int port, boolean stopGame) {
		super();
		this.username = username;
		this.ip = ip;
		this.port = port;
		this.stopGame = stopGame;
	}


	public String getUsername() {
		return username;
	}


	public String getIp() {
		return ip;
	}


	public int getPort() {
		return port;
	}


	public boolean isStopGame() {
		return stopGame;
	}


	@Override
	public String toString() {
		if (stopGame)
			return username + " wants to exit the game.";
		else
			return username + " wants to play a new game.";
	}
}
