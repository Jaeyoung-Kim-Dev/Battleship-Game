package problemdomain;

import java.io.Serializable;

/**
 * 
 * aiming
 * 
 * @author Jaeyoung Kim
 *
 */
public class Aim implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private boolean aim;	
	private int x,y;

	/**
	 * User-defined constructor for aiming
	 */
	public Aim() {		
	}
	
	/**
	 * User-defined constructor for attack the enemy
	 * 
	 * @param aim check it aim or not
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public Aim(boolean aim, int x, int y) {
		this.aim = aim;
		this.x = x;
		this.y = y;
	}

	/**
	 * Gets the user name
	 * 
	 * @return user name
	 */
	public boolean isAim() {
		return aim;
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
}
