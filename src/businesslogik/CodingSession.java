package businesslogik;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class CodingSession implements Initializable {

	// IDs
	private int benutzerId;
	private int id;

	// Daten der CS
	private String titel;
	private boolean speichern;
	private boolean neuerCode = false;

	// Aktueller Code
	private String code = "";

	// Neuester Code aus der JMS, zu �berpr�fungszwecken notwendig
	private String netCode = "";

	// Cs nur mit Titel und speichern erstellbar
	private Profil[] teilnehmer;
	private int anzahlTeilnehmer = 0;

	// F�r Einladungen
	private HashMap<String, String> daten;

	// Kommunikation
	KommunikationIncoming comi;
	KommunikationOutgoing como;

	// Chat von dieser CS
	Chat chat;

	@FXML
	private Button btnTest;

	@FXML
	private TextArea txtCodingSession;

	@FXML
	private TextArea txtChatRead;

	@FXML
	private TextArea txtChatWrite;

	public CodingSession(String titel, boolean speichern,
			KommunikationIncoming comi, KommunikationOutgoing como,
			int benutzerId, int id) {
		this.titel = titel;
		this.speichern = speichern;
		this.benutzerId = benutzerId;
		this.id = id;
		this.comi = comi;
		this.como = como;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		chat = new Chat(como, comi, "" + benutzerId, id);
		como.starteCs("CodingSession" + id);
		comi.bekommeCode("CodingSession" + id, benutzerId);
		/*
		 * new Thread(){ public void run() { while (true) { synchronized (lock)
		 * { System.out.println("In cs von " + benutzerId + " wird gewartet");
		 * try { lock.wait(); if
		 * (!netCode.equals(comi.getCode())&&!comi.getCode().equals(code)) {
		 * code = netCode = comi.getCode(); neuerCode = true; }
		 * System.out.println("In cs von " + benutzerId + " wurde aktualisert");
		 * } catch (InterruptedException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } } }
		 * 
		 * } }.start(); threadOut=new
		 * ThreadCSOutgoing(txtCodingSession,this,txtChatRead,chat); new
		 * Thread(threadOut).start();
		 */
		new Thread() {
			public void run() {
				while (true) {
					try {
						if (comi.hasChanged()) {
							netCode = comi.getCode();
							if (!netCode.equals(code)) {
								code = netCode;
								txtCodingSession.setText(CodingSession.this
										.getCode());
							}
						}
						CodingSession.this.neuerCodeGUI(txtCodingSession
								.getText());
						txtChatRead.setText("");
						for (String text : chat.empfangen()) {
							txtChatRead.appendText(text);
						}
						Thread.sleep(2000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	@FXML
	public void txtChatEnterGeklickt(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			chat.senden(txtChatWrite.getText() + "\n");
			txtChatWrite.setText("");
		}
	}

	// Methode die zeitlich aufgrufen wird, um den alten Code mit dem neuen zu
	// ersetzen
	public void aktualisiereCode(String text, boolean selbst) {
		code = text;
		if (selbst) {
			como.veroeffentlicheCode(code);
			netCode = text;
			neuerCode = false;
		}
	}

	public boolean hasChanged() {
		return neuerCode;
	}

	public void neuerCodeGUI(String text) {
		if (!text.equals(netCode)) {
			aktualisiereCode(text, true);
		}
	}

	public boolean addTeilnehmer(Profil b) {
		if (anzahlTeilnehmer < 10) {
			teilnehmer[anzahlTeilnehmer++] = b;
			return true;
		}
		return false;
	}

	public void sendeEinladung(int benutzer) {
		como.ladeEin(this.getDaten(), benutzer);
	}

	public HashMap<String, String> getDaten() {
		daten = new HashMap<String, String>();
		daten.put("titel", titel);
		daten.put("benutzerid", String.valueOf(benutzerId));
		daten.put("id", String.valueOf(id));
		daten.put("code", code);
		daten.put("anzahlTeilnehmer", String.valueOf(anzahlTeilnehmer));
		for (int i = 0; i < anzahlTeilnehmer; i++) {
			daten.put("Teilnehmer" + i, teilnehmer[i].toString());
		}
		return daten;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isSpeichern() {
		return speichern;
	}

	public void setSpeichern(boolean speichern) {
		this.speichern = speichern;
	}

	public String getCode() {
		neuerCode = false;
		return code;
	}

	public int getBenutzerId() {
		return benutzerId;
	}

	public void setBenutzerId(int benutzerId) {
		this.benutzerId = benutzerId;
	}
}
