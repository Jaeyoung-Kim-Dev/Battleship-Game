package problemdomain;

import java.io.Serializable;

public class QuitGame implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;

	private boolean quitGame;

	public QuitGame(String username, boolean wantReGame) {
		this.username = username;
		this.quitGame = wantReGame;
	}


	public String getUsername() {
		return username;
	}

	public boolean isQuitGame() {
		return quitGame;
	}


	@Override
	public String toString() {		
		return username + " quit the game.";			
	}
}
