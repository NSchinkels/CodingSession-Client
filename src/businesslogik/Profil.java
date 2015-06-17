package businesslogik;

import java.io.Serializable;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class Profil extends CodingSessionDialoge implements Serializable{
	
	private TextField txtTitel;
	
	@FXML
	public void profilBearbeitenGeklickt(ActionEvent event){
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
}

