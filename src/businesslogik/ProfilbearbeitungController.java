package businesslogik;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ProfilbearbeitungController{

	@FXML
	public void abmeldenGeklickt(ActionEvent event){
		new CodingSessionDialog().erstelleAbmeldeDialog();
	}
	
	@FXML
	public void aenderungenSpeichernGeklickt(ActionEvent event){
		
	}
	
	@FXML
	public void zurueckZumProfilGeklickt(ActionEvent event){
		ControllerMediator.getInstance().neuesProfil();
	}
	
}
