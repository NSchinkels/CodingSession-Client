package main;
import java.io.IOException;

import businesslogik.ControllerMediator;
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
		ControllerMediator.getInstance().neueLoginMaske();
	}

	
	public static void main(String[] args){
		launch(args);
	}
}
