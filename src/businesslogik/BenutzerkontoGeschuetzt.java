package businesslogik;

import java.util.ArrayList;

public class BenutzerkontoGeschuetzt {

	// Erstellt einen neuen Benutzer mit einem Nicknamen
	// Pr�fung, ob E-Mail oder Benutzername bereits vergeben sind, und ob die
	// Daten valide sind.
	public BenutzerkontoOriginal erstelleNickKonto(String email, String pw,
			String name, ArrayList<BenutzerkontoOriginal> benutzerliste) {
		try {
			for (BenutzerkontoOriginal b : benutzerliste) {
				if ((b.getEmail()).equals(email) || (b.getName()).equals(name)) {
					throw new Exception();
				}
			}
			if (ueberpruefeNick(email, pw, name) == true) {
				BenutzerkontoOriginal neuerBenutzer = new BenutzerkontoNickname(
						email, pw, name);
				benutzerliste.add(neuerBenutzer);
				return neuerBenutzer;
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			System.out
					.println("Benutzername/E-Mail bereits vergeben oder eingegebene Daten nicht valide!");
			return null;
		}
	}

	// Erstellt einen neuen Benutzer mit Vornamen und Nachnamen
	// Pr�fung, ob E-Mail oder Benutzername bereits vergeben sind, und ob die
	// Daten valide sind.
	// Keine Pr�fung des Namens erforderlich, da der gleiche Name mehrmals
	// vorkommen kann.
	public BenutzerkontoOriginal erstelleRealKonto(String email, String pw,
			String vor, String nach,
			ArrayList<BenutzerkontoOriginal> benutzerliste) {
		try {
			for (BenutzerkontoOriginal b : benutzerliste) {
				if ((b.getEmail()).equals(email)) {
					throw new Exception();
				}
			}
			if (ueberpruefeReal(email, pw, vor, nach) == true) {
				BenutzerkontoOriginal neuerBenutzer = new BenutzerkontoRealname(
						email, pw, vor, nach);
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

	//Genaue angaben, welche Eingaben erlaubt sind müssen noch Diskutiert werden
	private boolean ueberpruefeNick(String email, String pw, String nick) {
		return true;
	}
	//Genau angaben, welche Eingaben erlaubt sind müssen noch diskutiert werden
	private boolean ueberpruefeReal(String email, String pw, String vor,
			String nach) {
		return true;
	}
}
