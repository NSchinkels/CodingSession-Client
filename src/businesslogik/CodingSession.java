package businesslogik;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class CodingSession implements Initializable{
	
	@FXML
	private Button btnTest;
	@FXML
	private TextArea txtCodingSession;
	@FXML
	private TextArea txtChatRead;
	
	// Ids
	private int benutzerId;
	private int id;
	// Daten der CS
	private String titel;
	private boolean speichern;
	private boolean neuerCode = false;
	//Aktueller Code
	private String code = "";
	//Neuster Code aus der JMS, zu überprüfungs Zwecken notwenidig
	private String netCode = "";
	// Cs nur mit titel und speichern erstellbar
	private Profil[] teilnehmer;
	private int anzahlTeilnehmer = 0;
	//Für Einladungen
	private HashMap<String, String> daten;
	private Object lock;
	//Kommunikation
	KommunikationIncoming comi;
	KommunikationOutgoing como;
	//Textarear aus der fxml
	//Chat von dieser CS
	Chat chat;
	//Guielement von dem Chat
	//Threads für einkommen und auskommende Kommunikation hauptsächlich nur am warten
	ThreadCSOutgoing threadOut;

	
	public CodingSession(String titel, boolean speichern,
			KommunikationIncoming comi, KommunikationOutgoing como,
			int benutzerId, int id, Object lock) {
		this.titel = titel;
		this.speichern = speichern;
		this.benutzerId = benutzerId;
		this.id = id;
		this.comi = comi;
		this.como = como;
		this.lock=lock;
		
	}
	public void testMethode(ActionEvent a){
		
	}
	// Methode die zeitlich aufgrufen wird um den alten code mit dem neuen zu
	// erstetzen
	public synchronized void aktualisiereCode(String text, boolean selbst) {
		code = text;
		if (selbst){
			como.veröffentlicheCode(code);
			netCode = text;
			neuerCode = false;
		}
	}
	public void chatsenden(ActionEvent a){
		chat.senden(txtChatRead.getText());
		txtChatRead.setText("");
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
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		chat=new Chat(como,comi,""+benutzerId,id);
		como.starteCs("CodingSession" + id);
		comi.bekommeCode("CodingSession" + id, benutzerId);
		System.out.println("Initilizer");
		new Thread(){
			public void run() {
				while (true) {
					synchronized (lock) {
						System.out.println("In cs von " + benutzerId
								+ " wird gewartet");
						try {
							lock.wait();
							if (!netCode.equals(comi.getCode())&&!comi.getCode().equals(code)) {
								code = netCode = comi.getCode();
								neuerCode = true;
							}
							System.out.println("In cs von " + benutzerId
									+ " wurde aktualisert");
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
			}
		}.start();
		//threadOut=new ThreadCSOutgoing(txtCodingSession,this,txtChatRead,chat);
		//new Thread(threadOut).start();
		
		new Thread(){
			public void run() {
				while (true) {
					try {
						if (CodingSession.this.hasChanged()) {
							txtCodingSession.setText(CodingSession.this.getCode());
							System.out.println("hab neuen code");
						} else {
							CodingSession.this.neuerCodeGUI(txtCodingSession.getText());
							System.out.println("nix verändert");
						}
						Thread.sleep(2000);
						for(String text:chat.empfangen()){
							txtChatRead.appendText(text);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

}
