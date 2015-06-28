package businesslogik;

import java.net.URL;
import java.util.ResourceBundle;

import Persistence.Datenhaltung;
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

/**
 * 
 * Der CodingSessionController beinhaltet die Hauptfunktion unseres Programms.
 * Hier mit können Benutzer zusammen programmiern und chatten.
 *
 */
public class CodingSessionController implements Initializable {

	// Das CodingSessionModell der aktuellen CodingSessin
	private CodingSessionModell codingSessionModell;

	// Die Benutzeremail als Id und Identifikation
	private String benutzerEmail;

	// Aktueller Code
	private String code = "";

	// Neuester Code aus der JMS, zu �berpr�fungszwecken notwendig
	private String netCode = "";

	// Groesse des angezeigten Chats
	private int chatsize = -1;

	// Kommunikation
	private KommunikationIncoming kommunikationIn;
	private KommunikationOutgoing kommunikationOut;

	// Chat von dieser CS
	private Chat chat;

	// Ansammlung von allen CodingSessions eines Benutzers
	private PackageExplorerController packageExplorer;

	// Extra Thread der für den Code austausch und den Chat zuständig ist
	private Thread codingSessionThread;

	// Counter der hochzählt um das CodingSessionModell regelmäßig zu speichern
	private int speicherCounter = 10;

	// FXML Elemente
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

	/**
	 * Konstruktor der Klasse. Ihm werden die Objekte fuer die Kommunikation
	 * überreicht und das CodingSessionModell dieses Fensters
	 */

	public CodingSessionController(CodingSessionModell codingSessionModell, KommunikationIncoming kommunikationIn, KommunikationOutgoing kommunikationOut) {
		this.codingSessionModell = codingSessionModell;
		this.kommunikationIn = kommunikationIn;
		this.kommunikationOut = kommunikationOut;
		benutzerEmail = ControllerMediator.getInstance().getBenutzerkonto().getEmail();
	}

