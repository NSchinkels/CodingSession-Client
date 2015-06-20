package businesslogik;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ProfilbearbeitungController extends CodingSessionDialog{

	@FXML
	public void abmeldenGeklickt(ActionEvent event){
		Platform.exit();
	}
	
	@FXML
	public void aenderungenSpeichern(ActionEvent event){
		
	}
	
	@FXML
	public void codingSessionStartenGeklickt(ActionEvent event){
		erstelleStartDialog();
	}
	
	@FXML
	public void zurueckZumProfilGeklickt(ActionEvent event){
		ControllerMediator.getInstance().zurueckZumProfil();
	}
	
}
