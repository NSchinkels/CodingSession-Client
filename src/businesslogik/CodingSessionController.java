package businesslogik;

import java.net.URL;
import java.util.ResourceBundle;

import Persistence.PersistenzException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class CodingSessionController implements Initializable {

	private CodingSessionModell codingSessionModell;
	private String benutzerEmail;

	// Aktueller Code
	private String code = "";

	// Neuester Code aus der JMS, zu �berpr�fungszwecken notwendig
	private String netCode = "";

	// Kommunikation
	private KommunikationIncoming kommunikationIn;
	private KommunikationOutgoing kommunikationOut;

	// Chat von dieser CS
	private Chat chat;
	private PackageExplorerController packageExplorer;
	private Thread codingSessionThread;

	private int speicherCounter = 10;

	@FXML
	private Button btnTest;

	@FXML
	private TextArea txtCodingSession;

	@FXML
	private TextArea txtChatRead;

	@FXML
	private TextArea txtChatWrite;
	@FXML
	private ListView<CodingSessionModell> listCodingSession;

	public CodingSessionController(CodingSessionModell codingSessionModell,
			KommunikationIncoming kommunikationIn,
			KommunikationOutgoing kommunikationOut) {
		this.codingSessionModell = codingSessionModell;
		this.kommunikationIn = kommunikationIn;
		this.kommunikationOut = kommunikationOut;
		benutzerEmail=ControllerMediator.getInstance().getBenutzerkonto().getEmail();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		try {
			packageExplorer = new PackageExplorerController(benutzerEmail);
		} catch (PersistenzException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ObservableList<CodingSessionModell> codingSessionItems = listCodingSession
				.getItems();
		for (CodingSessionModell cs : packageExplorer.get()) {
			codingSessionItems.add(cs);
		}
		listCodingSession.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 2) {
						ControllerMediator.getInstance().changeCodingSession(
								listCodingSession.getSelectionModel()
										.getSelectedItem());
					}
				}
			}
		});
		chat = new Chat(kommunikationOut, kommunikationIn,benutzerEmail, codingSessionModell.getId());
		kommunikationOut
				.starteCs("CodingSession" + codingSessionModell.getId());
		kommunikationIn.bekommeCode(
				"CodingSession" + codingSessionModell.getId(),
				codingSessionModell.getBenutzerMail());
		netCode = code = codingSessionModell.getCode();
		txtCodingSession.setText(code);
		codingSessionThread = new Thread() {

			public void run() {
				boolean running = true;
				while (running) {
					try {
						synchronized (txtCodingSession) {
							if (kommunikationIn.hasChanged()) {
								netCode = kommunikationIn.getCode();
								if (!netCode.equals(code)) {
									code = netCode;
									txtCodingSession.setText(code);

								}
							} else {
								CodingSessionController.this
										.neuerCodeGUI(txtCodingSession
												.getText());
							}
						}
						if (chat.empfangen().size() > chat.getSize()) {
							txtChatRead.setText("");
							chat.setSize(chat.empfangen().size());
							for (String text : chat.empfangen()) {
								txtChatRead.appendText(text);
							}
						}
						if (speicherCounter++ > 10
								&& codingSessionModell.isSpeichern()) {
							Persistence.Datenhaltung.schreibeCS(codingSessionModell);
							chat.speichern();
							speicherCounter = 0;
						}
						codingSessionModell.setCode(code);
						Thread.sleep(200);
					} catch (Exception e) {
						running = false;
					}
				}
			}
		};
		codingSessionThread.start();
	}

	@FXML
	public void abmeldenGeklickt(ActionEvent event) {
		new CodingSessionDialog().erstelleAbmeldeDialog();
	}

	@FXML
	public void codingSessionSchliessenGeklickt(ActionEvent event) {
		new CodingSessionDialog().erstelleEndDialog();
	}

	@FXML
	public void txtChatEnterGeklickt(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			chat.senden(txtChatWrite.getText() + "\n");
			txtChatWrite.setText("");
		}
	}

	@FXML
	public void txtCodingSessionFormatierung(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			synchronized (txtCodingSession) {
				// txtCodingSession.setText(einruecken(txtCodingSession.getText()));
			}
		}
	}

	@FXML
	public void commFeedTeilen(ActionEvent event) {
		new CodingSessionDialog()
				.erstelleCfBeitragHinzufuegenDialog(this.codingSessionModell);
	}

	public boolean addTeilnehmer(String b) {
		if (codingSessionModell.getAnzahlTeilnehmer() < 10) {
			codingSessionModell.addTeilnehmer(b);
			return true;
		}
		return false;
	}

	// Methode die zeitlich aufgrufen wird, um den alten Code mit dem neuen zu
	// ersetzen
	public void aktualisiereCode(String text, boolean selbst) {
		code = text;
		if (selbst) {
			kommunikationOut.veroeffentlicheCode(code);
			netCode = text;
		}
	}

	public void beenden() {
		codingSessionThread.interrupt();
		kommunikationIn.beenden();
	}
	public void killThread() {
		codingSessionThread.interrupt();
	}
	public void neuerCodeGUI(String neuerCode) {
		if (!neuerCode.equals(netCode)) {
			aktualisiereCode(neuerCode, true);
		}
	}

	public void sendeEinladung(String benutzer) {
		System.out.println("Sende Einladung zu " + benutzer);
		kommunikationOut.ladeEin(codingSessionModell, benutzer);
	}

	// Funktion zum Einr�cken des Codes
	public static String einruecken(String eingabe) {

		String tabulator = "";

		// for-Schleife durchl�uft gesamten eingabe-String
		for (int i = 0; i < eingabe.length(); i++) {

			// Tabulator wird hinzugef�gt bei offener Klammer
			if (eingabe.charAt(i) == '{') {
				tabulator = tabulator + "\t";
			}

			// Bei Zeilenumbruch wird der Tabulator-String eingef�gt, sozusagen
			// String wird "einger�ckt"
			if (eingabe.charAt(i) == '\n') {

				// �berpr�fung, ob eine geschlossene Klammer in voriger Zeile
				// oder neuer Zeile
				// am Ende vorhanden war, um diese richtig "auszur�cken"
				if (eingabe.charAt(i + 1) == '}'
						|| eingabe.charAt(i - 1) == '}') {
					if (!tabulator.equals("")) {
						tabulator = tabulator.substring(0,
								tabulator.length() - 1);
					}
					// Durch Substring wird Tabulator-String bis auf seine
					// letzten 2 Zeichen reduziert
				}

				eingabe = eingabe.substring(0, i + 1) + tabulator
						+ eingabe.substring(i + 1, eingabe.length());
				// Substring zur \n-Stelle + Tabulator + Substring nach
				// \n-Stelle
			}
		}
		return eingabe;
	}
}