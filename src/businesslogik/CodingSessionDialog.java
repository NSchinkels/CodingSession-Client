package businesslogik;

import java.util.Optional;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;

public  class CodingSessionDialog extends Dialog<CodingSessionModell> {
	
	protected TextField txtTitel;

	/**
	 * Erstellt ein Dialog
	 */
	protected void erstelleStartDialog(){
		this.setTitle("CodingSession starten");
		this.setHeaderText(null);
		
		txtTitel = new TextField();
		txtTitel.setPromptText("Titel der CodingSession");
		
		Label lblTitel = new Label("Titel: ");
		Label lblSpeichern = new Label("Soll die nachfolgende CodingSession in Zukunft gespeichert werden?");
		
		GridPane grid = new GridPane();
		grid.add(lblTitel, 1, 1);
		grid.add(txtTitel, 2, 1);
		grid.add(lblSpeichern, 1, 2);
		this.getDialogPane().setContent(grid);
		
		ButtonType jaButtonType = new ButtonType("Ja", ButtonData.YES);
		ButtonType neinButtonType = new ButtonType("Nein", ButtonData.NO);
		ButtonType abbrechenButtonType = new ButtonType("Abbrechen", ButtonData.CANCEL_CLOSE);
		this.getDialogPane().getButtonTypes().addAll(jaButtonType, neinButtonType, abbrechenButtonType);
		this.getDialogPane().getStylesheets().add(getClass().getResource("/view/css/styles.css").toExternalForm());
		
		this.setResultConverter(thisButton -> {
		    if (thisButton == jaButtonType) {
		        return new CodingSessionModell();
		    }
		    return null;
		});
		
		
	}
	
	public CodingSessionDialog() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Erstellt ein Dialog
	 */
	protected void erstelleEndDialog(){
		
	}
}
