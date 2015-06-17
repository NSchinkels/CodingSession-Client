package Persistence;

import javax.persistence.*;

import businesslogik.BenutzerkontoOriginal;

public class DatenhaltungKonto {
	private final String PERSISTENCE_UNIT_NAME = "Benutzerkonten";
	private EntityManagerFactory factory = Persistence
			.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	EntityManager em = factory.createEntityManager();
	/**
	 * Methode die ein Benzuterkonto in der Datenbank speichert
	 * @param bk
	 * @throws PersistenzException
	 */
	public void schreibeDB(BenutzerkontoOriginal konto) throws PersistenzException {
		try {
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
			konto = em.find(BenutzerkontoOriginal.class,email);
		} catch (Exception e) {
			throw new PersistenzException(
					"Fehler bei der Synchronisation mit der Datenbank");
		}
		return konto;
	}
	/**
	 * Methode die Prueft, ob eine E-Mail Addresse schon vergeben ist
	 * @param email
	 * @throws PersistenzException
	 * @throws EmailVorhandenException
	 */
	public void mailVorhanden(String email) throws PersistenzException,EmailVorhandenException{
		try {
			if(em.find(BenutzerkontoOriginal.class,email)!=null){
				throw new EmailVorhandenException();
			}
		} catch (Exception e) {
			throw new PersistenzException(
					"Fehler bei der Synchronisation mit der Datenbank");
		}
	}
	/**
	 * Methode die ein gegebenes Credential prueft
	 * @param email
	 * @param passwort
	 * @throws PersistenzException
	 * @throws FalschesPasswortException
	 * 
	 */
	public void passwortRichtig(String email,String passwort) throws PersistenzException,FalschesPasswortException{
		try{
			if(this.leseDB(email).getPasswort() != passwort)
				throw new FalschesPasswortException();
		}catch(Exception e){
			throw new PersistenzException();
		}
	}
}
