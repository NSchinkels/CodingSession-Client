package businesslogik;

//wird noch durch JMS ersetzt!!!!
public class CodingSession {
	// nicht im Diagramm,aber bestimmt wichtig
	private int benutzerId;
	private int id;
	private String titel;
	private boolean speichern;
	// Im Moment noch als ein String,später was besseres
	private String code;
	// Cs nur mit titel und speichern erstellbar
	Kommunikation com;
	private Profil[] teilnehmer;
	private int anzahlTeilnehmer = 0;

	public CodingSession(String titel, boolean speichern, Kommunikation com,
			int benutzerid, int id) {
		this.titel = titel;
		this.speichern = speichern;
		this.com = com;
		this.benutzerId = benutzerId;
		this.id = id;
		// teilnehmer= new Profil[10];
		// com.bekomme("CodingSession"+id,"Benutzer"+benutzerId);
		new Thread() {
			public void run() {
				synchronized (com.neuerCode) {
					com.bekomme("CodingSession" + id, "Benutzer" + benutzerId);
					while (true) {
						try {
							com.neuerCode.wait();
							// code=com.neuerCode;
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						aktualisiereCode(com.neuerCode, false);
					}
				}
			}
		}.start();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getBenutzerId() {
		return benutzerId;
	}

	public void setBenutzerId(int benutzerId) {
		this.benutzerId = benutzerId;
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
		com.veröffentliche(code, "CodingSession" + id, "Benutzer" + benutzerId);
	}

	public boolean addTeilnehmer(Profil b) {
		if (anzahlTeilnehmer < 10) {
			teilnehmer[anzahlTeilnehmer++] = b;
			return true;
		}
		return false;
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
}