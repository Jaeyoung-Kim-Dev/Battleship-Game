package problemdomain;

import java.io.Serializable;

public class ReGame implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	
	private boolean wantReGame;
	
	
	public ReGame(String username, boolean wantReGame) {
		this.username = username;
		this.wantReGame = wantReGame;
	}

	public String getUsername() {
		return username;
	}
	
	public boolean isWantReGame() {
		return wantReGame;
	}

	@Override
	public String toString() {
		if (wantReGame) return username + " wants to play a new game.";
		else return username + " wants to exit the game.";		
	}	
}
	