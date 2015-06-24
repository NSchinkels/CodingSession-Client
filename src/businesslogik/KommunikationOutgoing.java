package businesslogik;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

public class KommunikationOutgoing {

	KommunikationStart kommunikationStart;
	MessageProducer producerCode;
	MessageProducer producerChat;
	TextMessage textMessage;
	Session session;
	String benutzerId;

	public KommunikationOutgoing(String benutzerId,KommunikationStart kommunikationStart) {
		this.session=kommunikationStart.getSession();
		this.benutzerId=benutzerId;
		this.kommunikationStart=kommunikationStart;

	}
	public void starteCs(String topic){
		System.out.println(session.toString());
		try {
			kommunikationStart.setTopicCode(session.createTopic(topic));
			producerCode = session.createProducer(kommunikationStart.getTopicCode());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void starteChat(String topic){
		try {
			kommunikationStart.setTopicChat(session.createTopic(topic));
			producerChat = session.createProducer(kommunikationStart.getTopicChat());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	public void veroeffentlicheCode(String nachricht) {
		// code an topic geschrieben
		try {
			textMessage = session.createTextMessage(nachricht);
			textMessage.setStringProperty("sender",benutzerId);
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
	

	public void ladeEin(CodingSessionModell cs, String freundEmail) {
		try {
			ObjectMessage om = session.createObjectMessage(cs);
			om.setStringProperty("id", freundEmail);
			System.out.println("om erstellt");
			kommunikationStart.getProducerEinladung().send(om);
			System.out.println("Veröffentlicht");
		} catch (Exception e2) {
			e2.printStackTrace();
		}

	}
	
}
