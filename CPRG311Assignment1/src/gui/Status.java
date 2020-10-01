package gui;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Status {
	public Pane statusArea() {

		BorderPane status = new BorderPane();
		status.setPadding(new Insets(50, 50, 15, 15));
		status.setStyle("-fx-background-color: #336699;");

		Text text = new Text("Status Bar is coming soon...");
		
		
		status.setCenter(text);
		
		
		
		return status;
	}
}
