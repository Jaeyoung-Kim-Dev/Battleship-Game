package problemdomain;

import java.io.Serializable;

/**
 * 
 * a user attack the enemy
 * 
 * @author Jaeyoung Kim
 *
 */
public class Battle implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String username;	
	private int x,y;

	/**
	 * User-defined constructor for attack the enemy
	 */
	public Battle() {		
	}
	
	/**
	 * User-defined constructor for attack the enemy
	 * 
	 * @param username a user name
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public Battle(String username, int x, int y) {
		this.username = username;
		this.x = x;
		this.y = y;
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
	 * Print user name and coordinate of the missed missile.
	 */
	@Override
	public String toString() {
		return username + " fired a missile at (" + x +", " + y + ").";
	}

}
