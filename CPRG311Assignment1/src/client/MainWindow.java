package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import problemdomain.Battle;
import problemdomain.Message;

public class MainWindow {

	// private Chat chat;

	private Socket socket;

	/// private Chat chat;

	private String username;

	private OutputStream outputStream;

	private ObjectOutputStream objectOutputStream;

	private InputStream inputStream;

	private ObjectInputStream objectInputStream;

	private Button buttonConnect;

	private Button buttonDisconnect;
	// private Connection connection;

	private Stage connectForm;

	private ListView<String> messageList;

	private static final int TILE_SIZE = 40;
	private static final int MAP_WIDTH = 400;
	private static final int MAP_HEIGHT = 400;

	private static final int X_TILES = MAP_WIDTH / TILE_SIZE;
	private static final int Y_TILES = MAP_HEIGHT / TILE_SIZE;

	public MyTile[][] myGrid = new MyTile[X_TILES][Y_TILES];
	private EnemyTile[][] enemyGrid = new EnemyTile[X_TILES][Y_TILES];

	public MainWindow() {
		// this.connection = new Connection(); //objectOutputStream, objectInputStream
		// this.chat = new Chat(objectOutputStream, objectInputStream);
		// this.objectOutputStream = chat.getObjectOutputStream();

	}

	public Parent base() {

		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: #336699;");
		root.setCenter(createGame());
		root.setRight(connect());

		return root;
	}

	public Pane createGame() {

		BorderPane gameBaord = new BorderPane();

		Status status = new Status();

		gameBaord.setTop(status.statusArea());
		gameBaord.setCenter(gameMap());

		return gameBaord;
	}

	public Pane gameMap() {

		BorderPane gameBaord = new BorderPane();

		// Map myMap = new Map();
		// Map oppositeMap = new Map();

		gameBaord.setLeft(createMyMap());
		gameBaord.setRight(createEnemyMap());

		return gameBaord;
	}

	public Pane connect() {

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

	public HBox connectionArea() {

		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 15, 15, 15));
		hbox.setSpacing(30);
		hbox.setStyle("-fx-background-color: #336699;");
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
			String ip = addressField.getText();
			int port = Integer.parseInt(portField.getText());

			try {
				socket = new Socket(ip, port);

				this.addMessage("Connected.");
				// System.out.println("Connected!"); //TODO: delete

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
				// TODO Auto-generated catch block
				e.printStackTrace();

				this.addMessage("Unable to connect.");
				// System.out.println("Unable to connect!"); //TODO: delete
			}

		});
		return grid;
	}

	// MyMap part begins

	public Pane createMyMap() {
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(15, 15, 15, 15));
		root.setStyle("-fx-background-color: #336699;");
		root.setPrefSize(MAP_WIDTH + 30, MAP_HEIGHT + 30);

		Pane titles = new Pane();

		for (int y = 0; y < Y_TILES; y++) {
			for (int x = 0; x < X_TILES; x++) {
				MyTile tile = new MyTile(x, y);

				myGrid[x][y] = tile;
				titles.getChildren().add(tile);
			}
		}

		// todo: to be deleted. just testing strikes
		for (int x = 0; x < 5; x++) { // Aircraft carrier occupies 5 squares
			myGrid[x][0].strike = true;
		}
		for (int x = 0; x < 4; x++) { // Battleship occupies 4 squares
			myGrid[x][1].strike = true;
		}
		for (int x = 0; x < 3; x++) { // Cruiser occupies 3 squares
			myGrid[x][2].strike = true;
		}
		for (int x = 0; x < 3; x++) { // Submarine occupies 3 squares
			myGrid[x][3].strike = true;
		}
		for (int x = 0; x < 2; x++) { // Destroyer occupies 2 squares
			myGrid[x][4].strike = true;
		}

		root.setCenter(titles);

		return root;
	}

	public class MyTile extends StackPane {
		private int x, y;
		private boolean strike;
		private boolean isOpen = false;

		private Rectangle border = new Rectangle(TILE_SIZE - 2, TILE_SIZE - 2);
		private Text text = new Text();

		public MyTile(int x, int y) {
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
			/*
			 * if (isOpen) return;
			 * 
			 * if (strike) { border.setFill(Color.RED);
			 * 
			 * // Create a Message object. Battle battle = new Battle(x,y);
			 * 
			 * try { // Send Message to the server. objectOutputStream.writeObject(battle);
			 * 
			 * // If it's sent successfully, add message to chat list.
			 * addMessage(battle.toString()); } catch (IOException e) { // TODO
			 * Auto-generated catch block e.printStackTrace();
			 * 
			 * addMessage("Unable to send message."); } return; }
			 * 
			 * isOpen = true; text.setVisible(true); border.setFill(null);
			 */
		}
	}

	// EnemyMap part begins

	public Pane createEnemyMap() {
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(15, 15, 15, 15));
		root.setStyle("-fx-background-color: #336699;");
		root.setPrefSize(MAP_WIDTH + 30, MAP_HEIGHT + 30);

		Pane titles = new Pane();

		for (int y = 0; y < Y_TILES; y++) {
			for (int x = 0; x < X_TILES; x++) {
				EnemyTile tile = new EnemyTile(x, y);

				enemyGrid[x][y] = tile;
				titles.getChildren().add(tile);
			}
		}

		// todo: to be deleted. just testing strikes
		for (int x = 0; x < 5; x++) { // Aircraft carrier occupies 5 squares
			enemyGrid[x][0].strike = true;
		}
		for (int x = 0; x < 4; x++) { // Battleship occupies 4 squares
			enemyGrid[x][1].strike = true;
		}
		for (int x = 0; x < 3; x++) { // Cruiser occupies 3 squares
			enemyGrid[x][2].strike = true;
		}
		for (int x = 0; x < 3; x++) { // Submarine occupies 3 squares
			enemyGrid[x][3].strike = true;
		}
		for (int x = 0; x < 2; x++) { // Destroyer occupies 2 squares
			enemyGrid[x][4].strike = true;
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

				// Create a Message object.
				Battle battle = new Battle(username, x, y);

				try {
					// Send Message to the server.
					objectOutputStream.writeObject(battle);
					objectOutputStream.reset(); // TODO: necessary?
					// If it's sent successfully, add message to chat list.
					addMessage(battle.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

					addMessage("Unable to send message.");
				}
				return;
			}

			isOpen = true;
			text.setVisible(true);
			border.setFill(null);
		}
	}
}