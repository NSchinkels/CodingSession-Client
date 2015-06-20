package businesslogik;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CommunityFeedController{
	
	private Beitrag[] beitraege;
	private static int anzahl;

	public CommunityFeedController() {
		// hier werden noch die Sachen vom Server gezogen
	}
	
	@FXML
	public void abmeldenGeklickt(ActionEvent event){
		Platform.exit();
	}
	
	@FXML
	public void codingSessionStartenGeklickt(ActionEvent event){
		ControllerMediator.getInstance().neueCodingSession();
	}
	
	@FXML
	public void communityFeedAktualisierenGeklickt(ActionEvent event){
		System.out.println("Test");
	}
	
	@FXML
	public void sucheFreunde(KeyEvent event){
		if(event.getCode() == KeyCode.ENTER){
			ControllerMediator.getInstance().neueFreundeSuche();	
		}	
	}

	public void addBeitrag(CodingSessionModell csmod) {
		//aktuelle Anzahl vom Server ziehen
		//beitraege[anzahl++] = beitrag;
		//hier wird dann gespeichert
	}
	public static int getAnzahl(){
		//zuerst aktualisieren vom server
		return anzahl;
	}
	public Beitrag[] getBeitraege() {
		return beitraege;
	}
}
