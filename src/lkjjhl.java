

import Persistence.Datenhaltung;
import Persistence.PersistenzException;
import businesslogik.Beitrag;
import businesslogik.CodingSessionModell;
import businesslogik.CommunityFeedController;

public class lkjjhl {
	public static void main(String[] args){
		try {
			CommunityFeedController cf=new CommunityFeedController();
			cf.initialize(null, null);
			//cf.addBeitrag(new Beitrag(new CodingSessionModell(213,"huhu","dufhd", false,"iji"),"Oh gott","Hier kaputt",true));
			//Datenhaltung.schreibeCF(cf);
			Datenhaltung.schreibeCF(cf);
			System.out.println(Datenhaltung.leseCF().getBeitrag().get(0));
		} catch (PersistenzException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
