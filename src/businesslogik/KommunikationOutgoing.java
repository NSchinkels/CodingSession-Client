package businesslogik;

import java.util.HashMap;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

public class KommunikationOutgoing {

	KommunikationStart komser;
	MessageProducer producerCode;
	MessageProducer producerChat;
	TextMessage textMessage;
	Session session;
	int benutzerId;

	public KommunikationOutgoing(int benutzerId,KommunikationStart komser) {
		this.session=komser.getSession();
		this.benutzerId=benutzerId;
		this.komser=komser;

	}
	public void starteCs(String topic){
		System.out.println(session.toString());
		try {
			komser.setTopicCode(session.createTopic(topic));
			producerCode = session.createProducer(komser.getTopicCode());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void starteChat(String topic){
		try {
			komser.setTopicChat(session.createTopic(topic));
			producerChat = session.createProducer(komser.getTopicChat());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	public void veroeffentlicheCode(String nachricht) {
		// code an topic geschrieben
		try {
			textMessage = session.createTextMessage(nachricht);
			textMessage.setIntProperty("sender",benutzerId);
			producerCode.send(textMessage);
			System.out.println("Code veröffentlicht von "+benutzerId);
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}
	
	public void veroeffentlicheChat(String nachricht, String sender) {
		// code an topic geschrieben
		try {
			textMessage = session.createTextMessage(nachricht);
			textMessage.setStringProperty("sender",sender);
			producerChat.send(textMessage);
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}
	

	public void ladeEin(HashMap<String, String> cs, int freundId) {
		try {
			ObjectMessage om = session.createObjectMessage(cs);
			om.setIntProperty("id", freundId);
			System.out.println("om erstellt");
			komser.getProducerEinladung().send(om);
			System.out.println("Veröffentlicht");
		} catch (Exception e2) {
			e2.printStackTrace();
		}

	}
	
}
