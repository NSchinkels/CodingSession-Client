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
	 * Erstellt ein Dialog
	 */
	public CodingSessionModell erstelleStartDialog() {
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
				return new CodingSessionModell((int) (Math.random() * 10000), ControllerMediator.getInstance().getBenutzerkonto().getEmail(), txtTitel.getText(), true, "Hier bitte Code eingeben");
			} else if (dialogButton == neinButtonType) {
				return new CodingSessionModell((int) (Math.random() * 10000), ControllerMediator.getInstance().getBenutzerkonto().getEmail(), txtTitel.getText(), false, "Hier bitte Code eingeben");
			}
			return null;
		});

		return dialog.showAndWait().get();
	}

	/**
	 * Erstellt ein Dialog
	 */
	public void erstelleEndDialog() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("CodingSession schliessen");
		alert.setHeaderText(null);
		alert.setContentText("Willst du die CodingSession wirklich schlie�en?\n" + "Alle nicht gespeicherten �nderungen gehen verloren.");

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
		} else {
			// Falls der Benutzer den Button Abbrechen klickt, schliesst der
			// Dialog
		}
	}

	public void erstelleEmailValidierungDialog() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Ung�ltige E-Mail-Adresse");
		alert.setHeaderText(null);
		alert.setContentText("Bitte gebe eine g�ltige E-Mail-Adresse ein!");

		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("/view/css/styles.css").toExternalForm());
		dialogPane.getStyleClass().add("dialog");

		alert.showAndWait();
	}

	public void erstellePasswortValidierungDialog() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Ung�ltiges Passwort");
		alert.setHeaderText(null);
		alert.setContentText("Bitte gebe ein g�ltiges Passwort ein!");

		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("/view/css/styles.css").toExternalForm());
		dialogPane.getStyleClass().add("dialog");

		alert.showAndWait();
	}

	public void erstelleVornameValidierungDialog() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Ung�ltiger Vorname");
		alert.setHeaderText(null);
		alert.setContentText("Bitte gebe einen g�ltigen Vornamen ein!");

		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("/view/css/styles.css").toExternalForm());
		dialogPane.getStyleClass().add("dialog");

		alert.showAndWait();
	}

	public void erstelleNachnameValidierungDialog() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Ung�ltiger Nachname");
		alert.setHeaderText(null);
		alert.setContentText("Bitte gebe einen g�ltigen Nachnamen ein!");

		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("/view/css/styles.css").toExternalForm());
		dialogPane.getStyleClass().add("dialog");

		alert.showAndWait();
	}

	public void erstelleNicknameValidierungDialog() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Ung�ltiger Nickname");
		alert.setHeaderText(null);
		alert.setContentText("Bitte gebe einen g�ltigen Nicknamen ein!");

		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("/view/css/styles.css").toExternalForm());
		dialogPane.getStyleClass().add("dialog");

		alert.showAndWait();
	}

	public void erstelleAltesPasswortValidierungDialog() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Ung�ltiges Passwort");
		alert.setHeaderText(null);
		alert.setContentText("Das alte Passwort stimmt nicht mit dem des Benutzers �berein!");

		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("/view/css/styles.css").toExternalForm());
		dialogPane.getStyleClass().add("dialog");

		alert.showAndWait();
	}
	
	public void erstellePasswoerterWiderspruchDialog() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Ung�ltiges Passwort");
		alert.setHeaderText(null);
		alert.setContentText("Die eingegebenen neuen Passw�rter stimmen nicht �berein!");

		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("/view/css/styles.css").toExternalForm());
		dialogPane.getStyleClass().add("dialog");

		alert.showAndWait();
	}
	
	public void erstelleEmailVorhandenDialog() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Ung�ltige E-Mail-Adresse");
		alert.setHeaderText(null);
		alert.setContentText("Die von dir eingegebene E-Mail-Adresse ist bereits vergeben!");

		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("/view/css/styles.css").toExternalForm());
		dialogPane.getStyleClass().add("dialog");

		alert.showAndWait();
	}

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

	public void erstelleCfBeitragHinzufuegenDialog(CodingSessionModell codingSessionModell) {
		Dialog<Beitrag> dialog = new Dialog<>();
		dialog.setTitle("CodingSession teilen");
		dialog.setHeaderText(null);

		TextField txtBetreff = new TextField();
		txtBetreff.setPromptText("Betreff der CodingSession");

		TextArea txtBeschreibung = new TextArea();
		txtBeschreibung.getStyleClass().add("text-area-cf");
		txtBeschreibung.setPromptText("Beschreibung der CodingSession");

		Label lblSpeichern = new Label("Sollen andere Benutzer den Code ver�ndert d�rfen?");

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
				return new Beitrag(codingSessionModell, txtBetreff.getText(), txtBeschreibung.getText(), false);
			} else if (dialogButton == neinButtonType) {
				return new Beitrag(codingSessionModell, txtBetreff.getText(), txtBeschreibung.getText(), true);
			}
			return null;
		});

		ControllerMediator.getInstance().addCommunityFeed(dialog.showAndWait().get());
	}

	public void erstellePersistenzExceptionDialog() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Datenbank-Fehler");
		alert.setHeaderText(null);
		alert.setContentText("Ups, da stimmt wohl etwas mit der Datenbank nicht!");

		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("/view/css/styles.css").toExternalForm());
		dialogPane.getStyleClass().add("dialog");

		alert.showAndWait();
	}
	public void erstelleFehlerMeldung(String meldung){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Fehler");
		alert.setHeaderText(null);
		alert.setContentText(meldung);

		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("/view/css/styles.css").toExternalForm());
		dialogPane.getStyleClass().add("dialog");

		alert.showAndWait();
	}
}
