package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class Chat {
	public Pane chatArea() {

		BorderPane chat = new BorderPane();

		chat.setCenter(chatList());
		chat.setBottom(typeArea());
		
		return chat;
	}

	public ListView<String> chatList() {
		ListView<String> messageList;

		ObservableList<String> colleges = FXCollections.observableArrayList("Penn State", "Drexel", "Widener",
				"Shippensburg", "Bloomsburg", "Penn Tech", "Lockhaven", "Kutztown");

		messageList = new ListView<String>();
		
		for(int i=0; i < 100; i++) {
			messageList.getItems().add(Integer.toString(i));	
		}
				

		return messageList;
	}

	private Pane typeArea() {

		BorderPane chat = new BorderPane();

		TextField userTextField = new TextField();
		Button buttonConnect = new Button("SEND");

		chat.setCenter(userTextField);
		chat.setRight(buttonConnect);

		return chat;
	}
}
