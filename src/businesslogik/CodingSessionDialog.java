package businesslogik;

import java.util.Optional;

import javafx.application.Platform;
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
		alert.setContentText("Willst du die CodingSession wirklich schlie�en?\n"
				            +"Alle nicht gespeicherten �nderungen gehen verloren.");
		
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
	
	public void erstelleAbmeldeDialog(){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("CodingSession schliessen");
		alert.setHeaderText(null);
		alert.setContentText("Willst du dich wirklich abmelden?");
		
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("/view/css/styles.css").toExternalForm());
		dialogPane.getStyleClass().add("dialog");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			ControllerMediator.getInstance().beenden();
			Platform.exit();
		} else {
		   //Falls der Benutzer den Button Abbrechen klickt, schliesst der Dialog
		}
	}
	
	public void erstelleEmailHinweisDialog(){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Ung�ltige E-Mail-Adresse");
		alert.setHeaderText(null);
		alert.setContentText("Bitte gebe eine g�ltige E-Mail-Adresse ein!");
		
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("/view/css/styles.css").toExternalForm());
		dialogPane.getStyleClass().add("dialog");
		
		alert.showAndWait();
	}
	
	public void erstellePasswortHinweisDialog(){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Ung�ltige E-Mail-Adresse");
		alert.setHeaderText(null);
		alert.setContentText("Bitte gebe ein g�ltiges Passwort ein!");
		
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("/view/css/styles.css").toExternalForm());
		dialogPane.getStyleClass().add("dialog");

		alert.showAndWait();
	}
	
	public void erstelleVornameHinweisDialog(){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Ung�ltige E-Mail-Adresse");
		alert.setHeaderText(null);
		alert.setContentText("Bitte gebe einen g�ltigen Vornamen ein!");
		
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("/view/css/styles.css").toExternalForm());
		dialogPane.getStyleClass().add("dialog");

		alert.showAndWait();
	}
	
	public void erstelleNachnameHinweisDialog(){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Ung�ltige E-Mail-Adresse");
		alert.setHeaderText(null);
		alert.setContentText("Bitte gebe einen g�ltigen Nachnamen ein!");
		
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("/view/css/styles.css").toExternalForm());
		dialogPane.getStyleClass().add("dialog");

		alert.showAndWait();
	}
	
	public void erstelleNicknameHinweisDialog(){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Ung�ltige E-Mail-Adresse");
		alert.setHeaderText(null);
		alert.setContentText("Bitte gebe einen g�ltigen Nicknamen ein!");
		
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("/view/css/styles.css").toExternalForm());
		dialogPane.getStyleClass().add("dialog");

		alert.showAndWait();
	}
}
