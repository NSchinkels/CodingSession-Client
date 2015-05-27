package businesslogik;

import java.util.LinkedList;

public abstract class BenutzerkontoOriginal {

	// Wir haben im UML hier das PW als String, sollten wir schnellstmoeglich durch DB mit Verschluesselung etc
	// ersetzen, ich implementier es trotzdem mal stumpf mit dem Passwort
	
	
	private String emailAdresse;
	private String passwort;
	private static int id;
	private LinkedList<BenutzerkontoOriginal> freunde = new LinkedList<BenutzerkontoOriginal>();
	
	public BenutzerkontoOriginal(String email, String pw) {
		this.emailAdresse = email;
		this.passwort = pw;
		id++;
	}
	
	// Einige getter/setter rausgelassen, da jeglicher Sinn nicht vorhanden ist
	
	public String getEmail() {
		return emailAdresse;
	}
	
	public int getID() {
		return id;
	}
	
	public abstract String getName();
	
	public void addFreund(BenutzerkontoOriginal fr) {
		if(!freunde.contains(fr)) {
			freunde.add(fr);
		    fr.addFreund(this);
		}
	}
	
	public void delFreund(BenutzerkontoOriginal fr) {
		if(freunde.contains(fr)) {
			freunde.remove(fr);
			fr.delFreund(this);
		}
	}
	
	public LinkedList<BenutzerkontoOriginal> getFreunde() {
		return freunde;
	}
	
	
}