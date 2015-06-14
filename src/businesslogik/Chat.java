package businesslogik;

import java.util.LinkedList;

public class Chat {
	int id;
	private LinkedList<String> verlauf;
	String sender;
	KommunikationOutgoing como;
	KommunikationIncoming comi;
	
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
	public LinkedList<String> empfangen(){
		return this.verlauf;
	}
}
