package client;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Map {

	private static final int X_TILES = 10; // The number of horizontal tiles
	private static final int Y_TILES = 10; // The number of vertical tiles

	private static final int TILE_SIZE = 40; // Single Tile pixel size
	private static final int MAP_WIDTH = X_TILES * TILE_SIZE;
	private static final int MAP_HEIGHT = Y_TILES * TILE_SIZE;

	private Tile[][] grid = new Tile[X_TILES][Y_TILES];

	public Pane createMap() {
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(15, 15, 15, 15));
		root.setStyle("-fx-background-color: #336699;");
		root.setPrefSize(MAP_WIDTH + 30, MAP_HEIGHT + 30);

		Pane titles = new Pane();

		for (int y = 0; y < Y_TILES; y++) {
			for (int x = 0; x < X_TILES; x++) {
				Tile tile = new Tile(x, y);

				grid[x][y] = tile;
				titles.getChildren().add(tile);
			}
		}

		// todo: to be deleted. just testing strikes
		for (int x = 0; x < 5; x++) {	//Aircraft carrier occupies 5 squares
			grid[x][0].strike = true;
		}
		for (int x = 0; x < 4; x++) {	//Battleship occupies 4 squares
			grid[x][1].strike = true;
		}
		for (int x = 0; x < 3; x++) {	//Cruiser occupies 3 squares
			grid[x][2].strike = true;
		}
		for (int x = 0; x < 3; x++) {	//Submarine occupies 3 squares
			grid[x][3].strike = true;
		}
		for (int x = 0; x < 2; x++) {	//Destroyer occupies 2 squares
			grid[x][4].strike = true;
		}
		
		root.setCenter(titles);

		return root;
	}

	public class Tile extends StackPane {
		private int x, y;
		private boolean strike;
		private boolean isOpen = false;

		private Rectangle border = new Rectangle(TILE_SIZE - 2, TILE_SIZE - 2);
		private Text text = new Text();

		public Tile(int x, int y) {
			this.x = x;
			this.y = y;

			border.setStroke(Color.LIGHTGRAY);

			text.setFont(Font.font(18));
			text.setText(strike ? "O" : "");
			text.setVisible(false);

			getChildren().addAll(border, text);

			setTranslateX(this.x * TILE_SIZE);
			setTranslateY(this.y * TILE_SIZE);

			setOnMouseClicked(e -> open());
		}

		public void open() {
			if (isOpen)
				return;

			if (strike) {
				border.setFill(Color.RED);
				return;
			}

			isOpen = true;
			text.setVisible(true);
			border.setFill(null);
		}
	}

}
