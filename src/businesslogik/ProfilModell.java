package businesslogik;
import java.io.Serializable;

import javax.persistence.*;


@Entity
@Table(name="ProfilModell")
public class ProfilModell implements Serializable{
	
	@Id
	private String benutzerMail;
	private String geschlecht;
	private String geburtsdatum;
	private String geburtsort;
	private String wohnort;
	private String aktuellerJob;
	private String programmierkenntnisse;
	
	public ProfilModell(){}
	
	public ProfilModell(String benutzerMail){
		this.benutzerMail = benutzerMail;
	}
	
	public String getGeschlecht() {
		return geschlecht;
	}

	public void setGeschlecht(String geschlecht) {
		this.geschlecht = geschlecht;
	}
	
	public String getGeburtsdatum(){
		return this.geburtsdatum;
	}
	
	public void setGeburtsdatum(String geburtsdatum){
		this.geburtsdatum = geburtsdatum;
	}
	
	public String getGeburtsort() {
		return geburtsort;
	}

	public void setGeburtsort(String geburtsort) {
		this.geburtsort = geburtsort;
	}
	
	public String getWohnort(){
		return this.wohnort;
	}

	public void setWohnort(String Wohnort){
		this.wohnort = Wohnort;
	}
	
	public String getAktuellerJob(){
		return this.aktuellerJob;
	}
	
	public void setAktuellerJob(String aktuellerJob){
		this.aktuellerJob = aktuellerJob;
	}
	
	public String getProgrammierkenntnisse(){
		return this.programmierkenntnisse;
	}
	
	public void setProgrammierkenntnisse(String programmierkenntnisse){
		this.programmierkenntnisse = programmierkenntnisse;
	}
}
