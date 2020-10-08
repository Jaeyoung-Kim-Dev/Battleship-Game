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
import javafx.scene.control.Tooltip;
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

	/**
	 * Ships for each player.
	 */
	private ArrayList<Ship> ships = new ArrayList<Ship>();
	
	/**
	 * A player's user name.
	 */
	private String username;

	/**
	 * Server's IP address.
	 */
	private String ip;

	/**
	 * Server's Port number.
	 */
	private int port;
	
	/**
	 * Server's Socket.
	 */
	private Socket socket;

	/**
	 * Output Stream.
	 */
	private OutputStream outputStream;

	/**
	 * OBeject Output Stream.
	 */
	private ObjectOutputStream objectOutputStream;

	/**
	 * Input Stream.
	 */
	private InputStream inputStream;

	/**
	 * OBeject Input Stream.
	 */
	private ObjectInputStream objectInputStream;

	/**
	 * Button to open the connect form.
	 */
	private Button buttonConnect;

	/**
	 * Button to disconnect the network.
	 */
	private Button buttonDisconnect;

	/**
	 * Form to connect the network.
	 */
	private Stage connectForm;

	/**
	 * List View to display messages.
	 */	
	private ListView<String> messageList;

	/**
	 * Total ships count to calculate all ships on maps.
	 */
	int totalships = 0; 

	/**
	 * Toggle that User can strike when this value is true.
	 */	
	private boolean myTurn = false; // 

	/**
	 * a single size of the tile. the width and height each.
	 */	
	private static final int TILE_SIZE = 40;
	
	/**
	 * a single width of the map.
	 */	
	private static final int MAP_WIDTH = 400;
	
	/**
	 * a single height of the map.
	 */	
	private static final int MAP_HEIGHT = 400;

	/**
	 * the number of horizontal tiles.
	 */	
	private static final int X_TILES = MAP_WIDTH / TILE_SIZE;
	
	/**
	 * the number of vertical tiles.
	 */	
	private static final int Y_TILES = MAP_HEIGHT / TILE_SIZE;

	/**
	 * a user's tile.
	 */	
	private MyTile[][] myGrid;
	
	/**
	 * a enemy's tile.
	 */	
	private EnemyTile[][] enemyGrid;
	
	/**
	 * Constructor for MainWindow 
	 */
	public MainWindow() {		
		myGrid = new MyTile[X_TILES][Y_TILES];
		enemyGrid = new EnemyTile[X_TILES][Y_TILES];		
		ships.add(new Ship("Aircraft carrier", "A", 5));
		ships.add(new Ship("Battleship", "B", 4));
		ships.add(new Ship("Cruiser", "C", 3));
		ships.add(new Ship("Submarine", "S", 3));
		ships.add(new Ship("Destroyer", "D", 2));
	}

	/**
	 * Sets myGrid
	 * 
	 * @param myGrid myGrid
	 */
	public void setMyGrid(MyTile[][] myGrid) {
		this.myGrid = myGrid;
	}

	/**
	 * Parent Root Base
	 * 
	 * @return Root Base
	 */
	public Parent base() {

		BorderPane root = new BorderPane();
		// root.setStyle("-fx-background-color: #336699;");
		root.setTop(titleArea());
		root.setCenter(createGame());
		root.setRight(connectArea());

		return root;
	}

	/**
	 * Pane of title area
	 * 
	 * @return title pane
	 */
	public Pane titleArea() {

		BorderPane title = new BorderPane();
		title.setPadding(new Insets(20, 15, 40, 15));
		// status.setStyle("-fx-background-color: #336699;");

		Text titleText = new Text("BATTLESHIP GAME");
		titleText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
		titleText.setFill(Color.WHITE);

		title.setCenter(titleText);

		return title;
	}

	/**
	 * Pane of creating game
	 * 
	 * @return game pane
	 */
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

	/**
	 * Pane of connect area
	 * 
	 * @return connect pane
	 */
	public Pane connectArea() {

		BorderPane connectionBaord = new BorderPane();

		connectionBaord.setTop(connectionArea());
		connectionBaord.setCenter(chatArea());

		return connectionBaord;
	}

	/**
	 * Pane of chat area
	 * 
	 * @return chat pane
	 */
	public Pane chatArea() {

		BorderPane chat = new BorderPane();

		chat.setCenter(chatList());
		chat.setBottom(typeArea());

		return chat;
	}

	/**
	 * HBox of connection area
	 * 
	 * @return connection HBox
	 */
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

	/**
	 * Modal GridPane of set connection
	 * 
	 * @return connection modal grid pane
	 */
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
		TextField addressField = new TextField(""); //localhost
		Label portNumberLabel = new Label("Port: ");
		TextField portField = new TextField(""); //1234

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

	/**
	 * Connect the network.
	 * 
	 * @param ip Server's IP address
	 * @param port Server's Port number.
	 */
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

	/**
	 * Disconnect the network.
	 * 
	 */
	public void disconnect() {
		
		try {
				objectInputStream.close();
				objectOutputStream.close();

				socket.close();

				buttonConnect.setDisable(false);
				buttonDisconnect.setDisable(true);

				addMessage("Disconnected.");
			
		} catch (IOException e) {			
			e.printStackTrace();
			addMessage("Unable to disconnect.");			
		} catch (NullPointerException e) {
			System.exit(0);
		}
	}

	/**
	 * Display message list
	 * 
	 * @return Message list View
	 */
	public ListView<String> chatList() {

		messageList = new ListView<String>();
		messageList.setStyle("-fx-border-color: GREY");
		
		return messageList;
	}

	/**
	 * User type in the text area and send it.
	 * 
	 * @return message input area
	 */
	private Pane typeArea() {

		BorderPane chat = new BorderPane();		
		TextField userTextField = new TextField();
		Button buttonSend = new Button("SEND");

		chat.setCenter(userTextField);
		chat.setRight(buttonSend);
		chat.setStyle("-fx-border-color: GREY");

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

	/**
	 * add a message to the list. 
	 * 
	 * @param message a message from the user.
	 */
	public void addMessage(String message) {
		this.messageList.getItems().add(message);
	}

	/**
	 * My status area.
	 * 
	 * @return My status area pane
	 */
	public Pane myStatusArea() {
		BorderPane myBoard = new BorderPane();

		Text myBoardTitle = new Text("My Board");

		myBoardTitle.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
		myBoardTitle.setFill(Color.WHITE);
		myBoard.setPadding(new Insets(15, 15, 15, 15));
		myBoard.setCenter(myBoardTitle);

		return myBoard;
	}

	/**
	 * Enemy's status area.
	 * 
	 * @return Enemy status area pane
	 */
	public Pane enemyStatusArea() {
		BorderPane enemyBoard = new BorderPane();

		Text enemyBoardTitle = new Text("Enemy's Board");

		enemyBoardTitle.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
		enemyBoardTitle.setFill(Color.WHITE);
		enemyBoard.setPadding(new Insets(15, 15, 15, 15));
		enemyBoard.setCenter(enemyBoardTitle);

		return enemyBoard;
	}

	
	/**
	 * Create my map on the left of the game pane.
	 * 
	 * @return my map pane.
	 */
	public Pane createMyMap() {
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(15, 15, 15, 15));
		root.setPrefSize(MAP_WIDTH + 30, MAP_HEIGHT + 30);

		Pane titles = new Pane();

		for (int y = 0; y < Y_TILES; y++) {
			for (int x = 0; x < X_TILES; x++) {
				MyTile tile = new MyTile(x, y, TILE_SIZE);

				myGrid[x][y] = tile;
				titles.getChildren().add(tile);
			}
		}
		root.setCenter(titles);

		return root;

	}

	/**
	 * cleanup the previous map and start a new game.
	 * 
	 * @param goFirst which player goes first
	 */
	public void startGame(boolean goFirst) {
	 	//int[] ships = { AIRCRAFTCARRIER, BATTLESHIP, CRUISER, SUBMARINE, DESTROYER };
		
		// clear maps already created.
		for (int y = 0; y < Y_TILES; y++) {
			for (int x = 0; x < X_TILES; x++) {
				myGrid[x][y].setStrike(false);
				myGrid[x][y].getRectangle().setFill(Color.BLACK);
				myGrid[x][y].getInitial().setText(null);;
				//myGrid[x][y].getInitial().setFill(Color.RED);
				enemyGrid[x][y].isOpen = false;
				enemyGrid[x][y].border.setFill(Color.BLACK);
				enemyGrid[x][y].initial.setText(null);;
			}
		}
		//ArrayList<Ship> ships = new ArrayList<Ship>();
		
		for (Ship ship : ships) {
			randomPlaceShip(ship); // randomly place ships on my map
			totalships += ship.getSize(); // to calculate all ships on maps
		}
		
		/*
		randomPlaceShip(new Ship("Aircraft carrier", "A", 5));
		randomPlaceShip(new Ship("Battleship", "B", 4));
		randomPlaceShip(new Ship("Cruiser", "C", 3));
		randomPlaceShip(new Ship("Submarine", "S", 3));
		randomPlaceShip(new Ship("Destroyer", "D", 2));
*/
		//randomPlaceShip(2); // TODO: delete
		//totalships = 2; // to calculate all ships on maps

		/*
		for (int ship : ships) {
			randomPlaceShip(ship); // randomly place ships(array) on my map
			totalships += ship; // to calculate all ships on maps
		}
		 */
		if (goFirst)
			myTurn = true;
	}

	/**
	 * Randomly place my ship one by one.
	 * 
	 * @param ship a size of the ship.
	 */
	public void randomPlaceShip(Ship ship) {
		boolean shipPlaced = false;
		boolean vertical = true;
		boolean alreadyExist = false;
		int start_x = 0;
		int start_y = 0;
		int shipSize = 0;

		while (!shipPlaced) {
			shipSize = ship.getSize();			
			shipPlaced = false;
			alreadyExist = false;
			Text initial = new Text();
			start_x = randomNumber(0, 10 - shipSize);
			start_y = randomNumber(0, 10 - shipSize);

			// vertical or horizontal for ship position //
			vertical = (((int) (Math.random() * 100)) % 2 == 0) ? true : false;

			// validate the other ship is NOT placed in the tile.
			if (vertical) {
				for (int i = 0; i < shipSize; i++) {
					if (myGrid[start_x + i][start_y].isStrike())
						alreadyExist = true;
				}
			} else {
				for (int i = 0; i < shipSize; i++) {
					if (myGrid[start_x][start_y + i].isStrike())
						alreadyExist = true;
				}
			}

			// placed ships in the tile.
			if (!alreadyExist && vertical) {
				for (int i = 0; i < shipSize; i++) {
					myGrid[start_x + i][start_y].setStrike(true);
					myGrid[start_x + i][start_y].getRectangle().setFill(Color.YELLOW);					
					myGrid[start_x + i][start_y].getInitial().setText(ship.getInitial());;
					myGrid[start_x + i][start_y].getInitial().setFill(Color.RED);
					myGrid[start_x + i][start_y].getInitial().setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
					//myGrid[start_x + i][start_y].getInitial().setVisible(true);
				}
				shipPlaced = true;
				//totalships += ship.getSize();
			} else if ((!alreadyExist && !vertical)) {
				for (int i = 0; i < shipSize; i++) {
					myGrid[start_x][start_y + i].setStrike(true);
					myGrid[start_x][start_y + i].getRectangle().setFill(Color.YELLOW);
					myGrid[start_x][start_y + i].getInitial().setText(ship.getInitial());;
					myGrid[start_x][start_y + i].getInitial().setFill(Color.RED);
					myGrid[start_x][start_y + i].getInitial().setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
					//myGrid[start_x][start_y + i].getInitial().setVisible(true);
				}
				shipPlaced = true;
				//totalships += ship.getSize();
			}
		}
	}

	/**
	 * make a random number is a range
	 * 
	 * @param min minimum number
	 * @param max maximum number
	 * @return random number
	 */
	public int randomNumber(int min, int max) {
		int range = (max - min) + 1;
		return (int) (Math.random() * range) + min;
	}



	/**
	 * Create enemy map on the right of the game pane.
	 * 
	 * @return enemy map pane.
	 */
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

	/**
	 * 
	 * @author kornk
	 *
	 */
	public class EnemyTile extends StackPane {
		private int x, y;
		private boolean strike;
		private boolean isOpen = false;

		private Rectangle border = new Rectangle(TILE_SIZE - 2, TILE_SIZE - 2);
		private Text initial = new Text();		

		public EnemyTile(int x, int y) {
			this.x = x;
			this.y = y;

			border.setStroke(Color.LIGHTGRAY);

			getChildren().addAll(border, initial);

			setTranslateX(this.x * TILE_SIZE);
			setTranslateY(this.y * TILE_SIZE);

			setOnMouseClicked(e -> open());

			border.setOnMouseEntered(e -> border.setStroke(Color.RED));
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
	}

	/**
	 * a user attack the enemy
	 * 
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public void battle(int x, int y) {
		boolean _strike = myGrid[x][y].isStrike();

		if (_strike) {
			myGrid[x][y].getRectangle().setFill(Color.RED);
			myGrid[x][y].getInitial().setFill(Color.WHITE);
			totalships--;
			addMessage("You have " + totalships + " ships left.");
		} else {
			myGrid[x][y].getRectangle().setFill(null);
		}
		String strikeInitial = myGrid[x][y].getInitial().getText();
		AfterAttack afterAttack = new AfterAttack(username, x, y, _strike, strikeInitial, totalships);
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
		
	/**
	 * send a result of enemy's attack(battle) to the enemy
	 * 
	 * @param x x-coordinate 
	 * @param y y-coordinate
	 * @param strike check if it's strike
	 * @param totalships count the number of ships left
	 */
	public void afterAttack(String username, int x, int y, boolean strike, String inital, int totalships) {
		if (strike) {
			enemyGrid[x][y].border.setFill(Color.RED);
			enemyGrid[x][y].initial.setText(inital);
			enemyGrid[x][y].initial.setFill(Color.WHITE);
			enemyGrid[x][y].initial.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
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

	/**
	 * Play a game again
	 * 
	 * @param username a user name
	 * @param ip Server's IP address.
	 * @param port Server's port number.
	 */
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

	/**
	 * when the enemy disconnect the game.
	 */
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