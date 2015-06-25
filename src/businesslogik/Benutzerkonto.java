package businesslogik;

import java.util.LinkedList;
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
	
	public abstract BenutzerkontoOriginal getBenutzerkontoOriginal();

	public abstract void addFreund(String freundEmail);

	public abstract void delFreund(String freundEmail);

	public abstract List<String> getFreunde();
	//zum testen
	public String toString(){
		return this.getName();
	}

	

}
