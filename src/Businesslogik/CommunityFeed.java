package Businesslogik;

public class CommunityFeed {
	private Beitrag[] Beitraege;
	
	public CommunityFeed(){
		//hier werden noch die Sachen vom Server gezogen
	}
	
	public Beitrag[] getBeitraege() {
		return Beitraege;
	}

	public void setBeitraege(Beitrag[] beitraege) {
		Beitraege = beitraege;
	}
	
}
