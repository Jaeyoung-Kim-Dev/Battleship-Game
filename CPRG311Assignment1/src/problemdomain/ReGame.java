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
	
	private boolean wantReGame;

	public ReGame(String username, String ip, int port, boolean wantReGame) {
		super();
		this.username = username;
		this.ip = ip;
		this.port = port;
		this.wantReGame = wantReGame;
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


	public boolean isWantReGame() {
		return wantReGame;
	}


	@Override
	public String toString() {
		if (wantReGame)
			return username + " wants to play a new game.";
		else
			return username + " wants to exit the game.";
	}
}
