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

	private CodingSessionModell csmod;

	// Aktueller Code
	private String code = "";

	// Neuester Code aus der JMS, zu �berpr�fungszwecken notwendig
	private String netCode = "";

	// Kommunikation
	private KommunikationIncoming comi;
	private KommunikationOutgoing como;

	// Chat von dieser CS
	private Chat chat;
	private PackageExplorerController pe;
	Thread codingSessionThread;

	int speicherCounter = 0;

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

	public CodingSessionController(CodingSessionModell csmod,
			KommunikationIncoming comi, KommunikationOutgoing como) {
		this.csmod = csmod;
		this.comi = comi;
		this.como = como;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		try {
			pe = new PackageExplorerController("12");
		} catch (PersistenzException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ObservableList<CodingSessionModell> items = listCodingSession
				.getItems();
		for (CodingSessionModell cs : pe.get()) {
			items.add(cs);
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
		chat = new Chat(como, comi, "" + csmod.getBenutzerMail(), csmod.getId());
		como.starteCs("CodingSession" + csmod.getId());
		comi.bekommeCode("CodingSession" + csmod.getId(),
				csmod.getBenutzerMail());
		netCode = code = csmod.getCode();
		txtCodingSession.setText(code);
		codingSessionThread = new Thread() {

			public void run() {
				boolean running = true;
				while (running) {
					try {
						synchronized (txtCodingSession) {
							if (comi.hasChanged()) {
								netCode = comi.getCode();
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
							for (String text : chat.empfangen()) {
								txtChatRead.appendText(text);
							}
						}
						if (speicherCounter++ > 10 && csmod.isSpeichern()) {
							// Persistence.Datenhaltung.schreibeCS(csmod);
							speicherCounter = 0;
						}
						csmod.setCode(code);
						Thread.sleep(200);
					} catch (Exception e) {
						System.out.println("kaputt");
						running = false;
					}
				}
			}
		};
		codingSessionThread.start();
	}

	@FXML
	public void abmeldenGeklickt(ActionEvent event){
		new CodingSessionDialog().erstelleAbmeldeDialog();
	}
	
	@FXML
	public void codingSessionSchliessenGeklickt(ActionEvent event){
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
			synchronized(txtCodingSession){
			//txtCodingSession.setText(einruecken(txtCodingSession.getText()));
			}
		}
	}

	public boolean addTeilnehmer(String b) {
		if (csmod.getAnzahlTeilnehmer() < 10) {
			csmod.addTeilnehmer(b);
			return true;
		}
		return false;
	}
	
	// Methode die zeitlich aufgrufen wird, um den alten Code mit dem neuen zu
	// ersetzen
	public void aktualisiereCode(String text, boolean selbst) {
		code = text;
		if (selbst) {
			como.veroeffentlicheCode(code);
			netCode = text;
		}
	}
	
	public void beenden() {
		codingSessionThread.interrupt();
		comi.beenden();
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
		
	public void killThread(){
		codingSessionThread.interrupt();
	}

	public void neuerCodeGUI(String text) {
		if (!text.equals(netCode)) {
			aktualisiereCode(text, true);
		}
	}

	public void sendeEinladung(String benutzer) {
		como.ladeEin(csmod, benutzer);
	}
}