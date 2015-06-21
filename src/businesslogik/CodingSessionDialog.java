package businesslogik;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;

public  class CodingSessionDialog{
	private Dialog<CodingSessionModell> dialog;
	private TextField txtTitel;
	private CodingSessionModell csmod;
	
	public CodingSessionModell getModell(){
		return csmod;
	}

	/**
	 * Erstellt ein Dialog
	 */
	public CodingSessionModell erstelleStartDialog(){
		Dialog<CodingSessionModell> dialog = new Dialog<>();
		dialog.setTitle("CodingSession starten");
		dialog.setHeaderText(null);
		
		txtTitel = new TextField();
		txtTitel.setPromptText("Titel der CodingSession");
		
		Label lblTitel = new Label("Titel: ");
		Label lblSpeichern = new Label("Soll die nachfolgende CodingSession in Zukunft gespeichert werden?");
		
		GridPane grid = new GridPane();
		grid.add(lblTitel, 1, 1);
		grid.add(txtTitel, 2, 1);
		grid.add(lblSpeichern, 1, 2);
		dialog.getDialogPane().setContent(grid);
		
		ButtonType jaButtonType = new ButtonType("Ja", ButtonData.YES);
		ButtonType neinButtonType = new ButtonType("Nein", ButtonData.NO);
		ButtonType abbrechenButtonType = new ButtonType("Abbrechen", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(jaButtonType, neinButtonType, abbrechenButtonType);
		dialog.getDialogPane().getStylesheets().add(getClass().getResource("/view/css/styles.css").toExternalForm());
		
		
		
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == jaButtonType) {
		        return new CodingSessionModell(1,"huhu@web.de", txtTitel.getText(), true, "");
		    }else if(dialogButton==neinButtonType){
		    	return new CodingSessionModell(2,"huhu@web.de", txtTitel.getText(), false, "");
		    }
		    return null;
		});

		return dialog.showAndWait().get();
	}
		
	
	/**
	 * Erstellt ein Dialog
	 */
	public void erstelleEndDialog(){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("CodingSession schliessen");
		alert.setHeaderText(null);
		alert.setContentText("Willst du die CodingSession wirklich schließen?\n"
				            +"Alle nicht gespeicherten Änderungen gehen verloren.");
		
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("/view/css/styles.css").toExternalForm());
		dialogPane.getStyleClass().add("dialog");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			ControllerMediator.getInstance().schliesseCodingSession();
		} else {
		   //Falls der Benutzer den Button Abbrechen klickt, schliesst der Dialog
		}
	}
}
