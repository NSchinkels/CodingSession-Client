
package businesslogik;

import java.util.Optional;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;

public class CodingSessionDialog {
	private TextField txtTitel;

	/**
	 * Erstelle einen Dialog, der beim schlieﬂen der Anwendung aufgerufen wird.
	 */
	public void erstelleAbmeldeDialog() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("CodingSession schliessen");
		alert.setHeaderText(null);
		alert.setContentText("Willst du dich wirklich abmelden?");

		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("/view/css/styles.css").toExternalForm());
		dialogPane.getStyleClass().add("dialog");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			ControllerMediator.getInstance().beenden();
			Platform.exit();
			System.exit(0);
		} else {
			// Falls der Benutzer den Button Abbrechen klickt, schliesst der
			// Dialog
		}
	}
	
	/**
	 * Erstellt einen Dialog, der aufgerufen wird sobald der Benutzer seine CodingSession mit 
	 * der Community teilen moechte.
	 * @param codingSessionModell
	 */
	public void erstelleCfBeitragHinzufuegenDialog(CodingSessionModell codingSessionModell) {
		Dialog<Beitrag> dialog = new Dialog<>();
		dialog.setTitle("CodingSession teilen");
		dialog.setHeaderText(null);

		TextField txtBetreff = new TextField();
		txtBetreff.setPromptText("Betreff der CodingSession");

		TextArea txtBeschreibung = new TextArea();
		txtBeschreibung.getStyleClass().add("text-area-cf");
		txtBeschreibung.setPromptText("Beschreibung der CodingSession");

		Label lblSpeichern = new Label("Sollen andere Benutzer den Code ver‰ndert d¸rfen?");

		GridPane grid = new GridPane();
		grid.add(txtBetreff, 0, 2);
		grid.add(txtBeschreibung, 0, 3);
		grid.add(lblSpeichern, 0, 0);
		dialog.getDialogPane().setContent(grid);

		ButtonType jaButtonType = new ButtonType("Ja", ButtonData.YES);
		ButtonType neinButtonType = new ButtonType("Nein", ButtonData.NO);
		ButtonType abbrechenButtonType = new ButtonType("Abbrechen", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(jaButtonType, neinButtonType, abbrechenButtonType);
		dialog.getDialogPane().getStylesheets().add(getClass().getResource("/view/css/styles.css").toExternalForm());

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == jaButtonType) {
				codingSessionModell.setSpeichern(true);
				return new Beitrag(codingSessionModell, txtBetreff.getText(), txtBeschreibung.getText(), false);
			} else if (dialogButton == neinButtonType) {
				codingSessionModell.setSpeichern(false);
				return new Beitrag(codingSessionModell, txtBetreff.getText(), txtBeschreibung.getText(), true);
			}
			return null;
		});
		
		ControllerMediator.getInstance().addCommunityFeed(dialog.showAndWait().get());
	}
	
	/**
	 * Erstellt einen Dialog der beim schliessen der CodingSession aufgerufen wird.
	 */
	public void erstelleCsSchliessenDialog() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("CodingSession schliessen");
		alert.setHeaderText(null);
		alert.setContentText("Willst du die CodingSession wirklich schlieﬂen?");

		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("/view/css/styles.css").toExternalForm());
		dialogPane.getStyleClass().add("dialog");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			ControllerMediator.getInstance().schliesseCodingSession();
		} else {
			// Falls der Benutzer den Button Abbrechen klickt, schliesst der
			// Dialog
		}
	}
	
	/**
	 * Erstellt einen Dialog der beim starten einer CodingSession aufgerufen wird.
	 */
	public CodingSessionModell erstelleCsStartenDialog() {
		Dialog<CodingSessionModell> dialog = new Dialog<>();
		dialog.setTitle("CodingSession starten");
		dialog.setHeaderText(null);

		Label lblSpeichern = new Label("Soll die nachfolgende CodingSession gespeichert werden?");
		
		txtTitel = new TextField();
		txtTitel.setPromptText("Titel der CodingSession");

		GridPane grid = new GridPane();
		grid.add(lblSpeichern, 0, 0);
		grid.add(txtTitel, 0, 1);
		dialog.getDialogPane().setContent(grid);

		ButtonType jaButtonType = new ButtonType("Ja", ButtonData.YES);
		ButtonType neinButtonType = new ButtonType("Nein", ButtonData.NO);
		ButtonType abbrechenButtonType = new ButtonType("Abbrechen", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(jaButtonType, neinButtonType, abbrechenButtonType);
		dialog.getDialogPane().getStylesheets().add(getClass().getResource("/view/css/styles.css").toExternalForm());

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == jaButtonType) {
				//Es gibt noch keine ID Verwaltung. Es ist bekannt das diese Methode nicht gut ist
				return new CodingSessionModell((int) (Math.random() * 10000), ControllerMediator.getInstance().getBenutzerkonto().getEmail(), txtTitel.getText(), true, "Hier bitte Code eingeben");
			} else if (dialogButton == neinButtonType) {
				return new CodingSessionModell((int) (Math.random() * 10000), ControllerMediator.getInstance().getBenutzerkonto().getEmail(), txtTitel.getText(), false, "Hier bitte Code eingeben");
			}
			return null;
		});

		return dialog.showAndWait().get();
	}
	
	/**
	 * Erstellt einen Dialog der aufgerufen wird, sobald der Benutzer in eine 
	 * bestehende CodingSession von einem anderen Benutzer eingeladen wird.
	 * @param email - E-Mail-Adresse des Benutzers
	 */
	public void erstelleEinladungDialog(String email) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Einladung");
		alert.setHeaderText("Einladung erhalten");
		alert.setContentText("Sie haben eine Einladung vom Benutzer " + email + " erhalten");
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("/view/css/styles.css").toExternalForm());
		dialogPane.getStyleClass().add("dialog");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			ControllerMediator.getInstance().einladungAngenommen();
			;
		}
	}
	
	/**
	 * Erstellt einen Dialog der bei jeglichen Fehlermeldungen aufgerufen werden kann
	 * @param ueberschrift - Titel des Fehlermeldungsdialogs
	 * @param fehler - Beschreibund des Fehlers im Dialog
	 */
	public void erstelleFehlermeldungDialog(String ueberschrift, String fehler){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(ueberschrift);
		alert.setHeaderText(null);
		alert.setContentText(fehler);

		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("/view/css/styles.css").toExternalForm());
		dialogPane.getStyleClass().add("dialog");

		alert.showAndWait();
	}
}