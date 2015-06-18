package businesslogik;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Profilbearbeitung extends CodingSessionDialog{

	@FXML
	public void abmeldenGeklickt(ActionEvent event){
		Platform.exit();
	}
	
	@FXML
	public void codingSessionStartenGeklickt(ActionEvent event){
		erstelleStartDialog();
	}
}
