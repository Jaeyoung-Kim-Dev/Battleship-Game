package problemdomain;

import java.io.Serializable;

public class Battle implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	
	private int x,y;

	public Battle() {		
	}
	
	public Battle(String username, int x, int y) {
		this.username = username;
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "Battle [username=" + username + ", x=" + x + ", y=" + y + "]";
	}	
	
}
