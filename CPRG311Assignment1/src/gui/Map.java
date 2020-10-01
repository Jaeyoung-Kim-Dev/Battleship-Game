package gui;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Map {

	private static final int TILE_SIZE = 40;
	private static final int MAP_WIDTH = 400;
	private static final int MAP_HEIGHT = 400;

	private static final int X_TILES = MAP_WIDTH / TILE_SIZE;
	private static final int Y_TILES = MAP_HEIGHT / TILE_SIZE;

	private Tile[][] grid = new Tile[X_TILES][Y_TILES];

	public Pane createMap() {
		Pane root = new Pane();
		root.setPrefSize(MAP_WIDTH, MAP_HEIGHT);

		for (int y = 0; y < Y_TILES; y++) {
			for (int x = 0; x < X_TILES; x++) {
				Tile tile = new Tile(x, y, Math.random() < 0.2);

				grid[x][y] = tile;
				root.getChildren().add(tile);
			}
		}

		for (int y = 0; y < Y_TILES; y++) {
			for (int x = 0; x < X_TILES; x++) {
				Tile tile = grid[x][y];

				if (tile.hasBomb)
					continue;

				long bombs = getNeighbors(tile).stream().filter(t -> t.hasBomb).count();

				if (bombs > 0)
					tile.text.setText(String.valueOf(bombs));
			}
		}

		return root;
	}

	public List<Tile> getNeighbors(Tile tile) {
		List<Tile> neighbors = new ArrayList<>();

		int[] points = new int[] { -1, -1, -1, 0, -1, 1, 0, -1, 0, 1, 1, -1, 1, 0, 1, 1 };

		for (int i = 0; i < points.length; i++) {
			int dx = points[i];
			int dy = points[++i];

			int newX = tile.x + dx;
			int newY = tile.y + dy;

			if (newX >= 0 && newX < X_TILES && newY >= 0 && newY < Y_TILES) {
				neighbors.add(grid[newX][newY]);
			}
		}

		return neighbors;
	}

	public class Tile extends StackPane {
		private int x, y;
		private boolean hasBomb;
		private boolean isOpen = false;

		private Rectangle border = new Rectangle(TILE_SIZE - 2, TILE_SIZE - 2);
		private Text text = new Text();

		public Tile(int x, int y, boolean hasBomb) {
			this.x = x;
			this.y = y;
			this.hasBomb = hasBomb;

			border.setStroke(Color.LIGHTGRAY);

			text.setFont(Font.font(18));
			text.setText(hasBomb ? "X" : "");
			text.setVisible(false);

			getChildren().addAll(border, text);

			setTranslateX(x * TILE_SIZE);
			setTranslateY(y * TILE_SIZE);

			setOnMouseClicked(e -> open());
		}

		public void open() {
			if (isOpen)
				return;

			if (hasBomb) {
				System.out.println("Game Over");
				return;
			}

			isOpen = true;
			text.setVisible(true);
			border.setFill(null);

			if (text.getText().isEmpty()) {
				getNeighbors(this).forEach(Tile::open);
			}
		}
	}

}
