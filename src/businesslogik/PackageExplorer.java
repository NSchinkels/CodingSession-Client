package businesslogik;

import java.util.List;

import Persistence.Datenhaltung;
import Persistence.PersistenzException;

public class PackageExplorer {
	// CodingSessions werden über die ID geholt
	private List<CodingSessionModell> inhalt;
	
	public PackageExplorer(String id) throws PersistenzException{
		inhalt=Datenhaltung.leseCS(id);
	}
	public void add(CodingSessionModell cs) {
		inhalt.add(cs);
	}
	public CodingSessionModell get(int id){
		return inhalt.get(id);
	}
}