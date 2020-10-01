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
import gui.Map;

public class MainWindow extends Application {

	private static final int TILE_SIZE = 40;
	private static final int MAP_WIDTH = 400;
	private static final int MAP_HEIGHT = 400;

	private static final int X_TILES = MAP_WIDTH / TILE_SIZE;
	private static final int Y_TILES = MAP_HEIGHT / TILE_SIZE;

	private Tile[][] grid = new Tile[X_TILES][Y_TILES];
	private Scene scene;

	public Parent base() {
		
		BorderPane root = new BorderPane();
		
		root.setRight(createGame());
		root.setCenter(createGame());
		
		//root.setTop(addHBox());
		//root.setRight(createContent());
		//root.setCenter(addGridPane());
		//root.setBottom(addFooter());
		
		return root;
	}
	
	public Pane createGame() {
		
		BorderPane gameBaord = new BorderPane();
		
		Map map = new Map();
		
		gameBaord.setTop(createContent());
		gameBaord.setBottom(map.createMap());
				
		return gameBaord;
	}
	
	public Pane connect() {
		
		BorderPane gameBaord = new BorderPane();
		
		gameBaord.setTop(createContent());
		gameBaord.setBottom(createContent());
		
		//root.setTop(addHBox());
		//root.setRight(createContent());
		//root.setCenter(addGridPane());
		//root.setBottom(addFooter());
		
		return gameBaord;
	}
	
	
	public Parent createContent() {
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

		// ttt
		// tXt
		// ttt

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
				scene.setRoot(createContent());
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

	@Override
	public void start(Stage primaryStage) {

		BorderPane root = new BorderPane();
		root.setTop(addHBox());
		root.setRight(createContent());
		root.setCenter(addGridPane());
		root.setBottom(addFooter());

		scene = new Scene(root, 2000, 1000);

		primaryStage.setTitle("UI Layouts");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public HBox addHBox() {

		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10);
		hbox.setStyle("-fx-background-color: #336699;");

		Button buttonCurrent = new Button("Home");
		buttonCurrent.setPrefSize(100, 20);

		Button buttonProjected = new Button("Exit");
		buttonProjected.setPrefSize(100, 20);
		hbox.getChildren().addAll(buttonCurrent, buttonProjected);

		return hbox;
	}

	public HBox addFooter() {
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10);
		hbox.setStyle("-fx-background-color: #336699");
		Text copyright = new Text("Copyright 2016");
		copyright.setFill(Color.WHITE);
		copyright.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		hbox.getChildren().add(copyright);
		return hbox;
	}

	public VBox addVBox() {
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10));
		vbox.setSpacing(8);

		Text title = new Text("Departments");
		title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		vbox.getChildren().add(title);

		Button options[] = new Button[] {};

		ArrayList<Button> op = new ArrayList<Button>();

		for (int i = 0; i < 100; i++) {
			op.add(new Button());
		}

		Button options2[] = new Button[] { new Button("Operations"), new Button("Customer Service"),
				new Button("Marketing"), new Button("Sales") };

		for (int i = 0; i < op.size(); i++) {
			VBox.setMargin(op.get(i), new Insets(0, 0, 0, 8));
			vbox.getChildren().add(op.get(i));
		}

		return vbox;
	}

	public GridPane addGridPane() {
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(0, 10, 0, 10));

		// Category in column 2, row 1
		Text category = new Text("Operations:");
		category.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(category, 1, 0);

		// Title in column 3, row 1
		Text chartTitle = new Text("Customer Service");
		chartTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(chartTitle, 2, 0);

		ImageView imageHouse = new ImageView(new Image(MainWindow.class.getResourceAsStream("home.gif")));
		grid.add(imageHouse, 0, 0, 1, 2);

		return grid;
	}

	/**
	 * @param args the command line arguments
	 */

}
