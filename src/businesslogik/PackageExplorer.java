package businesslogik;

import java.util.HashMap;

public class PackageExplorer {
	// CodingSessions werden über die ID geholt
	private HashMap<Integer, CodingSession> inhalt;

	public void add(CodingSession cs) {
		inhalt.put(cs.getId(),cs);
	}
	public CodingSession get(int id){
		return inhalt.get(id);
	}
}