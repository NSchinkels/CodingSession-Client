package businesslogik;

import java.util.HashMap;

public class PackageExplorer {
	// CodingSessions werden über die ID geholt
	private HashMap<Integer, CodingSessionModell> inhalt;

	public void add(CodingSessionModell cs) {
		inhalt.put(cs.getId(),cs);
	}
	public CodingSessionModell get(int id){
		return inhalt.get(id);
	}
}