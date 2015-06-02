package main;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

   /**
    * Automatischer Startpunkt der Applikation. Das erste Fenster (login.fxml) wird geladen und dem User angezeigt.
	* Exception wird geworfen, falls login.fxml nicht geladen werden kann.
	*/
	@Override
	public void start(Stage primaryStage){
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/hauptfenster.fxml"));
			Parent root = (Parent) loader.load();
			Scene scene = new Scene(root);		
			primaryStage.setTitle("Login");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args){
		launch(args);
	}
}
