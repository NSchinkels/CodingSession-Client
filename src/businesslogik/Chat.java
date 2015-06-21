package businesslogik;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="Chat")  
public class Chat {
	//Mit Transient makierte Attribute werden nicht persistert
	@Id         
	private int id;
	@ElementCollection
	private List<String> verlauf;
	@Transient
	private static int size=0;
	private String sender;
	@Transient
	private KommunikationOutgoing como;
	@Transient
	private KommunikationIncoming comi;
	
	public Chat(){}
	
	public Chat(KommunikationOutgoing como,KommunikationIncoming comi,String sender,int id){
		this.id=id;
		this.verlauf=new LinkedList<String>();
		this.como=como;
		this.comi=comi;
		this.sender=sender;
		como.starteChat("Chat"+id);
		comi.bekommeChat(id, verlauf);
	}
	public void senden(String nachricht){
		como.veroeffentlicheChat(nachricht, sender);
	}
	public List<String> empfangen(){
		return this.verlauf;
	}
	public int getSize(){
		return this.size;
	}
}
