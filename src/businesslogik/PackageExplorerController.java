package businesslogik;

import java.util.LinkedList;
import java.util.List;

import Persistence.Datenhaltung;
import Persistence.PersistenzException;

public class PackageExplorerController{
	// CodingSessions werden über die ID geholt
	private List<CodingSessionModell> inhalt;
	
	public PackageExplorerController(String id) throws PersistenzException{
		//inhalt=Datenhaltung.leseCS(id);
		inhalt=new LinkedList<CodingSessionModell>();
		inhalt.add(new CodingSessionModell("Test titel","TestBenutzer",false,"Public void "));
		inhalt.add(new CodingSessionModell("Test titel nummer 2","TestBenutzer 34",false,"Public void whatever"));
		
	}
	public void add(CodingSessionModell cs) {
		inhalt.add(cs);
	}
	public LinkedList<CodingSessionModell> get(){
		return (LinkedList<CodingSessionModell>)inhalt;
	}
}