package businesslogik;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

import Persistence.Datenhaltung;
import Persistence.PersistenzException;

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

	public Chat(KommunikationOutgoing kommunikationOut,
			KommunikationIncoming kommunikationIn, String sender, int id) {

		this.id = id;
		this.verlauf = new LinkedList<String>();
		this.kommunikationOut = kommunikationOut;
		this.kommunikationIn = kommunikationIn;
		this.sender = sender;
		try {
			for (Chat chatDb : Datenhaltung.leseChat(sender)) {
				if(chatDb.getId()==id){
					this.verlauf=chatDb.verlauf;
				}
			}
		} catch (Exception e) {

		}
		kommunikationOut.starteChat("Chat" + id);
		kommunikationIn.bekommeChat(id, verlauf);
	}

	public void senden(String nachricht) {
		kommunikationOut.veroeffentlicheChat(nachricht, sender);
	}

	public List<String> empfangen() {
		return this.verlauf;
	}
	
	public void speichern(){
			Datenhaltung.upateChat(this);
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
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getId() {
		return this.id;
	}

}
