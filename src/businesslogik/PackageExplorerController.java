package businesslogik;

import java.util.List;

import Persistence.Datenhaltung;
import Persistence.PersistenzException;

public class PackageExplorerController{
	// CodingSessions werden über die ID geholt
	private List<CodingSessionModell> inhalt;
	
	public PackageExplorerController(String benutzerEmail) throws PersistenzException{
		inhalt=Datenhaltung.leseCS(benutzerEmail);
		inhalt.add(0, new CodingSessionModell(0,"Beispielklasse","Beispiel",false,"Hier können sie Code eingeben"));
	}
	public void add(CodingSessionModell cs) {
		inhalt.add(cs);
	}
	public List<CodingSessionModell> get(){
		return inhalt;
	}
}