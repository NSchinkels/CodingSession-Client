
package businesslogik;

import java.io.Serializable;

import javax.persistence.*;

/**
 * 
 * Modell eines Beitrags fuer den CommunityFeed.Die meisten Variablen sind
 * selbsterklearend Der Beitrag hat ein CodingSessionModell was bei oeffnen des
 * Beitrags geladen wird. Die Klasse implentiert das Serializable Interface für
 * die Speicherung in der Datenbank
 *
 */
@Entity
@Table(name = "Beitrag")
public class Beitrag implements Serializable {
	@Transient
	private static final long serialVersionUID = 1L;
	@ManyToOne(targetEntity = CodingSessionModell.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "benutzerMail")
	private CodingSessionModell session;
	private String betreff;
	private String beschreibung;
	private boolean schreibschutz;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	long date;

	public Beitrag() {
	}

	public Beitrag(CodingSessionModell session, String betreff, String beschreibung, boolean schreibschutz) {

		this.session = session;
		this.betreff = betreff;
		this.beschreibung = beschreibung;
		this.schreibschutz = schreibschutz;
	}

	public CodingSessionModell getSession() {
		return session;
	}

	public void setSession(CodingSessionModell session) {
		this.session = session;
	}

	public String getBetreff() {
		return betreff;
	}

	public void setBetreff(String betreff) {
		this.betreff = betreff;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public boolean isSchreibschutz() {
		return schreibschutz;
	}

	public void setSchreibschutz(boolean schreibschutz) {
		this.schreibschutz = schreibschutz;
	}

	/**
	 * toString wird ueberschrieben um die Beitreage einfach in einer Liste
	 * anzeigen zu lassen
	 */
	public String toString() {
		return betreff + "\n" + beschreibung;
	}
}
