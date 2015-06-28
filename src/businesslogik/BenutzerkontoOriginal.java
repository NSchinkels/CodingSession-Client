
package businesslogik;

import java.util.List;
import java.io.*;

import javax.persistence.*;
@Entity
@Table(name="Benutzer_Herachie")
//Legt die Vererbungsstrategie als einzige Tabelle fest
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DISCRIMINATOR",discriminatorType=DiscriminatorType.STRING)
public abstract class BenutzerkontoOriginal extends Benutzerkonto implements Serializable {

	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = 1L;
	
	
	@Id
	private String emailAdresse;
	private String passwort;
	
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ElementCollection
	private List<String> freund ;
	
	//Default Konstruktor für JPA notwendig
	public BenutzerkontoOriginal(){}
	
	
	/**Konstruktor musste angepasst werden um erstmal das Problem mit der ID 
	 * zu umgehen; id wird jetzt zeitweise mit Ã¼bergeben**/
	public BenutzerkontoOriginal(String email, String pw, int id) {
		this.emailAdresse = email;
		this.passwort = pw;
		this.id = id;
	}
	
	@Override
	public String getEmail() {
		return emailAdresse;
	}
	
	@Override
	public int getID() {
		return id;
	}
	
	@Override
	public String getPasswort(){
		return this.passwort;
	}
	
	public abstract String getName();
	
	@Override
	public void setPasswort(String passwort){
		this.passwort = passwort;
	}
	
	public abstract void setVorname(String vorname);
	
	public abstract void setNachname(String nachname);
	
	public abstract void setNickname(String nickname);
	
	@Override
	public void addFreund(String freundEmail) {
		if(!freund.contains(freundEmail)) {
			freund.add(freundEmail);
		}
	}
	
	@Override
	public void delFreund(String freundEmail) {
		if(freund.contains(freundEmail)) {
			freund.remove(freundEmail);
		}
	}
	
	@Override
	public List<String> getFreunde() {
		return freund;
	}
}
