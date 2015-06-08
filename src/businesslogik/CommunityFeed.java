package businesslogik;

public class CommunityFeed {
	private Beitrag[] beitraege;
	int anzahl;

	public CommunityFeed() {
		// hier werden noch die Sachen vom Server gezogen
	}

	public void addBeitrag(Beitrag beitrag) {
		//aktuelle Anzahl vom Server ziehen
		beitraege[anzahl++] = beitrag;
		//hier wird dann gespeichert
	}
	public void refresh(){
		//Neue Beitreage vom Server bekommen
	}
	public int getAnzahl(){
		//zuerst aktualiern vom server
		return anzahl;
	}
	public Beitrag[] getBeitraege() {
		return beitraege;
	}

}
