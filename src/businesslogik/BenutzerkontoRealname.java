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
		return vorname + " " + nachname;
	}
	
	@Override
	public void setVorname(String vorname) {
		this.vorname = vorname;
		
	}
	
	@Override
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	
	@Override
	public void setNickname(String nickname) {
	
	}	
}
