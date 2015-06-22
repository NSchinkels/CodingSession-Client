package businesslogik;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ProfilController{
	
	ProfilbearbeitungController bearbeitung;
	FreundeSucheController suche;
	
	@FXML
	public void abmeldenGeklickt(ActionEvent event){
		new CodingSessionDialog().erstelleAbmeldeDialog();
	}
	
	@FXML 
	public void codingSessionStartenGeklickt(ActionEvent event){
		ControllerMediator.getInstance().neueCodingSession();
	}
	
	@FXML
	public void profilBearbeitenGeklickt(ActionEvent event){
		ControllerMediator.getInstance().neueProfilbearbeitung();
	}
	
	@FXML
	public void txtSucheFreundeGeklickt(KeyEvent event){
		if(event.getCode() == KeyCode.ENTER){
			ControllerMediator.getInstance().neueFreundeSuche();	
		}
	}
}

