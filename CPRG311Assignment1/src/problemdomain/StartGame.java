package problemdomain;

import java.io.Serializable;

public class StartGame implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean confirmStart;
	
	private boolean goFirst;
	
	public StartGame(boolean confirmStart, boolean goFirst) {
		this.confirmStart = confirmStart;
		this.goFirst = goFirst;
	}

	public boolean isConfirmStart() {
		return confirmStart;
	}

	public boolean isGoFirst() {
		return goFirst;
	}

	@Override
	public String toString() {
		if (goFirst) return "It's your turn to strike.";
		else return "Waiting for opponent to strike.";		
	}	
}
	