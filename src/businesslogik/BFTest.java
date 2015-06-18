package businesslogik;

import javax.persistence.*;
import Persistence.*;

import java.util.List;

public class BFTest {
	public static final String PERSISTENCE_UNIT_NAME = "Benutzerkonten";
	public static EntityManagerFactory factory;

	public static void main(String args[]) {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		List<CodingSessionModell> list;
		em.getTransaction().begin();
		// BenutzerkontoNickname konto1 = new
		// BenutzerkontoNickname("unknown@me.org","passwortsicherheit","Mandant900");
		/**
		 * BenutzerkontoNickname konto2 = new
		 * BenutzerkontoNickname("unknown1@me.org"
		 * ,"passwortsicherheit","CoolerTyp",2); BenutzerkontoNickname konto3 =
		 * new BenutzerkontoNickname("unknown2@me.org","passwortsicherheit",
		 * "RichtigCoolerTyp",3); BenutzerkontoNickname konto4 = new
		 * BenutzerkontoNickname
		 * ("unknown3@me.org","passwortsicherheit","KrasserTyp",4);
		 * em.persist(konto2); em.persist(konto3); em.persist(konto4);
		 * 
		 * CodingSessionModell cs1 = new CodingSessionModell(0,
		 * "unknown1@me.org", "cs1", true,null); CodingSessionModell cs2 = new
		 * CodingSessionModell(0, "unknown1@me.org", "cs2", true,null);
		 * em.persist(cs1); em.persist(cs2); em.getTransaction().commit();
		 **/

		/**
		 * BenutzerkontoOriginal konto =
		 * em.find(BenutzerkontoNickname.class,"unknown1@me.org" );
		 * System.out.println(konto.getName());
		 **/
		try {
			list = Datenhaltung.leseCS("unknown1@me.org");
			for(CodingSessionModell cs:list){
				System.out.println(cs.getTitel());
			}
		} catch (Exception e) {
		}
		em.close();
	}
}
