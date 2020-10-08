package problemdomain;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * A user tile
 * 
 * @author Jaeyoung Kim
 *
 */
public class MyTile extends StackPane {
	
	private int x, y;
	private boolean strike;
	private int tileSize;

	private Rectangle rectangle;
	private Text initial;
	
	/**
	 * User-defined constructor for a user tile
	 * 
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param tileSize size of tile
	 */
	public MyTile(int x, int y, int tileSize) {
		this.x = x;
		this.y = y;
		this.tileSize = tileSize;

		rectangle = new Rectangle(tileSize - 2, tileSize - 2);		
		rectangle.setStroke(Color.LIGHTGRAY);

		initial = new Text();
		
		getChildren().addAll(rectangle, initial);

		setTranslateX(this.x * tileSize);
		setTranslateY(this.y * tileSize);
	}
		
    /**
     * Gets initial of the ship
     * 
     * @return initial Ship's initial
     */
	public Text getInitial() {
		return initial;
	}

	/**
	 * Check if it's strike or miss
	 * 
	 * @return strike strike or miss.
	 */
	public boolean isStrike() {
		return strike;
	}

	/**
	 * Sets strike or miss
	 * 
	 * @param strike strike or miss.
	 */
	public void setStrike(boolean strike) {
		this.strike = strike;
	}

	/**
	 * Gets my tile(rectangle)
	 * 
	 * @return rectangle my tile
	 */
	public Rectangle getRectangle() {
		return rectangle;
	}
	
	/**
	 * Gets size of my tile
	 * 
	 * @return tileSize size of my tile
	 */
	public int getTileSize() {
		return tileSize;
	}	
}