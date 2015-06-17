package businesslogik;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;

public class CommunityFeed extends CodingSessionDialoge{
	
	private Beitrag[] beitraege;
	private static int anzahl;
	
	private TextField txtTitel;

	public CommunityFeed() {
		// hier werden noch die Sachen vom Server gezogen
	}
	
	@FXML
	public void communityFeedAktualisierenGeklickt(ActionEvent event){
		System.out.println("Test");
	}
	
	@FXML
	public void abmeldenGeklickt(ActionEvent event){
		System.out.println("Test");
	}
	
	@FXML
	public void codingSessionStartenGeklickt(ActionEvent event){
		erstelleStartDialog();
	}

	public void addBeitrag(Beitrag beitrag) {
		//aktuelle Anzahl vom Server ziehen
		beitraege[anzahl++] = beitrag;
		//hier wird dann gespeichert
	}
	public void refresh(){
		//Neue Beitraege vom Server bekommen
	}
	public static int getAnzahl(){
		//zuerst aktualisieren vom server
		return anzahl;
	}
	public Beitrag[] getBeitraege() {
		return beitraege;
	}
}
