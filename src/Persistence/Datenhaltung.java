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
			if(em.getTransaction() != null && em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new PersistenzException(
					"Fehler bei der Synchronisation mit der Datenbank");
		}finally{
			em.close();
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
			if(em.getTransaction() != null && em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new PersistenzException(
					"Fehler bei der Synchronisation mit der Datenbank");
		}finally{
			em.close();
		}
	}

	/**
	 * Methode die anhand einer E-Mail Addresse ein Konto aus der Datenbank  liest
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
			throw new PersistenzException(
					"Fehler bei der Synchronisation mit der Datenbank");
		}
		return konto;
	}

	/**
	 * Methode die anhand eines Titels eine CS aus der DB liest
	 * Es wird hier ein JP Query verwendet um anhand der Mail eines
	 * Nutzers alle seine CodingSessions zu ermitteln
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
	 * Methode die Prueft, ob eine E-Mail Addresse schon vergeben ist
	 * 
	 * @param email
	 * @throws PersistenzException
	 * @throws EmailVorhandenException
	 */
	public static void mailVorhanden(String email) throws PersistenzException,
			EmailVorhandenException {
		try {
			if (em.find(BenutzerkontoOriginal.class, email) != null) {
				throw new EmailVorhandenException();
			}
		} catch (Exception e) {
			throw new PersistenzException(
					"Fehler bei der Synchronisation mit der Datenbank");
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
	public static void passwortRichtig(String email, String passwort)
			throws PersistenzException, FalschesPasswortException {
		try {
			if (leseDB(email).getPasswort() != passwort)
				throw new FalschesPasswortException();
		} catch (Exception e) {
			throw new PersistenzException();
		}
	}
}
