package businesslogik;

import java.util.LinkedList;
import java.util.List;

import Persistence.*;

public class BenutzerkontoGeschuetzt extends Benutzerkonto {
	
	//Regulaere Ausdruecke f�r die Eingabevaldierung
	private final String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+"
									+ "(\\.[A-Za-z0-9]+)*(\\-[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private final String passwortRegex = "^[a-zA-Z0-9!�$%&/()=?@#^+-_*~'\"\\s]{8,25}$";
	private final String vornameRegex = "^[a-zA-Z]{3,20}";
	private final String nachnameRegex = "^[a-zA-Z]{3,20}";
	private final String nicknameRegex = "^[a-zA-Z][\\w_-]{3,25}$";
			
	private BenutzerkontoOriginal echtesKonto;
	//ID hier noch notwedig? Evntl. entfernen?
	private String vorname;
	private String nachname;
	private String nickname;
	private String email;
	private String passwort;
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
			new CodingSessionDialog().erstelleEmailVorhandenDialog();
		} catch (PersistenzException pe) {
			// Was sinvolles machen
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
			new CodingSessionDialog().erstelleEmailVorhandenDialog();
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
	
	public BenutzerkontoOriginal getBenutzerkontoOriginal(){
		return echtesKonto;
	}
	
	public void addFreund(BenutzerkontoOriginal fr){
		echtesKonto.addFreund(fr);
	}
	
	public void delFreund(BenutzerkontoOriginal fr){
		echtesKonto.delFreund(fr);
	}
	
	public List<BenutzerkontoOriginal> getFreunde(){
		return echtesKonto.getFreunde();
	}
	
	public boolean ueberpruefeReal(String vorname, String nachname, String email, String passwort) {
		if(!vorname.matches(vornameRegex)) {
			new CodingSessionDialog().erstelleVornameValidierungDialog();
			return false;
		} else if(!nachname.matches(nachnameRegex)) {
			new CodingSessionDialog().erstelleNachnameValidierungDialog();
			return false;
		} else if(!email.matches(emailRegex)) {
			new CodingSessionDialog().erstelleEmailValidierungDialog();
			return false;
		} else if(!passwort.matches(passwortRegex)) {
			new CodingSessionDialog().erstellePasswortValidierungDialog();
			return false;
		} else {
			return true;
		}
	}

	public boolean ueberpruefeNick(String nickname, String email, String passwort) {
		if(!nickname.matches(nicknameRegex)) {
			new CodingSessionDialog().erstelleNicknameValidierungDialog();
			return false;
		} else if(!email.matches(emailRegex)) {
			new CodingSessionDialog().erstelleEmailValidierungDialog();
			return false;
		} else if(!passwort.matches(passwortRegex)) {
			new CodingSessionDialog().erstellePasswortValidierungDialog();
			return false;
		} else {
			return true;
		}
	}
}
