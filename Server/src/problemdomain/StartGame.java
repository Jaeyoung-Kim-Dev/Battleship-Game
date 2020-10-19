package problemdomain;

import java.io.Serializable;


/**
 * Start a game.
 * 
 * @author Jaeyoung Kim
 *
 */
public class StartGame implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private boolean confirmStart;
	private boolean goFirst;
	
	/**
	 * User-defined constructor for attack from enemy.
	 * 
	 * @param confirmStart check if starts game or not
	 * @param goFirst check if a user goes first or not
	 */
	public StartGame(boolean confirmStart, boolean goFirst) {
		this.confirmStart = confirmStart;
		this.goFirst = goFirst;
	}

	/**
	 * check if starts game or not
	 * 
	 * @return starts game or not
	 */
	public boolean isConfirmStart() {
		return confirmStart;
	}

	/**
	 * check if a user goes first or not
	 * 
	 * @return goes first or not
	 */
	public boolean isGoFirst() {
		return goFirst;
	}

	@Override
	public String toString() {
		if (goFirst) return "It's your turn to strike.";
		else return "Waiting for opponent to strike.";		
	}	
}
	