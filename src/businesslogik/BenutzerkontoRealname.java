package businesslogik;

import javax.persistence.*;


import java.io.*;

@Entity
public class BenutzerkontoRealname extends BenutzerkontoOriginal{
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String vorname;
	private String nachname;
	
	public BenutzerkontoRealname(String email, String pw, String vor, String nach,int id) {
		super(email,pw,id);
		this.vorname = vor;
		this.nachname = nach;
	}
	//default Konstruktor hinzugefügt,da für JPA notwendig
	public BenutzerkontoRealname(){}
	
	public String getName() {
		return nachname + ", " + vorname;
	}
	
	public void setName(String vor, String nach) {
		this.vorname = vor;
		this.nachname = nach;
	}
	
}
