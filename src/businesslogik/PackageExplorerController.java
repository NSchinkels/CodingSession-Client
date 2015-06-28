package businesslogik;


import java.util.List;

import Persistence.Datenhaltung;
import Persistence.PersistenzException;

public class PackageExplorerController{
	/**
	 * Der Container in dem alle von dem Benutzer erstellten CodingSessions inhalten sind
	 * Sie werden ueber die Benutzeremail aus der Datenbank gelesen
	 */
	private List<CodingSessionModell> inhalt;
	
	public PackageExplorerController(String benutzerEmail) throws PersistenzException{
		inhalt=Datenhaltung.leseCS(benutzerEmail);
	}
	public void add(CodingSessionModell cs) {
		inhalt.add(cs);
	}
	public List<CodingSessionModell> get(){
		return inhalt;
	}
}