package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import problemdomain.Message;

public class Chat {
	
	private ListView<String> messageList;
	private String username;
	private ObjectOutputStream objectOutputStream;
	
	private ObjectInputStream objectInputStream;
	
	public Chat (ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream, String username) {
		this.objectOutputStream = objectOutputStream;
		this.objectInputStream = objectInputStream;
		this.username = username;
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
			//Message send = new Message("noname", text);
			
			try {
				// Send Message to the server.
				this.objectOutputStream.writeObject(send);
				
				// If it's sent successfully, clear the text field.
				userTextField.setText("");
				
				// If it's sent successfully, add message to chat list.
				this.addMessage(send.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				this.addMessage("Unable to send message.");
			}
		});
		
		return chat;
	}
	

	public void addMessage(String message) {
		this.messageList.getItems().add(message);
		//this.messageList.getItems().add("msg added");
	}
}