	/**
	 * Methode die von JavaFxaufgerufen wird um den Controller zu initializiern
	 * Erstellt die ListView des PackageExlporers und füllt diese mit den
	 * CodingSessions des Benutzers Lädt oder erstellt den Chat dieser
	 * CodingSession Erstellt den Thread für die Kommunikation und startet
	 * diesen
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 *      java.util.ResourceBundle)
	 */

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// Es wird ein neuer Chat erstellt
		chat = new Chat(kommunikationOut, kommunikationIn, benutzerEmail, codingSessionModell.getId());
		try {
			// Falls es einen in der Db gibt wird der geladen
			if (Datenhaltung.leseChat(codingSessionModell.getId()) != null){
				chat=Datenhaltung.leseChat(codingSessionModell.getId());
				chat.setKommunikationIn(kommunikationIn);
				chat.setKommunikationOut(kommunikationOut);
				chat.setSender(benutzerEmail);
			}
		} catch (Exception e) {
			// Excetion wird hier geworfen wenn der Chat nicht in der Db ist
		}
		chat.start();
		// wenn gespeichert werden soll und die CodingSession vom Benutzer ist
		// wird gespeichert
		if (codingSessionModell.isSpeichern() && codingSessionModell.getBenutzerMail().equals(benutzerEmail)) {
			try {
				Datenhaltung.schreibeCS(codingSessionModell);
			} catch (PersistenzException e) {
				new CodingSessionDialog().erstelleFehlermeldungDialog("CodingSession speichern", "Die CodingSession konnte nicht gepseichert werden");
			}
		}
		try {
			// Alle CodingSessions werden geholt
			packageExplorer = new PackageExplorerController(benutzerEmail);
		} catch (PersistenzException e1) {
			new CodingSessionDialog().erstelleFehlermeldungDialog("Datenbank-Fehler", "Es gab einen Fehler mit der Datenbank.\n Bitte starte eine neue CodingSession");
		}
		// Liste wird erstellt und gefüllt
		ObservableList<CodingSessionModell> codingSessionItems = listCodingSession.getItems();
		for (CodingSessionModell cs : packageExplorer.get()) {
			codingSessionItems.add(cs);
		}
		// Bei zwei Doppelklicks wird die gewählte CodingSession geöffnet
		listCodingSession.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 2) {
						ControllerMediator.getInstance().changeCodingSession(listCodingSession.getSelectionModel().getSelectedItem());
					}
				}
			}
		});
		txtChatRead.setText(chat.getChat());

		// Der Producer und Subscriber vom JMS wird gestartet
		kommunikationOut.starteCs("CodingSession" + codingSessionModell.getId());
		kommunikationIn.bekommeCode("CodingSession" + codingSessionModell.getId(), benutzerEmail);
		netCode = code = codingSessionModell.getCode();
		txtCodingSession.setText(code);

		codingSessionThread = new Thread() {

			public void run() {
				boolean running = true;
				while (running) {
					try {
						synchronized (txtCodingSession) {
							// wenn neuer Code aus dem JMS da ist
							if (kommunikationIn.hasChanged()) {
								netCode = kommunikationIn.getCode();
								// ist der neue Code anders als der aktuelle?
								if (!netCode.equals(code)) {
									// Code wird aktualisert und angezeigt
									code = netCode;
									txtCodingSession.setText(code);
								}
							} else {
								// kein neuer Code da eigenen Code aus dem
								// Fenster an das JMS schreiben
								CodingSessionController.this.aktualisiereCode(txtCodingSession.getText(), true);
							}
						}
						// Wenn es neue Nachrichten gibt wird das Chat Fenster
						// aktualsiert
						if (chatsize < chat.getSize()) {
							txtChatRead.setText(chat.getChat());
							chat.setSize(chat.getVerlauf().size());
							chatsize = chat.getSize();
						}
						// Alles 60 Sekunden werden Chat und CodingSession
						// persistiert
						if (speicherCounter++ > 300 && codingSessionModell.isSpeichern()) {
							speichern();
							speicherCounter = 0;
						}
						// Aktueller Code wird an das Modell Objekt
						// weitergegeben und der Thread schläft 200 MS
						codingSessionModell.setCode(code);
						Thread.sleep(200);
					} catch (InterruptedException e1) {
						// Der Thread wird beim beenden interrupted damit dieser
						// nicht unendlich weiterläuft
						running = false;
					} catch (Exception e2) {
						new CodingSessionDialog().erstelleFehlermeldungDialog("JMS-Fehler", "Es gab einen Fehler im JMS.\n Bitte starte eine neue CodingSession");
						running = false;
					}
				}
			}
		};
		codingSessionThread.start();
	}

	/**
	 * Methode die auf den Klick auf den Abmelde Hyperlink reagiert
	 * 
	 * @param event
	 *            wird nich benutzt
	 */
	@FXML
	public void abmeldenGeklickt(ActionEvent event) {
		new CodingSessionDialog().erstelleAbmeldeDialog();
	}

	/**
	 * Methode die auf den Klick auf den CodingSession schliessen Hyperlink
	 * reagiert
	 * 
	 * @param event
	 *            wird nicht benutzt
	 */

	@FXML
	public void codingSessionSchliessenGeklickt(ActionEvent event) {
		new CodingSessionDialog().erstelleCsSchliessenDialog();
	}

	/**
	 * Sobald im Chat write Fenster enter gedrückt wird sendet diese Methode den
	 * String an das JMS
	 * 
	 * @param event
	 *            wird benutzt um den KeyCode zu bekommen
	 */

	@FXML
	public void txtChatEnterGeklickt(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			chat.senden(txtChatWrite.getText() + "\n");
			txtChatWrite.setText("");
		}
	}

	/**
	 * Metohde die aus Key presses auf dem Fenster der CodingSession reagiert
	 * 
	 * @param event
	 */

	@FXML
	public void txtCodingSessionFormatierung(KeyEvent event) {
		if (event.isControlDown() && event.getCode() == KeyCode.S) {
			this.speichern();
		}
		if (event.isControlDown() && event.getCode() == KeyCode.I) {
			synchronized (txtCodingSession) {
				txtCodingSession.setText(einruecken(txtCodingSession.getText()));
			}
		}
	}

	/**
	 * Bei druck auf den CommmFeed teilen Button wird die CodingSession an denn
	 * CommunityFeed gesendet
	 * 
	 * @param event
	 *            wird nicht benutzt
	 */

	@FXML
	public void commFeedTeilen(ActionEvent event) {
		new CodingSessionDialog().erstelleCfBeitragHinzufuegenDialog(this.codingSessionModell);
	}

	/**
	 * Die Benutzer der CodingSession werden im Modell gespeichert Die Anzahl
	 * darf nich hoeher als 10 werden weil dann das JMS nicht mehr responsiv
	 * genug ist
	 * 
	 * @return
	 */
	public boolean addTeilnehmer() {
		if (codingSessionModell.getAnzahlTeilnehmer() < 10) {
			return true;
		}
		return false;
	}

	/**
	 * Methode die zeitlich aufgrufen wird, um den alten Code mit dem neuen zu
	 * ersetzen
	 * 
	 * @param text
	 *            Der neue Code
	 * @param selbst
	 *            Wenn der Code von einem selbst kommt wird dieser an das JMS
	 *            gesendet
	 */

	public void aktualisiereCode(String neuerCode, boolean selbst) {
		if (!neuerCode.equals(netCode)) {
			code = neuerCode;
			if (selbst) {
				kommunikationOut.veroeffentlicheCode(code);
				netCode = neuerCode;
			}
		}
	}

	/**
	 * Wenn das Programm beendet wird werden die Kommunikations Kanäle
	 * geschlossen und der Thread interrupted
	 */

	public void beenden() {
		codingSessionThread.interrupt();
		kommunikationOut.beenden();
		kommunikationIn.beenden();
	}

	/**
	 * Wenn nur die CodingSession geschlossen werden soll, wird nur der Thread
	 * interrupted
	 */

	public void killThread() {
		codingSessionThread.interrupt();
	}

	/**
	 * Ein Benutzer wird uber seine Id eingeladen
	 * 
	 * @param benutzer
	 *            Email des Benutzers der eingeladen werden soll
	 */

	public void sendeEinladung(String benutzer) {
		kommunikationOut.ladeEin(codingSessionModell, benutzer);
	}

	/**
	 * Die CodingSession wird in der DB persistiert
	 * 
	 */

	public void speichern() {
		try {
				Datenhaltung.schreibeCS(codingSessionModell);
				chat.speichern();
		} catch (Exception e) {
			new CodingSessionDialog().erstelleFehlermeldungDialog("Speichern", "Es konnte nicht gespeichert werden");
		}
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
				if (eingabe.charAt(i + 1) == '}' || eingabe.charAt(i - 1) == '}') {
					if (!tabulator.equals("")) {
						tabulator = tabulator.substring(0, tabulator.length() - 1);
					}
					// Durch Substring wird Tabulator-String bis auf seine
					// letzten 2 Zeichen reduziert
				}

				eingabe = eingabe.substring(0, i + 1) + tabulator + eingabe.substring(i + 1, eingabe.length());
				// Substring zur \n-Stelle + Tabulator + Substring nach
				// \n-Stelle
			}
		}
		return eingabe;
	}
}