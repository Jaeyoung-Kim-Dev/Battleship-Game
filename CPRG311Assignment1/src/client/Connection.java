package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Connection {
	
	private Socket socket;
	
	private Chat chat;
	
	private OutputStream outputStream;
	
	private ObjectOutputStream objectOutputStream;

	private InputStream inputStream;
	
	private ObjectInputStream objectInputStream;
	
	
	public Connection(Chat chat, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
		this.chat = chat;
		this.objectOutputStream = objectOutputStream;
		this.objectInputStream = objectInputStream;
	}
	
	public HBox connectionArea() {

		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 15, 15, 15));
		hbox.setSpacing(30);
		hbox.setStyle("-fx-background-color: #336699;");
		hbox.setAlignment(Pos.CENTER);

		Button buttonConnect = new Button("Connect");
		buttonConnect.setPrefSize(150, 40);

		Button buttonDisconnect = new Button("Disconnect");
		buttonDisconnect.setPrefSize(150, 40);
		hbox.getChildren().addAll(buttonConnect, buttonDisconnect);
		
		buttonConnect.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				final Stage dialog = new Stage();
				dialog.initModality(Modality.APPLICATION_MODAL);
				dialog.setTitle("Connect to the server");				
				VBox dialogVbox = new VBox(20);				
				dialogVbox.getChildren().add(setConnection());
				Scene dialogScene = new Scene(dialogVbox, 400, 260);
				dialog.setScene(dialogScene);
				dialog.show();
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
		TextField addressField = new TextField();
		Label portNumberLabel = new Label("Port: ");
		TextField portField = new TextField();

		grid.add(scenetitle, 0, 0, 2, 1);
		grid.add(userNameLabel, 0, 2);
		grid.add(userNameField, 1, 2);
		grid.add(ipAddressLabel, 0, 3);
		grid.add(addressField, 1, 3);
		grid.add(portNumberLabel, 0, 4);
		grid.add(portField, 1, 4);

		grid.setGridLinesVisible(false);

		Button buttonConnect = new Button("Connect");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(buttonConnect);
		grid.add(hbBtn, 1, 6);

		final Text actiontarget = new Text();
		grid.add(actiontarget, 1, 6);		
		
				
		buttonConnect.setOnAction(event -> {
		
			String username = userNameField.getText();
			String ip = addressField.getText();
			int port = Integer.parseInt(portField.getText());
			
			try {
				socket = new Socket(ip, port);
				
				chat.addMessage("Connected!");				
				
				outputStream = socket.getOutputStream();
				objectOutputStream = new ObjectOutputStream(outputStream);
				
				inputStream = socket.getInputStream();
				objectInputStream = new ObjectInputStream(inputStream);
				
				ServerHandler serverHandler = new ServerHandler(chat, socket, objectInputStream);
				Thread thread = new Thread(serverHandler);				
				
				thread.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				chat.addMessage("Unable to connect");
			}

		});
		return grid;
	}
	
}
