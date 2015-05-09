package Businesslogik;

import java.util.LinkedList;

public abstract class Benutzerkonto {

	// Wir haben im UML hier das PW als String, sollten wir schnellstmoeglich durch DB mit Verschluesselung etc
	// ersetzen, ich implementier es trotzdem mal stumpf mit dem Passwort
	
	
	private String emailAdresse;
	private String passwort;
	private static int id;
	private LinkedList<Benutzerkonto> freunde;
	
	public Benutzerkonto(String email, String pw) {
		this.emailAdresse = email;
		this.passwort = pw;
		id++;
	}
	
	// Einige getter/setter rausgelassen, da jeglicher Sinn nicht vorhanden ist
	
	public String getEmail() {
		return emailAdresse;
	}
	
	public abstract String getName();
	
	public void addFreund(Benutzerkonto fr) {
		if(!freunde.contains(fr))
			freunde.add(fr);
	}
	
	public void delFreund(Benutzerkonto fr) {
		if(freunde.contains(fr))
			freunde.remove(fr);
	}
	
	public LinkedList<Benutzerkonto> getFreunde() {
		return freunde;
	}
	
	
}