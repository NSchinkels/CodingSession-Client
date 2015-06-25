package businesslogik;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.io.*;

import javax.persistence.*;
@Entity
@Table(name="Benutzer_Herachie")
//Legt die Vererbungsstrategie als einzige Tabelle fest
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DISCRIMINATOR",discriminatorType=DiscriminatorType.STRING)
public abstract class BenutzerkontoOriginal extends Benutzerkonto implements Serializable {

	//Wir haben im UML hier das PW als String, sollten wir schnellstmoeglich durch DB mit Verschluesselung etc
	//ersetzen, ich implementier es trotzdem mal stumpf mit dem Passwort
	// Wir haben im UML hier das PW als String, sollten wir schnellstmoeglich durch DB mit Verschluesselung etc
	// ersetzen, ich implementier es trotzdem mal stumpf mit dem Passwort
	//Test

	
	@Id
	private String emailAdresse;
	private String passwort;
	//Achtung: Hier wurde testweise der statische Kontext entfernt
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ElementCollection
	private List<String> freund ;
	
	/**Konstruktor musste angepasst werden um erstmal das Problem mit der ID 
	 * zu umgehen; id wird jetzt zeitweise mit übergeben**/
	public BenutzerkontoOriginal(String email, String pw, int id) {
		this.emailAdresse = email;
		this.passwort = pw;
		this.id = id;
	}
	//default Konstruktor hinzugefügt,da für JPA notwendig
	public BenutzerkontoOriginal(){}
	
	// Einige getter/setter rausgelassen, da jeglicher Sinn fehlt
	
	public String getEmail() {
		return emailAdresse;
	}
	
	public int getID() {
		return id;
	}
	
	public String getPasswort(){
		return this.passwort;
	}
	
	public abstract String getName();
	
	public void setPasswort(String passwort){
		this.passwort = passwort;
	}
	
	public abstract void setVorname(String vorname);
	
	public abstract void setNachname(String nachname);
	
	public abstract void setNickname(String nickname);
	
	public BenutzerkontoOriginal getBenutzerkontoOriginal(){
		return this;
	}
	
	public void addFreund(String freundEmail) {
		if(!freund.contains(freundEmail)) {
			freund.add(freundEmail);
		    //fr.addFreund(this);
		}
	}
	
	public void delFreund(String freundEmail) {
		if(freund.contains(freundEmail)) {
			freund.remove(freundEmail);
			//fr.delFreund(this);
		}
	}
	
	public List<String> getFreunde() {
		return freund;
	}
}
