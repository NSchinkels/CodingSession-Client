package businesslogik;

import java.util.ArrayList;

public class BenutzerkontoGeschuetzt {

	/**
	 * Erstellt einen neuen Benutzer mit Nicknamen.
	 * Prueft, ob E-Mail oder Benutzername bereits vergeben sind, 
	 * und ob die Daten valide sind.
	 * 
	 * @param email - E-Mail des Benutzers
	 * @param pw - Password des Benutzers
	 * @param name - Name des Benutzers
	 * @param benutzerliste - Liste, in der die Benutzer abgespeichert werden
	 * @param id - ID des Benutzers
	 * @return Neuen Benutzer
	 * @throws Exception Falls die Daten nicht valide sind
	 */
	public BenutzerkontoOriginal erstelleNickKonto(String email, String pw,
			String name, ArrayList<BenutzerkontoOriginal> benutzerliste, int id) {
		try {
			for (BenutzerkontoOriginal b : benutzerliste) {
				if ((b.getEmail()).equals(email) || (b.getName()).equals(name)) {
					throw new Exception();
				}
			}
			if (ueberpruefeNick(email, pw, name) == true) {
				BenutzerkontoOriginal neuerBenutzer = new BenutzerkontoNickname(email, pw, name, id);
				benutzerliste.add(neuerBenutzer);
				return neuerBenutzer;
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("Benutzername/E-Mail bereits vergeben oder eingegebene Daten nicht valide!");
			return null;
		}
	}

	
	
	
	/**
	 * Erstellt einen neuen Benutzer mit Vornamen und Nachnamen.
	 * Prueft, ob E-Mail oder Benutzername bereits vergeben sind, 
	 * und ob die Daten valide sind.
	 * 
	 * @param email - E-Mail des Benutzers 
	 * @param pw - Passwort des Benutzers
	 * @param vor - Vorname des Benutzers
	 * @param nach - Nachname des Benutzers
	 * @param benutzerliste - Liste, in der die Benutzer abgespeichert werden
	 * @param id - ID des Benutzers
	 * @return Neuen Benutzer
	 * @throws Exception Falls die Daten nicht valide sind
	 */
	public BenutzerkontoOriginal erstelleRealKonto(String email, String pw, String vor, 
			String nach, ArrayList<BenutzerkontoOriginal> benutzerliste,int id) {
		try {
			for (BenutzerkontoOriginal b : benutzerliste) {
				if ((b.getEmail()).equals(email)) {
					throw new Exception();
				}
			}
			if (ueberpruefeReal(email, pw, vor, nach) == true) {
				BenutzerkontoOriginal neuerBenutzer = new BenutzerkontoRealname(email, pw, vor, nach,id);
				benutzerliste.add(neuerBenutzer);
				return neuerBenutzer;
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			System.out
					.println("E-Mail bereits vergeben oder eingegebene Daten nicht valide!");
			return null;
		}
	}

	//Genaue angaben, welche Eingaben erlaubt sind muessen noch diskutiert werden
	private boolean ueberpruefeNick(String email, String pw, String nick) {
		return true;
	}
	//Genau angaben, welche Eingaben erlaubt sind muessen noch diskutiert werden
	private boolean ueberpruefeReal(String email, String pw, String vor,
			String nach) {
		return true;
	}
}
