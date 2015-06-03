package businesslogik;

import java.util.LinkedList;

public class Chat {
	private LinkedList<String> verlauf;
	String sender;
	KommunikationOutgoing como;
	KommunikationIncoming comi;
	
	public Chat(KommunikationOutgoing como,KommunikationIncoming comi,String sender,int chatId){
		this.verlauf=new LinkedList<String>();
		this.como=como;
		this.comi=comi;
		this.sender=sender;
		como.starteChat("Chat"+chatId);
		comi.bekommeChat(chatId, verlauf);
	}
	public void senden(String nachricht){
		como.veröffentlicheChat(nachricht, sender);
	}
	public LinkedList<String> empfangen(){
		return this.verlauf;
	}
}
