package problemdomain;

import java.io.Serializable;

/**
 * Quit the game
 * 
 * @author Jaeyoung Kim
 *
 */
public class QuitGame implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String username;
	private boolean quitGame;

	/**
	 * User-defined constructor for quit the game
	 * 
	 * @param username a user name
	 * @param quitGame check if the user want to quit
	 */
	public QuitGame(String username, boolean quitGame) {
		this.username = username;
		this.quitGame = quitGame;
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
	 * Check if the user want to quit
	 * 
	 * @return quit or not
	 */
	public boolean isQuitGame() {
		return quitGame;
	}


	/**
	 * print a user quit the game.
	 */
	@Override
	public String toString() {		
		return username + " quit the game.";			
	}
}
