package Persistence;

import javax.persistence.*;

import businesslogik.BenutzerkontoNickname;
import businesslogik.BenutzerkontoOriginal;

public class DatenhaltungKonto {
	private final String PERSISTENCE_UNIT_NAME = "Benutzerkonten";
	private EntityManagerFactory factory;
	/**
	 * Methode die ein Benzuterkonto in der Datenbank speichert
	 * @param bk
	 * @throws PersistenzException
	 */
	public void schreibeDB(BenutzerkontoOriginal konto) throws PersistenzException {
		try {
			factory = Persistence
					.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = factory.createEntityManager();
			em.getTransaction().begin();
			em.persist(konto);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new PersistenzException(
					"Fehler bei der Synchronisation mit der Datenbank");
		}
	}
	/**
	 * Methode die anhand einer E-Mail Addresse ein Konto aus der Datenbank liest
	 * @param email
	 * @return BenutzerKontoOriginal
	 * @throws PersistenzException
	 */
	public BenutzerkontoOriginal leseDB(String email) throws PersistenzException{
		BenutzerkontoOriginal konto = null;
		try {
			factory = Persistence
					.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = factory.createEntityManager();
			konto = em.find(BenutzerkontoNickname.class,email);
		} catch (Exception e) {
			throw new PersistenzException(
					"Fehler bei der Synchronisation mit der Datenbank");
		}
		return konto;
	}
}
