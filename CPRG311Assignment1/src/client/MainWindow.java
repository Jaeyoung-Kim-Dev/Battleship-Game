package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import problemdomain.*;

public class MainWindow {

	private String username;

	private String ip;

	private int port;

	private Socket socket;

	private OutputStream outputStream;

	private ObjectOutputStream objectOutputStream;

	private InputStream inputStream;

	private ObjectInputStream objectInputStream;

	private Button buttonConnect;

	private Button buttonDisconnect;

	private Stage connectForm;

	private ListView<String> messageList;

	int totalships = 0; // to calculate all ships on maps

	private boolean myTurn = false; // user can strike when this value is true.

	private static final int TILE_SIZE = 40;
	private static final int MAP_WIDTH = 400;
	private static final int MAP_HEIGHT = 400;

	private static final int X_TILES = MAP_WIDTH / TILE_SIZE;
	private static final int Y_TILES = MAP_HEIGHT / TILE_SIZE;

	private static final int AIRCRAFTCARRIER = 5;
	private static final int BATTLESHIP = 4;
	private static final int CRUISER = 3;
	private static final int SUBMARINE = 3;
	private static final int DESTROYER = 2;

	private MyTile[][] myGrid;// = new MyTile[X_TILES][Y_TILES];
	private EnemyTile[][] enemyGrid = new EnemyTile[X_TILES][Y_TILES];

	public MainWindow() {
		myGrid = new MyTile[X_TILES][Y_TILES];
	}

	public void setMyGrid(MyTile[][] myGrid) {
		this.myGrid = myGrid;
	}

	public Parent base() {

		BorderPane root = new BorderPane();
		// root.setStyle("-fx-background-color: #336699;");
		root.setTop(titleArea());
		root.setCenter(createGame());
		root.setRight(connectArea());

		return root;
	}

	public Pane titleArea() {

		BorderPane title = new BorderPane();
		title.setPadding(new Insets(20, 15, 40, 15));
		// status.setStyle("-fx-background-color: #336699;");

		Text titleText = new Text("Battleship Game");
		titleText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
		titleText.setFill(Color.WHITE);

		title.setCenter(titleText);

		return title;
	}

	public Pane createGame() {

		BorderPane gameBaord = new BorderPane();

		BorderPane myBoard = new BorderPane();
		myBoard.setTop(myStatusArea());
		myBoard.setCenter(createMyMap());
		
		BorderPane enemyBoard = new BorderPane();
		enemyBoard.setTop(enemyStatusArea());
		enemyBoard.setCenter(createEnemyMap());

		gameBaord.setLeft(myBoard);
		gameBaord.setRight(enemyBoard);

		return gameBaord;
	}

	public Pane connectArea() {

		BorderPane connectionBaord = new BorderPane();

		connectionBaord.setTop(connectionArea());
		connectionBaord.setCenter(chatArea());

		return connectionBaord;
	}

	public Pane chatArea() {

		BorderPane chat = new BorderPane();

		chat.setCenter(chatList());
		chat.setBottom(typeArea());

		return chat;
	}

