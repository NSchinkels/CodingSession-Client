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

public class ProfilController extends CodingSessionDialog{
	
	Profilbearbeitung bearbeitung;
	FreundeSucheController suche;
	
	@FXML
	public void abmeldenGeklickt(ActionEvent event){
		ControllerMediator.getInstance().beenden();
		Platform.exit();
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
	public void sucheFreunde(KeyEvent event){
		if(event.getCode() == KeyCode.ENTER){
			ControllerMediator.getInstance().neueFreundeSuche();	
		}
	}
}

