package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			StackPane root = new StackPane();
			Scene scene = new Scene(root, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Battleship");
			primaryStage.setScene(scene);
			primaryStage.show();

			Circle cir = new Circle(200, 200, 100);
			cir.setFill(Color.CORAL);

			GridPane grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(25, 25, 25, 25));

			Text scenetitle = new Text("Let's play Battleship");
			scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			Label userName = new Label("User Name:");
			TextField userTextField = new TextField();
			Label pw = new Label("Password: ");
			PasswordField pwBox = new PasswordField();

			grid.add(scenetitle, 0, 0, 2, 1);
			grid.add(userName, 0, 1);
			grid.add(userTextField, 1, 1);
			grid.add(pw, 0, 2);
			grid.add(pwBox, 1, 2);

			grid.setGridLinesVisible(false); // todo: remove it before deploy

			Button bttn[] = new Button[5];
			HBox hbBtn = new HBox(10);
			hbBtn.setAlignment(Pos.TOP_LEFT);
			// hbBtn.setAlignment(Pos.BOTTOM_RIGHT); hbBtn.setAlignment(Pos.TOP_LEFT);

			for (int i = 0; i < bttn.length; i++) {				
				hbBtn.getChildren().add(bttn[i]);
				bttn[i].setText("X");
				bttn[i].setOnAction(event -> {
				});
			}
			grid.add(hbBtn, 1, 4);

			
			/*
			 * Button btn = new Button(); Button btn2 = new Button(); Button btn3 = new
			 * Button(); HBox hbBtn = new HBox(10); // hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
			 * hbBtn.setAlignment(Pos.TOP_LEFT); hbBtn.getChildren().add(btn);
			 * hbBtn.getChildren().add(btn2); hbBtn.getChildren().add(btn3); grid.add(hbBtn,
			 * 1, 4);
			 * 
			 * final Text actiontarget = new Text(); grid.add(actiontarget, 1, 6);
			 * 
			 * btn.setOnAction(event -> { actiontarget.setFill(Color.FIREBRICK);
			 * actiontarget.setAccessibleText("Sign in button pressed"); });
			 * 
			 * btn.setText("Login"); btn2.setText("Login2");
			 */

			// root.getChildren().add(cir);
			root.getChildren().add(grid);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
