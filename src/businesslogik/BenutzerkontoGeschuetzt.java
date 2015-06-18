package businesslogik;

import java.util.LinkedList;
import Persistence.*;

public class BenutzerkontoGeschuetzt {

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
	public BenutzerkontoOriginal erstelleNickKonto(String email, String pw,
			String name, int id) {
		try {
			Datenhaltung.mailVorhanden(email);
			BenutzerkontoOriginal konto = new BenutzerkontoNickname(email, pw,
					name, id);
			Datenhaltung.schreibeDB(konto);
			return konto;
		} catch (EmailVorhandenException ev) {
			// Was sinvolles machen
			return null;
		} catch (PersistenzException pe) {
			// Was sinvolles machen
			return null;
		}
	}

	/**
	 * Erstellt einen neuen Benutzer mit Vornamen und Nachnamen. Prueft, ob
	 * E-Mail oder Benutzername bereits vergeben sind, und ob die Daten valide
	 * sind.
	 * 
	 * @param email
	 *            - E-Mail des Benutzers
	 * @param pw
	 *            - Passwort des Benutzers
	 * @param vor
	 *            - Vorname des Benutzers
	 * @param nach
	 *            - Nachname des Benutzers
	 * @param benutzerliste
	 *            - Liste, in der die Benutzer abgespeichert werden
	 * @param id
	 *            - ID des Benutzers
	 * @return Neuen Benutzer
	 * @throws Exception
	 *             Falls die Daten nicht valide sind
	 */
	public BenutzerkontoOriginal erstelleRealKonto(String email, String pw,
			String vor, String nach, int id) {
		try {
			Datenhaltung.mailVorhanden(email);
			BenutzerkontoOriginal konto = new BenutzerkontoRealname(email, pw,
					vor,nach, id);
			Datenhaltung.schreibeDB(konto);
			return konto;
		} catch (EmailVorhandenException eve) {
			// Was sinvolles machen
			return null;
		} catch (PersistenzException pe) {
			// Was sinvolles machen
			return null;
		}
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
