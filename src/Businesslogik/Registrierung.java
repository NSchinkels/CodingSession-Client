package Businesslogik;

import java.util.ArrayList;

public class Registrierung {
	
	/**
	 * Der Code den Philipp geschrieben hat, wurde komplett in die Proxy-Klasse übertragen,
	 * dort findet die Überprüfung statt, und es wird dann die jeweilige Kontoklasse erstellt
	 */

	private BenutzerkontoGeschuetzt bg = new BenutzerkontoGeschuetzt();
	private ArrayList<BenutzerkontoOriginal> benutzerliste = new ArrayList<BenutzerkontoOriginal>();

	// Eigentlich mehrere eigene Exceptions benutzen!

	// Prüfung inerhalb der Proxy-Klasse
	private BenutzerkontoOriginal erstelleNick(String email, String pw,
			String name) {
		return bg.erstelleNickKonto(email, pw, name, this.benutzerliste);
	}

	// Prüfungen finden in Proxy-Klasse statt
	private BenutzerkontoOriginal erstelleReal(String email, String pw,
			String vor, String nach) {
		return bg.erstelleRealKonto(email, pw, vor, nach, benutzerliste);
	}

}
