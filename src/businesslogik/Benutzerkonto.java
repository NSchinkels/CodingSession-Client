package businesslogik;

import java.util.LinkedList;
/**
 * Klasse die als Proxy für das Proxy-Muster dienen soll 
 * @author Break Free
 *
 */
public abstract class Benutzerkonto {
	public abstract String getEmail();

	public abstract int getID();

	public abstract String getPasswort();

	public abstract String getName();

	public abstract void addFreund(BenutzerkontoOriginal fr);

	public abstract void delFreund(BenutzerkontoOriginal fr);

	public abstract LinkedList<BenutzerkontoOriginal> getFreunde();

	

}
