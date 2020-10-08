/**
 * 
 */
package problemdomain;

/**
 * User ship
 * 
 * @author kornk
 *
 */
public class Ship {
	
	private String name;	
	private String initial;	
	private int size;

	/**
	 * User-defined constructor for ship
	 * 
	 * @param name ship's name
	 * @param initial ship's initial
	 * @param size ship's size
	 */
	public Ship(String name, String initial, int size) {
		this.name = name;
		this.initial = initial;
		this.size = size;
	}

	/**
	 * Gets ship's name
	 * 
	 * @return ship's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets ship's initial
	 * 
	 * @return ship's initial
	 */
	public String getInitial() {
		return initial;
	}
	
	/**
	 * Gets ship's size
	 * 
	 * @return ship's size
	 */
	public int getSize() {
		return size;
	}	
}
