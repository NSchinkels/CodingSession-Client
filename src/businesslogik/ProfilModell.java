package businesslogik;
import java.io.Serializable;

import javax.persistence.*;


@Entity
@Table(name="ProfilModell")
public class ProfilModell implements Serializable{
	@Id
	private String benutzerMail;
	private String Programmierkenntnisse;
	//Wird bei Zeiten besser gemacht
	private int DOB;
	private String Wohnort;
	private String aktuellerJob;
	
	public ProfilModell(){}
	
	public ProfilModell(String benutzerMail){
		this.benutzerMail = benutzerMail;
	}
	
	public void setProgrammierkenntnise(String PK){
		this.Programmierkenntnisse = PK;
	}
	
	public String getProgrammierkenntnise(){
		return this.Programmierkenntnisse;
	}
	
	public void setDOB(int DOB ){
		this.DOB = DOB;
	}
	
	public int getDOB(){
		return this.DOB;
	}
	
	public void setWohnort(String Wohnort){
		this.Wohnort = Wohnort;
	}
	
	public String getWohnort(){
		return this.Wohnort;
	}
	
	public void setAktuellerJob(String aktuellerJob){
		this.aktuellerJob = aktuellerJob;
	}
	
	public String getAktuellerJob(){
		return this.aktuellerJob;
	}
}
