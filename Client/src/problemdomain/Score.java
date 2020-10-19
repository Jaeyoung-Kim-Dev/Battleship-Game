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
public class Score {
	
	private String shipName;	
	private int myShip;	
	private int enemyShip;
	
	/**
	 * User-defined constructor for score
	 * 
	 * @param shipName ship's name
	 * @param myShip my ship's size
	 * @param enemyShip enemy ship's size
	 */
	public Score(String shipName, int myShip, int enemyShip) {
		super();
		this.shipName = shipName;
		this.myShip = myShip;
		this.enemyShip = enemyShip;
	}

	
	public int getMyShip() {
		return myShip;
	}



	public void setMyShip(int myShip) {
		this.myShip = myShip;
	}



	public int getEnemyShip() {
		return enemyShip;
	}



	public void setEnemyShip(int enemyShip) {
		this.enemyShip = enemyShip;
	}


	/**
	 * Gets ship's name
	 * 
	 * @return ship's name
	 */
	public String getShipName() {
		return shipName;
	}}
