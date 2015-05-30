package businesslogik;

import java.io.Serializable;

public class CodingSession  implements Serializable {
	private static final long serialVersionUID = 1L;
	// nicht im Diagramm,aber bestimmt wichtig
	int benutzerId;
	int id;
	String titel;
	boolean speichern;
	// Im Moment noch als ein String,später was besseres
	String code = "HUU";
	// Cs nur mit titel und speichern erstellbar
	

	Profil[] teilnehmer;
	int anzahlTeilnehmer = 0;
	KommunikationIncoming comi;
	KommunikationOutgoing como;
	
	public CodingSession(String titel, boolean speichern,
			KommunikationIncoming comi, KommunikationOutgoing como,
			int benutzerid, int id, Object lock) {
		this.titel = titel;
		this.speichern = speichern;
		this.benutzerId = benutzerId;
		this.id = id;
		this.comi = comi;
		this.como = como;
		comi.bekommeCode("CodingSession" + id, "Benutzer" + benutzerId);
		new Thread() {
			public void run() {
				while (true) {
					synchronized (lock) {
						// comi.bekomme("CodingSession" + id, "Benutzer"+
						// benutzerId);
						// System.out.println("Warte auf neuen Code");
						try {
							lock.wait();
							code = comi.neuerCode;
							// System.out.println("Warte auf neuen Code");
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally {
							aktualisiereCode(comi.neuerCode, false);
						}
					}
				}
			}
		}.start();
	}

	// Methode die zeitlich aufgrufen wird um den alten code mit dem neuen zu
	// erstetzen
	public synchronized void aktualisiereCode(String text, boolean selbst) {
		this.code = text;
		if (selbst)
			codeVeroeffentlichen();

	}

	public void codeVeroeffentlichen() {
		// Server Magic. Die anderne clienenten wird der neue code gegeben
		como.veröffentlicheCode(code, "CodingSession" + id, "Benutzer"
				+ benutzerId);
	}

	public boolean addTeilnehmer(Profil b) {
		if (anzahlTeilnehmer < 10) {
			teilnehmer[anzahlTeilnehmer++] = b;
			return true;
		}
		return false;
	}
	public void sendeEinladung(int benutzer){
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
		return code;
	}

	public int getBenutzerId() {
		return benutzerId;
	}

	public void setBenutzerId(int benutzerId) {
		this.benutzerId = benutzerId;
	}
	

}