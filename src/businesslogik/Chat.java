package businesslogik;

import java.util.LinkedList;
import java.util.List;

public class Chat {
	private int id;
	private List<String> verlauf;
	private static int size=0;
	private String sender;
	private KommunikationOutgoing como;
	private KommunikationIncoming comi;
	
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
