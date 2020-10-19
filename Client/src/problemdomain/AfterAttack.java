package problemdomain;

import java.io.Serializable;

/**
 * send a result of enemy's attack(battle) to the enemy
 * 
 * @author Jaeyoung Kim
 *
 */
public class AfterAttack implements Serializable {

	private static final long serialVersionUID = 1L;
	private String username;
	private int x, y, totalships;
	private String strikeInitial;
	private boolean strike;

	/**
	 * User-defined constructor for attack from enemy
	 * 
	 * @param username a user name
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param strike check if it's strike or miss
	 * @param strikeInitial 
	 * @param totalships total ships count to calculate all ships on maps.
	 */
	public AfterAttack(String username, int x, int y, boolean strike, String strikeInitial, int totalships) {
		this.username = username;
		this.x = x;
		this.y = y;
		this.strike = strike;
		this.strikeInitial = strikeInitial;
		this.totalships = totalships;
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
	 * Gets strike ship initial
	 * 
	 * @return initial 
	 */
	public String getStrikeInitial() {
		return strikeInitial;
	}

	/**
	 * Gets x-coordinate
	 * 
	 * @return x-coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets y-coordinate
	 * 
	 * @return y-coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * Gets the total number of ships
	 * 
	 * @return total number of ships
	 */
	public int getTotalships() {
		return totalships;
	}

	/**
	 * check if it's strike or miss
	 * 
	 * @return strike or miss.
	 */
	public boolean isStrike() {
		return strike;
	}

	/**
	 * Print user name and coordinate of the strike or missed.
	 */
	@Override
	public String toString() {
		if (strike) return "Strike to " + username + " (" + x +", " + y + ").";
		else return "The missile missed.";
	}
}
