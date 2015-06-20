package businesslogik;

import java.util.LinkedList;

import Persistence.*;

public class BenutzerkontoGeschuetzt extends Benutzerkonto {
	private BenutzerkontoOriginal echtesKonto;
	private String email;
	private String pw;
	private String name;
	//ID hier noch notwedig? Evntl. entfernen?
	private int id;
	private String vor;
	private String nach;

	/**
	 * Erstellt einen neuen Benutzer mit Nicknamen. Prueft, ob E-Mail oder
	 * Benutzername bereits vergeben sind, und ob die Daten valide sind.
	 * 
	 * Eingebene Daten müssen weiterhin noch ueberprueft werden, werde bei
	 * gelegenheit das Passwort hashen(gez. BreakFree)
	 * 
	 * @param email
	 *            - E-Mail des Benutzers
	 * @param pw
	 *            - Password des Benutzers
	 * @param name
	 *            - Name des Benutzers
	 * @param benutzerliste
	 *            - Liste, in der die Benutzer abgespeichert werden
	 * @param id
	 *            - ID des Benutzers
	 * @return Neuen Benutzer
	 * @throws Exception
	 *             Falls die Daten nicht valide sind
	 */
	/**
	 * Konstruktor für Konto mit Nickname
	 * @param email
	 * @param pw
	 * @param name
	 * @param id
	 */
	public BenutzerkontoGeschuetzt(String email,String pw,String name,int id){
		this.email = email;
		this.pw=pw;
		this.name = name;
		this.id = id;
		try {
			Datenhaltung.mailVorhanden(email);
			echtesKonto = new BenutzerkontoNickname(email, pw,
					name, id);
			Datenhaltung.schreibeDB(echtesKonto);
		} catch (EmailVorhandenException ev) {
			// Was sinvolles machen
		} catch (PersistenzException pe) {
			// Was sinvolles machen
		}
	}
	
	public BenutzerkontoGeschuetzt(String email,String pw,String vor,String nach,int id){
		this.email = email;
		this.pw=pw;
		this.vor = vor;
		this.nach = nach;
		this.id = id;
		try {
			Datenhaltung.mailVorhanden(email);
			BenutzerkontoOriginal konto = new BenutzerkontoRealname(email, pw,
					vor,nach, id);
			Datenhaltung.schreibeDB(echtesKonto);
		} catch (EmailVorhandenException eve) {
			// Was sinvolles machen
		} catch (PersistenzException pe) {
			// Was sinvolles machen
		}
	}
	
	public String getEmail(){
		return echtesKonto.getEmail();
	}
	
	public int getID(){
		return echtesKonto.getID();
	}
	
	public String getPasswort(){
		return echtesKonto.getPasswort();
	}
	
	public String getName(){
		return echtesKonto.getName();
	}
	
	public void addFreund(BenutzerkontoOriginal fr){
		echtesKonto.addFreund(fr);
	}
	
	public void delFreund(BenutzerkontoOriginal fr){
		echtesKonto.delFreund(fr);
	}
	
	public LinkedList<BenutzerkontoOriginal> getFreunde(){
		return echtesKonto.getFreunde();
	}

	// Genaue angaben, welche Eingaben erlaubt sind muessen noch diskutiert
	// werden
	private boolean ueberpruefeNick(String email, String pw, String nick) {
		return true;
	}

	// Genau angaben, welche Eingaben erlaubt sind muessen noch diskutiert
	// werden
	private boolean ueberpruefeReal(String email, String pw, String vor,
			String nach) {
		return true;
	}


}
