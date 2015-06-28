package businesslogik;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

import Persistence.Datenhaltung;
import Persistence.PersistenzException;

/**
 * 
 * Eine einfache Chat Klasse fuer den Austausch zwischen Benutzern. Benutzt fuer
 * den Austausch JMS Objekte
 *
 */
@Entity
@Table(name = "Chat")
public class Chat {
	// Mit Transient makierte Attribute werden nicht persistert
	@Id
	private int id;
	@ElementCollection
	private List<String> verlauf;
	@Transient
	private int size = 0;
	private String sender;
	@Transient
	private KommunikationOutgoing kommunikationOut;
	@Transient
	private KommunikationIncoming kommunikationIn;

	public Chat() {
	}

	public Chat(KommunikationOutgoing kommunikationOut, KommunikationIncoming kommunikationIn, String sender, int id) {
		this.id = id;
		this.verlauf = new LinkedList<String>();
		this.kommunikationOut = kommunikationOut;
		this.kommunikationIn = kommunikationIn;
		this.sender = sender;
	}

	/**
	 * Schreibt eine Nachricht an das Chat Topic
	 * 
	 * @param nachricht
	 *            Die zu schreibende Nachricht
	 */
	public void senden(String nachricht) {
		kommunikationOut.veroeffentlicheChat(nachricht, sender);
	}

	public void start() {
		// Startet das JMS Topic fure den Chat als Producer
		kommunikationOut.starteChat("Chat" + id);
		// Startet das JMS Topic als Subscriber
		kommunikationIn.bekommeChat(id, verlauf);
	}


	/**
	 * 
	 * @return Der komplette formatierte Chat wird returned
	 * 
	 */

	public String getChat() {
		String c = "";
		synchronized (verlauf) {
			for (String a : verlauf) {
				c += a;
			}
		}
		return c;
	}
	
	public void speichern() throws PersistenzException{
		synchronized(verlauf){
			Datenhaltung.schreibeChat(this);
		}
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public void setVerlauf(List<String> verlauf) {
		this.verlauf = verlauf;
	}

	public int getSize() {
		synchronized(verlauf){
		 return verlauf.size();
		}
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getId() {
		return this.id;
	}

	public List<String> getVerlauf() {
		return this.verlauf;
	}

	public void setKommunikationOut(KommunikationOutgoing kommunikationOut) {
		this.kommunikationOut = kommunikationOut;

	}

	public void setKommunikationIn(KommunikationIncoming kommunikationIn) {
		this.kommunikationIn = kommunikationIn;

	}
}
