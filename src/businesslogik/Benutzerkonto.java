package businesslogik;

import java.util.List;
/**
 * Klasse die als Proxy für das Proxy-Muster dienen soll, bitte immer die zum erstellen nutzen
 *
 */
public abstract class Benutzerkonto {
	public abstract String getEmail();

	public abstract int getID();

	public abstract String getPasswort();

	public abstract String getName();
	
	public abstract void setPasswort(String passwort);
	
	public abstract void setVorname(String vorname);
	
	public abstract void setNachname(String nachname);
	
	public abstract void setNickname(String nickname);

	public abstract void addFreund(String freundEmail);

	public abstract void delFreund(String freundEmail);

	public abstract List<String> getFreunde();

	public String toString(){
		return this.getName();
	}

	

}
