package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class ServerGUI {

	private ListView<String> messageList;

	public ServerGUI() {
		
	}
	
	public Parent base() {

		BorderPane root = new BorderPane();
		// root.setStyle("-fx-background-color: #336699;");
		root.setCenter(messageArea());
		root.setBottom(exitArea());
	
		return root;
	}

	private Pane messageArea() {
		BorderPane message = new BorderPane();
		
		message.setCenter(messageViewer());
		return message;
	}

	public ListView<String> messageViewer() {

		messageList = new ListView<String>();

		return messageList;
	}
	
	private HBox exitArea() {

		HBox hbox = new HBox();
		
		Button buttonExit = new Button("Exit");

		HBox.setHgrow(buttonExit, Priority.ALWAYS);
					
		buttonExit.setMaxWidth(Double.MAX_VALUE);
		
		hbox.getChildren().add(buttonExit);
		
		buttonExit.setOnAction(event -> {
			System.exit(0);
		});

		return hbox;
	}
	
/*
	private Pane exitArea() {

		BorderPane exit = new BorderPane();
		
		Button buttonExit = new Button("Exit");

		exit.(buttonExit);
			
		
		buttonExit.setOnAction(event -> {
			
		});

		return exit;
	}
*/
	public void addMessage(String message) {
		this.messageList.getItems().add(message);
		// this.messageList.getItems().add("msg added");
	}

}