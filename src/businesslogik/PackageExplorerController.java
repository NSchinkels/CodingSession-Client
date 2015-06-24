package businesslogik;

import java.util.LinkedList;
import java.util.List;

import Persistence.Datenhaltung;
import Persistence.PersistenzException;

public class PackageExplorerController{
	// CodingSessions werden �ber die ID geholt
	private List<CodingSessionModell> inhalt;
	
	public PackageExplorerController(String benutzerEmail) throws PersistenzException{
		inhalt=Datenhaltung.leseCS(benutzerEmail);
	}
	public void add(CodingSessionModell cs) {
		inhalt.add(cs);
	}
	public LinkedList<CodingSessionModell> get(){
		return (LinkedList<CodingSessionModell>)inhalt;
	}
}