package problemdomain;

import java.io.Serializable;

public class AfterAttack implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;

	private int x, y, totalships;

	private boolean strike;

	public AfterAttack(String username, int x, int y, boolean strike, int totalships) {
		this.username = username;
		this.x = x;
		this.y = y;
		this.strike = strike;
		this.totalships = totalships;
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

	public int getTotalships() {
		return totalships;
	}

	public boolean isStrike() {
		return strike;
	}

	@Override
	public String toString() {
		if (strike) return "Strike to " + username + " (" + x +", " + y + ").";
		else return "The missile missed.";
	}
}
