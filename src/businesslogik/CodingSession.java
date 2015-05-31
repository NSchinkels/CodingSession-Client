package businesslogik;

import java.io.Serializable;
import java.util.HashMap;

public class CodingSession implements Serializable {
	private static final long serialVersionUID = 1L;
	// nicht im Diagramm,aber bestimmt wichtig
	private int benutzerId;
	private int id;
	private String titel;
	private HashMap<String, String> daten;
	private boolean speichern;
	// Im Moment noch als ein String,später was besseres
	private String code = "HUU";
	// Cs nur mit titel und speichern erstellbar
	private Profil[] teilnehmer;
	private int anzahlTeilnehmer = 0;
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
		como.starteCsKommu("CodingSession"+id);
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
							// System.out.println("Warte auf neuen Code");
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally {
							aktualisiereCode(comi.getCode(), false);
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
		como.veröffentlicheCode(code,"Benutzer"+id);
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
		return code;
	}

	public int getBenutzerId() {
		return benutzerId;
	}

	public void setBenutzerId(int benutzerId) {
		this.benutzerId = benutzerId;
	}

}