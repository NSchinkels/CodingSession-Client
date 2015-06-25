package Persistence;

import javax.persistence.*;

import businesslogik.*;
import java.util.List;

public class Datenhaltung {
	private static final String PERSISTENCE_UNIT_NAME = "Benutzerkonten";
	private static EntityManagerFactory factory = Persistence
			.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	private static EntityManager em = factory.createEntityManager();

	/**
	 * Methode die ein Benzuterkonto in der Datenbank speichert
	 * 
	 * @param bk
	 * @throws PersistenzException
	 */
	public static void schreibeDB(BenutzerkontoOriginal konto)
			throws PersistenzException {
		try {
			em.getTransaction().begin();
			em.persist(konto);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction() != null && em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new PersistenzException(
					"Fehler bei der Synchronisation mit der Datenbank");
		} finally {
			em.flush();
		}
	}

	/**
	 * Methode die eine CodingSession in der Datenbank speichert
	 * 
	 * @param cs
	 * @throws PersistenzException
	 */
	public static void schreibeCS(CodingSessionModell cs)
			throws PersistenzException {
		try {
			em.getTransaction().begin();
			em.persist(cs);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction() != null && em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new PersistenzException(
					"Fehler bei der Synchronisation mit der Datenbank");
		} finally {
			em.flush();
		}
	}

	/**
	 * Methode die anhand einer E-Mail Addresse ein Konto aus der Datenbank
	 * liest
	 * 
	 * @param email
	 * @return BenutzerKontoOriginal
	 * @throws PersistenzException
	 */
	public static BenutzerkontoOriginal leseDB(String email)
			throws PersistenzException {
		BenutzerkontoOriginal konto = null;
		try {
			konto = em.find(BenutzerkontoOriginal.class, email);
		} catch (Exception e) {
			throw new PersistenzException();
		}
		return konto;
	}

	/**
	 * Methode die anhand eines Titels eine CS aus der DB liest Es wird hier ein
	 * JP Query verwendet um anhand der Mail eines Nutzers alle seine
	 * CodingSessions zu ermitteln
	 * 
	 * @param titel
	 * @return
	 * @throws PersistenzException
	 */
	public static List<CodingSessionModell> leseCS(String email)
			throws PersistenzException {
		List<CodingSessionModell> list = null;
		try {
			Query q = em
					.createQuery(
							"Select c from CodingSessionModell c where c.benutzerMail = :cmail",
							CodingSessionModell.class);
			q.setParameter("cmail", email);
			list = q.getResultList();
		} catch (Exception e) {
			throw new PersistenzException();
		}
		return list;
	}

	/**
	 * Methode die eine Chat-Instanz in der Datenbank speichert
	 * 
	 * @param ch
	 * @throws PersistenzException
	 */
	public static void schreibeChat(Chat ch) throws PersistenzException {
		try {
			em.getTransaction().begin();
			em.persist(ch);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction() != null && em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new PersistenzException(
					"Fehler bei der Synchronisation mit der Datenbank");
		} finally {
			em.flush();
		}
	}

	/**
	 * Methode die anhand eines Titels eine Chat-Instanz aus der DB liest Es
	 * wird hier ein JP Query verwendet um anhand der Mail eines Nutzers alle
	 * seine Chats zu ermitteln
	 * 
	 * @param sender
	 * @return
	 * @throws PersistenzException
	 */
	// Hab hier nicht richtig verstanden, nach welchen Parameter du suchen
	// musst, evntl. noch bescheid geben gez. Break Free
	public static Chat leseChat(int id) throws PersistenzException {
		Chat chat = null;
		try {
			chat=em.find(Chat.class, id);
		} catch (Exception e) {
			throw new PersistenzException();
		}
		return chat;

	}

	/**
	 * Methode die einen CommunityFeedController in die DB schreibt, Beitraege
	 * sollten mit Persistiert werden
	 * 
	 * @param CF
	 * @throws PersistenzException
	 */

	public static void schreibeCF(CommunityFeedController CF)
			throws PersistenzException {
		try {
			em.getTransaction().begin();
			em.persist(CF);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction() != null && em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new PersistenzException(
					"Fehler bei der Synchronisation mit der Datenbank");
		} finally {
			em.flush();
		}
	}

	/**
	 * Methode die CFContriller (inkl. Beitraege aus der DB liest)
	 * 
	 * @return
	 * @throws PersistenzException
	 */
	public static CommunityFeedController leseCF() throws PersistenzException {
		CommunityFeedController CF = null;
		try {
			CF = em.find(CommunityFeedController.class, "1");
		} catch (Exception e) {
			throw new PersistenzException(
					"Fehler bei der Synchronisation mit der Datenbank");
		}
		return CF;
	}

	/**
	 * Schreibt Profil in DB
	 * 
	 * @param PM
	 * @throws PersistenzException
	 */
	public static void schreibeProfil(ProfilModell PM)
			throws PersistenzException {
		try {
			em.getTransaction().begin();
			em.persist(PM);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction() != null && em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new PersistenzException(
					"Fehler bei der Synchronisation mit der Datenbank");
		} finally {
			em.flush();
		}
	}

	/**
	 * Liest Profil anhand der Mail aus DB
	 * 
	 * @param email
	 * @return
	 * @throws PersistenzException
	 */
	public static ProfilModell leseProfil(String email)
			throws PersistenzException {
		ProfilModell PM = null;
		try {
			PM = em.find(ProfilModell.class, email);
		} catch (Exception e) {
			throw new PersistenzException(
					"Fehler bei der Synchronisation mit der Datenbank");
		}
		return PM;
	}
	
	public static void updateProfil(ProfilModell PM) throws PersistenzException{
		try {
			em.getTransaction().begin();
			em.merge(PM);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction() != null && em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new PersistenzException(
					"Fehler bei der Synchronisation mit der Datenbank");
		} finally {
			em.flush();
		}
	}
	/**
	 * Methode, die beim Beenden der Anwendung ausgefuehrt werden sollte
	 */
	public static void closeEM(){
		em.close();
	}

	/**
	 * Methode die Prueft, ob eine E-Mail Addresse schon vergeben ist
	 * 
	 * @param email
	 * @throws PersistenzException
	 * @throws EmailVorhandenException
	 */
	public static void mailVorhanden(String email)
			throws EmailVorhandenException {

		if (em.find(BenutzerkontoOriginal.class, email) != null) {
			throw new EmailVorhandenException();
		}

	}

	/**
	 * Methode die ein gegebenes Credential prueft
	 * 
	 * @param email
	 * @param passwort
	 * @throws PersistenzException
	 * @throws FalschesPasswortException
	 */
	public static boolean passwortRichtig(String email, String passwort)
			throws PersistenzException {
		try {
			if (leseDB(email).getPasswort().equals(passwort)) {
				return true;
			} else {
				return false;
			}
		} catch (PersistenzException exception) {
			throw exception;
		}
	}
}