	public HBox connectionArea() {

		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 15, 15, 15));
		hbox.setSpacing(30);
		// hbox.setStyle("-fx-background-color: #336699;");
		hbox.setAlignment(Pos.CENTER);

		buttonConnect = new Button("Connect");
		buttonConnect.setPrefSize(150, 40);

		buttonDisconnect = new Button("Disconnect");
		buttonDisconnect.setPrefSize(150, 40);
		buttonDisconnect.setDisable(true);
		hbox.getChildren().addAll(buttonConnect, buttonDisconnect);

		buttonConnect.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				connectForm = new Stage();
				connectForm.initModality(Modality.APPLICATION_MODAL);
				connectForm.setTitle("Connect to the server");
				VBox dialogVbox = new VBox(20);
				dialogVbox.getChildren().add(setConnection());
				Scene dialogScene = new Scene(dialogVbox, 400, 260);
				connectForm.setScene(dialogScene);
				connectForm.show();
			}
		});

		buttonDisconnect.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					QuitGame quitGame = new QuitGame(username, true);
					objectOutputStream.writeObject(quitGame);
					disconnect();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		return hbox;
	}

	private GridPane setConnection() {

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text scenetitle = new Text("Let's connect!");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		Label userNameLabel = new Label("User Name:");
		TextField userNameField = new TextField();
		Label ipAddressLabel = new Label("IP address/hostname: ");
		TextField addressField = new TextField("localhost");
		Label portNumberLabel = new Label("Port: ");
		TextField portField = new TextField("1234");

		grid.add(scenetitle, 0, 0, 2, 1);
		grid.add(userNameLabel, 0, 2);
		grid.add(userNameField, 1, 2);
		grid.add(ipAddressLabel, 0, 3);
		grid.add(addressField, 1, 3);
		grid.add(portNumberLabel, 0, 4);
		grid.add(portField, 1, 4);

		grid.setGridLinesVisible(false);

		Button buttonConfirm = new Button("Connect");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(buttonConfirm);
		grid.add(hbBtn, 1, 6);

		final Text actiontarget = new Text();
		grid.add(actiontarget, 1, 6);

		buttonConfirm.setOnAction(event -> {

			username = userNameField.getText();
			ip = addressField.getText();
			port = Integer.parseInt(portField.getText());

			connect(ip, port);

		});
		return grid;
	}

	public void connect(String ip, int port) {
		try {
			socket = new Socket(ip, port);

			this.addMessage("Connected.");

			buttonConnect.setDisable(true);
			buttonDisconnect.setDisable(false);
			connectForm.close();

			outputStream = socket.getOutputStream();
			objectOutputStream = new ObjectOutputStream(outputStream);

			inputStream = socket.getInputStream();
			objectInputStream = new ObjectInputStream(inputStream);

			ServerHandler serverHandler = new ServerHandler(this, socket, objectInputStream);
			Thread thread = new Thread(serverHandler);

			thread.start();
		} catch (IOException e) {
			e.printStackTrace();

			this.addMessage("Unable to connect.");
		}
	}

	public void disconnect() {
		try {
			objectInputStream.close();
			objectOutputStream.close();

			socket.close();

			buttonConnect.setDisable(false);
			buttonDisconnect.setDisable(true);

			addMessage("Disconnected.");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addMessage("Unable to disconnect.");
		}
	}

	public ListView<String> chatList() {

		messageList = new ListView<String>();

		return messageList;
	}

	private Pane typeArea() {

		BorderPane chat = new BorderPane();

		TextField userTextField = new TextField();
		Button buttonSend = new Button("SEND");

		chat.setCenter(userTextField);
		chat.setRight(buttonSend);

		buttonSend.setOnAction(event -> {
			String text = userTextField.getText();

			// Create a Message object.
			Message send = new Message(this.username, text);

			try {
				// Send Message to the server.
				objectOutputStream.writeObject(send);
				objectOutputStream.reset(); // TODO: necessary?

				// If it's sent successfully, clear the text field.
				userTextField.setText("");

				// If it's sent successfully, add message to chat list.
				addMessage(send.toString());
			} catch (IOException e) {
				e.printStackTrace();
				addMessage("Unable to send message.");
			}

		});

		return chat;
	}

	public void addMessage(String message) {
		this.messageList.getItems().add(message);
		// this.messageList.getItems().add("msg added");
	}

	public Pane myStatusArea() {

		// BorderPane status = new BorderPane();
		// status.setPadding(new Insets(50, 50, 15, 15));
		// status.setStyle("-fx-background-color: #336699;");

		BorderPane myBoard = new BorderPane();

		Text myBoardTitle = new Text("My Board");

		myBoardTitle.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
		myBoardTitle.setFill(Color.WHITE);
		myBoard.setPadding(new Insets(15, 15, 15, 15));
		myBoard.setCenter(myBoardTitle);

		return myBoard;
	}
	
	public Pane enemyStatusArea() {

		// BorderPane status = new BorderPane();
		// status.setPadding(new Insets(50, 50, 15, 15));
		// status.setStyle("-fx-background-color: #336699;");

		BorderPane enemyBoard = new BorderPane();

		Text enemyBoardTitle = new Text("Enemy's Board");

		enemyBoardTitle.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
		enemyBoardTitle.setFill(Color.WHITE);
		enemyBoard.setPadding(new Insets(15, 15, 15, 15));
		enemyBoard.setCenter(enemyBoardTitle);

		return enemyBoard;
	}

	// MyMap part begins

	public Pane createMyMap() {
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(15, 15, 15, 15));
		// root.setStyle("-fx-background-color: #336699;");
		root.setPrefSize(MAP_WIDTH + 30, MAP_HEIGHT + 30);

		Pane titles = new Pane();

		for (int y = 0; y < Y_TILES; y++) {
			for (int x = 0; x < X_TILES; x++) {
				MyTile tile = new MyTile(x, y);

				myGrid[x][y] = tile;
				titles.getChildren().add(tile);
			}
		}
		// startGame();
		root.setCenter(titles);

		return root;

	}

	public class MyTile extends StackPane {
		private int x, y;
		private boolean strike;

		private Rectangle border = new Rectangle(TILE_SIZE - 2, TILE_SIZE - 2);
		private Text text = new Text();

		public MyTile(int x, int y) {
			this.x = x;
			this.y = y;

			border.setStroke(Color.LIGHTGRAY);

			getChildren().addAll(border, text);

			setTranslateX(this.x * TILE_SIZE);
			setTranslateY(this.y * TILE_SIZE);
		}

	}

	public void startGame(boolean goFirst) {
		int[] ships = { AIRCRAFTCARRIER, BATTLESHIP, CRUISER, SUBMARINE, DESTROYER };

		// clear maps already created.
		for (int y = 0; y < Y_TILES; y++) {
			for (int x = 0; x < X_TILES; x++) {
				myGrid[x][y].strike = false;
				myGrid[x][y].border.setFill(Color.BLACK);
				enemyGrid[x][y].isOpen = false;
				enemyGrid[x][y].border.setFill(Color.BLACK);
			}
		}

		randomPlaceShip(2); // TODO: delete
		totalships = 2; // to calculate all ships on maps

		for (int ship : ships) {
			// randomPlaceShip(ship); // randomly place ships(array) on my map
			// totalships += ship; // to calculate all ships on maps
		}

		if (goFirst)
			myTurn = true;
	}

	public void randomPlaceShip(int ship) {
		boolean shipPlaced = false;
		boolean vertical = true;
		boolean alreadyExist = false;
		int start_x = 0;
		int start_y = 0;
		int shipSize = 0;

		while (!shipPlaced) {
			shipSize = ship;
			shipPlaced = false;
			alreadyExist = false;
			start_x = randomNumber(0, 10 - shipSize);
			start_y = randomNumber(0, 10 - shipSize);

			// vertical or horizontal for ship position //
			vertical = (((int) (Math.random() * 100)) % 2 == 0) ? true : false;

			// validate the other ship is placed in the tile.
			if (vertical) {
				for (int i = 0; i < shipSize; i++) {
					if (myGrid[start_x + i][start_y].strike)
						alreadyExist = true;
				}
			} else {
				for (int i = 0; i < shipSize; i++) {
					if (myGrid[start_x][start_y + i].strike)
						alreadyExist = true;
				}
			}

			// placed ships in the tile.
			if (!alreadyExist && vertical) {
				for (int i = 0; i < shipSize; i++) {
					myGrid[start_x + i][start_y].strike = true;
					myGrid[start_x + i][start_y].border.setFill(Color.YELLOW);
				}
				shipPlaced = true;
			} else if ((!alreadyExist && !vertical)) {
				for (int i = 0; i < shipSize; i++) {
					myGrid[start_x][start_y + i].strike = true;
					myGrid[start_x][start_y + i].border.setFill(Color.YELLOW);
				}
				shipPlaced = true;
			}
		}
	}

	public int randomNumber(int min, int max) {
		int range = (max - min) + 1;
		return (int) (Math.random() * range) + min;
	}

	// EnemyMap part begins

	public Pane createEnemyMap() {
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(15, 15, 15, 15));
		// root.setStyle("-fx-background-color: #336699;");
		root.setPrefSize(MAP_WIDTH + 30, MAP_HEIGHT + 30);

		Pane titles = new Pane();

		for (int y = 0; y < Y_TILES; y++) {
			for (int x = 0; x < X_TILES; x++) {
				EnemyTile tile = new EnemyTile(x, y);

				enemyGrid[x][y] = tile;
				titles.getChildren().add(tile);
			}
		}

		root.setCenter(titles);

		return root;
	}

	public class EnemyTile extends StackPane {
		private int x, y;
		private boolean strike;
		private boolean isOpen = false;

		private Rectangle border = new Rectangle(TILE_SIZE - 2, TILE_SIZE - 2);
		private Text text = new Text();

		
		
		
		public EnemyTile(int x, int y) {
			this.x = x;
			this.y = y;

			
			border.setStroke(Color.LIGHTGRAY);

			getChildren().addAll(border, text);

			setTranslateX(this.x * TILE_SIZE);
			setTranslateY(this.y * TILE_SIZE);

			setOnMouseClicked(e -> open());
			
			border.setOnMouseEntered( e -> border.setStroke(Color.RED));
			border.setOnMouseExited(e -> border.setStroke(Color.LIGHTGRAY));
		}		
		
		public void open() {
			// return immediately if it's already open or not my turn.
			if (isOpen || !myTurn)
				return;

			try {
				Battle battle = new Battle(username, x, y);
				objectOutputStream.writeObject(battle);				
			} catch (IOException e) {				
				e.printStackTrace();
				addMessage("Unable to attack.");
			}

			if (strike) {
				border.setFill(Color.RED);				
			} else {
				border.setFill(null);
			}

			isOpen = true;
			myTurn = false;
		}

		public void hover() {
			border.setStroke(Color.RED);
		}

	}
	
	public void battle(int x, int y) {
		boolean _strike = myGrid[x][y].strike;

		if (_strike) {
			myGrid[x][y].border.setFill(Color.RED);
			totalships--;
			addMessage("You have " + totalships + " ships left.");
		} else {
			myGrid[x][y].border.setFill(null);
		}

		AfterAttack afterAttack = new AfterAttack(username, x, y, _strike, totalships);
		try {
			objectOutputStream.writeObject(afterAttack);
			// objectOutputStream.reset(); // TODO: necessary?
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (totalships == 0) {
			addMessage("Game Over. You lose.");
			reGame(username, ip, port);
		} else {
			myTurn = true;
			addMessage("It's your turn to stike.");
		}

	}

	public void afterAttack(int x, int y, boolean strike, int totalships) {
		if (strike) {
			enemyGrid[x][y].border.setFill(Color.RED);
			addMessage("Strike!");
		} else {
			enemyGrid[x][y].border.setFill(null);
			addMessage("Missed.");
		}
		if (totalships > 0)
			addMessage("Waiting for opponent to strike.");
		else
			addMessage("Game over. You win!");
	}

	public void reGame(String username, String ip, int port) {
		int _reGame = JOptionPane.showConfirmDialog(null, "Do you want to play a new game?", "Play again?",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (_reGame == 0) {
			try {
				addMessage("Let's play again!");
				boolean stopGame = Boolean.parseBoolean(Integer.toString(_reGame));

				ReGame reGame = new ReGame(username, ip, port, stopGame);

				objectOutputStream.writeObject(reGame);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// objectOutputStream.reset(); // TODO: necessary?
			// addMessage(reGame.toString());
			disconnect();
			connect(ip, port);
		}
	}

	public void enemyDisconnected() {
		int _reGame = JOptionPane.showConfirmDialog(null,
				"The enemy is disconnect the game. Do you want to play again?", "Play again?",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (_reGame == 0) {
			// disconnect();
			connect(ip, port);
		}
	}

}