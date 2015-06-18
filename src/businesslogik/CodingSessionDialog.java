package businesslogik;

import java.util.Optional;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;

public abstract class CodingSessionDialog {
	
	protected TextField txtTitel;

	/**
	 * Erstellt ein Dialog
	 */
	protected void erstelleStartDialog(){
		Dialog<String> dialog = new Dialog<String>();
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
		        return txtTitel.getText();
		    }
		    return null;
		});
		
		Optional<String> result = dialog.showAndWait();
		if(result.isPresent()){
			System.out.println(result.get());
		}
	}
	
	/**
	 * Erstellt ein Dialog
	 */
	protected void erstelleEndDialog(){
		
	}
}
