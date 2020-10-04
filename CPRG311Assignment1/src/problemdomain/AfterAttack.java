package problemdomain;

import java.io.Serializable;

public class AfterAttack implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	
	private int x,y;
	
	private boolean strike;

	public AfterAttack(String username, int x, int y, boolean strike) {
		this.username = username;
		this.x = x;
		this.y = y;
		this.strike = strike;
	}

	public String getUsername() {
		return username;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isStrike() {
		return strike;
	}

	@Override
	public String toString() {
		return "AfterAttack [username=" + username + ", x=" + x + ", y=" + y + ", strike=" + strike + "]";
	}
	
	
	
}
	