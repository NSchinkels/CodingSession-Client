package businesslogik;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Das Modell einer CodingSession. Sie beinhaltet den Code sowie andere
 * selbsterklärende Variablen
 * 
 *
 */
@Entity
@Table(name = "CodingSessionModell")
public class CodingSessionModell implements Serializable {

	private static final long serialVersionUID = 1L;
	// IDs
	private String benutzerMail;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	// Daten der CS
	private String titel;
	private boolean speichern;

	// Teilnehmer
	private int anzahlTeilnehmer = 0;

	// Geschriebener Code
	private String code;

	public CodingSessionModell() {
	}

	public CodingSessionModell(int id, String benutzerMail, String titel, boolean speichern, String code) {
		this.id = id;
		this.benutzerMail = benutzerMail;
		this.titel = titel;
		this.speichern = speichern;
		this.anzahlTeilnehmer = 1;
		this.code = code;
	}

	public String getBenutzerMail() {
		return benutzerMail;
	}

	public void setBenutzerId(String benutzerMail) {
		this.benutzerMail = benutzerMail;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public boolean isSpeichern() {
		return speichern;
	}

	public void setSpeichern(boolean speichern) {
		this.speichern = speichern;
	}

	public int getAnzahlTeilnehmer() {
		return anzahlTeilnehmer;
	}

	public void setAnzahlTeilnehmer(int anzahlTeilnehmer) {
		this.anzahlTeilnehmer = anzahlTeilnehmer;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Die Methode toString wurde überschrieben um das Modell in einer List
	 * anzeigen zu lassen
	 */
	public String toString() {
		return "Titel : " + this.titel;
	}

}
