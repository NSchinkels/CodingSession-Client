package businesslogik;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class ProfilController extends CodingSessionDialog{
	
	Profilbearbeitung bearbeitung;
	
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
		try{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/profilbearbeitung.fxml"));
			bearbeitung = new Profilbearbeitung();
			loader.setController(bearbeitung);
			Parent root = (Parent) loader.load();
			Stage stage = new Stage();
			Scene scene = new Scene(root);		
			stage.setScene(scene);
			stage.show();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	@FXML
	public void sucheFreunde(KeyEvent event){
		
	}
}

