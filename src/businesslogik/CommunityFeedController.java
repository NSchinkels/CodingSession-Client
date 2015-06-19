package businesslogik;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class CommunityFeedController{
	
	private Beitrag[] beitraege;
	private static int anzahl;

	public CommunityFeedController() {
		// hier werden noch die Sachen vom Server gezogen
	}
	
	@FXML
	public void communityFeedAktualisierenGeklickt(ActionEvent event){
		System.out.println("Test");
	}
	
	@FXML
	public void abmeldenGeklickt(ActionEvent event){
		Platform.exit();
	}
	
	@FXML
	public void codingSessionStartenGeklickt(ActionEvent event){
		ControllerMediator.getInstance().neueCodingSession();
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
