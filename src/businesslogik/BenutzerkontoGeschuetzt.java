package businesslogik;


import java.util.List;

import Persistence.*;

public class BenutzerkontoGeschuetzt extends Benutzerkonto {
	
	//Regulaere Ausdruecke fuer die Eingabevaldierung
	private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+"
										    + "(\\.[A-Za-z0-9]+)*(\\-[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String PASSWORT_REGEX = "^[a-zA-Z0-9!ï¿½$%&/()=?@#^+-_*~'\"\\s]{8,25}$";
	private static final String VORNAME_REGEX = "^[a-zA-Z]{3,20}$";
	private static final String NACHNAME_REGEX = "^[a-zA-Z]{3,20}$";
	private static final String NICKNAME_REGEX = "^[a-zA-Z][\\w_-]{3,25}$";
			
	private BenutzerkontoOriginal echtesKonto;
	//ID hier noch notwedig? Evntl. entfernen?
	@SuppressWarnings("unused")
	private String vorname;
	@SuppressWarnings("unused")
	private String nachname;
	@SuppressWarnings("unused")
	private String nickname;
	@SuppressWarnings("unused")
	private String email;
	@SuppressWarnings("unused")
	private String passwort;
	@SuppressWarnings("unused")
	private int id;

	/**
	 * Erstellt einen neuen Benutzer mit Nicknamen. Prueft, ob E-Mail oder
	 * Benutzername bereits vergeben sind, und ob die Daten valide sind.
	 * 
	 * Eingebene Daten muessen weiterhin noch ueberprueft werden, werde bei
	 * gelegenheit das Passwort hashen(gez. BreakFree)
	 * 
	 * @param email
	 *            - E-Mail des Benutzers
	 * @param pw
	 *            - Passwort des Benutzers
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
	
	//Nur vorruebergehend
	public BenutzerkontoGeschuetzt(){
		
	}
	
	public BenutzerkontoGeschuetzt(String email, String pw, String name, int id){
		this.email = email;
		this.passwort = pw;
		this.nickname = name;
		this.id = id;
		try {
			Datenhaltung.mailVorhanden(email);
			echtesKonto = new BenutzerkontoNickname(email, pw, name, id);
			Datenhaltung.schreibeDB(echtesKonto);
		} catch (EmailVorhandenException ev) {
			new CodingSessionDialog().erstelleFehlermeldungDialog("Ungültige E-Mail-Adresse", 
					"Die von dir eingegebene E-Mail-Adresse ist bereits vergeben!");
		} catch (PersistenzException pe) {
			new CodingSessionDialog().erstelleFehlermeldungDialog("Datenbank-Fehler", 
					"Ups, da stimmt wohl etwas mit der Datenbank nicht!");
		}
	}
	
	public BenutzerkontoGeschuetzt(String email, String pw, String vor, String nach, int id){
		this.email = email;
		this.passwort = pw;
		this.vorname = vor;
		this.nachname = nach;
		this.id = id;
		try {
			Datenhaltung.mailVorhanden(email);
			echtesKonto = new BenutzerkontoRealname(email, pw, vor, nach, id);
			Datenhaltung.schreibeDB(echtesKonto);
		} catch (EmailVorhandenException ev) {
			new CodingSessionDialog().erstelleFehlermeldungDialog("Ungültige E-Mail-Adresse", 
					"Die von dir eingegebene E-Mail-Adresse ist bereits vergeben!");
		} catch (PersistenzException pe) {
			new CodingSessionDialog().erstelleFehlermeldungDialog("Datenbank-Fehler", 
					"Ups, da stimmt wohl etwas mit der Datenbank nicht!");
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
	
	public void setPasswort(String passwort){
		this.passwort = passwort;
	}
	
	public void setVorname(String vorname){
		
	}
	
	public void setNachname(String nachname){
		
	}
	
	public void setNickname(String nickname){
		
	}
	
	public void addFreund(String freundEmail){
		echtesKonto.addFreund(freundEmail);
	}
	
	public void delFreund(String freundEmail){
		echtesKonto.delFreund(freundEmail);
	}
	
	public List<String> getFreunde(){
		return echtesKonto.getFreunde();
	}
	
	public static boolean ueberpruefeReal(String vorname, String nachname, String email, String passwort) {
		if(!vorname.matches(VORNAME_REGEX)) {
			new CodingSessionDialog().erstelleFehlermeldungDialog("Ungültiger Vorname", 
					"Bitte gebe einen gültigen Vornamen ein!");
			return false;
		} else if(!nachname.matches(NACHNAME_REGEX)) {
			new CodingSessionDialog().erstelleFehlermeldungDialog("Ungültiger Nachname", 
					"Bitte gebe einen gültigen Nachnamen ein!");
			return false;
		} else if(!email.matches(EMAIL_REGEX)) {
			new CodingSessionDialog().erstelleFehlermeldungDialog("Ungültige E-Mail-Adresse", 
					"Bitte gebe eine gültige E-Mail-Adresse ein!");
			return false;
		} else if(!passwort.matches(PASSWORT_REGEX)) {
			new CodingSessionDialog().erstelleFehlermeldungDialog("Ungültiges Passwort", 
					"Bitte gebe ein gültiges Passwort ein!");
			return false;
		} else {
			return true;
		}
	}

	public static boolean ueberpruefeNick(String nickname, String email, String passwort) {
		if(!nickname.matches(NICKNAME_REGEX)) {
			new CodingSessionDialog().erstelleFehlermeldungDialog("Ungültiger Nickname", 
					"Bitte gebe einen gültigen Nicknamen ein!");
			return false;
		} else if(!email.matches(EMAIL_REGEX)) {
			new CodingSessionDialog().erstelleFehlermeldungDialog("Ungültige E-Mail-Adresse", 
					"Bitte gebe eine gültige E-Mail-Adresse ein!");
			return false;
		} else if(!passwort.matches(PASSWORT_REGEX)) {
			new CodingSessionDialog().erstelleFehlermeldungDialog("Ungültiges Passwort", 
					"Bitte gebe ein gültiges Passwort ein!");
			return false;
		} else {
			return true;
		}
	}
	public BenutzerkontoOriginal getEchtesKonto(){
		return this.echtesKonto;
	}
}
