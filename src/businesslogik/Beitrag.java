package businesslogik;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="Beitrag")
public class Beitrag implements Serializable{
	@ManyToOne(targetEntity=CodingSessionModell.class,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "benutzerMail")
	private CodingSessionModell session;
	private String betreff;
	private String beschreibung;
	private boolean schreibschutz;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	long date;
	
	public Beitrag(){}
	
	public Beitrag(CodingSessionModell session, String betreff, String beschreibung,
			boolean schreibschutz) {
		
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
	public String toString(){
		return betreff+"\n"+beschreibung;
	}
}
